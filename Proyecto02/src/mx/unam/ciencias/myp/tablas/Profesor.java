/* -------------------------------------------------------------------
 * Profesores.java
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
import static mx.unam.ciencias.myp.tablas.Conexion.abreCanal;
import static mx.unam.ciencias.myp.tablas.Conexion.cierraCanal;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Clase que emula el comportamiento de la tabla de profesores.</p>
 *
 * <p>
 * Clase que nos ayuda a añadir, modificar, eliminar, etc., objetos de tipo
 * profesores a nuestra base de datos.</p>
 */
public class Profesor extends Facultad {

    private final String tabla;
    private int id;
    private String titulo;
    private String nombre;

    /**
     * Método constructor que se ofrece por completes.
     */
    public Profesor() {
        this.tabla = "profesores";
        this.id = -1;
        this.nombre = "";
        this.titulo = "";
    }

    /**
     * Método constructor de tipo profesor.
     *
     * @param id - es el id del profesor.
     * @param titulo - es el título del profesor.
     * @param nombre - es el nombre del profesor.
     */
    public Profesor(int id, String titulo, String nombre) {
        this.tabla = "profesores";
        this.id = id;
        this.nombre = nombre;
        this.titulo = titulo;

    }

    /**
     * Metodo que nos regresa el nombre de la tabla profesores.
     *
     * @return el nombre de la tabla.
     */
    @Override
    public String getNombreTabla() {
        return this.tabla;
    }

    /**
     * Método que nos regresa el id del profesor.
     *
     * @return - el id del profesor.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Método que nos regresa el nombre del profesor.
     *
     * @return - el nombre del profesor.
     */
    @Override
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Método que actualiza del id del profesor.
     *
     * @param nuevaClave - es la nueva clave.
     * @throws SQLException - en caso de tener error en la conexión.
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
        modificaRelaciones(nuevaClave);
        cierraCanal();
    }

    /**
     * Metodo que nos regresa el título del profesor.
     *
     * @return - el título del profesor.
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Método para asignarle id al profesor.
     *
     * @param id - el id a asignar.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método para asignar nombre al profesor.
     *
     * @param nombre - el nombre del profesor.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método para asignar título al profesor.
     *
     * @param titulo - el nuevo título.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Método que agrega un nuevo profesor a la base de datos.
     *
     * @throws SQLException - en caso de tener error en la conexión.
     */
    public void agrega()
            throws SQLException {
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("INSERT INTO " + getNombreTabla() + "(id,titulo,nombre)"
                + " VALUES(" + this.id + ",'"
                + this.titulo + "','" + this.nombre + "');");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método que actualiza el título de un profesor.
     *
     * @throws SQLException - en caso de tener error en la conexión.
     */
    public void actualizaTitulo() throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaAccion = conexion.createStatement();
        String sql = "UPDATE " + getNombreTabla() + ""
                + " SET titulo = '" + this.titulo + "'"
                + " WHERE id == " + this.id + ";";
        tablaAccion.executeUpdate(sql);
        tablaAccion.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método que regresa todos los grupos donde imparte clases el profesor.
     *
     * @return - los grupos donde imparte clases.
     * @throws SQLException - en caso de tener error en la conexión.
     */
    @Override
    public ResultSet buscaGrupo() throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM grupo A LEFT JOIN grupo_profesor_materia B ON "
                + "A.id = B.id WHERE B.profesor == " + this.getId() + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado;
    }

    /**
     * Método que busca las coincidencias de un profesor con los parámetros.
     *
     * @param nombre - es el nombre del profesor.
     * @param titulo - es el título del profesor.
     * @param materia - es la materia que imparte.
     * @return - las coincidencias de la búsqueda.
     * @throws SQLException - en caso de tener error en la conexión.
     */
    public ResultSet buscaRelaciones(String nombre, String titulo, int materia) throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaAccion = conexion.createStatement();
        String sql = "";
        if (materia != -1 && !titulo.equals("Todos")) {
            sql = "SELECT A.id,A.titulo,A.nombre FROM profesores A LEFT JOIN"
                    + " grupo_profesor_materia B ON A.id = B.profesor WHERE "
                    + "B.materia == " + materia + " AND A.titulo LIKE '%" + titulo + "%'"
                    + " AND A.nombre LIKE '%" + nombre + "%';";
        } else if (materia != -1) {
            sql = "SELECT A.id,A.titulo,A.nombre FROM profesores A LEFT JOIN"
                    + " grupo_profesor_materia B ON A.id = B.profesor WHERE "
                    + "B.materia == " + materia + " "
                    + " AND A.nombre LIKE '%" + nombre + "%';";
        } else if (!titulo.equals("Todos")) {
            sql = "SELECT A.id,A.titulo,A.nombre FROM profesores A LEFT JOIN"
                    + " grupo_profesor_materia B ON A.id = B.profesor WHERE "
                    + "A.titulo LIKE '%" + titulo + "%' "
                    + " AND A.nombre LIKE '%" + nombre + "%';";
        } else {
            sql = "SELECT A.id,A.titulo,A.nombre FROM profesores A"
                    + " WHERE A.nombre LIKE '%" + nombre + "%';";
        }
        ResultSet resultado = tablaAccion.executeQuery(sql);
        return resultado;
    }

    //Método privado que modifica las relaciones.
    private void modificaRelaciones(int idNueva) throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaUno = conexion.createStatement();
        String sql = "UPDATE grupo_profesor_materia"
                + " SET profesor = '" + idNueva + "'"
                + " WHERE profesor == " + this.id + ";";
        tablaUno.executeUpdate(sql);
        tablaUno.close();
        conexion.commit();
    }

} //Fin de Profesor.java
