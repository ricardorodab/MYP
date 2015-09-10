/* -------------------------------------------------------------------
 * Tokens.java
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
 * <p>Esta clase contiene un identificador de casos.</p>
 *
 * <p>Esta clase nos auxilia únicamente para reconoces patrones.</p>
*/
public class Tokens{
    
    /**
     * Método que nos ayuda a identificar expresiones.
     * @param token - es la expresión a identificar.
     * @return es el número de expresión.
     */
    public static int token(String token){
	token = token.toUpperCase();
	switch(token){
	case "X":
	    return 1;
	case "-X":
	    return 2;
	case "SEN":
	    return 3;
	case "SIN":
	    return 4;
	case "COS":
	    return 5;
	case "TAN":
	    return 6;
	case "COT":
	    return 7;
	case "SEC":
	    return 8;
	case "CSC":
	    return 9;
	case "+":
	    return 10;
	case "-":
	    return 11;
	case "*":
	    return 12;
	case "/":
	    return 13;
	case "^":
	    return 14;
	default:
	    return 0;
	}
    }
} //Fin de Tokens.java
