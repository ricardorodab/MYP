/* -------------------------------------------------------------------
 * ExcepcionExpresionIncorrecta.java
 * versión 2.0
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
package mx.unam.ciencias.myp;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 2.0
 * @since Aug 27 2014.
 * <p>Esta clase contiene una nueva excepción.</p>
 *
 * <p>Esta clase nos ayuda a lanzar una excepción en el caso
 de identificar una expresión incorrecta.</p>
*/
public class ExcepcionExpresionIncorrecta extends Exception{
    
    /**
     * Método que lanza la excepción.
     * @param error - es el mensaje que tiene que decir.
     */
    public ExcepcionExpresionIncorrecta(String error){
	super(error);
    }
} //Fin de ExcepcionExpresionIncorrecta.java
