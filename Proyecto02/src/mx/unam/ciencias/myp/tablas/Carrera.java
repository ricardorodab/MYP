/* -------------------------------------------------------------------
 * Carrera.java
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

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since Oct 11 2014.
 * <p>
 * Clase que da el comportamiento de la tabla carreras.</p>
 *
 * <p>
 * Desde esta clase podemos obtener el comportamiento deseado de la tabla.</p>
 */
public class Carrera extends Facultad {

    private final String tabla;
    private int id;
    private String nombre;

    /**
     * Contructor que se ofrece por completes.
     */
    public Carrera() {
        this.tabla = "carreras";
        this.id = -1;
        this.nombre = "";
    }

    /**
     * Constructor para usar la tabla carrera.
     *
     * @param id - es el id de la carrera.
     * @param nombre - es el nombre de la carrera.
     */
    public Carrera(int id, String nombre) {
        this.tabla = "carreras";
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Metodo que nos regresa el nombre de la tabla de carreras.
     *
     * @return el nombre de la tabla.
     */
    @Override
    public String getNombreTabla() {
        return this.tabla;
    }

    /**
     * Método que nos regresa el id de una carrera
     *
     * @return - el id de la carrera.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Método que nos regesa el nombre de la carrera
     *
     * @return - el nombre de la carrera.
     */
    @Override
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Método para colocar id a una carrera.
     *
     * @param id - es el id nuevo de la carrera.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método para darle nombre a una carrera.
     *
     * @param nombre - es el nuevo nombre de la carrera
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método todavía no implementado que busca un grupo de cierta carrera.
     *
     * @exception UnsupportedOperationException - Sin implementación.
     * @return - UnsupportedOperationException ya que no está soportado.
     * @throws SQLException - en caso de tener problemas de conexión al
     * implementar.
     */
    @Override
    public ResultSet buscaGrupo() throws SQLException {
        //Queda abierto a posibilidades como grupos exclusivos de algunas carreras.
        throw new UnsupportedOperationException("Las carreras no tienen grupos");
    }
} //Fin de Carrera.java
