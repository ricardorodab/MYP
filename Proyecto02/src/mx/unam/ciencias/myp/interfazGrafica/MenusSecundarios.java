/* -------------------------------------------------------------------
 * MenusSecundarios.java
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

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfx.messagebox.MessageBox;
import static mx.unam.ciencias.myp.interfazGrafica.ControladorGraficoController.*;
import mx.unam.ciencias.myp.tablas.*;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Clase que define el comportamiento de los menús secundarios.</p>
 *
 * <p>
 * Clase de los menús secundarios, agrega, elimina, etc. .</p>
 */
public class MenusSecundarios extends ControladorGraficoController {

    /**
     * Constante para identificar el menu estudiante.
     */
    private static final int ESTUDIANTE = 0;
    /**
     * Constante para identificar el menu profesor.
     */
    private static final int PROFESOR = 1;
    /**
     * Constante para identificar el menu materia.
     */
    private static final int MATERIA = 2;

    //Cada uno de los componentes necesarios para hacer las búsquedas.
    //Como ya no podemos acceder como estáticos ahora hay que pasarlos de parámetros.
    private AnchorPane menuPrincipal;
    private AnchorPane menuEstudiante;
    private AnchorPane menuProfesor;
    private AnchorPane menuMateria;
    private AnchorPane menuCarrera;
    private TableView<ObservableList<String>> tablaPrincipalEstudiantes;
    private TableView<ObservableList<String>> tablaPrincipalProfesores;
    private TableView<ObservableList<String>> tablaPrincipalMaterias;
    private TreeView<Hyperlink> arbolEstudiantes;
    private TreeView<Hyperlink> arbolProfesores;
    private TreeView<Hyperlink> arbolMaterias;
    private Button eliminarEstudiantes;
    private Button modificarEstudiantes;
    private Button eliminarProfesores;
    private Button modificarProfesores;
    private Button eliminarMaterias;
    private Button modificarMaterias;
    private TableView<ObservableList<String>> tablaPrincipalCarreras;

    /**
     * Constructor que necesitamos para optener los componentes.
     *
     * @param menuPrincipal - es el menú principal del programa.
     * @param menuEstudiante - es el menú de estudiantes.
     * @param menuProfesor - es el menú de profesores.
     * @param menuMateria - es el menú de materias.
     * @param menuCarrera - es el menú de carreras.
     * @param tablaPrincipalEstudiantes - es la tabla del menú de estudiantes.
     * @param tablaPrincipalProfesores - es la tabla del menú de profesores.
     * @param tablaPrincipalMaterias - es la tabla del menú de materias.
     * @param arbolEstudiantes - es el árbol del menú de estudiantes.
     * @param arbolProfesores - es el árbol del menú de profesores.
     * @param arbolMaterias - es el árbol del menú de materias.
     * @param eliminarEstudiantes - es el botón de eliminar estudiantes.
     * @param modificarEstudiantes - es el botón de modificar estudiantes.
     * @param eliminarProfesores - es el botón de eliminar profesores.
     * @param modificarProfesores - es el botón de modificar profesores.
     * @param eliminarMaterias - es el botón de eliminar materias.
     * @param modificarMaterias - es el botón de modificar materias.
     * @param tablaPrincipalCarreras - es la tabla del menú de carreras.
     */
    public MenusSecundarios(
            AnchorPane menuPrincipal,
            AnchorPane menuEstudiante,
            AnchorPane menuProfesor,
            AnchorPane menuMateria,
            AnchorPane menuCarrera,
            TableView<ObservableList<String>> tablaPrincipalEstudiantes,
            TableView<ObservableList<String>> tablaPrincipalProfesores,
            TableView<ObservableList<String>> tablaPrincipalMaterias,
            TreeView<Hyperlink> arbolEstudiantes,
            TreeView<Hyperlink> arbolProfesores,
            TreeView<Hyperlink> arbolMaterias,
            Button eliminarEstudiantes,
            Button modificarEstudiantes,
            Button eliminarProfesores,
            Button modificarProfesores,
            Button eliminarMaterias,
            Button modificarMaterias,
            TableView<ObservableList<String>> tablaPrincipalCarreras
    ) {
        this.menuPrincipal = menuPrincipal;
        this.menuEstudiante = menuEstudiante;
        this.menuProfesor = menuProfesor;
        this.menuMateria = menuMateria;
        this.menuCarrera = menuCarrera;
        this.tablaPrincipalEstudiantes = tablaPrincipalEstudiantes;
        this.tablaPrincipalProfesores = tablaPrincipalProfesores;
        this.tablaPrincipalMaterias = tablaPrincipalMaterias;
        this.arbolEstudiantes = arbolEstudiantes;
        this.arbolProfesores = arbolProfesores;
        this.arbolMaterias = arbolMaterias;
        this.eliminarEstudiantes = eliminarEstudiantes;
        this.modificarEstudiantes = modificarEstudiantes;
        this.eliminarProfesores = eliminarProfesores;
        this.modificarProfesores = modificarProfesores;
        this.eliminarMaterias = eliminarMaterias;
        this.modificarMaterias = modificarMaterias;
        this.tablaPrincipalCarreras = tablaPrincipalCarreras;
    }

    /**
     * Método que muestra los 4 menús secundario del programa.
     *
     * @param e - es el identificador del hyperlink del que fue llamado.
     * @throws SQLException - en caso de tener algún problema con la base.
     */
    public void menuSecundario(ActionEvent e) throws SQLException {
        Hyperlink hyperFuente = (Hyperlink) e.getSource();
        hyperFuente.setVisited(false);

        //Caso donde muestra el menú secundario de estudiantes.
        if (hyperFuente.getText().equalsIgnoreCase("ESTUDIANTES")) {
            Estudiante estudiante = new Estudiante();

            //Poblamos la tabla de los estudiantes.
            agregaTabla(tablaPrincipalEstudiantes, estudiante.getTabla());

            //Hacemos un cambio de menú.
            menuPrincipal.setDisable(true);
            menuPrincipal.setVisible(false);
            menuEstudiante.setDisable(false);
            menuEstudiante.setVisible(true);

            //Instanciamos los links para el árbol.
            Hyperlink raiz = new Hyperlink("Grupos");
            Hyperlink agregaRelaciones = new Hyperlink("Agregar a grupo");
            Hyperlink eliminaRelaciones = new Hyperlink("Eliminar de grupo");

            raiz.setDisable(true);
            agregaRelaciones.setDisable(true);
            eliminaRelaciones.setDisable(true);

            //Creamos todos los hyperlinks necesarios.
            final Hyperlink agregaRelaciones1 = new Hyperlink("Nuevo grupo");
            final Hyperlink agregaRelaciones2 = new Hyperlink("Agregar a un grupo");
            TreeItem<Hyperlink> rootAgregaRelaciones
                    = new TreeItem<Hyperlink>(agregaRelaciones);
            TreeItem<Hyperlink> agrega1 = new TreeItem<Hyperlink>(agregaRelaciones1);
            TreeItem<Hyperlink> agrega2 = new TreeItem<Hyperlink>(agregaRelaciones2);
            rootAgregaRelaciones.getChildren().add(0,agrega1);
            rootAgregaRelaciones.getChildren().add(1, agrega2);

            //Agrega un nuevo grupo.
            agregaRelaciones1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    try {
                        agregaRelaciones1.setVisited(false);
                        agregaNuevoGrupo();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });

            //Agrega a grupo existente.
            agregaRelaciones2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        agregaRelaciones2.setVisited(false);
                        agregaAGrupoExistente();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });

            final Hyperlink eliminaRelaciones1 = new Hyperlink("Eliminar de un solo grupo");
            final Hyperlink eliminaRelaciones2 = new Hyperlink("Eliminar de todos los grupos");
            TreeItem<Hyperlink> rootEliminaRealaciones
                    = new TreeItem<Hyperlink>(eliminaRelaciones);
            TreeItem<Hyperlink> elinina1 = new TreeItem<Hyperlink>(eliminaRelaciones1);
            TreeItem<Hyperlink> elinina2 = new TreeItem<Hyperlink>(eliminaRelaciones2);
            rootEliminaRealaciones.getChildren().add(0,elinina1);
            rootEliminaRealaciones.getChildren().add(1, elinina2);
                    

            //Elimina de sólo un grupo.
            eliminaRelaciones1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    try {
                        eliminaRelaciones1.setVisited(false);
                        eliminarDeUnGrupo();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                        Logger.getLogger(MenusSecundarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            //Elimina de todos los grupos.
            eliminaRelaciones2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        eliminaRelaciones2.setVisited(false);
                        eliminarDeTodosLosGrupos();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });

            //Para evitar nullPointerExceptions
            eliminaRelaciones1.setDisable(true);
            eliminaRelaciones2.setDisable(true);
            agregaRelaciones1.setDisable(true);
            agregaRelaciones2.setDisable(true);

            final TreeItem<Hyperlink> root = new TreeItem<Hyperlink>(raiz);
            root.setExpanded(true);
            rootAgregaRelaciones.setExpanded(true);
            rootEliminaRealaciones.setExpanded(true);
            root.getChildren().add(0, rootAgregaRelaciones);
            root.getChildren().add(1, rootEliminaRealaciones);
            arbolEstudiantes.setRoot(root);
            //Avisamos cuando haya un alumno seleccionado.
            tablaPrincipalEstudiantes.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if (t.getClickCount() >= 2
                            && tablaPrincipalEstudiantes.getSelectionModel().getSelectedItem()
                            != null) {
                        try {
                            muestraInfo("estudiante");
                        } catch (SQLException ex) {
                            Conexion.cierraCanal();
                            MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                    + "error inesperado. Por favor, vuelva a intentarlo.",
                                    "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                    } else {
                        eliminarEstudiantes.setDisable(false);
                        modificarEstudiantes.setDisable(false);
                    }
                    //Creamos un thread que vea cuando está o no seleccionado.
                    final Thread botones = new Thread(new Task() {
                        @Override
                        protected Void call() throws Exception {
                            while (tablaPrincipalEstudiantes.getSelectionModel().getSelectedItem()
                                    != null) {
                                eliminaRelaciones1.setDisable(false);
                                eliminaRelaciones2.setDisable(false);
                                agregaRelaciones1.setDisable(false);
                                agregaRelaciones2.setDisable(false);
                            }
                            //Cuando se acabe la selección volvemos a desactivar.
                            eliminaRelaciones1.setDisable(true);
                            eliminaRelaciones2.setDisable(true);
                            agregaRelaciones1.setDisable(true);
                            agregaRelaciones2.setDisable(true);
                            eliminarEstudiantes.setDisable(true);
                            modificarEstudiantes.setDisable(true);
                            return null;
                        }
                    });
                    botones.start();
                }
            });
            //Caso donde muestra el menú secundario de profesores.
        } else if (hyperFuente.getText().equalsIgnoreCase("PROFESORES")) {
            Profesor profesor = new Profesor();

            //Poblamos la tabla de los estudiantes.
            agregaTabla(tablaPrincipalProfesores, profesor.getTabla());

            //Hacemos un cambio de menú.
            Hyperlink hyperProfeso = (Hyperlink) e.getSource();
            hyperProfeso.setVisited(false);
            menuPrincipal.setDisable(true);
            menuPrincipal.setVisible(false);
            menuProfesor.setDisable(false);
            menuProfesor.setVisible(true);

            //Instanciamos los links para el árbol.
            Hyperlink raiz = new Hyperlink("Grupos");
            Hyperlink agregaRelaciones = new Hyperlink("Agregar a...");
            Hyperlink eliminaRelaciones = new Hyperlink("Eliminar de..");

            raiz.setDisable(true);
            agregaRelaciones.setDisable(true);
            eliminaRelaciones.setDisable(true);

            //Creamos todos los hyperlinks necesarios.
            final Hyperlink agregaRelaciones1 = new Hyperlink("Nuevo grupo");
            final Hyperlink agregaRelaciones2 = new Hyperlink("Agregar a un grupo");
            TreeItem<Hyperlink> rootAgregaRelaciones
                    = new TreeItem<Hyperlink>(agregaRelaciones);
            TreeItem<Hyperlink> agrega1 = new TreeItem<Hyperlink>(agregaRelaciones1);
            TreeItem<Hyperlink> agrega2 = new TreeItem<Hyperlink>(agregaRelaciones2);
            rootAgregaRelaciones.getChildren().add(0,agrega1);
            rootAgregaRelaciones.getChildren().add(1, agrega2);

            //Agrega un nuevo grupo.
            agregaRelaciones1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    try {
                        agregaRelaciones1.setVisited(false);
                        agregaNuevoGrupo();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });
            //Agrega a grupo existente.
            agregaRelaciones2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        agregaRelaciones2.setVisited(false);
                        agregaAGrupoExistente();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });

            final Hyperlink eliminaRelaciones1 = new Hyperlink("Eliminar de un solo grupo");
            final Hyperlink eliminaRelaciones2 = new Hyperlink("Eliminar de todos los grupos");
            TreeItem<Hyperlink> rootEliminaRealaciones
                    = new TreeItem<Hyperlink>(eliminaRelaciones);
            TreeItem<Hyperlink> elinina1 = new TreeItem<Hyperlink>(eliminaRelaciones1);
            TreeItem<Hyperlink> elinina2 = new TreeItem<Hyperlink>(eliminaRelaciones2);
            rootEliminaRealaciones.getChildren().add(0,elinina1);
            rootEliminaRealaciones.getChildren().add(1, elinina2);

            //Elimina de sólo un grupo.
            eliminaRelaciones1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        eliminaRelaciones1.setVisited(false);
                        eliminarDeUnGrupo();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });
            //Elimina de todos los grupos.
            eliminaRelaciones2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        eliminaRelaciones2.setVisited(false);
                        eliminarDeTodosLosGrupos();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });

            //Para evitar nullPointerExceptions
            eliminaRelaciones1.setDisable(true);
            eliminaRelaciones2.setDisable(true);
            agregaRelaciones1.setDisable(true);
            agregaRelaciones2.setDisable(true);

            final TreeItem<Hyperlink> root = new TreeItem<Hyperlink>(raiz);
            root.setExpanded(true);
            rootAgregaRelaciones.setExpanded(true);
            rootEliminaRealaciones.setExpanded(true);
            root.getChildren().add(0, rootAgregaRelaciones);
            root.getChildren().add(1, rootEliminaRealaciones);
            arbolProfesores.setRoot(root);
            //Avisamos cuando haya un profesor seleccionado.
            tablaPrincipalProfesores.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if (t.getClickCount() == 2
                            && tablaPrincipalProfesores.getSelectionModel().getSelectedItem()
                            != null) {
                        try {
                            muestraInfo("profesor");
                        } catch (SQLException ex) {
                            Conexion.cierraCanal();
                            MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                    + "error inesperado. Por favor, vuelva a intentarlo.",
                                    "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                    } else {
                        eliminarProfesores.setDisable(false);
                        modificarProfesores.setDisable(false);
                    }
                    final Thread botones = new Thread(new Task() {

                        @Override
                        protected Void call() throws Exception {
                            while (tablaPrincipalProfesores.getSelectionModel().getSelectedItem()
                                    != null) {
                                eliminaRelaciones1.setDisable(false);
                                eliminaRelaciones2.setDisable(false);
                                agregaRelaciones1.setDisable(false);
                                agregaRelaciones2.setDisable(false);
                            }
                            eliminaRelaciones1.setDisable(true);
                            eliminaRelaciones2.setDisable(true);
                            agregaRelaciones1.setDisable(true);
                            agregaRelaciones2.setDisable(true);
                            eliminarProfesores.setDisable(true);
                            modificarProfesores.setDisable(true);
                            return null;
                        }
                    });
                    botones.start();
                }
            });
            //Caso donde muestra el menú secundario de materias.
        } else if (hyperFuente.getText().equalsIgnoreCase("MATERIAS")) {
            Materia materia = new Materia();

            //Poblamos la tabla de los estudiantes.
            agregaTabla(tablaPrincipalMaterias, materia.getTabla());

            //Hacemos un cambio de menú.
            Hyperlink hyperMateria = (Hyperlink) e.getSource();
            hyperMateria.setVisited(false);
            menuPrincipal.setDisable(true);
            menuPrincipal.setVisible(false);
            menuMateria.setDisable(false);
            menuMateria.setVisible(true);

            //Instanciamos los links para el árbol.
            Hyperlink raiz = new Hyperlink("Grupos");
            Hyperlink agregaRelaciones = new Hyperlink("Agregar a grupo");
            Hyperlink eliminaRelaciones = new Hyperlink("Eliminar de grupo");

            raiz.setDisable(true);
            agregaRelaciones.setDisable(true);
            eliminaRelaciones.setDisable(true);

            //Creamos todos los hyperlinks necesarios.
            final Hyperlink agregaRelaciones1 = new Hyperlink("Nuevo grupo");
            final Hyperlink agregaRelaciones2 = new Hyperlink("Agregar a un grupo");
            TreeItem<Hyperlink> rootAgregaRelaciones
                    = new TreeItem<Hyperlink>(agregaRelaciones);
            TreeItem<Hyperlink> agrega1 = new TreeItem<Hyperlink>(agregaRelaciones1);
            TreeItem<Hyperlink> agrega2 = new TreeItem<Hyperlink>(agregaRelaciones2);
            rootAgregaRelaciones.getChildren().add(0,agrega1);
            rootAgregaRelaciones.getChildren().add(1, agrega2);

            //Agrega un nuevo grupo.
            agregaRelaciones1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    try {
                        agregaRelaciones1.setVisited(false);
                        agregaNuevoGrupo();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });
            //Agrega a grupo existente.
            agregaRelaciones2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        agregaRelaciones2.setVisited(false);
                        agregaAGrupoExistente();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });

            final Hyperlink eliminaRelaciones1 = new Hyperlink("Eliminar de un solo grupo");
            final Hyperlink eliminaRelaciones2 = new Hyperlink("Eliminar de todos los grupos");
            TreeItem<Hyperlink> rootEliminaRealaciones
                    = new TreeItem<Hyperlink>(eliminaRelaciones);
            TreeItem<Hyperlink> elinina1 = new TreeItem<Hyperlink>(eliminaRelaciones1);
            TreeItem<Hyperlink> elinina2 = new TreeItem<Hyperlink>(eliminaRelaciones2);
            rootEliminaRealaciones.getChildren().add(0,elinina1);
            rootEliminaRealaciones.getChildren().add(1, elinina2);

            //Elimina de sólo un grupo.
            eliminaRelaciones1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        eliminaRelaciones1.setVisited(false);
                        eliminarDeUnGrupo();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });
            //Elimina de todos los grupos
            eliminaRelaciones2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        eliminaRelaciones2.setVisited(false);
                        eliminarDeTodosLosGrupos();
                    } catch (SQLException ex) {
                        Conexion.cierraCanal();
                        MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                + "error inesperado. Por favor, vuelva a intentarlo.",
                                "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                    }
                }
            });

            //Para evitar nullPointerExceptions
            eliminaRelaciones1.setDisable(true);
            eliminaRelaciones2.setDisable(true);
            agregaRelaciones1.setDisable(true);
            agregaRelaciones2.setDisable(true);

            TreeItem<Hyperlink> root = new TreeItem<Hyperlink>(raiz);
            root.setExpanded(true);
            rootAgregaRelaciones.setExpanded(true);
            rootEliminaRealaciones.setExpanded(true);
            root.getChildren().add(0, rootAgregaRelaciones);
            root.getChildren().add(1, rootEliminaRealaciones);
            arbolMaterias.setRoot(root);
            //Avisamos cuando haya un profesor seleccionado.
            tablaPrincipalMaterias.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if (t.getClickCount() == 2
                            && tablaPrincipalMaterias.getSelectionModel().getSelectedItem()
                            != null) {
                        try {
                            muestraInfo("materia");
                        } catch (SQLException ex) {
                            Conexion.cierraCanal();
                            MessageBox.show(new Stage(), "¡UPS! \n\n Ocurrió un "
                                    + "error inesperado. Por favor, vuelva a intentarlo.",
                                    "Lamento las molestias", MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                    } else {
                        eliminarMaterias.setDisable(false);
                        modificarMaterias.setDisable(false);
                    }
                    final Thread botones = new Thread(new Task() {

                        @Override
                        protected Void call() throws Exception {
                            while (tablaPrincipalMaterias.getSelectionModel().getSelectedItem()
                                    != null) {
                                eliminaRelaciones1.setDisable(false);
                                eliminaRelaciones2.setDisable(false);
                                agregaRelaciones1.setDisable(false);
                                agregaRelaciones2.setDisable(false);
                            }
                            eliminaRelaciones1.setDisable(true);
                            eliminaRelaciones2.setDisable(true);
                            agregaRelaciones1.setDisable(true);
                            agregaRelaciones2.setDisable(true);
                            eliminarMaterias.setDisable(true);
                            modificarMaterias.setDisable(true);
                            return null;
                        }
                    });
                    botones.start();
                }
            });
        } else if (hyperFuente.getText().equalsIgnoreCase("CARRERAS")) {
            Carrera carrera = new Carrera();
            agregaTabla(tablaPrincipalCarreras, carrera.getTabla());
            menuPrincipal.setDisable(true);
            menuPrincipal.setVisible(false);
            menuCarrera.setDisable(false);
            menuCarrera.setVisible(true);
        }
    }

    //Método privado para agregar un nuevo grupo.
    private void agregaNuevoGrupo() throws SQLException {
        if (menuEstudiante.isVisible()) {
            agregaNuevoGrupo(
                    tablaPrincipalEstudiantes.getSelectionModel().getSelectedItems(),
                    ESTUDIANTE);
        } else if (menuProfesor.isVisible()) {
            agregaNuevoGrupo(
                    tablaPrincipalProfesores.getSelectionModel().getSelectedItems(),
                    PROFESOR);
        } else if (menuMateria.isVisible()) {
            agregaNuevoGrupo(
                    tablaPrincipalMaterias.getSelectionModel().getSelectedItems(),
                    MATERIA);
        }
    }

    //Método privado para agregar a un grupo existente.
    private void agregaAGrupoExistente() throws SQLException {
        if (menuEstudiante.isVisible()) {
            agregaAGrupoExistente(ESTUDIANTE);
        } else if (menuProfesor.isVisible()) {
            agregaAGrupoExistente(PROFESOR);
        } else if (menuMateria.isVisible()) {
            agregaAGrupoExistente(MATERIA);
        }
    }

    //Método privado para eliminar de un solo grupo.
    private void eliminarDeUnGrupo() throws SQLException {
        if (menuEstudiante.isVisible()) {
            eliminarDeUnGrupo(ESTUDIANTE);
        } else if (menuProfesor.isVisible()) {
            eliminarDeUnGrupo(PROFESOR);
        } else if (menuMateria.isVisible()) {
            eliminarDeUnGrupo(MATERIA);
        }
    }

    //Método privado para eliminar de todos los grupos.
    private void eliminarDeTodosLosGrupos() throws SQLException {
        if (menuEstudiante.isVisible()) {
            eliminarDeTodosLosGrupos(ESTUDIANTE);
        } else if (menuProfesor.isVisible()) {
            eliminarDeTodosLosGrupos(PROFESOR);
        } else if (menuMateria.isVisible()) {
            eliminarDeTodosLosGrupos(MATERIA);
        }
    }

    //Método privado para eliminar de todos los grupos.
    private void eliminarDeTodosLosGrupos(int objeto) throws SQLException {
        ObservableList<ObservableList<String>> datos = null;
        switch (objeto) {
            case ESTUDIANTE:
                datos = tablaPrincipalEstudiantes.getSelectionModel().getSelectedItems();
                break;
            case PROFESOR:
                datos = tablaPrincipalProfesores.getSelectionModel().getSelectedItems();
                break;
            case MATERIA:
                datos = tablaPrincipalMaterias.getSelectionModel().getSelectedItems();
                break;
        }
        String columna = datos.get(0).toString();
        final String[] arregloColumna
                = columna.substring(1, columna.length() - 1).split(",");
        arregloColumna[0] = arregloColumna[0].trim();
        arregloColumna[1] = arregloColumna[1].trim();
        arregloColumna[2] = arregloColumna[2].trim();
        if (objeto == ESTUDIANTE) {
            menuEstudiante.setDisable(true);
            arregloColumna[3] = arregloColumna[3].trim();
            int confirmacion = MessageBox.show(new Stage(),
                    "Favor de confirmar la operación: \n\n ¿Desea eliminar por "
                    + "completo de todos los grupos a " + arregloColumna[1]
                    + " " + arregloColumna[2]
                    + " con número de cuenta " + arregloColumna[0] + "?",
                    "Eliminar de grupos",
                    MessageBox.ICON_WARNING | MessageBox.YES | MessageBox.NO);
            if (confirmacion == MessageBox.YES) {
                Grupo grupo = new Grupo();
                Integer idEstudiante = Integer.parseInt(arregloColumna[0]);
                Estudiante estudiante = new Estudiante();
                estudiante.setId(idEstudiante);
                grupo.eliminaEstudiante(estudiante);
                MessageBox.show(new Stage(),
                        "Se eliminó correctamente al estudiante.",
                        "Estudiante eliminado", MessageBox.ICON_INFORMATION | MessageBox.OK);
                menuEstudiante.setDisable(false);
            } else {
                menuEstudiante.setDisable(false);
            }

        } else if (objeto == PROFESOR) {
            menuProfesor.setDisable(true);
            int confirmacion = MessageBox.show(new Stage(),
                    "Favor de confirmar la operación: \n\n ¿Desea eliminar por "
                    + "completo de todos los grupos al " + arregloColumna[1]
                    + " " + arregloColumna[2]
                    + " con número de trabajador " + arregloColumna[0] + "?",
                    "Eliminar de grupos",
                    MessageBox.ICON_WARNING | MessageBox.YES | MessageBox.NO);
            if (confirmacion == MessageBox.YES) {
                Grupo grupo = new Grupo();
                Integer idProfesor = Integer.parseInt(arregloColumna[0]);
                Profesor profesor = new Profesor();
                profesor.setId(idProfesor);
                grupo.eliminaProfesor(profesor);
                MessageBox.show(new Stage(),
                        "Se eliminó correctamente al profesor.",
                        "Profesor eliminado", MessageBox.ICON_INFORMATION | MessageBox.OK);
                menuProfesor.setDisable(false);
            } else {
                menuProfesor.setDisable(false);
            }
        } else if (objeto == MATERIA) {
            menuMateria.setDisable(true);
            int confirmacion = MessageBox.show(new Stage(),
                    "Favor de confirmar la operación: \n\n ¿Desea eliminar por "
                    + "completo de todos los grupos la materia de " + arregloColumna[1]
                    + " con clave " + arregloColumna[0] + "?",
                    "Eliminar de grupos",
                    MessageBox.ICON_WARNING | MessageBox.YES | MessageBox.NO);
            if (confirmacion == MessageBox.YES) {
                Grupo grupo = new Grupo();
                Integer clave = Integer.parseInt(arregloColumna[0]);
                Integer semestre = Integer.parseInt(arregloColumna[2]);
                Materia materia = new Materia();
                materia.setId(clave);
                grupo.eliminaMateria(materia);
                MessageBox.show(new Stage(),
                        "Se eliminó correctamente la materia.",
                        "Materia eliminada", MessageBox.ICON_INFORMATION | MessageBox.OK);
                menuMateria.setDisable(false);
            } else {
                menuMateria.setDisable(false);
            }
        }
    }

    //Método privado para eliminar de un solo grupo.
    private void eliminarDeUnGrupo(int objeto) throws SQLException {
        final int caso = objeto;
        final Stage seleccionarGrupo = new Stage();
        seleccionarGrupo.setMinWidth(525);
        seleccionarGrupo.setMinHeight(400);
        if (menuEstudiante.isVisible()) {
            menuEstudiante.setDisable(true);
        } else if (menuProfesor.isVisible()) {
            menuProfesor.setDisable(true);
        } else if (menuMateria.isVisible()) {
            menuMateria.setDisable(true);
        }
        seleccionarGrupo.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                switch (caso) {
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
        Text encabezado = new Text("AGREGAR A GRUPO");
        ObservableList<ObservableList<String>> aModificar = null;
        switch (caso) {
            case ESTUDIANTE:
                aModificar = tablaPrincipalEstudiantes.getSelectionModel().getSelectedItems();
                break;
            case PROFESOR:
                aModificar = tablaPrincipalProfesores.getSelectionModel().getSelectedItems();
                break;
            case MATERIA:
                aModificar = tablaPrincipalMaterias.getSelectionModel().getSelectedItems();
                break;
        }
        String columna = aModificar.get(0).toString();
        final String[] arregloColumna
                = columna.substring(1, columna.length() - 1).split(",");
        arregloColumna[0] = arregloColumna[0].trim();
        arregloColumna[1] = arregloColumna[1].trim();
        arregloColumna[2] = arregloColumna[2].trim();

        final TextField numDeGrupo = new TextField();
        numDeGrupo.setMinHeight(10);
        numDeGrupo.setMaxWidth(100);

        Label numDeGrupoLabel = new Label("#Grupo");
        Label listaLabel = new Label("Lista de Grupos");

        VBox tablaBox = new VBox(10);
        VBox etiquetas = new VBox(20);
        HBox union = new HBox(15);
        HBox entrada = new HBox(15);
        union.setAlignment(Pos.CENTER);
        HBox botones = new HBox(30);

        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");

        BorderPane border = new BorderPane();
        border.setMinSize(400, 200);

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
        ListView<String> listaView = new ListView<String>(vistaLista);
        listaView.setMaxSize(150, 300);

        tablaBox.getChildren().add(listaView);
        etiquetas.getChildren().add(listaLabel);

        entrada.getChildren().addAll(numDeGrupo, numDeGrupoLabel);
        union.getChildren().addAll(tablaBox, etiquetas);
        botones.getChildren().addAll(aceptar, cancelar);

        border.setTop(encabezado);
        BorderPane.setAlignment(encabezado, Pos.CENTER);
        border.setRight(union);
        BorderPane.setAlignment(union, Pos.CENTER);
        BorderPane.setMargin(union, new Insets(30, 10, 0, 10));
        border.setCenter(entrada);
        BorderPane.setMargin(entrada, new Insets(30, 0, 0, 50));
        border.setBottom(botones);
        BorderPane.setAlignment(botones, Pos.CENTER_RIGHT);
        BorderPane.setMargin(botones, new Insets(0, 10, 20, 130));

        Scene scene = new Scene(border, 525, 120, Color.LIGHTGREY);
        seleccionarGrupo.setScene(scene);
        seleccionarGrupo.setResizable(true);
        seleccionarGrupo.setMaxHeight(400);
        seleccionarGrupo.setMaxWidth(525);
        seleccionarGrupo.setMinHeight(400);
        seleccionarGrupo.setMinWidth(525);
        seleccionarGrupo.setTitle("Agregar a grupo");
        seleccionarGrupo.show();

        cancelar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                switch (caso) {
                    case ESTUDIANTE:
                        menuEstudiante.setDisable(false);
                        break;
                    case PROFESOR:
                        menuProfesor.setDisable(false);
                        break;
                    case MATERIA:
                        menuMateria.setDisable(false);
                        break;
                }
                seleccionarGrupo.close();
            }
        });
        aceptar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                switch (caso) {
                    case ESTUDIANTE:
                        String numeroGrupoE = numDeGrupo.getText();
                        if (EsNumero.esNumero(numeroGrupoE)) {
                            try {
                                Integer grupo = Integer.parseInt(numeroGrupoE);
                                Integer id = Integer.parseInt(arregloColumna[0]);
                                Grupo grupoEliminar = new Grupo(grupo);
                                if (!grupoEliminar.existeGrupo(grupo)) {
                                    throw new SQLException();
                                }
                                Estudiante estudiante = new Estudiante();
                                estudiante.setId(id);
                                grupoEliminar.eliminaEstudiante(estudiante);
                                menuEstudiante.setDisable(false);
                                seleccionarGrupo.close();
                            } catch (SQLException ex) {
                                Conexion.cierraCanal();
                                MessageBox.show(new Stage(),
                                        "Ocurrió un error, favor de revisar si el grupo existe",
                                        "¡Ups! Error al eliminar.",
                                        MessageBox.ICON_ERROR | MessageBox.OK);
                            }
                        } else {
                            MessageBox.show(new Stage(),
                                    "La entrada no es un número correcto.",
                                    "¡Error! Problemas con el número.",
                                    MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                        break;
                    case PROFESOR:
                        String numeroGrupoP = numDeGrupo.getText();
                        if (EsNumero.esNumero(numeroGrupoP)) {
                            try {
                                Integer grupo = Integer.parseInt(numeroGrupoP);
                                Integer id = Integer.parseInt(arregloColumna[0]);
                                Grupo grupoElimina = new Grupo(grupo);
                                if (!grupoElimina.existeGrupo(grupo)) {
                                    throw new SQLException();
                                }
                                Profesor profesor = new Profesor();
                                profesor.setId(id);
                                grupoElimina.eliminaProfesor(profesor);
                                menuProfesor.setDisable(false);
                                seleccionarGrupo.close();
                            } catch (SQLException ex) {
                                Conexion.cierraCanal();
                                MessageBox.show(new Stage(),
                                        "Ocurrió un error, favor de revisar si el grupo existe.",
                                        "¡Ups! Error al eliminar.",
                                        MessageBox.ICON_ERROR | MessageBox.OK);
                            }
                        } else {
                            MessageBox.show(new Stage(),
                                    "La entrada no es un número correcto.",
                                    "¡Error! Problemas con el número.",
                                    MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                        break;
                    case MATERIA:
                        String numeroGrupoM = numDeGrupo.getText();
                        if (EsNumero.esNumero(numeroGrupoM)) {
                            try {
                                Integer grupo = Integer.parseInt(numeroGrupoM);
                                Integer id = Integer.parseInt(arregloColumna[0]);
                                Grupo grupoEliminar = new Grupo(grupo);
                                if (!grupoEliminar.existeGrupo(grupo)) {
                                    throw new SQLException();
                                }
                                Materia materia = new Materia();
                                materia.setId(id);
                                grupoEliminar.eliminaMateria(materia);
                                menuMateria.setDisable(false);
                                seleccionarGrupo.close();
                            } catch (SQLException ex) {
                                Conexion.cierraCanal();
                                MessageBox.show(new Stage(),
                                        "Ocurrió un error, favor de revisar si el grupo existe.",
                                        "¡Ups! Error al eliminar.",
                                        MessageBox.ICON_ERROR | MessageBox.OK);
                            }
                        } else {
                            MessageBox.show(new Stage(),
                                    "La entrada no es un número correcto.",
                                    "¡Error! Problemas con el número.",
                                    MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                        break;
                }
            }
        });
    }

    //Método privado para agregar a un grupo existente.
    private void agregaAGrupoExistente(int objeto) throws SQLException {
        final int caso = objeto;
        final Stage seleccionarGrupo = new Stage();
        seleccionarGrupo.setMinWidth(525);
        seleccionarGrupo.setMinHeight(400);
        if (menuEstudiante.isVisible()) {
            menuEstudiante.setDisable(true);
        } else if (menuProfesor.isVisible()) {
            menuProfesor.setDisable(true);
        } else if (menuMateria.isVisible()) {
            menuMateria.setDisable(true);
        }
        seleccionarGrupo.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                switch (caso) {
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
        Text encabezado = new Text("AGREGAR A GRUPO");
        ObservableList<ObservableList<String>> aModificar = null;
        switch (caso) {
            case ESTUDIANTE:
                aModificar = tablaPrincipalEstudiantes.getSelectionModel().getSelectedItems();
                break;
            case PROFESOR:
                aModificar = tablaPrincipalProfesores.getSelectionModel().getSelectedItems();
                break;
            case MATERIA:
                aModificar = tablaPrincipalMaterias.getSelectionModel().getSelectedItems();
                break;
        }
        String columna = aModificar.get(0).toString();
        final String[] arregloColumna
                = columna.substring(1, columna.length() - 1).split(",");
        arregloColumna[0] = arregloColumna[0].trim();
        arregloColumna[1] = arregloColumna[1].trim();
        arregloColumna[2] = arregloColumna[2].trim();

        final TextField numDeGrupo = new TextField();
        numDeGrupo.setMinHeight(10);
        numDeGrupo.setMaxWidth(100);

        Label numDeGrupoLabel = new Label("#Grupo");
        Label listaLabel = new Label("Lista de Grupos");

        VBox tablaBox = new VBox(10);
        VBox etiquetas = new VBox(20);
        HBox union = new HBox(15);
        HBox entrada = new HBox(15);
        union.setAlignment(Pos.CENTER);
        HBox botones = new HBox(30);

        Button aceptar = new Button("Aceptar");
        Button cancelar = new Button("Cancelar");

        BorderPane border = new BorderPane();
        border.setMinSize(400, 200);

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
        ListView<String> listaView = new ListView<String>(vistaLista);
        listaView.setMaxSize(150, 300);

        tablaBox.getChildren().add(listaView);
        etiquetas.getChildren().add(listaLabel);

        entrada.getChildren().addAll(numDeGrupo, numDeGrupoLabel);
        union.getChildren().addAll(tablaBox, etiquetas);
        botones.getChildren().addAll(aceptar, cancelar);

        border.setTop(encabezado);
        BorderPane.setAlignment(encabezado, Pos.CENTER);
        border.setRight(union);
        BorderPane.setAlignment(union, Pos.CENTER);
        BorderPane.setMargin(union, new Insets(30, 10, 0, 10));
        border.setCenter(entrada);
        BorderPane.setMargin(entrada, new Insets(30, 0, 0, 50));
        border.setBottom(botones);
        BorderPane.setAlignment(botones, Pos.CENTER_RIGHT);
        BorderPane.setMargin(botones, new Insets(0, 10, 20, 130));

        Scene scene = new Scene(border, 525, 120, Color.LIGHTGREY);
        seleccionarGrupo.setScene(scene);
        seleccionarGrupo.setResizable(true);
        seleccionarGrupo.setMaxHeight(400);
        seleccionarGrupo.setMaxWidth(525);
        seleccionarGrupo.setMinHeight(400);
        seleccionarGrupo.setMinWidth(525);
        seleccionarGrupo.setTitle("Agregar a grupo");
        seleccionarGrupo.show();

        cancelar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                switch (caso) {
                    case ESTUDIANTE:
                        menuEstudiante.setDisable(false);
                        break;
                    case PROFESOR:
                        menuProfesor.setDisable(false);
                        break;
                    case MATERIA:
                        menuMateria.setDisable(false);
                        break;
                }
                seleccionarGrupo.close();
            }
        });
        aceptar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                switch (caso) {
                    case ESTUDIANTE:
                        String numeroGrupoE = numDeGrupo.getText();
                        if (EsNumero.esNumero(numeroGrupoE)) {
                            try {
                                Integer grupo = Integer.parseInt(numeroGrupoE);
                                Integer id = Integer.parseInt(arregloColumna[0]);
                                Grupo grupoAgregar = new Grupo(grupo);
                                if (!grupoAgregar.existeGrupo(grupo)) {
                                    throw new SQLException();
                                }
                                Estudiante estudiante = new Estudiante();
                                estudiante.setId(id);
                                grupoAgregar.agregaEstudiante(estudiante);
                                menuEstudiante.setDisable(false);
                                seleccionarGrupo.close();
                            } catch (SQLException ex) {
                                Conexion.cierraCanal();
                                MessageBox.show(new Stage(),
                                        "Ocurrió un error, favor de revisar si el grupo ya existe"
                                        + " o el alumno ya está en el grupo.",
                                        "¡Ups! Error al agregar.",
                                        MessageBox.ICON_ERROR | MessageBox.OK);
                            }
                        } else {
                            MessageBox.show(new Stage(),
                                    "La entrada no es un número correcto.",
                                    "¡Error! Problemas con el número.",
                                    MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                        break;
                    case PROFESOR:
                        String numeroGrupoP = numDeGrupo.getText();
                        if (EsNumero.esNumero(numeroGrupoP)) {
                            try {
                                Integer grupo = Integer.parseInt(numeroGrupoP);
                                Integer id = Integer.parseInt(arregloColumna[0]);
                                Grupo grupoAgregar = new Grupo(grupo);
                                if (!grupoAgregar.existeGrupo(grupo)) {
                                    throw new SQLException();
                                }
                                Profesor profesor = new Profesor();
                                profesor.setId(id);
                                grupoAgregar.modificaProfesor(profesor);
                                menuProfesor.setDisable(false);
                                seleccionarGrupo.close();
                            } catch (SQLException ex) {
                                Conexion.cierraCanal();
                                MessageBox.show(new Stage(),
                                        "Ocurrió un error, favor de revisar si el grupo ya existe.",
                                        "¡Ups! Error al agregar.",
                                        MessageBox.ICON_ERROR | MessageBox.OK);
                            }
                        } else {
                            MessageBox.show(new Stage(),
                                    "La entrada no es un número correcto.",
                                    "¡Error! Problemas con el número.",
                                    MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                        break;
                    case MATERIA:
                        String numeroGrupoM = numDeGrupo.getText();
                        if (EsNumero.esNumero(numeroGrupoM)) {
                            try {
                                Integer grupo = Integer.parseInt(numeroGrupoM);
                                Integer id = Integer.parseInt(arregloColumna[0]);
                                Grupo grupoAgregar = new Grupo(grupo);
                                if (!grupoAgregar.existeGrupo(grupo)) {
                                    throw new SQLException();
                                }
                                Materia materia = new Materia();
                                materia.setId(id);
                                grupoAgregar.modificaMateria(materia);
                                menuMateria.setDisable(false);
                                seleccionarGrupo.close();
                            } catch (SQLException ex) {
                                Conexion.cierraCanal();
                                MessageBox.show(new Stage(),
                                        "Ocurrió un error, favor de revisar si el grupo ya existe.",
                                        "¡Ups! Error al agregar.",
                                        MessageBox.ICON_ERROR | MessageBox.OK);
                            }
                        } else {
                            MessageBox.show(new Stage(),
                                    "La entrada no es un número correcto.",
                                    "¡Error! Problemas con el número.",
                                    MessageBox.ICON_ERROR | MessageBox.OK);
                        }
                        break;
                }
            }
        });
    }

    //Método privado para agregar a un nuevo grupo.
    private void agregaNuevoGrupo(ObservableList<ObservableList<String>> datos, int objeto) throws SQLException {
        String columna = datos.get(0).toString();
        final String[] arregloColumna
                = columna.substring(1, columna.length() - 1).split(",");
        arregloColumna[0] = arregloColumna[0].trim();
        arregloColumna[1] = arregloColumna[1].trim();
        arregloColumna[2] = arregloColumna[2].trim();
        if (objeto == ESTUDIANTE) {
            menuEstudiante.setDisable(true);
            arregloColumna[3] = arregloColumna[3].trim();
            int confirmacion = MessageBox.show(new Stage(),
                    "Favor de confirmar la operación: \n\n ¿Desea abrir un nuevo"
                    + " grupo que tenga de alumno a " + arregloColumna[1]
                    + " " + arregloColumna[2]
                    + " con número de cuenta " + arregloColumna[0] + "?",
                    "Abrir nuevo grupo",
                    MessageBox.ICON_QUESTION | MessageBox.YES | MessageBox.NO);
            if (confirmacion == MessageBox.YES) {
                Grupo grupo = new Grupo();
                grupo.setId(grupo.creaGrupo());
                Integer idEstudiante = Integer.parseInt(arregloColumna[0]);
                grupo.agregaEstudiante(new Estudiante(
                        idEstudiante, arregloColumna[1],
                        arregloColumna[2], arregloColumna[3].charAt(0)));
                MessageBox.show(new Stage(),
                        "Se abrió el grupo con número " + grupo.getId(),
                        "Grupo creado", MessageBox.ICON_INFORMATION | MessageBox.OK);
                menuEstudiante.setDisable(false);
            } else {
                menuEstudiante.setDisable(false);
            }

        } else if (objeto == PROFESOR) {
            menuProfesor.setDisable(true);
            int confirmacion = MessageBox.show(new Stage(),
                    "Favor de confirmar la operación: \n\n ¿Desea abrir un nuevo"
                    + " grupo que tenga de profesor al " + arregloColumna[1]
                    + " " + arregloColumna[2]
                    + " con número de trabajador " + arregloColumna[0] + "?",
                    "Abrir nuevo grupo",
                    MessageBox.ICON_QUESTION | MessageBox.YES | MessageBox.NO);
            if (confirmacion == MessageBox.YES) {
                Grupo grupo = new Grupo();
                grupo.setId(grupo.creaGrupo());
                Integer idProfesor = Integer.parseInt(arregloColumna[0]);
                grupo.creaGrupoProfesor(new Profesor(
                        idProfesor, arregloColumna[1],
                        arregloColumna[2]));
                MessageBox.show(new Stage(),
                        "Se abrió el grupo con número " + grupo.getId(),
                        "Grupo creado", MessageBox.ICON_INFORMATION | MessageBox.OK);
                menuProfesor.setDisable(false);
            } else {
                menuProfesor.setDisable(false);
            }
        } else if (objeto == MATERIA) {
            menuMateria.setDisable(true);
            int confirmacion = MessageBox.show(new Stage(),
                    "Favor de confirmar la operación: \n\n ¿Desea abrir un nuevo"
                    + " grupo para impartir la materia de " + arregloColumna[1]
                    + " con clave " + arregloColumna[0] + "?",
                    "Abrir nuevo grupo",
                    MessageBox.ICON_QUESTION | MessageBox.YES | MessageBox.NO);
            if (confirmacion == MessageBox.YES) {
                Grupo grupo = new Grupo();
                grupo.setId(grupo.creaGrupo());
                Integer clave = Integer.parseInt(arregloColumna[0]);
                Integer semestre = Integer.parseInt(arregloColumna[2]);
                grupo.creaGrupoMateria(new Materia(
                        clave, arregloColumna[1], semestre));
                MessageBox.show(new Stage(),
                        "Se abrió el grupo con número " + grupo.getId(),
                        "Grupo creado", MessageBox.ICON_INFORMATION | MessageBox.OK);
                menuMateria.setDisable(false);
            } else {
                menuMateria.setDisable(false);
            }
        }
    }

    //Método privado que muestra el menú correspondiente del objeto seleccionado.
    private void muestraInfo(String objeto) throws SQLException {
        final Stage informacion = new Stage();
        informacion.setMinWidth(525);
        informacion.setMinHeight(250);
        int menu = 0;
        if (objeto.equals("estudiante")) {
            menuEstudiante.setDisable(true);
        }
        if (objeto.equals("profesor")) {
            menu = 1;
            menuProfesor.setDisable(true);
        }
        if (objeto.equals("materia")) {
            menu = 2;
            menuMateria.setDisable(true);
        }
        Text encabezado = new Text();
        ObservableList<ObservableList<String>> objetoAVer = null;
        switch (menu) {
            case 0:
                encabezado.setText("INFORMACION DEL ESTUDIANTE");
                objetoAVer = tablaPrincipalEstudiantes.getSelectionModel().getSelectedItems();
                break;
            case 1:
                encabezado.setText("INFORMACION DEL PROFESOR");
                objetoAVer = tablaPrincipalProfesores.getSelectionModel().getSelectedItems();
                break;
            case 2:
                encabezado.setText("INFORMACION DE LA MATERIA");
                objetoAVer = tablaPrincipalMaterias.getSelectionModel().getSelectedItems();
                break;
        }
        String columna = objetoAVer.get(0).toString();
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
        ResultSet resultado = null;
        ListView<String> listaView = new ListView<String>();
        listaView.setMaxSize(100, 150);
        if (menu == 0) {
            Estudiante sacarCarrera = new Estudiante();
            sacarCarrera.setId(Integer.parseInt(arregloColumna[0]));
            resultado = sacarCarrera.buscaGrupo();
            List<String> lista = new ArrayList<String>();
            while (resultado.next()) {
                int idGrupo = resultado.getInt(1);
                Grupo grupo = new Grupo(idGrupo);
                String materiaGrupo = "(Sin Materia)";
                String profesorGrupo = "(Sin Profesor)";
                if (grupo.getProfesor().next()) {
                    profesorGrupo = grupo.getProfesor().getString(3);
                }
                if (grupo.getMateria().next()) {
                    materiaGrupo = grupo.getMateria().getString(2);
                }
                lista.add(idGrupo + " - " + materiaGrupo + " - " + profesorGrupo);
            }
            ObservableList<String> vistaLista = FXCollections.observableList(lista);
            listaView.setItems(vistaLista);
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
            Profesor profesor = new Profesor();
            profesor.setId(Integer.parseInt(arregloColumna[0]));
            resultado = profesor.buscaGrupo();
            List<String> lista = new ArrayList<String>();
            while (resultado.next()) {
                int idGrupo = resultado.getInt(1);
                Grupo grupo = new Grupo(idGrupo);
                String materiaGrupo = "(Sin Materia)";
                String profesorGrupo = "(Sin Profesor)";
                if (grupo.getProfesor().next()) {
                    profesorGrupo = grupo.getProfesor().getString(3);
                }
                if (grupo.getMateria().next()) {
                    materiaGrupo = grupo.getMateria().getString(2);
                }
                lista.add(idGrupo + " - " + materiaGrupo + " - " + profesorGrupo);
            }
            ObservableList<String> vistaLista = FXCollections.observableList(lista);
            listaView.setItems(vistaLista);
        } else if (menu == 2) {
            Materia materia = new Materia();
            materia.setId(Integer.parseInt(arregloColumna[0]));
            resultado = materia.buscaGrupo();
            List<String> lista = new ArrayList<String>();
            while (resultado.next()) {
                int idGrupo = resultado.getInt(1);
                Grupo grupo = new Grupo(idGrupo);
                String materiaGrupo = "(Sin Materia)";
                String profesorGrupo = "(Sin Profesor)";
                if (grupo.getProfesor().next()) {
                    profesorGrupo = grupo.getProfesor().getString(3);
                }
                if (grupo.getMateria().next()) {
                    materiaGrupo = grupo.getMateria().getString(2);
                }
                lista.add(idGrupo + " - " + materiaGrupo + " - " + profesorGrupo);
            }
            ObservableList<String> vistaLista = FXCollections.observableList(lista);
            listaView.setItems(vistaLista);
            cajaSemestres.setValue(arregloColumna[2]);
        }

        nombre.setDisable(true);
        apellidos.setDisable(true);
        id.setDisable(true);
        hombre.setDisable(true);
        mujer.setDisable(true);
        cajaTitulos.setDisable(true);
        cajaSemestres.setDisable(true);
        cajaCarreras.setDisable(true);

        Label idLabel = new Label("Número de Cuenta");
        Label nombreLabel = new Label("Nombre (s)");
        Label apellidosLabel = new Label("Apellidos");
        Label sexoLabel = new Label("Sexo");
        Label titulo = new Label("Título");
        Label semestre = new Label("Semestre");
        Label carreras = new Label("Carrera");
        Label gruposEstudiantes = new Label("Grupos que cursa");
        Label gruposProfesores = new Label("Grupos que imparte");
        Label gruposMaterias = new Label("Grupos en la que se imparte");
        if (menu == 1) {
            idLabel.setText("Número de Trabajador");
            nombreLabel.setText("Nombre Completo");
        } else if (menu == 2) {
            idLabel.setText("Clave de la Materia");
            nombreLabel.setText("Nombre de la Materia");
        }
        VBox entradas = new VBox(15);
        VBox etiquetas = new VBox(20);
        VBox tabla = new VBox(5);
        HBox union = new HBox(15);
        HBox genero = new HBox(10);
        BorderPane border = new BorderPane();

        Button aceptar = new Button("OK");

        border.setMinSize(400, 300);

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
            tabla.getChildren().add(gruposEstudiantes);
            etiquetas.getChildren().add(apellidosLabel);
            etiquetas.getChildren().add(sexoLabel);
            etiquetas.getChildren().add(carreras);
        } else if (menu == 1) {
            tabla.getChildren().add(gruposProfesores);
            etiquetas.getChildren().add(titulo);
        } else {
            tabla.getChildren().add(gruposMaterias);
            etiquetas.getChildren().add(semestre);
        }
        union.getChildren().addAll(entradas, etiquetas);
        tabla.getChildren().add(listaView);

        border.setTop(encabezado);
        border.setAlignment(encabezado, Pos.CENTER);
        border.setCenter(union);
        border.setAlignment(union, Pos.CENTER);
        border.setMargin(union, new Insets(30, 0, 0, 50));
        border.setBottom(aceptar);
        border.setAlignment(aceptar, Pos.CENTER);
        border.setMargin(aceptar,
                new Insets(0, 50, 20, 50));
        border.setRight(tabla);
        border.setAlignment(tabla, Pos.CENTER);
        border.setMargin(tabla,
                new Insets(0, 60, 20, 50));

        Scene scene = new Scene(border, 525, 120, Color.LIGHTGREY);

        informacion.setScene(scene);
        informacion.setResizable(true);
        informacion.setMaxHeight(350);
        informacion.setMaxWidth(525);
        informacion.setMinHeight(350);
        informacion.setMinWidth(525);
        informacion.setTitle("VER MAS");
        informacion.show();

        aceptar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (menuEstudiante.isDisable()) {
                    menuEstudiante.setDisable(false);
                }
                if (menuProfesor.isDisable()) {
                    menuProfesor.setDisable(false);
                }
                if (menuMateria.isDisable()) {
                    menuMateria.setDisable(false);
                }
                informacion.close();
            }
        });
        informacion.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                if (menuEstudiante.isDisable()) {
                    menuEstudiante.setDisable(false);
                }
                if (menuProfesor.isDisable()) {
                    menuProfesor.setDisable(false);
                }
                if (menuMateria.isDisable()) {
                    menuMateria.setDisable(false);
                }
            }
        });
    }

}//Fin de MenusSecundarios.java
