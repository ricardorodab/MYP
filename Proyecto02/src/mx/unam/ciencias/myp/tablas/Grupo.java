/* -------------------------------------------------------------------
 * Grupo.java
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
 * Clase manejadora de grupos.</p>
 *
 * <p>
 * Con ayuda de esta clase podemos manejar, eliminar, agregar y modificar los
 * diferentes grupos que incluyen UN profesor, UNA materia y estudiantes.</p>
 */
public class Grupo {


    private final String tabla;
    private final String tablaEstudiantes;
    private final String tablaProfMat;
    private int id;
    private boolean idEsNull;

    /**
     * Constructor que se ofrece para nuevos grupos.
     */
    public Grupo() {
        this.tabla = "grupo";
        this.tablaEstudiantes = "grupo_estudiantes";
        this.tablaProfMat = "grupo_profesor_materia";
        this.idEsNull = true;
    }

    /**
     * Constructor para grupos ya existentes.
     *
     * @param id - es el id del grupo.
     */
    public Grupo(int id) {
        this.tabla = "grupo";
        this.id = id;
        this.tablaEstudiantes = "grupo_estudiantes";
        this.tablaProfMat = "grupo_profesor_materia";
        this.idEsNull = false;
    }

    /**
     * Método que regresa el nombre de la tabla.
     *
     * @return - el nombre de la tabla.
     */
    public String getNombreTabla() {
        return this.tabla;
    }

    /**
     * Método que regresa el id del grupo.
     *
     * @return - el id del grupo.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Método con el que asignamos un id a un grupo.
     *
     * @param id - el nuevo id del grupo.
     */
    public void setId(int id) {
        this.id = id;
        this.idEsNull = false;
    }

    /**
     * Método para crear un grupo.
     *
     * @return - el número de grupo al que fue asignado.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public int creaGrupo() throws SQLException {
        int indice = 0;
        if (idEsNull) {
            indice = siguienteNumero();
        } else {
            indice = getId();
        }
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("INSERT INTO " + this.tabla + "(id)"
                + " VALUES(" + indice + ");");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
        return indice;
    }

    /**
     * Método para agregar un estudiante a un grupo.
     *
     * @param estudiante - es el estudiante que vamos a añadir.
     * @return - es el grupo al que se añadió el estudiante.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public int agregaEstudiante(Estudiante estudiante) throws SQLException {
        int indice = 0;
        if (idEsNull) {
            indice = siguienteNumero();
        } else {
            indice = getId();
            if (existeAlumnoEnGrupo(id, estudiante.getId())) {
                throw new SQLException();
            }
        }
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("INSERT INTO " + this.tablaEstudiantes + "(id,estudiante)"
                + " VALUES(" + indice + "," + estudiante.getId() + ");");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
        return indice;
    }

    /**
     * Método para agregar un profesor a un grupo.
     *
     * @param profesor - es el profesor a agregar.
     * @return - el grupo al que se agregó.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public int creaGrupoProfesor(Profesor profesor) throws SQLException {
        int indice = 0;
        if (idEsNull) {
            indice = siguienteNumero();
        } else {
            indice = getId();
        }
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("INSERT INTO " + this.tablaProfMat + "(id,profesor)"
                + " VALUES(" + indice + "," + profesor.getId() + ");");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
        return indice;
    }

    /**
     * Método para agregar una materia a un grupo.
     *
     * @param materia - es la materia a agregar.
     * @return - el grupo al que se añadió.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public int creaGrupoMateria(Materia materia) throws SQLException {
        int indice = 0;
        if (idEsNull) {
            indice = siguienteNumero();
        } else {
            indice = getId();
        }
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("INSERT INTO " + this.tablaProfMat + "(id,materia)"
                + " VALUES(" + indice + "," + materia.getId() + ");");
        operacion.executeUpdate(sql);
        operacion.close();
        conexion.commit();
        cierraCanal();
        return indice;
    }

    /**
     * Método para modificar el profesor de un grupo.
     *
     * @param profesor - el nuevo profesor del grupo.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void modificaProfesor(Profesor profesor) throws SQLException {
        if (!idEsNull) {
            if (existeGrupoSinMateriaNiProfe(this.getId())) {
                creaGrupoProfesor(profesor);
                return;
            }
            Connection conexion = abreCanal();
            Statement tablaQuery = conexion.createStatement();
            String sql = "UPDATE " + this.tablaProfMat + ""
                    + " SET profesor = " + profesor.getId() + ""
                    + " WHERE id == " + this.getId() + ";";
            tablaQuery.executeUpdate(sql);
            tablaQuery.close();
            conexion.commit();
            cierraCanal();
        }
    }

    /**
     * Método para modificar una materia de un grupo.
     *
     * @param materia - es la nueva materia del grupo.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void modificaMateria(Materia materia) throws SQLException {
        if (!idEsNull) {
            if (existeGrupoSinMateriaNiProfe(this.getId())) {
                creaGrupoMateria(materia);
                return;
            }
            Connection conexion = abreCanal();
            Statement tablaQuery = conexion.createStatement();
            String sql = "UPDATE " + this.tablaProfMat + ""
                    + " SET materia = " + materia.getId() + ""
                    + " WHERE id == " + this.getId() + ";";
            tablaQuery.executeUpdate(sql);
            tablaQuery.close();
            conexion.commit();
            cierraCanal();
        }
    }

    /**
     * Método que elimina al grupo.
     *
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void elimina() throws SQLException {
        Connection conexion = abreCanal();
        Statement operacion = conexion.createStatement();
        String sql = "";
        sql = sql.concat("DELETE FROM " + this.getNombreTabla() + " "
                + "WHERE id == " + this.getId() + ";");
        String sql2 = "DELETE FROM " + tablaEstudiantes + " "
                + "WHERE id == " + this.getId() + ";";
        String sql3 = "DELETE FROM " + tablaProfMat + " "
                + "WHERE id == " + this.getId() + ";";
        operacion.executeUpdate(sql);
        operacion.executeUpdate(sql2);
        operacion.executeUpdate(sql3);
        operacion.close();
        conexion.commit();
        cierraCanal();
    }

    /**
     * Método que elimina a un estudiante de un grupo.
     *
     * @param estudiante - es el estudiante a eliminar.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void eliminaEstudiante(Estudiante estudiante) throws SQLException {
        if (!idEsNull) {
            Connection conexion = abreCanal();
            Statement operacion = conexion.createStatement();
            String sql = "";
            sql = sql.concat("DELETE FROM " + tablaEstudiantes + " "
                    + "WHERE estudiante == " + estudiante.getId() + ""
                    + " AND id == " + this.getId() + ";");
            operacion.executeUpdate(sql);
            operacion.close();
            conexion.commit();
            cierraCanal();
        } else {
            Connection conexion = abreCanal();
            Statement operacion = conexion.createStatement();
            String sql = "";
            sql = sql.concat("DELETE FROM " + tablaEstudiantes + " "
                    + "WHERE estudiante == " + estudiante.getId() + ";");
            operacion.executeUpdate(sql);
            operacion.close();
            conexion.commit();
            cierraCanal();
        }
    }

    /**
     * Método para eliminar a el profesor del grupo.
     *
     * @param profesor - es el profesor a eliminar.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void eliminaProfesor(Profesor profesor) throws SQLException {
        if (!idEsNull) {
            Connection conexion = abreCanal();
            Statement operacion = conexion.createStatement();
            String sql = "";
            sql = sql.concat("UPDATE " + tablaProfMat + " "
                    + "SET profesor = '' "
                    + "WHERE profesor == " + profesor.getId() + ""
                    + " AND id == " + this.getId() + ";");
            operacion.executeUpdate(sql);
            operacion.close();
            conexion.commit();
            cierraCanal();
        } else {
            Connection conexion = abreCanal();
            Statement operacion = conexion.createStatement();
            String sql = "";
            sql = sql.concat("UPDATE " + tablaProfMat + " "
                    + "SET profesor = '' "
                    + "WHERE profesor == " + profesor.getId() + ";");
            operacion.executeUpdate(sql);
            operacion.close();
            conexion.commit();
            cierraCanal();
        }
    }

    /**
     * Método para eliminar la materia del grupo.
     *
     * @param materia - es la materia a eliminar.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public void eliminaMateria(Materia materia) throws SQLException {
        if (!idEsNull) {
            Connection conexion = abreCanal();
            Statement operacion = conexion.createStatement();
            String sql = "";
            sql = sql.concat("UPDATE " + tablaProfMat + " "
                    + "SET materia = '' "
                    + "WHERE materia == " + materia.getId() + ""
                    + " AND id == " + this.getId() + ";");
            operacion.executeUpdate(sql);
            operacion.close();
            conexion.commit();
            cierraCanal();
        } else {
            Connection conexion = abreCanal();
            Statement operacion = conexion.createStatement();
            String sql = "";
            sql = sql.concat("UPDATE " + tablaProfMat + " "
                    + "SET materia = '' "
                    + "WHERE materia == " + materia.getId() + ";");
            operacion.executeUpdate(sql);
            operacion.close();
            conexion.commit();
            cierraCanal();
        }
    }

    /**
     * Método para saber si el grupo existe.
     *
     * @param grupo - es el id del grupo.
     * @return regresa verdadero si el grupo existe en la tabla, falso caso
     * contrario.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public boolean existeGrupo(int grupo) throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM " + this.getNombreTabla() + " "
                + "WHERE id == " + grupo + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado.next();
    }

    /**
     * Método que nos regresa la tabla de los grupos.
     *
     * @return regresa la tabla en forma de ResultSet.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public ResultSet getTabla() throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM " + this.getNombreTabla() + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado;
    }

    /**
     * Método que regresa los estudiantes de un grupo.
     *
     * @return los estudiandiantes en un ResultSet de alumnos.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public ResultSet getEstudiantes() throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT A.id,A.nombre,A.apellidos,A.sexo FROM estudiantes A LEFT JOIN " + tablaEstudiantes + " B ON "
                + "A.id = B.estudiante WHERE B.id == " + this.getId() + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado;
    }

    /**
     * Método que regresa al profesor de un grupo.
     *
     * @return el profesor del grupo.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public ResultSet getProfesor() throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT A.id,A.titulo,A.nombre FROM profesores A LEFT JOIN " + tablaProfMat + " B ON "
                + "A.id = B.profesor WHERE B.id == " + this.getId() + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado;
    }

    /**
     * Método para obtener la materia del grupo.
     *
     * @return - la materia del grupo.
     * @throws SQLException - en caso de tener problemas de conexión.
     */
    public ResultSet getMateria() throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT A.id,A.nombre,A.semestre FROM materias A LEFT JOIN " + tablaProfMat + " B ON "
                + "A.id = B.materia WHERE B.id == " + this.getId() + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado;
    }

    //Método privado para ver si existe el alumno en el grupo.
    private boolean existeAlumnoEnGrupo(int grupo, int alumno) throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM " + tablaEstudiantes + " "
                + "WHERE id == " + grupo + " AND ESTUDIANTE == " + alumno + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado.next();
    }

    //Método privado para ver si el grupo se quedó sin profesor y sin materia.
    private boolean existeGrupoSinMateriaNiProfe(int grupo) throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM " + tablaProfMat + " "
                + "WHERE id == " + grupo + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        if (!resultado.next()) {
            Connection conexion2 = abreCanal();
            Statement tabla2 = conexion.createStatement();
            String sql2 = "SELECT * FROM " + this.getNombreTabla() + " "
                    + "WHERE id == " + grupo + ";";
            ResultSet resultado2 = tablaQuery.executeQuery(sql2);
            if (resultado2.next()) {
                return true;
            }
            return false;
        }
        return false;
    }

    //Método privado que me da el siguiente número de grupo.
    private int siguienteNumero() throws SQLException {
        Connection conexion = abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT MAX(id) FROM " + this.getNombreTabla() + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        int siguiente = resultado.getInt(1);
        siguiente++;
        return siguiente;
    }
}//Fin de Grupo.java
