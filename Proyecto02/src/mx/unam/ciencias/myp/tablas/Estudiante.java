/* -------------------------------------------------------------------
 * Estudiante.java
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
import java.util.logging.Level;
import java.util.logging.Logger;
import static mx.unam.ciencias.myp.tablas.Conexion.*;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Clase que representa a la tabla estudiantes.</p>
 *
 * <p>
 * Esta clase representa las acciones de la tabla estudiantes en la base de
 * datos.</p>
 */
public class Estudiante extends Facultad {


    private final String tabla;
    private int id;
    private String nombre;
    private String apellido;
    private char genero;

    /**
     * Constructor que se ofrece por completés.
     */
    public Estudiante() {
        this.tabla = "estudiantes";
        this.id = siguienteNumero();
        this.nombre = "";
        this.apellido = "";
        this.genero = 'H';
    }

    /**
     * Constructor que emula un estudiante en la tabla en la base de datos.
     *
     * @param id - es el id del estudiante.
     * @param nombre - es el nombre del estudiante.
     * @param apellido - es el apellido del estudiante.
     * @param genero - es el género del estudiante.
     */
    public Estudiante(int id, String nombre, String apellido, char genero) {
        this.tabla = "estudiantes";
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
    }

    /**
     * Metodo que nos regresa el nombre de la tabla estudiante.
     *
     * @return el nombre de la tabla.
     */
    @Override
    public String getNombreTabla() {
        return this.tabla;
    }

    /**
     * Método que nos regresa el id del estudiante.
     *
     * @return - el id de un estudiante.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Método que nos regresa el nombre de un estudiante.
     *
     * @return - el nombre del estudiante.
     */
    @Override
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Método que nos regresa el apellido de un estudiante.
     *
     * @return - el apellido de un estudiante.
     */
    public String getApellido() {
        return this.apellido;
    }

    /**
     * Método que nos regresa el género de un estudiante.
     *
     * @return - H si es hombre y M si es mujer.
     */
    public char getGenero() {
        return this.genero;
    }

    /**
     * Método que nos regresa el género de un estudiante en forma de String.
     *
     * @return - el género, ya sea H o M, en forma de string.
     */
    public String getCadenaGenero() {
        return Character.toString(genero);
    }

    /**
     * Método para cambiar el id de un estudiante.
     *
     * @param id - el nuevo id de un estudiante.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método para poner el nombre a un estudiante.
     *
     * @param nombre - el nombre nuevo del estudiante.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método para cambiar el apellido de un estudiante.
     *
     * @param apellido - es el nuevo apellido del estudiante.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Método para cambiar el género de un estudiante.
     *
     * @param genero - es el género nuevo del estudiante.
     */
    public void setGenero(char genero) {
        this.genero = genero;
    }

    /**
     * Método que nos regresa la tabla en forma de ResultSet de estudiantes.
     *
     * @param where - las condiciones que deben cumplir los estudiantes.
     * @return - Un ResultSet con todos los estudiantes que cumplan la
     * condición.
     * @throws SQLException - en caso de tener algún problema de conexión.
     */
    @Override
    public ResultSet getTabla(String where) throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "SELECT * FROM " + this.getNombreTabla() + " "
                + "WHERE id == '" + where + "' OR nombre LIKE '%" + where + "%' OR "
                + "apellidos LIKE '%" + where + "%';";
        ResultSet resultado = tabla.executeQuery(sql);
        return resultado;
    }

    /**
     * Método para agregar un nuevo estudiante a la base de datos.
     *
     * @param carrera - es la carrera del estudiante.
     * @throws SQLException - en caso de tener un problema de conexión.
     */
    public void agrega(int carrera)
            throws SQLException {
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("INSERT INTO " + getNombreTabla() + "(id,nombre,apellidos"
                + ",sexo) VALUES"
                + "(" + this.id + ",'"
                + this.nombre + "','" + this.apellido + "','" + this.genero + "');");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
        agregaCarrera(carrera);
    }

    /**
     * Método para buscar dentro de un grupo a un estudiante.
     *
     * @return - una lista de grupos en forma de ResultSet.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    @Override
    public ResultSet buscaGrupo() throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM grupo A LEFT JOIN grupo_estudiantes B ON "
                + "A.id = B.id WHERE B.estudiante == " + this.getId() + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado;
    }

    /**
     * Método que actualiza el Id de un alumno.
     *
     * @param nuevaClave - es la nueva clase.
     * @throws SQLException - en caso de tener algún problema de conexión.
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
        modificaRelaciones(nuevaClave);
    }

    /**
     * Método que nos actualiza el apellido de un alumno.
     *
     * @throws SQLException - en caso de tener algún problema de conexión.
     */
    public void actualizaApellido() throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "UPDATE " + getNombreTabla() + ""
                + " SET apellidos = '" + this.apellido + "'"
                + " WHERE id == " + this.id + ";";
        tabla.executeUpdate(sql);
        tabla.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método que cambia el género a un estudiante.
     *
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void cambiaGenero() throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "UPDATE " + getNombreTabla() + " "
                + "SET sexo = '" + this.genero + "' "
                + "WHERE id == " + this.id + ";";
        tabla.executeUpdate(sql);
        tabla.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método que regresa estudiantes de una determinada carrera.
     *
     * @param carrera - es la carrera de los estudiantes.
     * @return - los estudiantes en forma de ResultSet.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public ResultSet getEstudiantesCarrera(int carrera) throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "SELECT id,nombre,apellidos,sexo FROM " + this.getNombreTabla() + " "
                + "A LEFT JOIN estudiantes_carreras B ON "
                + "A.id = B.id_estudiantes WHERE B.id_carreras == " + carrera + ";";
        ResultSet resultado = tabla.executeQuery(sql);
        return resultado;
    }

    /**
     * Método que regresa estudiantes de una determinada carrera con cierta
     * condición.
     *
     * @param carrera - es la carrera de los estudiantes.
     * @param where - la condición que deben cumplir.
     * @return - los estudiantes en forma de ResultSet.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public ResultSet getEstudiantesCarrera(int carrera, String where) throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "SELECT id,nombre,apellidos,sexo FROM " + this.getNombreTabla() + " "
                + "A LEFT JOIN estudiantes_carreras B ON "
                + "A.id = B.id_estudiantes WHERE B.id_carreras == " + carrera + " "
                + "AND (A.id == '" + where + "' OR A.nombre LIKE '%" + where + "%' OR "
                + "A.apellidos LIKE '%" + where + "%');";
        ResultSet resultado = tabla.executeQuery(sql);
        return resultado;
    }

    /**
     * Método que regesa la carrera del estudiante quien llama al método.
     *
     * @return - una cadena con la carrera que cursa.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public String getCarrera() throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "SELECT nombre FROM carreras A LEFT JOIN estudiantes_carreras B ON "
                + "A.id = B.id_carreras WHERE B.id_estudiantes == " + this.getId() + ";";
        ResultSet resultado = tabla.executeQuery(sql);
        if (resultado.next()) {
            return resultado.getString(1);
        }
        return "";
    }

    /**
     * Método para asignar una carrera a un estudiante.
     *
     * @param carrera - es la nueva carrera del estudiante.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void setEstudiantesCarrera(int carrera) throws SQLException {
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "UPDATE estudiantes_carreras "
                + "SET id_carreras = " + carrera + " "
                + "WHERE id_estudiantes == " + this.getId() + ";";
        tabla.executeUpdate(sql);
    }

    /**
     * Elimina es estudiante de la carrera.
     *
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void eliminaCarrera() throws SQLException {
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("DELETE FROM estudiantes_carreras WHERE id_estudiantes == " + this.id + ";");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método que nos da los alumnos que cumplen con los parámetros.
     *
     * @param nombre - es el posible nombre del alumno.
     * @param genero - es el género del alumno.
     * @param profesor - es el profesor con quien cursa el alumno.
     * @param materia - es la materia que cursa el alumno.
     * @param carrera - es la carrera del alumno.
     * @return - una ResultSet con los alumnos que cumplan los parámetros.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public ResultSet buscaRelaciones(String nombre, String genero, int profesor, int materia, int carrera) throws SQLException {
        String profesorCadena, materiaCadena, carreraCadena;
        Connection conexion = abreCanal();
        Statement tabla = conexion.createStatement();
        String sql = "";
        if (profesor != -1 && materia != -1 && carrera != -1) {
            sql = "SELECT A.id,A.nombre,A.apellidos,A.sexo FROM estudiantes A LEFT JOIN"
                    + " estudiantes_carreras B ON A.id = B.id_estudiantes WHERE "
                    + "B.id_carreras = " + carrera + " AND (A.nombre LIKE '%" + nombre + "%'"
                    + " OR A.apellidos LIKE '%" + nombre + "%') AND A.sexo LIKE"
                    + " '%" + genero + "%' AND A.id IN (SELECT C.estudiante FROM grupo_estudiantes"
                    + " C LEFT JOIN grupo_profesor_materia D ON C.id = D.id"
                    + " WHERE D.profesor = " + profesor + ") AND A.id IN "
                    + "(SELECT E.estudiante FROM grupo_estudiantes E LEFT JOIN "
                    + "grupo_profesor_materia F ON E.id = F.id WHERE F.materia == " + materia + ");";
        } else if (profesor == -1 && materia == -1 && carrera == -1) {
            sql = "SELECT * FROM estudiantes WHERE (nombre LIKE '%" + nombre + "%' "
                    + " OR apellidos LIKE '%" + nombre + "%') AND sexo LIKE '%" + genero + "%';";
        } else if (profesor == -1 && materia == -1) {
            sql = "SELECT A.id,A.nombre,A.apellidos,A.sexo "
                    + "FROM estudiantes A LEFT JOIN estudiantes_carreras B ON "
                    + "A.id = B.id_estudiantes WHERE B.id_carreras == " + carrera + " AND (nombre LIKE "
                    + "'%" + nombre + "%' OR apellidos LIKE '%" + nombre + "%') AND sexo LIKE '%" + genero + "%';";
        } else {
            profesorCadena = carreraCadena = " = ";
            materiaCadena = " == ";
            profesorCadena += new Integer(profesor).toString() + " ";
            materiaCadena += new Integer(materia).toString() + " ";
            carreraCadena += new Integer(carrera).toString() + " ";
            if (profesor == -1) {
                profesorCadena = " LIKE '%%' ";
            }
            if (materia == -1) {
                materiaCadena = " LIKE '%%' ";
            }
            if (carrera == -1) {
                carreraCadena = " LIKE '%%' ";
            }
            sql = "SELECT A.id,A.nombre,A.apellidos,A.sexo FROM estudiantes A LEFT JOIN"
                    + " estudiantes_carreras B ON A.id = B.id_estudiantes WHERE "
                    + "B.id_carreras " + carreraCadena + " AND (A.nombre LIKE '%" + nombre + "%'"
                    + " OR A.apellidos LIKE '%" + nombre + "%') AND A.sexo LIKE"
                    + " '%" + genero + "%' AND A.id IN (SELECT C.estudiante FROM grupo_estudiantes"
                    + " C LEFT JOIN grupo_profesor_materia D ON C.id = D.id"
                    + " WHERE D.profesor " + profesorCadena + ") AND A.id IN "
                    + "(SELECT E.estudiante FROM grupo_estudiantes E LEFT JOIN "
                    + "grupo_profesor_materia F ON E.id = F.id WHERE F.materia" + materiaCadena + ");";
        }
        ResultSet resultado = tabla.executeQuery(sql);
        return resultado;
    }

    //Método privado que agrega carrera a alumno
    private void agregaCarrera(int carrera) throws SQLException {
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("INSERT INTO estudiantes_carreras(id_estudiantes,id_carreras) "
                + "VALUES(" + this.getId() + "," + carrera + ");");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
    }

    //Método privado que nos da el número siguente de estudiante.
    //No lo uso, sólo lo implemento para el constructor sin parámetros.
    private int siguienteNumero() {
        int siguiente = -1;
        try {
            Connection conexion = abreCanal();
            Statement tabla = conexion.createStatement();
            String sql = "SELECT MAX(id) FROM " + this.getNombreTabla() + ";";
            ResultSet resultado = tabla.executeQuery(sql);
            siguiente = resultado.getInt(1);
            siguiente++;
            return siguiente;
        } catch (SQLException ex) {
            return siguiente;
        }
    }

//Método privado para modificar relaciones en las tablas.
    private void modificaRelaciones(int idNueva) throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaUno = conexion.createStatement();
        String sql = "UPDATE estudiantes_carreras"
                + " SET id_estudiantes = '" + idNueva + "'"
                + " WHERE id_estudiantes == " + this.id + ";";
        tablaUno.executeUpdate(sql);
        conexion.commit();

        conexion = abreCanal();
        Statement tablaDos = conexion.createStatement();
        sql = "UPDATE estudiantes_materias"
                + " SET id_estudiantes = '" + idNueva + "'"
                + " WHERE id_estudiantes == " + this.id + ";";
        tablaDos.executeUpdate(sql);
        conexion.commit();

        conexion = abreCanal();
        Statement tablaTres = conexion.createStatement();
        sql = "UPDATE grupo_estudiantes"
                + " SET estudiante = '" + idNueva + "'"
                + " WHERE estudiante == " + this.id + ";";
        tablaTres.executeUpdate(sql);
        conexion.commit();
        cierraCanal();
    }
} //Fin de Estudiante.java
