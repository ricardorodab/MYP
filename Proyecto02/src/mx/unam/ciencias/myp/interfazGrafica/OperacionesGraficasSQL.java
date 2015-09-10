/* -------------------------------------------------------------------
 * OperacionesGraficasSQL.java
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

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfx.messagebox.MessageBox;
import mx.unam.ciencias.myp.tablas.*;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Clase que define el comportamiento las operaciones SQL en la interfaz.</p>
 *
 * <p>
 * Clase para eliminar, modificar, agregar desde la interfaz.</p>
 */
public class OperacionesGraficasSQL extends ControladorGraficoController {

    //Cada uno de los componentes necesarios para hacer las búsquedas.
    //Como ya no podemos acceder como estáticos ahora hay que pasarlos de parámetros.
    private AnchorPane menuPrincipal;
    private AnchorPane menuEstudiante;
    private AnchorPane menuProfesor;
    private AnchorPane menuMateria;
    private TableView<ObservableList<String>> tablaPrincipalEstudiantes;
    private TableView<ObservableList<String>> tablaPrincipalProfesores;
    private TableView<ObservableList<String>> tablaPrincipalMaterias;

    /**
     * Constructor que necesitamos para optener los componentes.
     *
     * @param menuPrincipal - es el menú principal.
     * @param menuEstudiante - es el menú de estudiantes.
     * @param menuProfesor - es el menú de profesores.
     * @param menuMateria - es el menú de materias.
     * @param tablaPrincipalEstudiantes - es la tabla de estudiantes.
     * @param tablaPrincipalProfesores - es la tabla de los profesores.
     * @param tablaPrincipalMaterias - es la tabla de las materias.
     */
    public OperacionesGraficasSQL(AnchorPane menuPrincipal,
            AnchorPane menuEstudiante,
            AnchorPane menuProfesor,
            AnchorPane menuMateria,
            TableView<ObservableList<String>> tablaPrincipalEstudiantes,
            TableView<ObservableList<String>> tablaPrincipalProfesores,
            TableView<ObservableList<String>> tablaPrincipalMaterias) {
        this.menuPrincipal = menuPrincipal;
        this.menuEstudiante = menuEstudiante;
        this.menuProfesor = menuProfesor;
        this.menuMateria = menuMateria;
        this.tablaPrincipalEstudiantes = tablaPrincipalEstudiantes;
        this.tablaPrincipalProfesores = tablaPrincipalProfesores;
        this.tablaPrincipalMaterias = tablaPrincipalMaterias;
    }

    /**
     * Método para agregar un nuevo elemento a la base de datos.
     *
     * @param e - acción que nos indica desde dónde fue llamado el método.
     */
    public void agrega(ActionEvent e) {
        int menus = -1;
        if (e.getSource() instanceof MenuItem) {
            MenuItem menu = (MenuItem) e.getSource();
            if (menu.getText().equals("Alumno")) {
                menus = 0;
            }
            if (menu.getText().equals("Profesor")) {
                menus = 1;
            }
            if (menu.getText().equals("Materia")) {
                menus = 2;
            }
            menuPrincipal.setDisable(true);
        }
        final Stage crearNuevo = new Stage();
        crearNuevo.setMinWidth(525);
        crearNuevo.setMinHeight(250);
        if (menuEstudiante.isVisible()) {
            menuEstudiante.setDisable(true);
            menus = 0;
        } else if (menuProfesor.isVisible()) {
            menuProfesor.setDisable(true);
            menus = 1;
        } else if (menuMateria.isVisible()) {
            menuMateria.setDisable(true);
            menus = 2;
        }
        final int menu = menus;
        crearNuevo.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                if (menuPrincipal.isVisible()) {
                    menuPrincipal.setDisable(false);
                } else {
                    switch (menu) {
                        case 0:
                            menuEstudiante.setDisable(false);
                            break;
                        case 1:
                            menuProfesor.setDisable(false);
                            break;
                        case 2:
                            menuMateria.setDisable(false);
                            break;
                    }
                }
            }
        });
        Text encabezado = new Text();
        switch (menu) {
            case 0:
                encabezado.setText("NUEVO ESTUDIANTE");
                break;
            case 1:
                encabezado.setText("NUEVO PROFESOR");
                ;
                break;
            case 2:
                encabezado.setText("NUEVA MATERIA");
                break;
        }

        final TextField id = new TextField("Número de Cuenta");
        final TextField nombre = new TextField("Nombre (s)");
        final TextField apellidos = new TextField("Apellidos");
        final RadioButton hombre = new RadioButton("H");

        hombre.setSelected(true);
        final RadioButton mujer = new RadioButton("M");
        final ChoiceBox<String> cajaTitulos = new ChoiceBox<String>(
                FXCollections.observableArrayList(
                        "Dr.", "Mtro.", "Mat.", "Lic.", "Biol.",
                        "Fis.", "Act.", "Ing."));
        final ChoiceBox<String> cajaSemestres = new ChoiceBox<String>(
                FXCollections.observableArrayList(
                        "1", "2", "3", "4", "5", "6", "7", "8", "9", "Opt"));
        final ChoiceBox<String> cajaCarreras = new ChoiceBox<String>(
                FXCollections.observableArrayList(
                        "Actuaria", "Biologia", "C. Computacion", "C. Tierra",
                        "Fisica", "Matematicas"));

        cajaSemestres.setValue("1");
        cajaTitulos.setValue("Dr.");
        cajaCarreras.setValue("Actuaria");

        Label idLabel = new Label("Número de Cuenta");
        Label nombreLabel = new Label("Nombre (s)");
        Label apellidosLabel = new Label("Apellidos");
        Label sexoLabel = new Label("Sexo");
        Label titulo = new Label("Título");
        Label semestre = new Label("Semestre");
        Label carreras = new Label("Carrera");

        if (menu == 1) {
            id.setText("Número de Trabajador");
            idLabel.setText("Número de Trabajador");
            nombre.setText("Nombre Completo");
            nombreLabel.setText("Nombre Completo");
        } else if (menu == 2) {
            id.setText("Clave de la Materia");
            idLabel.setText("Clave de la Materia");
            nombre.setText("Nombre de la Materia");
            nombreLabel.setText("Nombre de la Materia");
        }

        VBox entradas = new VBox(10);
        VBox etiquetas = new VBox(20);
        HBox union = new HBox(15);
        HBox botones = new HBox(30);
        HBox genero = new HBox(10);

        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");
        BorderPane border = new BorderPane();
        border.setMinSize(400, 200);
        genero.getChildren()
                .addAll(hombre, mujer);
        entradas.getChildren()
                .add(id);
        entradas.getChildren()
                .add(nombre);
        if (menu == 0) {
            entradas.getChildren().add(apellidos);
            entradas.getChildren().add(genero);
            entradas.getChildren().add(cajaCarreras);
        } else if (menu == 1) {
            entradas.getChildren().add(cajaTitulos);
        } else {
            entradas.getChildren().add(cajaSemestres);
        }

        etiquetas.getChildren()
                .add(idLabel);
        etiquetas.getChildren()
                .add(nombreLabel);
        if (menu == 0) {
            etiquetas.getChildren().add(apellidosLabel);
            etiquetas.getChildren().add(sexoLabel);
            etiquetas.getChildren().add(carreras);
        } else if (menu == 1) {
            etiquetas.getChildren().add(titulo);
        } else {
            etiquetas.getChildren().add(semestre);
        }

        union.getChildren().addAll(entradas, etiquetas);
        botones.getChildren().addAll(aceptar, cancelar);
        border.setTop(encabezado);
        border.setAlignment(encabezado, Pos.CENTER);
        border.setCenter(union);
        border.setAlignment(union, Pos.CENTER);
        border.setMargin(union, new Insets(30, 0, 0, 100));
        border.setBottom(botones);
        border.setAlignment(botones, Pos.CENTER_RIGHT);
        border.setMargin(botones, new Insets(0, 10, 20, 130));

        Scene scene = new Scene(border, 525, 120, Color.LIGHTGREY);

        crearNuevo.setScene(scene);
        crearNuevo.setResizable(true);
        if (menu == 0) {
            crearNuevo.setMaxHeight(350);
            crearNuevo.setMaxWidth(525);
            crearNuevo.setMinHeight(350);
            crearNuevo.setMinWidth(525);
        } else {
            crearNuevo.setMaxHeight(250);
            crearNuevo.setMaxWidth(525);
            crearNuevo.setMinHeight(250);
            crearNuevo.setMinWidth(525);
        }
        crearNuevo.setTitle(
                "Agregar nuevo...");
        crearNuevo.show();
        hombre.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mujer.isSelected()) {
                    mujer.setSelected(false);
                    hombre.setSelected(true);
                }
                hombre.setSelected(true);
            }
        });
        mujer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (hombre.isSelected()) {
                    hombre.setSelected(false);
                    mujer.setSelected(true);
                }
                mujer.setSelected(true);
            }
        });
        cancelar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t
            ) {
                if (menuPrincipal.isVisible()) {
                    menuPrincipal.setDisable(false);
                } else {
                    switch (menu) {
                        case 0:
                            menuEstudiante.setDisable(false);
                            break;
                        case 1:
                            menuProfesor.setDisable(false);
                            break;
                        case 2:
                            menuMateria.setDisable(false);
                            break;
                    }
                }
                crearNuevo.close();
            }
        });
        aceptar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t
            ) {
                try {
                    if (id.getText().equals("")) {
                        MessageBox.show(new Stage(), "¡UPS! \n\n El ID (Número de cuenta)"
                                + " no puede ser vacío. Favor de dar un nuevo número de cuenta",
                                "ID Vacío", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                    Integer revisarID = Integer.parseInt(id.getText());
                    if (revisarID <= 0) {
                        MessageBox.show(new Stage(), "¡UPS! \n\n El ID (Número de cuenta)"
                                + " no puede ser menor igual a cero. Favor de dar un nuevo número de cuenta",
                                "ID menor igual a 0", MessageBox.ICON_ERROR | MessageBox.OK);
                    } else {
                        if (menu == 0) {
                            Integer numCuenta = Integer.parseInt(id.getText());

                            String genero = (hombre.isSelected()) ? "H" : "M";
                            Estudiante estudiante
                                    = new Estudiante(numCuenta, nombre.getText(),
                                            apellidos.getText(), genero.charAt(0));
                            switch (cajaCarreras.getSelectionModel().getSelectedItem().toString()) {
                                case "Actuaria":
                                    estudiante.agrega(Facultad.ACTUARIA);
                                    break;
                                case "Biologia":
                                    estudiante.agrega(Facultad.BIOLOGIA);
                                    break;
                                case "C. Computacion":
                                    estudiante.agrega(Facultad.CIENCIAS_DE_LA_COMPUTACION);
                                    break;
                                case "C. Tierra":
                                    estudiante.agrega(Facultad.CIENCIAS_DE_LA_TIERRA);
                                    break;
                                case "Fisica":
                                    estudiante.agrega(Facultad.FISICA);
                                    break;
                                case "Matematicas":
                                    estudiante.agrega(Facultad.MATEMATICAS);
                                    break;
                            }
                            agregaTabla(tablaPrincipalEstudiantes, estudiante.getTabla());
                            if (!menuPrincipal.isVisible()) {
                                menuEstudiante.setDisable(false);
                            } else {
                                menuPrincipal.setDisable(false);
                            }
                            crearNuevo.close();
                        } else if (menu == 1) {
                            Integer numTrabajador = Integer.parseInt(id.getText());
                            String titulo = (String) cajaTitulos.getValue();
                            Profesor profesor = new Profesor(numTrabajador, titulo, nombre.getText());
                            profesor.agrega();
                            agregaTabla(tablaPrincipalProfesores, profesor.getTabla());
                            if (!menuPrincipal.isVisible()) {
                                menuProfesor.setDisable(false);
                            } else {
                                menuPrincipal.setDisable(false);
                            }
                            crearNuevo.close();
                        } else {
                            Integer clave, semestre;
                            clave = Integer.parseInt(id.getText());
                            String numSemetre = (String) cajaSemestres.getValue();
                            semestre = numSemetre.equals("Opt") ? 0
                                    : Integer.parseInt(numSemetre);
                            Materia materia = new Materia(clave, nombre.getText(), semestre);
                            materia.agrega();
                            agregaTabla(tablaPrincipalMaterias, materia.getTabla());
                            if (!menuPrincipal.isVisible()) {
                                menuMateria.setDisable(false);
                            } else {
                                menuPrincipal.setDisable(false);
                            }
                            crearNuevo.close();
                        }
                    }
                } catch (SQLException ex) {
                    Conexion.cierraCanal();
                    MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                            + "error inesperado. Por favor, vuelva a intentarlo.",
                            "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                } catch (NumberFormatException e) {
                    Conexion.cierraCanal();
                    MessageBox.show(new Stage(), "¡UPS! \n\n El número de cuenta (ID)"
                            + " SÓLO puede contener números.",
                            "Psss... Sin números.", MessageBox.ICON_ERROR | MessageBox.OK);
                }
            }
        }
        );
    }

    /**
     * Método para modificar a un Estudiante, profesor o materia de la base.
     *
     * @param e - es el identificador de la fuente que llama al método.
     * @throws SQLException - en caso de problemas de conexión.
     */
    public void modificarCampo(ActionEvent e) throws SQLException {
        int menus = -1;
        final Stage ventanaModificar = new Stage();
        ventanaModificar.setMinWidth(525);
        ventanaModificar.setMinHeight(250);
        if (menuEstudiante.isVisible()) {
            menuEstudiante.setDisable(true);
            menus = 0;
        } else if (menuProfesor.isVisible()) {
            menuProfesor.setDisable(true);
            menus = 1;
        } else if (menuMateria.isVisible()) {
            menuMateria.setDisable(true);
            menus = 2;
        }
        final int menu = menus;
        ventanaModificar.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                switch (menu) {
                    case 0:
                        menuEstudiante.setDisable(false);
                        break;
                    case 1:
                        menuProfesor.setDisable(false);
                        break;
                    case 2:
                        menuMateria.setDisable(false);
                        break;
                }
            }
        });
        Text encabezado = new Text();
        ObservableList aModificar = null;
        switch (menu) {
            case 0:
                encabezado.setText("MODIFICAR ESTUDIANTE");
                aModificar = tablaPrincipalEstudiantes.getSelectionModel().getSelectedItems();
                break;
            case 1:
                encabezado.setText("MODIFICAR PROFESOR");
                aModificar = tablaPrincipalProfesores.getSelectionModel().getSelectedItems();
                break;
            case 2:
                encabezado.setText("MODIFICAR MATERIA");
                aModificar = tablaPrincipalMaterias.getSelectionModel().getSelectedItems();
                break;
        }
        String columna = aModificar.get(0).toString();
        final String[] arregloColumna
                = columna.substring(1, columna.length() - 1).split(",");
        arregloColumna[0] = arregloColumna[0].trim();
        arregloColumna[1] = arregloColumna[1].trim();
        arregloColumna[2] = arregloColumna[2].trim();
        if (menu == 0) {
            arregloColumna[3] = arregloColumna[3].trim();
        }

        final TextField id = new TextField(arregloColumna[0]);
        final TextField nombre = new TextField();
        final TextField apellidos = new TextField(arregloColumna[2]);
        if (menu == 0 || menu == 2) {
            nombre.setText(arregloColumna[1]);
        } else {
            nombre.setText(arregloColumna[2]);
        }

        final RadioButton hombre = new RadioButton("H");
        final RadioButton mujer = new RadioButton("M");
        final ChoiceBox<String> cajaTitulos = new ChoiceBox<String>(
                FXCollections.observableArrayList(
                        "Dr.", "Mtro.", "Mat.", "Lic.", "Biol.",
                        "Fis.", "Act.", "Ing."));
        final ChoiceBox<String> cajaSemestres = new ChoiceBox<String>(
                FXCollections.observableArrayList(
                        "1", "2", "3", "4", "5", "6", "7", "8", "9", "Opt"));
        final ChoiceBox<String> cajaCarreras = new ChoiceBox<String>(
                FXCollections.observableArrayList(
                        "Actuaria", "Biologia", "C. Computacion", "C. Tierra",
                        "Fisica", "Matematicas"));
        if (menu == 0) {
            Estudiante sacarCarrera = new Estudiante();
            sacarCarrera.setId(Integer.parseInt(arregloColumna[0]));
            String carrera = sacarCarrera.getCarrera();
            if (carrera.equalsIgnoreCase("Ciencias de la Computacion")) {
                cajaCarreras.setValue("C. Computacion");
            } else if (carrera.equalsIgnoreCase("Ciencias de la Tierra")) {
                cajaCarreras.setValue("C. Tierra");
            } else {
                cajaCarreras.setValue(carrera);
            }
            if (arregloColumna[3].equals("H")) {
                hombre.setSelected(true);
            } else {
                mujer.setSelected(true);
            }
        } else if (menu == 1) {
            cajaTitulos.setValue(arregloColumna[1]);
        } else if (menu == 2) {
            cajaSemestres.setValue(arregloColumna[2]);
        }
        Label idLabel = new Label("Número de Cuenta");
        Label nombreLabel = new Label("Nombre (s)");
        Label apellidosLabel = new Label("Apellidos");
        Label sexoLabel = new Label("Sexo");
        Label titulo = new Label("Título");
        Label semestre = new Label("Semestre");
        Label carreras = new Label("Carrera");
        if (menu == 1) {
            id.setText(arregloColumna[0]);
            idLabel.setText("Número de Trabajador");
            nombre.setText(arregloColumna[2]);
            nombreLabel.setText("Nombre Completo");
        } else if (menu == 2) {
            id.setText(arregloColumna[0]);
            idLabel.setText("Clave de la Materia");
            nombre.setText(arregloColumna[1]);
            nombreLabel.setText("Nombre de la Materia");
        }

        VBox entradas = new VBox(10);
        VBox etiquetas = new VBox(20);
        HBox union = new HBox(15);
        HBox botones = new HBox(30);
        HBox genero = new HBox(10);

        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");

        BorderPane border = new BorderPane();
        border.setMinSize(400, 200);

        genero.getChildren().addAll(hombre, mujer);
        entradas.getChildren().add(id);
        entradas.getChildren().add(nombre);
        if (menu == 0) {
            entradas.getChildren().add(apellidos);
            entradas.getChildren().add(genero);
            entradas.getChildren().add(cajaCarreras);
        } else if (menu == 1) {
            entradas.getChildren().add(cajaTitulos);
        } else {
            entradas.getChildren().add(cajaSemestres);
        }
        etiquetas.getChildren().add(idLabel);
        etiquetas.getChildren().add(nombreLabel);
        if (menu == 0) {
            etiquetas.getChildren().add(apellidosLabel);
            etiquetas.getChildren().add(sexoLabel);
            etiquetas.getChildren().add(carreras);
        } else if (menu == 1) {
            etiquetas.getChildren().add(titulo);
        } else {
            etiquetas.getChildren().add(semestre);
        }
        union.getChildren().addAll(entradas, etiquetas);
        botones.getChildren().addAll(aceptar, cancelar);

        border.setTop(encabezado);
        border.setAlignment(encabezado, Pos.CENTER);
        border.setCenter(union);
        border.setAlignment(union, Pos.CENTER);
        border.setMargin(union, new Insets(30, 0, 0, 100));
        border.setBottom(botones);
        border.setAlignment(botones, Pos.CENTER_RIGHT);
        border.setMargin(botones, new Insets(0, 10, 20, 130));

        Scene scene = new Scene(border, 525, 120, Color.LIGHTGREY);
        ventanaModificar.setScene(scene);
        ventanaModificar.setResizable(true);
        if (menu == 0) {
            ventanaModificar.setMaxHeight(350);
            ventanaModificar.setMaxWidth(525);
            ventanaModificar.setMinHeight(350);
            ventanaModificar.setMinWidth(525);
        } else {
            ventanaModificar.setMaxHeight(250);
            ventanaModificar.setMaxWidth(525);
            ventanaModificar.setMinHeight(250);
            ventanaModificar.setMinWidth(525);
        }
        ventanaModificar.setTitle("Modificar...");
        ventanaModificar.show();

        hombre.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (mujer.isSelected()) {
                    mujer.setSelected(false);
                    hombre.setSelected(true);
                }
                hombre.setSelected(true);
            }
        });
        mujer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (hombre.isSelected()) {
                    hombre.setSelected(false);
                    mujer.setSelected(true);
                }
                mujer.setSelected(true);
            }
        });

        cancelar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                switch (menu) {
                    case 0:
                        menuEstudiante.setDisable(false);
                        break;
                    case 1:
                        menuProfesor.setDisable(false);
                        break;
                    case 2:
                        menuMateria.setDisable(false);
                        break;
                }
                ventanaModificar.close();
            }
        });
        aceptar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    if (id.getText().equals("")) {
                        MessageBox.show(new Stage(), "¡UPS! \n\n El ID (Número de cuenta)"
                                + " no puede ser vacío. Favor de dar un nuevo número de cuenta",
                                "ID Vacío", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                    Integer revisarID = Integer.parseInt(id.getText());
                    if (revisarID <= 0) {
                        MessageBox.show(new Stage(), "¡UPS! \n\n El ID (Número de cuenta)"
                                + " no puede ser menor igual a cero. Favor de dar un nuevo número de cuenta",
                                "ID menor igual a 0", MessageBox.ICON_ERROR | MessageBox.OK);
                    } else {
                        if (menu == 0) {
                            Integer numCuenta = Integer.parseInt(id.getText());
                            Integer numCuentaAnterior
                                    = Integer.parseInt(arregloColumna[0]);
                            String genero = (hombre.isSelected()) ? "H" : "M";
                            Estudiante estudiante = new Estudiante(numCuentaAnterior,
                                    nombre.getText(), apellidos.getText(), genero.charAt(0));
                            estudiante.actualiza();
                            estudiante.actualizaApellido();
                            estudiante.cambiaGenero();
                            switch (cajaCarreras.getSelectionModel().getSelectedItem().toString()) {
                                case "Actuaria":
                                    estudiante.setEstudiantesCarrera(Facultad.ACTUARIA);
                                    break;
                                case "Biologia":
                                    estudiante.setEstudiantesCarrera(Facultad.BIOLOGIA);
                                    break;
                                case "C. Computacion":
                                    estudiante.setEstudiantesCarrera(Facultad.CIENCIAS_DE_LA_COMPUTACION);
                                    break;
                                case "C. Tierra":
                                    estudiante.setEstudiantesCarrera(Facultad.CIENCIAS_DE_LA_TIERRA);
                                    break;
                                case "Fisica":
                                    estudiante.setEstudiantesCarrera(Facultad.FISICA);
                                    break;
                                case "Matematicas":
                                    estudiante.setEstudiantesCarrera(Facultad.MATEMATICAS);
                                    break;
                            }
                            estudiante.actualizaId(numCuenta);
                            agregaTabla(tablaPrincipalEstudiantes, estudiante.getTabla());
                            menuEstudiante.setDisable(false);
                            ventanaModificar.close();
                        } else if (menu == 1) {
                            Integer numTrabajador = Integer.parseInt(id.getText());
                            Integer numTrabajadorAnterior
                                    = Integer.parseInt(arregloColumna[0]);
                            Profesor profesor = new Profesor(numTrabajadorAnterior,
                                    cajaTitulos.getValue().toString(), nombre.getText());
                            profesor.actualiza();
                            profesor.actualizaTitulo();
                            profesor.actualizaId(numTrabajador);
                            agregaTabla(tablaPrincipalProfesores, profesor.getTabla());
                            menuProfesor.setDisable(false);
                            ventanaModificar.close();
                        } else if (menu == 2) {
                            Integer clave = Integer.parseInt(id.getText());
                            Integer claveAnterior
                                    = Integer.parseInt(arregloColumna[0]);
                            String numSemetre = (String) cajaSemestres.getValue();
                            Integer semestre = numSemetre.equals("Opt") ? 0
                                    : Integer.parseInt(numSemetre);
                            Materia materia = new Materia(claveAnterior,
                                    nombre.getText(), semestre);
                            materia.actualiza();
                            materia.actualizaSemestre();
                            materia.actualizaId(clave);
                            agregaTabla(tablaPrincipalMaterias, materia.getTabla());
                            menuMateria.setDisable(false);
                            ventanaModificar.close();
                        }
                    }
                } catch (SQLException ex) {
                    Conexion.cierraCanal();
                    MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                            + "error inesperado. Por favor, vuelva a intentarlo.",
                            "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                } catch (NumberFormatException e) {
                    Conexion.cierraCanal();
                    MessageBox.show(new Stage(), "¡UPS! \n\n El número de cuenta (ID)"
                            + " SÓLO puede contener números.",
                            "Psss... Sin números.", MessageBox.ICON_ERROR | MessageBox.OK);
                }
            }
        });
    }

    /**
     * Método para eliminar un campo de la base de datos.
     *
     * @throws SQLException - en caso de tener algún problema de conexión.
     */
    public void eliminarCampo() throws SQLException {
        ObservableList aEliminar = null;
        String caso = "";
        String cuenta = "";
        int nombre, casoEliminar = nombre = 0;
        if (menuEstudiante.isVisible()) {
            aEliminar = tablaPrincipalEstudiantes.getSelectionModel().getSelectedItems();
            menuEstudiante.setDisable(true);
            caso = "el esudiante";
            cuenta = "número de cuenta";
            nombre = 1;
        } else if (menuMateria.isVisible()) {
            aEliminar = tablaPrincipalMaterias.getSelectionModel().getSelectedItems();
            menuMateria.setDisable(true);
            caso = "la materia";
            cuenta = "clave";
            nombre = 1;
            casoEliminar = 2;
        } else if (menuProfesor.isVisible()) {
            aEliminar = tablaPrincipalProfesores.getSelectionModel().getSelectedItems();
            menuProfesor.setDisable(true);
            caso = "el profesor";
            cuenta = "número de trabajador";
            nombre = 2;
            casoEliminar = 1;
        }
        String columna = aEliminar.get(0).toString();
        String[] arregloColumna = columna.substring(1, columna.length() - 1).split(",");
        Integer id = Integer.parseInt(arregloColumna[0]);
        int confirmacion = MessageBox.show(new Stage(),
                "Favor de confirmar la operación: \n\n ¿Desea eliminar a " + caso + " con "
                + cuenta + " " + id + " y de nombre " + arregloColumna[nombre] + "?",
                "Eliminar",
                MessageBox.ICON_WARNING | MessageBox.YES | MessageBox.NO);
        if (confirmacion == MessageBox.YES) {
            switch (casoEliminar) {
                case 0:
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(id);
                    estudiante.eliminaCarrera();
                    new Grupo().eliminaEstudiante(estudiante);
                    estudiante.elimina();
                    menuEstudiante.setDisable(false);
                    agregaTabla(tablaPrincipalEstudiantes, estudiante.getTabla());
                    break;
                case 1:
                    Profesor profesor = new Profesor();
                    profesor.setId(id);
                    new Grupo().eliminaProfesor(profesor);
                    profesor.elimina();
                    menuProfesor.setDisable(false);
                    agregaTabla(tablaPrincipalProfesores, profesor.getTabla());
                    break;
                case 2:
                    Materia materia = new Materia();
                    materia.setId(id);
                    new Grupo().eliminaMateria(materia);
                    materia.elimina();
                    menuMateria.setDisable(false);
                    agregaTabla(tablaPrincipalMaterias, materia.getTabla());
                    break;
            }
        } else {
            switch (casoEliminar) {
                case 0:
                    menuEstudiante.setDisable(false);
                    break;
                case 1:
                    menuProfesor.setDisable(false);
                    break;
                case 2:
                    menuMateria.setDisable(false);
                    break;
            }
        }
    }

} //Fin de OperacionesGraficasSQL.java
