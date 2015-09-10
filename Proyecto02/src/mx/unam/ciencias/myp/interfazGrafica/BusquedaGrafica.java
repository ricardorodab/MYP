/* -------------------------------------------------------------------
 * BuquedaGrafica.java
 * versión 1.0
 * Copyright (C) 2014  José Ricardo Rodríguez Abreu.
 * Facultad de Ciencias,
 * Universidad Nacional Autónoma de México, Mexico.
 *
 * Este programa es software libre; se puede redistribuir
 * y/o modificar en los términos establecidos por la
 * Licencia Pública General de GNU tal como fue publicada
 * por la Free Software Foundation en la versión 2 o
 * superior.
 *
 * Este programa es distribuido con la esperanza de que
 * resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho
 * sin la garantía implícita de COMERCIALIZACIÓN o
 * ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la
 * Licencia Pública General de GNU para mayores detalles.
 *
 * Con este programa se debe haber recibido una copia de la
 * Licencia Pública General de GNU, de no ser así, visite el
 * siguiente URL:
 * http://www.gnu.org/licenses/gpl.html
 * o escriba a la Free Software Foundation Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * -------------------------------------------------------------------
 */
package mx.unam.ciencias.myp.interfazGrafica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import jfx.messagebox.MessageBox;
import static mx.unam.ciencias.myp.interfazGrafica.ControladorGraficoController.*;
import mx.unam.ciencias.myp.tablas.*;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Esta clase realiza las búsquedas.</p>
 *
 * <p>
 * Clase que realiza búsquedas no triviales y de grupos.</p>
 */
public class BusquedaGrafica extends ControladorGraficoController {

    //Cada uno de los componentes necesarios para hacer las búsquedas.
    //Como ya no podemos acceder como estáticos ahora hay que pasarlos de parámetros.
    private TableView<ObservableList<String>> tablaSecundariaEstudiantes;
    private TableView<ObservableList<String>> tablaSecundariaProfesores;
    private TableView<ObservableList<String>> tablaSecundariaMaterias;
    private AnchorPane menuPrincipal;
    private AnchorPane menuEstudiante;
    private AnchorPane menuProfesor;
    private AnchorPane menuMateria;
    private AnchorPane menuConsultas;
    private ListView<String> listaGrupos;
    private Button eliminaGrupo;
    private ChoiceBox<String> cajaCarreras;

    /**
     * Constructor que necesitamos para optener los componentes.
     *
     * @param tablaSecundariaEstudiantes - tabla donde van los estudiantes.
     * @param tablaSecundariaProfesores -tabla donde van los profesores.
     * @param tablaSecundariaMaterias - tabla donde van las materias.
     * @param menuPrincipal - menu del cual partimos.
     * @param menuEstudiante - menu de tabla de estudiante.
     * @param menuProfesor - menu de la tabla del profesor.
     * @param menuMateria - menu de la tabla de materias.
     * @param menuConsultas - menu donde hacemos las búsquedas.
     * @param listaGrupos - la lista donde podemos ver los grupos que existen.
     * @param eliminaGrupo - boton para eliminar un grupo.
     * @param cajaCarreras - caja para ver alumnos de distintas carreras.
     */
    public BusquedaGrafica(
            TableView<ObservableList<String>> tablaSecundariaEstudiantes,
            TableView<ObservableList<String>> tablaSecundariaProfesores,
            TableView<ObservableList<String>> tablaSecundariaMaterias,
            AnchorPane menuPrincipal,
            AnchorPane menuEstudiante,
            AnchorPane menuProfesor,
            AnchorPane menuMateria,
            AnchorPane menuConsultas,
            ListView<String> listaGrupos,
            Button eliminaGrupo,
            ChoiceBox<String> cajaCarreras) {
        this.tablaSecundariaEstudiantes = tablaSecundariaEstudiantes;
        this.tablaSecundariaProfesores = tablaSecundariaProfesores;
        this.tablaSecundariaMaterias = tablaSecundariaMaterias;
        this.menuPrincipal = menuPrincipal;
        this.menuEstudiante = menuEstudiante;
        this.menuProfesor = menuProfesor;
        this.menuMateria = menuMateria;
        this.menuConsultas = menuConsultas;
        this.listaGrupos = listaGrupos;
        this.eliminaGrupo = eliminaGrupo;
        this.cajaCarreras = cajaCarreras;
    }

    /**
     * Muestra el menu secundario donde realizamos las búsquedas.
     *
     * @param e - Es la acción del botón que necesitamos.
     * @throws SQLException - en caso de problemas de conexión.
     */
    public final void muestraMenu(ActionEvent e) throws SQLException {
        //Cambio de menu.
        menuPrincipal.setDisable(true);
        menuPrincipal.setVisible(false);
        menuConsultas.setDisable(false);
        menuConsultas.setVisible(true);

        //Agregamos los elementos a las tablas.
        agregaTabla(tablaSecundariaEstudiantes, new Estudiante().getTabla());
        agregaTabla(tablaSecundariaMaterias, new Materia().getTabla());
        agregaTabla(tablaSecundariaProfesores, new Profesor().getTabla());

        //Agregamos los grupos a la lista.
        ResultSet resultado = new Grupo().getTabla();
        List<String> lista = new ArrayList<String>();
        while (resultado.next()) {
            int id = resultado.getInt(1);
            Grupo grupo = new Grupo(id);
            String materia = "(Sin Materia)";
            String profesor = "(Sin Profesor)";
            if (grupo.getProfesor().next()) {
                profesor = grupo.getProfesor().getString(3);
            }
            if (grupo.getMateria().next()) {
                materia = grupo.getMateria().getString(2);
            }
            lista.add(id + " - " + materia + " - " + profesor);
        }
        ObservableList<String> vistaLista = FXCollections.observableList(lista);
        listaGrupos.setTooltip(new Tooltip("Doble click al grupo para más detalles"));
        listaGrupos.setItems(vistaLista);
        //Activamos el botón de eliminar y podemos ver detalles del grupo.
        listaGrupos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Thread botonEliminar = new Thread(new Task() {
                    @Override
                    protected Object call() throws Exception {
                        while (listaGrupos.getSelectionModel().getSelectedItems() == null) {
                            eliminaGrupo.setDisable(true);
                        }
                        String[] grupo = listaGrupos.getSelectionModel()
                                .getSelectedItems().toString().split("-");
                        grupo[0] = grupo[0].substring(1).trim();
                        if (EsNumero.esNumero(grupo[0].substring(0, grupo[0].length()))) {
                            eliminaGrupo.setDisable(false);
                        }
                        return null;
                    }
                });
                botonEliminar.start();
                //Doble click información del grupo.
                if (t.getClickCount() >= 2) {
                    try {
                        String grupo = listaGrupos.getSelectionModel()
                                .getSelectedItems().toString();
                        String datosGrupo[] = grupo.split("-");
                        datosGrupo[0] = datosGrupo[0].trim();
                        dobleClickGrupo(Integer.parseInt(datosGrupo[0].substring(1, datosGrupo[0].length())));
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            }
        });
        //Llenamos las cajas de las carreras.
        cajaCarreras.setItems(FXCollections.observableArrayList(
                "Todas", "Actuaria", "Biologia", "C. Computacion",
                "C. Tierra", "Fisica", "Matematicas"));
        cajaCarreras.setValue("Todas");
        //En caso de seleccionar una modificamos la tabla estudiantes.
        cajaCarreras.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue ov, Number t, Number t1) {
                        try {
                            int seleccion = t1.intValue();
                            switch (seleccion) {
                                case 0:
                                    agregaTabla(tablaSecundariaEstudiantes,
                                            new Estudiante().getTabla());
                                    break;
                                case 1:
                                    agregaTabla(tablaSecundariaEstudiantes,
                                            new Estudiante().
                                            getEstudiantesCarrera(
                                                    Facultad.ACTUARIA));
                                    break;
                                case 2:
                                    agregaTabla(tablaSecundariaEstudiantes,
                                            new Estudiante().
                                            getEstudiantesCarrera(
                                                    Facultad.BIOLOGIA));
                                    break;
                                case 3:
                                    agregaTabla(tablaSecundariaEstudiantes,
                                            new Estudiante().
                                            getEstudiantesCarrera(
                                                    Facultad.CIENCIAS_DE_LA_COMPUTACION));
                                    break;
                                case 4:
                                    agregaTabla(tablaSecundariaEstudiantes,
                                            new Estudiante().
                                            getEstudiantesCarrera(
                                                    Facultad.CIENCIAS_DE_LA_TIERRA));
                                    break;
                                case 5:
                                    agregaTabla(tablaSecundariaEstudiantes,
                                            new Estudiante().
                                            getEstudiantesCarrera(
                                                    Facultad.FISICA));
                                    break;
                                case 6:
                                    agregaTabla(tablaSecundariaEstudiantes,
                                            new Estudiante().
                                            getEstudiantesCarrera(
                                                    Facultad.MATEMATICAS));
                                    break;
                            }
                        } catch (SQLException ex) {
                            Conexion.cierraCanal();
                            MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                    + "error inesperado. Por favor, vuelva a intentarlo.",
                                    "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                    }
                });
    }

    /**
     * Método para ver la información del grupo seleccionado.
     *
     * @param grupo - es el grupo que se ha seleccionado.
     * @throws SQLException - En caso de falla de conexión.
     */
    public void dobleClickGrupo(int grupo) throws SQLException {
        int respuesta = MessageBox.show(new Stage(),
                "¿Desea ver la información del grupo seleccionado?",
                "Ver Grupo", MessageBox.ICON_QUESTION | MessageBox.YES | MessageBox.NO);
        if (respuesta == MessageBox.YES) {
            agregaTabla(tablaSecundariaEstudiantes, new Grupo(grupo).getEstudiantes());
            agregaTabla(tablaSecundariaProfesores, new Grupo(grupo).getProfesor());
            agregaTabla(tablaSecundariaMaterias, new Grupo(grupo).getMateria());
        }
    }

    /**
     * Método que elimina un grupo de la base de datos.
     *
     * @throws SQLException - En caso de falla de conexión.
     */
    public void eliminarGrupo() throws SQLException {
        int respuesta = MessageBox.show(new Stage(),
                "¿Está seguro de querer borrar el grupo y borrar la relación de "
                + "todos los que se encuentran inscritos en él?",
                "Borrar grupo", MessageBox.ICON_WARNING | MessageBox.NO | MessageBox.YES);
        if (respuesta == MessageBox.YES) {
            eliminaGrupo.setDisable(true);
            Grupo grupo = new Grupo();
            String[] grupoString = listaGrupos.getSelectionModel()
                    .getSelectedItems().toString().split("-");
            grupoString[0] = grupoString[0].substring(1).trim();
            Integer grupoAEliminar = Integer.parseInt(grupoString[0]);
            grupo.setId(grupoAEliminar);
            grupo.elimina();
            ResultSet resultado = new Grupo().getTabla();
            List<String> lista = new ArrayList<String>();
            while (resultado.next()) {
                int id = resultado.getInt(1);
                Grupo grupoLista = new Grupo(id);
                String materia = "(Sin Materia)";
                String profesor = "(Sin Profesor)";
                if (grupoLista.getProfesor().next()) {
                    profesor = grupoLista.getProfesor().getString(3);
                }
                if (grupoLista.getMateria().next()) {
                    materia = grupoLista.getMateria().getString(2);
                }
                lista.add(id + " - " + materia + " - " + profesor);
            }
            ObservableList<String> vistaLista = FXCollections.observableList(lista);
            listaGrupos.setItems(vistaLista);
        }

    }

    /**
     * Método que lo único que hace es decirnos que carrera está seleccionada.
     *
     * @return - regresa un entero predeterminado que representa a la carrera.
     */
    public int carreraPuesta() {
        String carrera = cajaCarreras.getSelectionModel().getSelectedItem().toString();
        switch (carrera) {
            case "Actuaria":
                return Facultad.ACTUARIA;
            case "Biologia":
                return Facultad.BIOLOGIA;
            case "C. Computacion":
                return Facultad.CIENCIAS_DE_LA_COMPUTACION;
            case "C. Tierra":
                return Facultad.CIENCIAS_DE_LA_TIERRA;
            case "Fisica":
                return Facultad.FISICA;
            case "Matematicas":
                return Facultad.MATEMATICAS;
        }
        return 0;
    }

    /**
     * Método para realizar búsquedas (no triviales de estudiantes) en la base
     * de datos.
     */
    public void busquedasAvanzadas() {
        //Creamos un nuevo stage donde pediremos toda la información.
        menuConsultas.setDisable(true);
        final Stage avanzadas = new Stage();
        avanzadas.setTitle("Busquedas avanzadas");
        avanzadas.setMaxHeight(600);
        avanzadas.setMaxWidth(600);
        avanzadas.setMinHeight(600);
        avanzadas.setMinWidth(600);
        avanzadas.setResizable(true);
        avanzadas.getIcons().add(new Image("/resources/lupa.png"));
        avanzadas.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                menuConsultas.setDisable(false);
            }
        });

        //Definimos nuestro fondo principal.
        AnchorPane fondo = new AnchorPane();
        fondo.setMinHeight(600);
        fondo.setMaxHeight(600);
        fondo.setMaxWidth(600);
        fondo.setMinWidth(600);

        //Acomodamos nuestros componentes.
        final VBox principal = new VBox(50);
        final Button buscar = new Button("ACEPTAR");
        final Button reiniciar = new Button("REINICIAR VALORES");
        final Button cancelar = new Button("CANCELAR");
        final HBox botones = new HBox(20);
        botones.getChildren().addAll(cancelar, reiniciar, buscar);
        principal.setAlignment(Pos.TOP_CENTER);
        fondo.getChildren().add(principal);
        principal.setMaxSize(600, 600);
        principal.setMinSize(600, 600);

        //Definimos etiquetas a usar.
        Label encabezado = new Label("Busquedas Avanzadas \n\t Buscar:");
        final Label nombre = new Label("Nombre");
        final Label titulo = new Label("Título");
        final Label semestre = new Label("Semestre");
        final Label generoEstudiante = new Label("Género");
        final Label nombreProfesorLabel = new Label("Toma clases con:");
        nombreProfesorLabel.setVisible(false);
        final Label nombreDeLaMateriaLabel = new Label("Imparte/Recibe la materia de:");
        nombreDeLaMateriaLabel.setVisible(false);
        final Label nombreDeLaCarreraLabel = new Label("Es de la carrera de:");
        nombreDeLaCarreraLabel.setVisible(false);

        //Intanciamos nuestros componentes principales.
        final TextField nombreEntrada = new TextField();
        final TextField nombreProfesor = new TextField();
        nombreProfesor.setVisible(false);
        final TextField nombreMaterias = new TextField();
        nombreMaterias.setVisible(false);
        nombreEntrada.setTooltip(new Tooltip("Dejar vacío para despreciar"));
        final ChoiceBox<String> generoEstudiantes = new ChoiceBox<String>(FXCollections.
                observableArrayList("Ambos", "Hombre", "Mujer"));
        final ChoiceBox<String> cajaTitulos = new ChoiceBox<String>(
                FXCollections.observableArrayList(
                        "Todos", "Dr.", "Mtro.", "Mat.", "Lic.", "Biol.",
                        "Fis.", "Act.", "Ing."));
        final ChoiceBox<String> cajaSemestres = new ChoiceBox<String>(
                FXCollections.observableArrayList(
                        "Todos", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Opt"));
        final ChoiceBox<String> cajaCarreras = new ChoiceBox<String>(
                FXCollections.observableArrayList(
                        "Actuaria", "Biologia", "C. Computacion",
                        "C. Tierra", "Fisica", "Matematicas"));
        cajaCarreras.setVisible(false);
        generoEstudiantes.setValue("Ambos");
        cajaSemestres.setValue("Todos");
        cajaTitulos.setValue("Todos");
        cajaCarreras.setValue("Actuaria");

        final RadioButton estudiante = new RadioButton("Estudiante");
        final RadioButton profesor = new RadioButton("Profesor");
        final RadioButton materia = new RadioButton("Materia");

        final CheckBox tomaClases = new CheckBox("Profesor");
        final CheckBox tomanMateria = new CheckBox("Materia");
        final CheckBox sonCarrera = new CheckBox("Carrera");

        //Cajas para permitirnos acomodar mejor los componentes.
        final HBox objetos = new HBox(30);
        final HBox estudiantes0 = new HBox(30);
        final VBox estudiantes1 = new VBox(60);
        final VBox estudiantes2 = new VBox(20);
        final HBox profesores0 = new HBox(30);
        final VBox profesores1 = new VBox(60);
        final VBox profesores2 = new VBox(20);
        final HBox materias0 = new HBox(30);
        final VBox materias1 = new VBox(60);
        final VBox materias2 = new VBox(20);

        final HBox unionEstudiantes = new HBox(20);
        unionEstudiantes.getChildren().addAll(estudiantes1, estudiantes2);
        final HBox unionProfesores = new HBox(20);
        unionProfesores.getChildren().addAll(profesores1, profesores2);
        final HBox unionMaterias = new HBox(20);
        unionMaterias.getChildren().addAll(materias1, materias2);

        principal.getChildren().addAll(encabezado, objetos);
        encabezado.setAlignment(Pos.CENTER);
        objetos.setAlignment(Pos.CENTER);
        objetos.getChildren().addAll(estudiante, profesor, materia);

        //Definimos la búsqueda NO trivial para estudiantes.
        estudiante.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                //Añadimos los componentes necesarios.
                estudiantes0.getChildren().addAll(
                        nombre, nombreEntrada, generoEstudiante, generoEstudiantes);
                estudiantes1.getChildren().addAll(tomaClases, tomanMateria, sonCarrera);
                estudiantes2.getChildren().addAll(nombreProfesorLabel, nombreProfesor,
                        nombreDeLaMateriaLabel, nombreMaterias, nombreDeLaCarreraLabel, cajaCarreras);
                principal.getChildren().addAll(estudiantes0, unionEstudiantes, botones);
                estudiantes0.setAlignment(Pos.CENTER);
                unionEstudiantes.setAlignment(Pos.CENTER);
                botones.setAlignment(Pos.CENTER);
                objetos.setDisable(true);
                //Creamos un pequeño efecto visual.
                FadeTransition aparece = new FadeTransition(Duration.millis(750), estudiantes0);
                aparece.setFromValue(0);
                aparece.setToValue(1);
                FadeTransition aparece2 = new FadeTransition(Duration.millis(750), unionEstudiantes);
                aparece2.setFromValue(0);
                aparece2.setToValue(1);
                FadeTransition aparece3 = new FadeTransition(Duration.millis(750), botones);
                aparece3.setFromValue(0);
                aparece3.setToValue(1);
                aparece.play();
                aparece2.play();
                aparece3.play();
                //Definimos la búsqueda de alumnos respecto a los profesores.
                tomaClases.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        try {
                            final Stage profesores = new Stage();
                            VBox tabla = new VBox(20);
                            final Button aceptar = new Button("Aceptar");
                            final TableView<ObservableList<String>> tablaProfesores = new TableView<ObservableList<String>>();
                            agregaTabla(tablaProfesores, new Profesor().getTabla());
                            tablaProfesores.setMinHeight(200);
                            tabla.getChildren().addAll(tablaProfesores, aceptar);
                            aceptar.setAlignment(Pos.CENTER);
                            tabla.setAlignment(Pos.CENTER);
                            tabla.setMargin(aceptar, new Insets(10));
                            Scene scene = new Scene(tabla, 450, 400, Color.LIGHTGRAY);
                            profesores.setScene(scene);
                            profesores.show();
                            profesores.setOnCloseRequest(new EventHandler<WindowEvent>() {

                                @Override
                                public void handle(WindowEvent t) {
                                    tomaClases.setSelected(false);
                                }
                            });
                            aceptar.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent t) {
                                    String seleccionString
                                            = tablaProfesores.getSelectionModel().getSelectedItems().toString();                                    
                                    if (seleccionString.length() <= 3) {
                                        nombreProfesor.setText("(Todos)");
                                    } else {
                                        nombreProfesor.setText(seleccionString.substring(2, seleccionString.length() - 2));
                                    }
                                    nombreProfesor.setDisable(true);
                                    profesores.close();
                                    nombreProfesor.setVisible(true);
                                    nombreProfesorLabel.setVisible(true);
                                    FadeTransition aparece = new FadeTransition(Duration.millis(750), nombreProfesor);
                                    FadeTransition aparece2 = new FadeTransition(Duration.millis(750), nombreProfesorLabel);
                                    aparece.setFromValue(0);
                                    aparece.setToValue(1);
                                    aparece2.setFromValue(0);
                                    aparece2.setToValue(1);
                                    aparece2.play();
                                    aparece.play();
                                    tomaClases.setDisable(true);
                                }
                            });
                        } catch (SQLException ex) {
                            Conexion.cierraCanal();
                            MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                    + "error inesperado. Por favor, vuelva a intentarlo.",
                                    "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                    }
                });

                //Definimos la búsqueda de alumnos respecto a las materias.
                tomanMateria.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        try {
                            final Stage materias = new Stage();
                            VBox tabla = new VBox(20);
                            Button aceptar = new Button("Aceptar");
                            final TableView<ObservableList<String>> tablaMaterias = new TableView<ObservableList<String>>();
                            agregaTabla(tablaMaterias, new Materia().getTabla());
                            tablaMaterias.setMinHeight(200);
                            tabla.getChildren().addAll(tablaMaterias, aceptar);
                            aceptar.setAlignment(Pos.CENTER);
                            tabla.setAlignment(Pos.CENTER);
                            tabla.setMargin(aceptar, new Insets(10));
                            Scene scene = new Scene(tabla, 450, 400, Color.LIGHTGRAY);
                            materias.setScene(scene);
                            materias.show();
                            materias.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent t) {
                                    tomanMateria.setSelected(false);

                                }
                            });
                            aceptar.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent t) {

                                    String seleccionString
                                            = tablaMaterias.getSelectionModel().getSelectedItems().toString();
                                    if (seleccionString.length() <= 3) {
                                        nombreMaterias.setText("(Todas)");
                                    } else {
                                        nombreMaterias.setText(seleccionString.substring(2, seleccionString.length() - 2));
                                    }
                                    nombreMaterias.setDisable(true);
                                    materias.close();
                                    nombreMaterias.setVisible(true);
                                    nombreDeLaMateriaLabel.setVisible(true);
                                    FadeTransition aparece = new FadeTransition(Duration.millis(750), nombreMaterias);
                                    FadeTransition aparece2 = new FadeTransition(Duration.millis(750), nombreDeLaMateriaLabel);
                                    aparece.setFromValue(0);
                                    aparece.setToValue(1);
                                    aparece2.setFromValue(0);
                                    aparece2.setToValue(1);
                                    aparece2.play();
                                    aparece.play();
                                    tomanMateria.setDisable(true);
                                }
                            });
                        } catch (SQLException ex) {
                            Conexion.cierraCanal();
                            MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                    + "error inesperado. Por favor, vuelva a intentarlo.",
                                    "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                    }
                });

                //Definimos la búsqueda de alumnos respecto a las carreras.
                sonCarrera.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        cajaCarreras.setVisible(true);
                        nombreDeLaCarreraLabel.setVisible(true);
                        FadeTransition aparece = new FadeTransition(Duration.millis(750), cajaCarreras);
                        FadeTransition aparece2 = new FadeTransition(Duration.millis(750), nombreDeLaCarreraLabel);
                        aparece.setFromValue(0);
                        aparece.setToValue(1);
                        aparece2.setFromValue(0);
                        aparece2.setToValue(1);
                        aparece2.play();
                        aparece.play();
                        sonCarrera.setDisable(true);
                    }
                });
            }
        }); //Termina acción del radioButton Estudiante

        //Busquedas de profesores en la base de datos.
        profesor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                //Agregamos los componentes que requerimos.
                profesores0.getChildren().addAll(
                        nombre, nombreEntrada, titulo, cajaTitulos);
                profesores1.getChildren().addAll(tomanMateria);
                profesores2.getChildren().addAll(nombreDeLaMateriaLabel, nombreMaterias);

                principal.getChildren().addAll(profesores0, unionProfesores, botones);
                profesores0.setAlignment(Pos.CENTER);
                unionProfesores.setAlignment(Pos.CENTER);
                botones.setAlignment(Pos.CENTER);
                objetos.setDisable(true);
                //Agregamos pequeños efectos visuales.
                FadeTransition aparece = new FadeTransition(Duration.millis(750), profesores0);
                aparece.setFromValue(0);
                aparece.setToValue(1);
                FadeTransition aparece2 = new FadeTransition(Duration.millis(750), unionProfesores);
                aparece2.setFromValue(0);
                aparece2.setToValue(1);
                FadeTransition aparece3 = new FadeTransition(Duration.millis(750), botones);
                aparece3.setFromValue(0);
                aparece3.setToValue(1);
                aparece.play();
                aparece2.play();
                aparece3.play();
                //Buscamos profesores respecto a la materia que imparten.
                tomanMateria.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        try {
                            final Stage materias = new Stage();
                            VBox tabla = new VBox(20);
                            Button aceptar = new Button("Aceptar");
                            final TableView<ObservableList<String>> tablaMaterias = new TableView<ObservableList<String>>();
                            agregaTabla(tablaMaterias, new Materia().getTabla());
                            tablaMaterias.setMinHeight(200);
                            tabla.getChildren().addAll(tablaMaterias, aceptar);
                            aceptar.setAlignment(Pos.CENTER);
                            tabla.setAlignment(Pos.CENTER);
                            tabla.setMargin(aceptar, new Insets(10));
                            Scene scene = new Scene(tabla, 450, 400, Color.LIGHTGRAY);
                            materias.setScene(scene);
                            materias.show();
                            materias.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent t) {
                                    tomanMateria.setSelected(false);
                                }
                            });
                            aceptar.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent t) {
                                    String seleccionString
                                            = tablaMaterias.getSelectionModel().getSelectedItems().toString();
                                    if (seleccionString.length() <= 3) {
                                        nombreMaterias.setText("(Todas)");
                                    } else {
                                        nombreMaterias.setText(seleccionString.substring(2, seleccionString.length() - 2));
                                    }
                                    nombreMaterias.setDisable(true);
                                    materias.close();
                                    nombreMaterias.setVisible(true);
                                    nombreDeLaMateriaLabel.setVisible(true);
                                    FadeTransition aparece = new FadeTransition(Duration.millis(750), nombreMaterias);
                                    FadeTransition aparece2 = new FadeTransition(Duration.millis(750), nombreDeLaMateriaLabel);
                                    aparece.setFromValue(0);
                                    aparece.setToValue(1);
                                    aparece2.setFromValue(0);
                                    aparece2.setToValue(1);
                                    aparece2.play();
                                    aparece.play();
                                    tomanMateria.setDisable(true);
                                }
                            });
                        } catch (SQLException ex) {
                            Conexion.cierraCanal();
                            MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                    + "error inesperado. Por favor, vuelva a intentarlo.",
                                    "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                    }
                });
            }
        }); //Termina acción del radioButton Profesor

        //Realizamos las buquedas "avanzadas" de las materias.
        materia.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                //Añadimos los componentes convenientes.
                materias0.getChildren().addAll(
                        nombre, nombreEntrada, semestre, cajaSemestres);
                materias1.getChildren().addAll(tomaClases);
                materias2.getChildren().addAll(nombreProfesorLabel, nombreProfesor);
                principal.getChildren().addAll(materias0, unionMaterias, botones);
                materias0.setAlignment(Pos.CENTER);
                unionMaterias.setAlignment(Pos.CENTER);
                botones.setAlignment(Pos.CENTER);
                objetos.setDisable(true);
                //Agregamos pequeños efectos visuales para entretenimiento del usuario.
                FadeTransition aparece = new FadeTransition(Duration.millis(750), materias0);
                aparece.setFromValue(0);
                aparece.setToValue(1);
                FadeTransition aparece2 = new FadeTransition(Duration.millis(750), unionMaterias);
                aparece2.setFromValue(0);
                aparece2.setToValue(1);
                FadeTransition aparece3 = new FadeTransition(Duration.millis(750), botones);
                aparece3.setFromValue(0);
                aparece3.setToValue(1);
                aparece.play();
                aparece2.play();
                aparece3.play();
                //Definimos la búsqueda de las materias respecto a los profesores.
                tomaClases.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        try {
                            final Stage profesorStage = new Stage();
                            VBox tabla = new VBox(20);
                            Button aceptar = new Button("Aceptar");
                            final TableView<ObservableList<String>> tablaProfesores = new TableView<ObservableList<String>>();
                            agregaTabla(tablaProfesores, new Profesor().getTabla());
                            tablaProfesores.setMinHeight(200);
                            tabla.getChildren().addAll(tablaProfesores, aceptar);
                            aceptar.setAlignment(Pos.CENTER);
                            tabla.setAlignment(Pos.CENTER);
                            VBox.setMargin(aceptar, new Insets(10));
                            Scene scene = new Scene(tabla, 450, 400, Color.LIGHTGRAY);
                            profesorStage.setScene(scene);
                            profesorStage.show();
                            profesorStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent t) {
                                    tomaClases.setSelected(false);
                                }
                            });
                            aceptar.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent t) {
                                    String seleccionString
                                            = tablaProfesores.getSelectionModel().getSelectedItems().toString();
                                    if (seleccionString.length() <= 3) {
                                        nombreProfesor.setText("(Todos)");
                                    } else {
                                        nombreProfesor.setText(seleccionString.substring(2, seleccionString.length() - 2));
                                    }
                                    nombreProfesor.setDisable(true);
                                    profesorStage.close();
                                    nombreProfesor.setVisible(true);
                                    nombreProfesorLabel.setVisible(true);
                                    FadeTransition aparece = new FadeTransition(Duration.millis(750), nombreProfesor);
                                    FadeTransition aparece2 = new FadeTransition(Duration.millis(750), nombreProfesorLabel);
                                    aparece.setFromValue(0);
                                    aparece.setToValue(1);
                                    aparece2.setFromValue(0);
                                    aparece2.setToValue(1);
                                    aparece2.play();
                                    aparece.play();
                                    tomaClases.setDisable(true);
                                }
                            });
                        } catch (SQLException ex) {
                            Conexion.cierraCanal();
                            MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                    + "error inesperado. Por favor, vuelva a intentarlo.",
                                    "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                    }
                });
            }
        }); //Termina acción del radioButton Profesor

        //Definimos el botón reiniciar para quitar las componentes y desbloquear.
        reiniciar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (estudiante.isSelected()) {
                    estudiante.setSelected(false);
                    tomaClases.setSelected(false);
                    tomanMateria.setSelected(false);
                    sonCarrera.setSelected(false);
                    tomaClases.setDisable(false);
                    tomanMateria.setDisable(false);
                    sonCarrera.setDisable(false);
                    nombreMaterias.setVisible(false);
                    nombreDeLaMateriaLabel.setVisible(false);
                    cajaCarreras.setVisible(false);
                    nombreDeLaCarreraLabel.setVisible(false);
                    nombreProfesor.setVisible(false);
                    nombreProfesorLabel.setVisible(false);

                    nombreEntrada.setText("");
                    generoEstudiantes.setValue("Ambos");
                    nombreProfesor.setText("");
                    nombreEntrada.setText("");
                    nombreMaterias.setText("");

                    principal.getChildren().remove(2, 5);
                    estudiantes0.getChildren().clear();
                    estudiantes1.getChildren().clear();
                    estudiantes2.getChildren().clear();

                } else if (profesor.isSelected()) {
                    profesor.setSelected(false);
                    tomanMateria.setSelected(false);
                    tomanMateria.setDisable(false);
                    nombreDeLaMateriaLabel.setVisible(false);
                    nombreMaterias.setText("");
                    nombreMaterias.setVisible(false);
                    nombreEntrada.setText("");

                    principal.getChildren().remove(2, 5);
                    profesores0.getChildren().clear();
                    profesores1.getChildren().clear();
                    profesores2.getChildren().clear();

                } else if (materia.isSelected()) {
                    materia.setSelected(false);
                    tomaClases.setSelected(false);
                    tomaClases.setDisable(false);
                    nombreProfesorLabel.setVisible(false);
                    nombreProfesor.setText("");
                    nombreProfesor.setVisible(false);
                    nombreEntrada.setText("");

                    principal.getChildren().remove(2, 5);
                    materias0.getChildren().clear();
                    materias1.getChildren().clear();
                    materias2.getChildren().clear();
                }
                objetos.setDisable(false);
            }
        });

        //Asigamos cierre de Stage al botón cancelar.
        cancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                menuConsultas.setDisable(false);
                avanzadas.close();
            }
        });

        //Agregamos al botón de buscar acción y métodos correspondientes.
        buscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //Buscar un estudiante:
                if (estudiante.isSelected()) {
                    try {
                        String entradaNombre = nombreEntrada.getText().trim();
                        String genero = generoEstudiantes.getValue().toString();
                        switch (genero) {
                            case "Ambos":
                                genero = "";
                                break;
                            case "Hombre":
                                genero = "H";
                                break;
                            case "Mujer":
                                genero = "M";
                                break;
                        }
                        String[] entradaProfesor = nombreProfesor.getText().split(",");
                        String[] entradaMateria = nombreMaterias.getText().split(",");
                        String entradaCarrera = cajaCarreras.getValue().toString();
                        int intProfesor = (entradaProfesor[0].equals("")) ? -1 : Integer.parseInt(entradaProfesor[0]);
                        int intMateria = (entradaMateria[0].equals("")) ? -1 : Integer.parseInt(entradaMateria[0]);
                        int intCarrera = -1;
                        switch (entradaCarrera) {
                            case "Actuaria":
                                intCarrera = Facultad.ACTUARIA;
                                break;
                            case "Biologia":
                                intCarrera = Facultad.BIOLOGIA;
                                break;
                            case "C. Computacion":
                                intCarrera = Facultad.CIENCIAS_DE_LA_COMPUTACION;
                                break;
                            case "C. Tierra":
                                intCarrera = Facultad.CIENCIAS_DE_LA_TIERRA;
                                break;
                            case "Fisica":
                                intCarrera = Facultad.FISICA;
                                break;
                            case "Matematicas":
                                intCarrera = Facultad.MATEMATICAS;
                                break;
                        }
                        if (!sonCarrera.isSelected()) {
                            intCarrera = -1;
                        }
                        agregaTabla(tablaSecundariaEstudiantes, new Estudiante().buscaRelaciones(
                                entradaNombre, genero, intProfesor, intMateria, intCarrera));
                        MessageBox.show(new Stage(), "Su busqueda fue realizada con éxito. \n"
                                + " El resultado se mostrará en la tabla de estudiantes.", "Búsqueda exitosa",
                                MessageBox.ICON_INFORMATION | MessageBox.OK);
                        menuConsultas.setDisable(false);
                        avanzadas.close();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                    //Buscar un profesor:
                } else if (profesor.isSelected()) {
                    try {
                        String entradaNombre = nombreEntrada.getText().trim();
                        String[] entradaMateria = nombreMaterias.getText().split(",");
                        int intMateria = (entradaMateria[0].equals("")) ? -1 : Integer.parseInt(entradaMateria[0]);
                        agregaTabla(tablaSecundariaProfesores, new Profesor().buscaRelaciones(
                                entradaNombre, cajaTitulos.getValue().toString(), intMateria));
                        MessageBox.show(new Stage(), "Su busqueda fue realizada con éxito. \n"
                                + " El resultado se mostrará en la tabla de profesores.", "Búsqueda exitosa",
                                MessageBox.ICON_INFORMATION | MessageBox.OK);
                        menuConsultas.setDisable(false);
                        avanzadas.close();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                    //Buscar una materia:
                } else {
                    try {
                        String entradaNombre = nombreEntrada.getText().trim();
                        String[] entradaProfesor = nombreProfesor.getText().split(",");
                        String semestres = cajaSemestres.getValue().toString();
                        int semestreActual = 0;
                        if (!semestres.equalsIgnoreCase("Opt")) {
                            if (semestres.equalsIgnoreCase("Todos")) {
                                semestreActual = -1;
                            } else {
                                semestreActual = Integer.parseInt(semestres);
                            }
                        }
                        int intMateria = (entradaProfesor[0].equals("")) ? -1 : Integer.parseInt(entradaProfesor[0]);
                        agregaTabla(tablaSecundariaMaterias, new Materia().buscaRelaciones(
                                entradaNombre, semestreActual, intMateria));
                        MessageBox.show(new Stage(), "Su busqueda fue realizada con éxito. \n"
                                + " El resultado se mostrará en la tabla de materias.", "Búsqueda exitosa",
                                MessageBox.ICON_INFORMATION | MessageBox.OK);
                        menuConsultas.setDisable(false);
                        avanzadas.close();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            }
        });
        Scene root = new Scene(fondo, 600, 600, Color.LIGHTGREY);
        avanzadas.setScene(root);
        avanzadas.show();
    }
}// Fin de BusquedaGrafica.java
