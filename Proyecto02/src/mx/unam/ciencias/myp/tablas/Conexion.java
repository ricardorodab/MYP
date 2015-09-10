/* -------------------------------------------------------------------
 * Conexion.java
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
package mx.unam.ciencias.myp.tablas;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Clase que da la conexión a la base de datos.</p>
 *
 * <p>
 * Esta clase contiene un singleton que es la conexión a la base de datos.</p>
 */
public class Conexion {

    /*
    * OJO! NO MODIFICAR SIN SABER LO QUE SE HACE .
    */
    
    /**
     * Es la conexión hacia la base de datos. 
     */
    public static Connection conexion;

    /**
     * Método que nos abre una conexión a la base de datos.
     * @return connection - conexión a la base de datos.
     */
    public static Connection abreCanal() {
        //SINGLETON a la base de datos.
        try {
            //Revisa si ya existe una conexión cerrada o nula.
            if (conexion == null || conexion.isClosed()) {
                dameLinea();
            }
        } catch (SQLException ex) {
            Conexion.cierraCanal();
            MessageBox.show(new Stage(), "¡Error de conexión! \n\n Ocurrió un "
			    + "error inesperado en la conexión de la base de datos."
			    + " Por favor, vuelva a intentarlo y verifique que el archivo Facultad.db se "
			    + "encuentre en la carpeta \n /lib dentro del directorio de instalación \n"
			    + " También revise que su versión de java sea 8.0_20 o superior.1",
			    "Problema de conexión", MessageBox.ICON_ERROR | MessageBox.OK);
        }
        return conexion;
    }

    /**
     * Método que cierra la conexión a la base de datos.
     */
    public synchronized static void cierraCanal() {
        //En caso de que alguien esté ocupando la solución de sincroniza.
        try {
            if (conexion == null || conexion.isClosed()) {
                return;
            }
            conexion.close();
        } catch (SQLException ex) {
            MessageBox.show(new Stage(), "¡Error de conexión! \n\n Ocurrió un "
                                + "error inesperado en la conexión de la base de datos."
                    + " Por favor, vuelva a intentarlo y verifique que el archivo Facultad.db se "
                    + "encuentre en la carpeta /lib dentro del directorio de instalación 2",
                                "Problema de conexión", MessageBox.ICON_ERROR | MessageBox.OK);
        }
    }

    //Metodo privado que abre la conexión a la base de datos.
    private synchronized static Connection dameLinea() {
        try {
            Class.forName("org.sqlite.JDBC");
            //CONEXIÓN A LA BASE DE DATOS:
            conexion = DriverManager.getConnection("jdbc:sqlite:./lib/Facultad.db");
            conexion.setAutoCommit(false);
        } catch (ClassNotFoundException ex) {
            Conexion.cierraCanal();
	    MessageBox.show(new Stage(), "¡Error de conexión! \n\n Ocurrió un "
			    + "error inesperado en la conexión de la base de datos."
			    + " Por favor, vuelva a intentarlo y verifique que el archivo Facultad.db se "
			    + "encuentre en la carpeta \n /lib dentro del directorio de instalación \n"
			    + " También revise que su versión de java sea 8.0_20 o superior.3",					
			    "Problema de conexión", MessageBox.ICON_ERROR | MessageBox.OK);
        } catch (SQLException ex) {
            Conexion.cierraCanal();
	    MessageBox.show(new Stage(), "¡Error de conexión! \n\n Ocurrió un "
			    + "error inesperado en la conexión de la base de datos."
			    + " Por favor, vuelva a intentarlo y verifique que el archivo Facultad.db se "
			    + "encuentre en la carpeta \n /lib dentro del directorio de instalación \n"
			    + " También revise que su versión de java sea 8.0_20 o superior.\n",			    
			    "Problema de conexión", MessageBox.ICON_ERROR | MessageBox.OK);
        }
        return conexion;
    }

}// Fin de Conexion.java
