/* -------------------------------------------------------------------
 * ArbolBinarioDeOperadores.java
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

import java.util.concurrent.LinkedBlockingQueue;
import java.util.LinkedList;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 2.0
 * @since Aug 27 2014.
 * <p>Esta clase crea un arbol binario de operadores.</p>
 *
 * <p>Construye y maneja ciertas operaciones para crear 
 y resolver ciertas operaciones matemáticas.</p>
*/
public class ArbolBinarioDeOperadores<T>{
    
    /**
     * Clase interna privada para vértices.
     */
    private class Vertice<T>{
	//Es el elemento de cada nodo.
	public T elemento;
	//Es el padre de cada nodo.
	public Vertice<T> padre;
	//Es el hijo izquierdo de cada nodo.
	public Vertice<T> izquierdo;
	//Es el hijo derecho de cada nodo.
	public Vertice<T> derecho;
	
	/**
	 * Constructor de la clase Vertice<T>.
	 * @param elemento - es el elemento del nodo.
	 */
        public Vertice(T elemento) {
            this.elemento = elemento;
        }
	
	/**
	 * Método que nos regresa el elemento del nodo en un String.
	 */
        public String toString() {
            return this.elemento.toString();
        }
	
        /**
	 * Método que nos dice si tiene o no hijo izquierdo el nodo.
	 * @return true en caso de tener y falso en caso contrario. 
	 */
	public boolean hayIzquierdo() {
            return this.izquierdo != null;
	}
	
        /**
	 * Método que nos dice si tiene o no hijo derecho el nodo.
	 * @return true en caso de tener y falso en caso contrario. 
	 */        
	public boolean hayDerecho() {
            return this.derecho != null;
	}
    }
    
    //Raíz que todo arbol tiene.
    private Vertice<T> raiz;

    /**
     * Constructor del arbol vacío.
     */
    public ArbolBinarioDeOperadores(){
	raiz = null;
    }

    /**
     * Constructor del arbol en base a una lista.
     * @param lista - es la lista de expresiones que contendrá el árbol.
     */
    public ArbolBinarioDeOperadores<T> generarDeLista(LinkedList<T> lista){
	ArbolBinarioDeOperadores<T> arbol = new ArbolBinarioDeOperadores<T>();
	if(lista.size() == 0)
	    return this;
	return ArbolBinarioDeOperadores(lista, arbol, null, 0);
    }
    
    /**
     * Método que nos dice si el árbol es o no null.
     * @return true sólo si el arbol es vacío.
     */
    public boolean arbolNull(){
	if(this.raiz == null)
	    return true;
	return false;
    }    

    /**
     * Método que evalua a una X en nuestro árbol.
     * @param x - es el valor a evaluar.
     * @return es el valor en base al x dado.
     */
    public double evaluaX(double x)
	throws ExcepcionExpresionIncorrecta{
	Vertice<T> v = this.raiz;
	double resultado = 0;
	if(x < 0)
	    return evaluaX(v, x, resultado,true);
	return evaluaX(v,x,resultado,false);
    }

    //Método privado que agrega sobre la izquierda.
    private Vertice<T> agregaIzquierda(T elemento, Vertice<T> v){
	Vertice<T> nuevo = new Vertice<T>(elemento);
	if(raiz == null || v == null){
	    raiz = nuevo;
	    return nuevo;
	}
	v.izquierdo = nuevo;
	nuevo.padre = v;
	return nuevo;
	
    }
    
    //Método privado que agrega sobre la derecha.
    private Vertice<T> agregaDerecha(T elemento, Vertice<T> v){
	Vertice<T> nuevo = new Vertice<T>(elemento);
	if(raiz == null || v == null){
	    raiz = nuevo;
	    return nuevo;
	}
	v.derecho = nuevo;
	nuevo.padre = v;
	return nuevo;
    }
    
    //Método privado que conecta por la derecha dos subárboles.
    private void conectaDerecha(Vertice<T> padre, Vertice<T> hijo){
	padre.derecho = hijo;
	hijo.padre = padre;
    }

    //Método privado que conecta por la izquierda dos subárboles.
    private void conectaIzquierda(Vertice<T> padre, Vertice<T> hijo){
	padre.izquierdo = hijo;
	hijo.padre = padre;
    }

    //Método privado que nos ayuda a contruir el arbol en base a una lista.
    private ArbolBinarioDeOperadores<T>
	ArbolBinarioDeOperadores(LinkedList<T> lista,
				 ArbolBinarioDeOperadores<T> arbol,
				 Vertice<T> vertice, int repetContador){
	if(lista.size() == 0)
	    return arbol;
	T elemento = lista.removeFirst();
	int caso = Tokens.token(elemento.toString());
	switch(caso){
	case 0:
	    vertice = arbol.agregaDerecha(elemento, vertice);
	    if(repetContador != 0){
		repetContador--;
		if(repetContador == 0)
		    break;
	    }
	    break;
	case 1:
	case 2:
	    vertice = arbol.agregaDerecha(elemento, vertice);
	    if(repetContador != 0){
		repetContador--;
		if(repetContador == 0)
		    break;
	    }
	    break;
	case 3:
	case 4:
	case 5:
	case 6:
	case 7:
	case 8:
	case 9:
	    vertice = arbol.agregaDerecha(elemento, vertice);
	    ArbolBinarioDeOperadores(lista, arbol, vertice,repetContador);				
	    break;		
	case 10:
	case 11:
	case 12:
	case 13:
	case 14:
	    repetContador++;
	    vertice = arbol.agregaDerecha(elemento, vertice); 
	    ArbolBinarioDeOperadores<T> arbolD = new ArbolBinarioDeOperadores<T>();
	    ArbolBinarioDeOperadores<T> arbolI = new ArbolBinarioDeOperadores<T>();
	    ArbolBinarioDeOperadores(lista, arbolD, null, repetContador);
	    ArbolBinarioDeOperadores(lista, arbolI, null, repetContador);
	    if(arbolD.raiz != null)
		arbol.conectaDerecha(vertice,arbolD.raiz);
	    if(arbolI.raiz != null)
		arbol.conectaIzquierda(vertice,arbolI.raiz);
	}
	return arbol;
    }

    //Quitar double resultado
    private double evaluaX(Vertice<T> v, double x, double resultado, boolean valor)
	throws ExcepcionExpresionIncorrecta{
	if(v == null)
	    return 0;
	double derecha, izquierda;
	switch(Tokens.token(v.toString())){
	case 0:
	    return Double.parseDouble(v.toString());
	case 1:
	    return x;
	case 2:
	    return ((x)*(-1));
	case 3:
	case 4:
	    double sen = evaluaX(v.derecho, x, resultado, valor);
	    return Math.sin(sen);
	case 5:
	    double cos = evaluaX(v.derecho, x, resultado, valor);
	    return Math.cos(cos);
	case 6:
	    double tan = evaluaX(v.derecho,x,resultado, valor);
	    return Math.tan(tan);
	case 7:
	    double cot = evaluaX(v.derecho,x,resultado, valor);
	    return (1/Math.tan(cot));
	case 8:
	    double sec = evaluaX(v.derecho,x,resultado, valor);
	    return (1/Math.cos(sec));
	case 9:
	    double csc = evaluaX(v.derecho,x,resultado,valor);
	    return (1/Math.sin(csc));
	case 10:
	    derecha = evaluaX(v.derecho,x,resultado,valor);
	    izquierda = evaluaX(v.izquierdo,x,resultado, valor);
	    return (derecha + izquierda);
	case 11:
	    derecha = evaluaX(v.derecho,x,resultado, valor);
	    izquierda = evaluaX(v.izquierdo,x,resultado,valor);
	    return (derecha - izquierda);
	case 12:
	    derecha = evaluaX(v.derecho,x,resultado,valor);
	    izquierda = evaluaX(v.izquierdo,x,resultado,valor);
	    return (derecha * izquierda);
	case 13:	    
	    derecha = evaluaX(v.derecho,x,resultado,valor);
	    izquierda = evaluaX(v.izquierdo,x,resultado,valor);
	    if(izquierda == 0)
		throw new ExcepcionExpresionIncorrecta("División entre 0");
	    return (derecha / izquierda);
	case 14:
	    derecha = evaluaX(v.derecho,x,resultado,valor);
	    izquierda = evaluaX(v.izquierdo,x,resultado,valor);
	    if(derecha < 0 && ((int)(izquierda) != (izquierda)) && valor){
		return 0;
	    }else if(derecha < 0 && ((int)(izquierda) != (izquierda)) && !valor){
	    	throw new ExcepcionExpresionIncorrecta("Se intentó sacar raíz a un número negativo que no es X");
	    }
	    return Math.pow(derecha, izquierda);
	default: 
	    	throw new ExcepcionExpresionIncorrecta("No es una expresión válida.\n"
						       +"Expresiones válidas: \n"
						       +"<Números>, x, -x, +, -, *, /, ^, "
						       +"sin, sen, cos, tan, cot, sec, csc");
	}
	
    }
} //Fin de ArbolBinarioDeOperadores.java
