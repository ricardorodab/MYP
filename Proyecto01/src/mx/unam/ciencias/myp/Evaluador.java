/* -------------------------------------------------------------------
 * Evaluador.java
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
 * <p>Esta clase contiene un Evaluador de funciones.</p>
 *
 * <p>Esta clase crea (como su nombre lo dice) un evaluador
 de funciones que nos permite tabular.</p>
*/
public class Evaluador{
    
    //Arbol de operadores que nos devuelve valores.
    private ArbolBinarioDeOperadores<String> arbol;
    //Son los valores desde y hasta donde evaluaremos.
    private double x1, x2;
    
    /**
     * Crea un objeto de tipo Evaluador.
     */
    public Evaluador(){
	this(new ArbolBinarioDeOperadores<String>(),-10,10);
    }
    
    /**
     * Crea un objeto de tipo Evaluador.
     * @param x1 - donde empieza a tabular.
     * @param x2 - donde termina de tabular.
     *
     */    
    public Evaluador(double x1, double x2){
	this(new ArbolBinarioDeOperadores<String>(),x1,x2);
    }
    
    /**
     * Crea un objeto de tipo Evaluador.
     * @param arbol - es un arbol de opreadores.
     */
    public Evaluador(ArbolBinarioDeOperadores<String> arbol){
	this(arbol,-10, 10);
    }
    
    /**
     * Crea un objeto de tipo Evaluador.
     * @param arbol - es un arbol de operadores.
     * @param x1 - donde empieza a tabular.
     * @param x2 - donde termina de tabular.
     */
    public Evaluador(ArbolBinarioDeOperadores<String> arbol, 
		     double x1, double x2){
	this.arbol = arbol;
	this.x1 = x1;
	this.x2 = x2;
    }
    
    /**
     * Método que dado ciertos valores de X tabula una función. 
     * @return un arreglo de Double que serán nuestra imagen.
     */
    public double[] tabular()
	throws ExcepcionExpresionIncorrecta{
	double[] valores;
	if(this.arbol.arbolNull()){
	    valores = new double[1];
	    valores[0] = 0;
	    return valores;
	}
	Double tamañoDouble = new Double(Math.ceil(Math.abs(this.x2-this.x1))); //MULTIPLICA POR 100
	int tamañoArreglo = (tamañoDouble.intValue())*100;
	double valorTemp, valorSuma;
	valorTemp = Math.min(this.x1, this.x2);
	valorSuma = (Math.abs(this.x2-this.x1))/tamañoArreglo;
	valores = new double[tamañoArreglo];      
	for(int i = 0; i < tamañoArreglo; i++){
	    valores[i] = arbol.evaluaX(valorTemp);
	    valorTemp += valorSuma;
	}
	return valores;	
    }
    
    /**
     * Método que asigna un arbol a nuestro objeto Evaluador.
     */
    public void setArbol(ArbolBinarioDeOperadores<String> arbol){
	this.arbol = arbol;
    }
    
    /**
     * Método que asigna el dominio a nuestro objeto Evaluador.
     * @param x1 - donde empieza el dominio.
     * @param x2 - donde termina el dominio.
     */
    public void setDominio(double x1, double x2){
	this.x1 = x1;
	this.x2 = x2;
    }
} //Fin de Evaluador.java
