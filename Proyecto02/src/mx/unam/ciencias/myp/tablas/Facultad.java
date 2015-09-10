/* -------------------------------------------------------------------
 * Facultad.java
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import static mx.unam.ciencias.myp.tablas.Conexion.*;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Clase abstracta que representan los objetos de la Facultad.</p>
 *
 * <p>
 * Esta clase abstracta representa a estudiantes, profesores, materias y
 * carreras que son objetos de la Facultad..</p>
 */
public abstract class Facultad implements OperacionesSQL {

    /**
     * Constante que representa la carrera de actuaría.
     */
    public static final int ACTUARIA = 1;
    /**
     * Constante que representa la carrera de biología.
     */
    public static final int BIOLOGIA = 2;
    /**
     * Constante que representa la carrera de ciencias de la computación.
     */
    public static final int CIENCIAS_DE_LA_COMPUTACION = 3;
    /**
     * Constante que representa la carrera de ciencias de la tierra.
     */
    public static final int CIENCIAS_DE_LA_TIERRA = 4;
    /**
     * Constante que representa la carrera de física.
     */
    public static final int FISICA = 5;
    /**
     * Constante que representa la carrera de matemáticas.
     */
    public static final int MATEMATICAS = 6;

    /**
     * Metodo abstracto que nos regresa el nombre de la tabla.
     *
     * @return el nombre de la tabla.
     */
    public abstract String getNombreTabla();

    /**
     * Método abstracto que nos da el id del objeto.
     *
     * @return - el id del objeto.
     */
    public abstract int getId();

    /**
     * Método abstracto que nos regresa el nombre del objeto.
     *
     * @return - el nombre del objeto.
     */
    public abstract String getNombre();

    /**
     * Metodo que nos regresa la tabla.
     *
     * @return Los resultados de la tabla.
     * @throws SQLException - en caso de tener algun error de comunicacion.
     */
    @Override
    public ResultSet getTabla() throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "SELECT * FROM " + this.getNombreTabla() + ";";
        ResultSet resultado = tabla.executeQuery(sql);
        return resultado;
    }

    /**
     * Metodo que nos regresa la tabla de carreras con ciertas condiciones
     *
     * @param where - es la condicion que cumplen.
     * @return Los resultados de la tabla carreras.
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    @Override
    public ResultSet getTabla(String where) throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "SELECT * FROM " + this.getNombreTabla() + " "
                + "WHERE id == '" + where + "' OR nombre LIKE '%" + where + "%';";
        ResultSet resultado = tabla.executeQuery(sql);
        return resultado;
    }

    /**
     * Método para agregar un elemento a la tabla y base de datos.
     *
     * @throws SQLException - en caso de tener algún problema de comunicación.
     */
    @Override
    public void agrega() throws SQLException {
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("INSERT INTO " + getNombreTabla() + "(id,nombre) "
                + "VALUES(" + getId() + ",'" + getNombre() + "');");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Metodo para buscar un elemento de la tabla.
     *
     * @param nombre es el nombre a buscar.
     * @return Una lista ligada con los índices de las coincidencias.
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    @Override
    public LinkedList<Integer> buscaNombre(String nombre) throws SQLException {
        LinkedList<Integer> indices = new LinkedList<Integer>();
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "SELECT * FROM " + this.getNombreTabla() + " "
                + "WHERE nombre LIKE '%" + nombre + "%';";
        ResultSet resultado = tabla.executeQuery(sql);
        while (resultado.next()) {
            indices.add(resultado.getInt("id"));
        }
        return indices;
    }

    /**
     * Método que busca dada una condición a un grupo de objetos y los regresa
     * en una lista.
     *
     * @deprecated
     * @param condicion - es la condición que debe empezar con un "WHERE " más
     * algo.
     * @return - una lista con los elementos.
     * @throws SQLException - en caso tener algún problema de conexión.
     */
    @Override
    public LinkedList<Integer> busca(String condicion) throws SQLException {
        LinkedList<Integer> indices = new LinkedList<Integer>();
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "SELECT * FROM " + this.getNombreTabla() + " "
                + condicion + ";";
        ResultSet resultado = tabla.executeQuery(sql);
        while (resultado.next()) {
            indices.add(resultado.getInt("id"));
        }
        return indices;
    }

    /**
     * Método que actualiza los datos del objeto en su misma tabla.
     *
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    @Override
    public void actualiza() throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "UPDATE " + getNombreTabla() + ""
                + " SET nombre = '" + getNombre() + "'"
                + " WHERE id == " + getId() + ";";
        tabla.executeUpdate(sql);
        tabla.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método que actualiza el id de un objeto.
     *
     * @param nuevaClave - es la nueva clave.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    @Override
    public void actualizaId(int nuevaClave) throws SQLException {
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "UPDATE " + getNombreTabla() + ""
                + " SET id = " + nuevaClave + ""
                + " WHERE id == " + getId() + ";";
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método que elimina un objeto de su tabla.
     *
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    @Override
    public void elimina() throws SQLException {
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("DELETE FROM " + getNombreTabla() + " "
                + "WHERE id == " + getId() + ";");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
    }
} //Fin de Facultad.java
