/* -------------------------------------------------------------------
 * Expresiones.java
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

import java.util.LinkedList;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 2.0
 * @since Aug 27 2014.
 * <p>Clase para identificar expresiones matemáticas.</p>
 *
 * <p>Esta clase crea un objeto llamado Expresiones que 
 nos ayuda a identificar funciones matemáticas.</p>
 */
public class Expresiones{

    //Lista que nos ayudará a ordenar las expresiones.
    private LinkedList<String> lista;
    //Arbol que podemos crear a partir del reconocimiento de los Tokens.
    private ArbolBinarioDeOperadores<String> arbol; 
    
    /**
     * Constructor de la clase Expresiones.
     * @param expresion - es la cadena a revisar.
     * @throws ExcepcionExpresionIncorrecta - en caso de no ser válida.
     */
    public Expresiones(String expresion)
	throws ExcepcionExpresionIncorrecta{
	this.lista = new LinkedList<String>();
	this.arbol = new ArbolBinarioDeOperadores<String>();
	reconoce(lista, expresion);
    }
    
    /**
     * Método que nos ayuda a reconocer con nuestra gramática
     * aquellas expresiones bien formadas y las coloca en una lista.
     * @param lista - la lista donde colocará las expresiones.
     * @param expresion - es la posible expresión bien formada.
     */
    public void reconoce(LinkedList<String> lista, String expresion)
	throws ExcepcionExpresionIncorrecta{
	expresion = expresion.trim();
	GramaticaPrefija.reconocerGramaticaPrefija(lista, expresion);
    }

    /**
     * Método que regresa el árbol de la expresión.
     * @return el arbol que contiene a la expresión.
     */
    public ArbolBinarioDeOperadores<String> construyeArbol(){
        arbol = arbol.generarDeLista(this.lista);
	return arbol;
    }
    
} //Fin de Expresiones.java
