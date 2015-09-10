/* -------------------------------------------------------------------
 * Proyecto2.java
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

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mx.unam.ciencias.myp.tablas.Conexion;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Clase principal del Proyecto2.</p>
 *
 * <p>
 * Clase principal del proyecto. Ejecuta los métodos principales.</p>
 */
public class Proyecto2 extends Application {

    /**
     * Método principal del proyecto.
     *
     * @param stage es la intefaz del proyecto.
     * @throws Exception - en caso de tener algún problema al cargar el
     * programa.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ControladorGrafico.fxml"));
        Scene scene = new Scene(root);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Conexion.cierraCanal();
                System.exit(0);
            }
        });
        //Funcionamiento intermitente.
        stage.getIcons().add(new Image("/resources/Facultad_Ciencias.png"));
        stage.setTitle("Proyecto 2 - Base de datos");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * El método main (que es ignorado en el funcionamiento de javaFX).
     *
     * @param args los argumentos del programa.
     */
    public static void main(String[] args) {
        launch(args);
    }

}// Fin de Proyecto2.java
