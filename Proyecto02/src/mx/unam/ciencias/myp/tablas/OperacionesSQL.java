/* -------------------------------------------------------------------
 * OperacionesSQL.java
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Interfaz de operaciones SQL.</p>
 *
 * <p>
 * Interfaz que define las operaciones básicas que nuestro programa debe cubrir,
 * que cada tabla debe poder implementar.</p>
 */
public interface OperacionesSQL {

    /**
     * Metodo que nos regresa la tabla.
     *
     * @return Los resultados de la tabla.
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    public ResultSet getTabla() throws SQLException;

    /**
     * Metodo que nos regresa una tabla con condiciones.
     *
     * @param where - es la condicion que cumplen. Debe ir en formato: "WHERE
     * [CONDICION]".
     * @return Los resultados de la tabla.
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    public ResultSet getTabla(String where) throws SQLException;

    /**
     * Método que nos regresa los grupos de la tabla.
     *
     * @return - los grupos que contengan al objeto.
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    public ResultSet buscaGrupo() throws SQLException;

    /**
     * Método para agregar a nuestra tabla.
     *
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    public void agrega() throws SQLException;

    /**
     * Metodo para buscar un elemento de la tabla.
     *
     * @param where - es la condicion que cumplen. Debe ir en formato: "WHERE
     * [CONDICION]".
     * @return Una lista ligada con los índices de las coincidencias.
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    public LinkedList<Integer> buscaNombre(String where) throws SQLException;

    /**
     * Metodo para buscar un elemento de la tabla.
     *
     * @deprecated
     * @param condicion - es la condicion a buscar.
     * @return Una lista ligada con los índices de las coincidencias.
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    public LinkedList<Integer> busca(String condicion) throws SQLException;

    /**
     * Método para actualizar la clave del objeto.
     *
     * @param clave - es la nueva clave.
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    public void actualizaId(int clave) throws SQLException;

    /**
     * Método para actualizar la tabla con el objeto que la llama.
     *
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    public void actualiza() throws SQLException;

    /**
     * Método para eliminar un objeto de nuestra base de datos.
     *
     * @throws SQLException - en caso de tener algun error de comunicacion o de
     * parametros incorrectos.
     */
    public void elimina() throws SQLException;

} //Fin de OperacionesSQL.java
