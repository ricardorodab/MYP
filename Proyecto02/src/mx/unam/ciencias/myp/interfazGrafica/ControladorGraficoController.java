/* -------------------------------------------------------------------
 * ControladoGraficoController.java
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

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfx.messagebox.MessageBox;
import mx.unam.ciencias.myp.tablas.*;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Clase principal de la intefaz grafica.</p>
 *
 * <p>
 * Clase principal que asigna comportamiento a la interfaz gráfica.</p>
 */
public class ControladorGraficoController implements Initializable {

    //Cada @FXML es un componente de la intefaz definida en el archivo .fxml
    @FXML
    private TableView<ObservableList<String>> tablaPrincipalEstudiantes;
    @FXML
    private TableView<ObservableList<String>> tablaPrincipalProfesores;
    @FXML
    private TableView<ObservableList<String>> tablaPrincipalMaterias;
    @FXML
    private TableView<ObservableList<String>> tablaPrincipalCarreras;
    @FXML
    private TableView<ObservableList<String>> tablaSecundariaProfesores;
    @FXML
    private TableView<ObservableList<String>> tablaSecundariaMaterias;
    @FXML
    private TableView<ObservableList<String>> tablaSecundariaEstudiantes;
    @FXML
    private AnchorPane menuPrincipal;
    @FXML
    private AnchorPane menuEstudiante;
    @FXML
    private AnchorPane menuProfesor;
    @FXML
    private AnchorPane menuMateria;
    @FXML
    private AnchorPane menuCarrera;
    @FXML
    private AnchorPane menuConsultas;
    @FXML
    private TreeView<Hyperlink> arbolEstudiantes;
    @FXML
    private TreeView<Hyperlink> arbolProfesores;
    @FXML
    private TreeView<Hyperlink> arbolMaterias;
    @FXML
    private Button eliminarEstudiantes;
    @FXML
    private Button modificarEstudiantes;
    @FXML
    private Button eliminarProfesores;
    @FXML
    private Button modificarProfesores;
    @FXML
    private Button eliminarMaterias;
    @FXML
    private Button modificarMaterias;
    @FXML
    private Button eliminaGrupo;
    @FXML
    private ChoiceBox<String> cajaCarreras;
    @FXML
    private ListView<String> listaGrupos;
    @FXML
    private CheckBox verEstudiantes;
    @FXML
    private CheckBox verProfesores;
    @FXML
    private CheckBox verMaterias;
    @FXML
    private PieChart porcentajes;

    /**
     * Método que agrega un nuevo estudiante, profesor o materia a la base de
     * datos.
     *
     * @param e - evento de botón.
     */
    public void agregaNuevo(ActionEvent e) {
        new OperacionesGraficasSQL(menuPrincipal, menuEstudiante, menuProfesor,
                menuMateria, tablaPrincipalEstudiantes, tablaPrincipalProfesores,
                tablaPrincipalMaterias).agrega(e);
    }

    /**
     * Método que muestra el menu principal.
     *
     * @param e - evento de algún botón.
     */
    public void muestraMenuPrincipal(ActionEvent e) {
        if (menuEstudiante.isVisible()) {
            menuEstudiante.setVisible(false);
            menuEstudiante.setDisable(true);
        } else if (menuProfesor.isVisible()) {
            menuProfesor.setDisable(true);
            menuProfesor.setVisible(false);
        } else if (menuMateria.isVisible()) {
            menuMateria.setDisable(true);
            menuMateria.setVisible(false);
        } else if (menuCarrera.isVisible()) {
            menuCarrera.setDisable(true);
            menuCarrera.setVisible(false);
        } else if (menuConsultas.isVisible()) {
            menuConsultas.setDisable(true);
            menuConsultas.setVisible(false);
        }
        menuPrincipal.setDisable(false);
        menuPrincipal.setVisible(true);
    }

    /**
     * Método para mostrar los 4 menus secundarios.
     *
     * @param e - acción del botón y medio de identificación de menu secundario.
     * @throws SQLException - en caso de problemas de conexión con la base.
     */
    public void muestraMenuSecundario(ActionEvent e) throws SQLException {
        new MenusSecundarios(menuPrincipal, menuEstudiante, menuProfesor,
                menuMateria, menuCarrera, tablaPrincipalEstudiantes,
                tablaPrincipalProfesores, tablaPrincipalMaterias, arbolEstudiantes,
                arbolProfesores, arbolMaterias, eliminarEstudiantes, modificarEstudiantes,
                eliminarProfesores, modificarProfesores, eliminarMaterias,
                modificarMaterias, tablaPrincipalCarreras).menuSecundario(e);
    }

    /**
     * Método que nos muestra el menú de busquedas avanzadas.
     *
     * @param e - acción del ratón.
     * @throws SQLException - en caso de tener problemas de conexión con la
     * base.
     */
    public void muestraMenuBusqueda(ActionEvent e) throws SQLException {
        new BusquedaGrafica(tablaSecundariaEstudiantes, tablaSecundariaProfesores,
                tablaSecundariaMaterias, menuPrincipal, menuEstudiante,
                menuProfesor, menuMateria, menuConsultas, listaGrupos,
                eliminaGrupo, cajaCarreras).muestraMenu(e);
    }

    /**
     * Método para abrir el menu de búsquedas avanzadas.
     *
     * @param e - es el identificador de la acción del botón
     * @throws SQLException - en el caso de tener problemas de conexión con la
     * base.
     */
    public void busquedasAvanzada(ActionEvent e) throws SQLException {
        new BusquedaGrafica(tablaSecundariaEstudiantes, tablaSecundariaProfesores,
                tablaSecundariaMaterias, menuPrincipal, menuEstudiante,
                menuProfesor, menuMateria, menuConsultas, listaGrupos,
                eliminaGrupo, cajaCarreras).busquedasAvanzadas();
    }

    /**
     * Método para eliminar un estudiante, profesor o materia de la base.
     *
     * @throws SQLException - en caso de tener problemas de conexión con la
     * base.
     */
    public void eliminar() throws SQLException {
        new OperacionesGraficasSQL(menuPrincipal, menuEstudiante, menuProfesor,
                menuMateria, tablaPrincipalEstudiantes, tablaPrincipalProfesores,
                tablaPrincipalMaterias).eliminarCampo();
    }

    /**
     * Método para poder eliminar un grupo por completo de la base de datos.
     *
     * @throws SQLException - en caso de tener problemas de conexión con la
     * base.
     */
    public void eliminaGrupo() throws SQLException {
        new BusquedaGrafica(tablaSecundariaEstudiantes, tablaSecundariaProfesores,
                tablaSecundariaMaterias, menuPrincipal, menuEstudiante,
                menuProfesor, menuMateria, menuConsultas, listaGrupos,
                eliminaGrupo, cajaCarreras).eliminarGrupo();
    }

    /**
     * Método para buscar una persona o materia en la interfaz gráfica.
     *
     * @param e - es el evento de la acción del teclado presionado.
     * @throws SQLException - en caso de tener algún problema de conexión.
     */
    public void buscaPersona(KeyEvent e) throws SQLException {
        //Cada que escribe algo actualiza la tabla con las coincidencias.
        if (e.getSource() instanceof TextField) {
            TextField barra = (TextField) e.getSource();
            String busqueda = barra.getText();
            if (e.getCode() != KeyCode.BACK_SPACE) {
                busqueda = busqueda.concat(e.getText());
            } else if (busqueda.length() > 0) {
                busqueda = busqueda.substring(0, busqueda.length() - 1);
            }
            if (menuEstudiante.isVisible()) {
                Estudiante estudiante = new Estudiante();
                agregaTabla(tablaPrincipalEstudiantes, estudiante.getTabla(busqueda));
            } else if (menuProfesor.isVisible()) {
                Profesor profesor = new Profesor();
                agregaTabla(tablaPrincipalProfesores, profesor.getTabla(busqueda));
            } else if (menuMateria.isVisible()) {
                Materia materia = new Materia();
                agregaTabla(tablaPrincipalMaterias, materia.getTabla(busqueda));
            } else if (menuConsultas.isVisible()) {
                if (!cajaCarreras.getValue().equals("Todas")) {
                    if (verEstudiantes.isSelected()) {
                        agregaTabla(tablaSecundariaEstudiantes,
                                new Estudiante().getEstudiantesCarrera(
                                        new BusquedaGrafica(tablaSecundariaEstudiantes,
                                                tablaSecundariaProfesores,
                                                tablaSecundariaMaterias, menuPrincipal, menuEstudiante,
                                                menuProfesor, menuMateria, menuConsultas, listaGrupos,
                                                eliminaGrupo, cajaCarreras).carreraPuesta(), busqueda));
                    }
                } else {
                    if (verEstudiantes.isSelected()) {
                        agregaTabla(tablaSecundariaEstudiantes, new Estudiante().getTabla(busqueda));
                    }
                    if (verProfesores.isSelected()) {
                        agregaTabla(tablaSecundariaProfesores, new Profesor().getTabla(busqueda));
                    }
                    if (verMaterias.isSelected()) {
                        agregaTabla(tablaSecundariaMaterias, new Materia().getTabla(busqueda));
                    }
                }
            }
        }
    }

    /**
     * Método para modificar un estudiante, profesor o materia de la base de
     * datos.
     *
     * @param e - es la acción del botón modificar.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void modificar(ActionEvent e) throws SQLException {
        new OperacionesGraficasSQL(menuPrincipal, menuEstudiante, menuProfesor,
                menuMateria, tablaPrincipalEstudiantes, tablaPrincipalProfesores,
                tablaPrincipalMaterias).modificarCampo(e);
    }

    /**
     * Método para finalizar el programa de forma segura.
     *
     * @param e - es la acción de la llamada del componente.
     */
    public void terminarEjecucion(ActionEvent e) {
        Conexion.cierraCanal();
        System.exit(0);
    }

    /**
     * Método para poner por default las tablas de la base de datos.
     *
     * @param e - es la acción del botón para limpiar las tablas
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void limpiaTablasBusqueda(ActionEvent e) throws SQLException {
        agregaTabla(tablaSecundariaEstudiantes, new Estudiante().getTabla());
        agregaTabla(tablaSecundariaProfesores, new Profesor().getTabla());
        agregaTabla(tablaSecundariaMaterias, new Materia().getTabla());
        verEstudiantes.setSelected(true);
        verProfesores.setSelected(true);
        verMaterias.setSelected(true);
        cajaCarreras.setValue("Todas");
    }

    /**
     * Método que despliega la licencia del programa y una pequeña ayuda.
     */
    public void msjAcerca(){
        String msj = " Programa de manejo de bases de datos y búsquedas no triviales:\n" +
                " Versión 1.0\n" +
                " Copyright (C) 2014  José Ricardo Rodríguez Abreu.\n" +
                " Facultad de Ciencias,\n" +
                " Universidad Nacional Autónoma de México, Mexico.\n" +
                " \n" +
                " Este programa es software libre; se puede redistribuir\n" +
                " y/o modificar en los términos establecidos por la\n" +
                " Licencia Pública General de GNU tal como fue publicada\n" +
                " por la Free Software Foundation en la versión 2 o\n" +
                " superior.\n" +
                " \n" +
                " Este programa es distribuido con la esperanza de que\n" +
                " resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho\n" +
                " sin la garantía implícita de COMERCIALIZACIÓN o\n" +
                " ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la\n" +
                " Licencia Pública General de GNU para mayores detalles.\n" +
                " \n" +
                " Con este programa se debe haber recibido una copia de la\n" +
                " Licencia Pública General de GNU, de no ser así, visite el\n" +
                " siguiente URL:\n" +
                " http://www.gnu.org/licenses/gpl.html\n" +
                " o escriba a la Free Software Foundation Inc.,\n" +
                " 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.\n\n"
                + "Proyecto 2 - Modelado y Programación. \n"
                + "Alumno: José Ricardo Rodríguez Abreu. \n"
                + "IMPORTANTE: Para más información sobre algún grupo, estudiante, \n profesor o materia,"
                + "dar doble click encima de éste mismo.";
        
        MessageBox.show(new Stage(), msj, "Un poco sobre el programa...",
                MessageBox.OK);
        
    }
    
    /**
     * Método principal que se ejecuta al inicial el programa.
     *
     * @param url - Ignoramos los parámetros.
     * @param rb - Parámetro ignorado.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            ResultSet totalEstudiantes, actuaria, biologia, compu, tierra, fisica, mate;
            int numeroEstudiantes, numActuario, numBiologo, numCompu, numTierra, numFisica, numMate;
            numeroEstudiantes = numActuario = numBiologo = numCompu = numTierra = numFisica = numMate = 0;
            double act, bio, comp, tierr, fis, mat;

            Estudiante estudiante = new Estudiante();

            totalEstudiantes = estudiante.getTabla();
            actuaria = estudiante.getEstudiantesCarrera(Facultad.ACTUARIA);
            biologia = estudiante.getEstudiantesCarrera(Facultad.BIOLOGIA);
            compu = estudiante.getEstudiantesCarrera(Facultad.CIENCIAS_DE_LA_COMPUTACION);
            tierra = estudiante.getEstudiantesCarrera(Facultad.CIENCIAS_DE_LA_TIERRA);
            fisica = estudiante.getEstudiantesCarrera(Facultad.FISICA);
            mate = estudiante.getEstudiantesCarrera(Facultad.MATEMATICAS);

            while (totalEstudiantes.next()) {
                numeroEstudiantes++;
            }
            while (actuaria.next()) {
                numActuario++;
            }
            while (biologia.next()) {
                numBiologo++;
            }
            while (compu.next()) {
                numCompu++;
            }
            while (tierra.next()) {
                numTierra++;
            }
            while (fisica.next()) {
                numFisica++;
            }
            while (mate.next()) {
                numMate++;
            }

            act = (double) (numActuario * 10) / numeroEstudiantes;
            bio = (double) (numBiologo * 10) / numeroEstudiantes;
            comp = (double) (numCompu * 10) / numeroEstudiantes;
            tierr = (double) (numTierra * 10) / numeroEstudiantes;
            fis = (double) (numFisica * 10) / numeroEstudiantes;
            mat = (double) (numMate * 10) / numeroEstudiantes;

            ObservableList<PieChart.Data> carrerasPorcentajes
                    = FXCollections.observableArrayList(
                            new PieChart.Data("Actuaría", act),
                            new PieChart.Data("Biología", bio),
                            new PieChart.Data("C. Computación", comp),
                            new PieChart.Data("C. Tierra", tierr),
                            new PieChart.Data("Física", fis),
                            new PieChart.Data("Matemáticas", mat));
            porcentajes.setData(carrerasPorcentajes);
        } catch (SQLException ex) {
            Conexion.cierraCanal();
            MessageBox.show(new Stage(), "¡ERROR FATAL! \n\n Ocurrió un "
                    + "error al querer acceder a la base de datos. "
                    + "Por favor, vuelva a intentarlo y revise que el archivo Facultad.db "
                    + "se encuentra en la carpeta /lib dentro de donde fue instalado el programa.\n"
                    + "Si continua el problema comuníquese a la siguiente cuenta de correo: \n"
                    + "ricardo_rodab@ciencias.unam.mx",
                    "Error crítico", MessageBox.ICON_ERROR | MessageBox.OK);
        }
    }

    /*
     * Método  IMPORTANTISIMO protegido para poner en una tabla las columnas y filas de la base.
     * NO MODIFICAR SIN SABER LO QUE SE ESTÁ HACIENDO
     * SI SE MODIFICA ESTE MÉTODO LA INTERFAZ GRÁFICA DEJA DE FUNCIONAR.
     */
    protected static void agregaTabla(TableView<ObservableList<String>> tabla, ResultSet resultado) throws SQLException {
        tabla.getColumns().clear();
        tabla.getItems().clear();
        final ResultSetMetaData datosResultado = resultado.getMetaData();
        List<String> columnas = new ArrayList<String>();
        for (int i = 1; i <= datosResultado.getColumnCount(); i++) {
            columnas.add(datosResultado.getColumnName(i).toUpperCase());
        }

        for (int i = 0; i < columnas.size(); i++) {
            final int j = i;
            final TableColumn<ObservableList<String>,String> col = new TableColumn<ObservableList<String>,String>(columnas.get(i));
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });
            col.setMinWidth(150);
            tabla.getColumns().add(col);
            //tabla.getColumns().add(col);
        }

        ObservableList<String> colum;
        while (resultado.next()) {
            colum = FXCollections.observableArrayList();
            for (int j = 1; j <= columnas.size(); j++) {
                if (datosResultado.getColumnType(j) == Types.INTEGER) {
                    Integer entero = resultado.getInt(j);
                    colum.add(entero.toString());
                } else {
                    colum.add(resultado.getString(j));
                }
            }
            tabla.getItems().add(colum);
        }
    }
} //Fin de ControladorGraficoController.java
