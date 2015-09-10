/* -------------------------------------------------------------------
 * Materia.java
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
 * Clase que emula la tabla de materias de la base de datos..</p>
 *
 * <p>
 * Con ayuda de esta clase podemos emular las materias y manejar nuestra base de
 * datos.</p>
 */
public class Materia extends Facultad {

    private final String tabla;
    private int id;
    private String nombre;
    private int semestre;

    /**
     * Constructor que se ofrece por completés.
     */
    public Materia() {
        this.tabla = "materias";
        this.id = -1;
        this.nombre = "";
        this.semestre = 0;
    }

    /**
     * Método constructor de una materia.
     * @param id - es el id de la materia.
     * @param nombre - es el nombre de la materia.
     * @param semestre - es el semestre de la materia.
     */
    public Materia(int id, String nombre, int semestre) {
        this.tabla = "materias";
        this.id = id;
        this.nombre = nombre;
        this.semestre = semestre;
    }

    /**
     * Metodo que nos regresa el nombre de la tabla materias.
     *
     * @return el nombre de la tabla.
     */
    @Override
    public String getNombreTabla() {
        return this.tabla;
    }

    /**
     *Método que nos regresa el id de la materia.
     * @return - el id de la materia.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Método que nos regresa el nombre de la materia
     * @return - el nombre de la materia.
     */
    @Override
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Método que nos regresa el semestre de la materia.
     * @return el semestre de la materia.
     */
    public int getSemestre() {
        return this.semestre;
    }

    /**
     * Método para asignar un id a una materia.
     * @param id - es el nuevo id de la mareria.
     * @throws SQLException - en caso de tener error en la conexión.
     */
    public void setId(int id) throws SQLException {
        this.id = id;
    }

    /**
     * Método para cambiar el nombre de la materia.
     * @param nombre - es el nuevo nombre de la materia.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método para asignar un semestre a una materia.
     * @param semestre - es el semestre.
     */
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    /**
     * Método para agregar una nueva materia.
     * @throws SQLException - en caso de tener error en la conexión.
     */
    public void agrega()
            throws SQLException {
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("INSERT INTO " + getNombreTabla() + "(id,nombre,semestre)"
                + " VALUES(" + this.id + ",'"
                + this.nombre + "'," + this.semestre + ");");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método para actualizar el semestre de una materia.
     * @throws SQLException - en caso de tener error en la conexión.
     */
    public void actualizaSemestre() throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "UPDATE " + getNombreTabla() + ""
                + " SET semestre = " + this.semestre + ""
                + " WHERE id == " + this.id + ";";
        tabla.executeUpdate(sql);
        tabla.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método para buscar los grupos que tengan esa materia.
     * @return - los grupos que impartan esa materia.
     * @throws SQLException - en caso de tener error en la conexión.
     */
    @Override
    public ResultSet buscaGrupo() throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM grupo A LEFT JOIN grupo_profesor_materia B ON "
                + "A.id = B.id WHERE B.materia == " + this.getId() + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado;
    }

    /**
     * Método para actalizar el id
     * @param nuevaClave - es el nuevo id.
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
     * Método para buscar las relaciones que cumplan las materias.
     * @param nombre - es el nombre de la materia.
     * @param semestre - es el número de semestre que se imparte.
     * @param profesor - es el profesor que la imparte.
     * @return - una lista con las posibles coincidencias.
     * @throws SQLException  - en caso de tener error en la conexión.
     */
    public ResultSet buscaRelaciones(String nombre, int semestre, int profesor) throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaAccion = conexion.createStatement();
        String sql = "";
        if (semestre != -1 && profesor != -1) {
            sql = "SELECT A.id,A.nombre,A.semestre FROM materias A LEFT JOIN"
                    + " grupo_profesor_materia B ON A.id = B.materia WHERE "
                    + "B.profesor == " + profesor + " AND A.semestre == " + semestre + " "
                    + " AND A.nombre LIKE '%" + nombre + "%';";
        } else if (profesor != -1) {
            sql = "SELECT A.id,A.nombre,A.semestre FROM materias A LEFT JOIN"
                    + " grupo_profesor_materia B ON A.id = B.materia WHERE "
                    + "B.profesor == " + profesor + " "
                    + " AND A.nombre LIKE '%" + nombre + "%';";
        } else if (semestre != -1) {
            sql = "SELECT A.id,A.nombre,A.semestre FROM materias A LEFT JOIN"
                    + " grupo_profesor_materia B ON A.id = B.materia WHERE "
                    + "A.semestre == " + semestre + " "
                    + " AND A.nombre LIKE '%" + nombre + "%';";
        } else {
            sql = "SELECT A.id,A.nombre,A.semestre FROM materias A"
                    + " WHERE A.nombre LIKE '%" + nombre + "%';";
        }
        ResultSet resultado = tablaAccion.executeQuery(sql);
        return resultado;
    }

    private void modificaRelaciones(int idNueva) throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaUno = conexion.createStatement();
        String sql = "UPDATE grupo_profesor_materia"
                + " SET materia = '" + idNueva + "'"
                + " WHERE materia == " + this.id + ";";
        tablaUno.executeUpdate(sql);
        tablaUno.close();
        conexion.commit();
        cierraCanal();
    }
} //Fin de Materia.java
