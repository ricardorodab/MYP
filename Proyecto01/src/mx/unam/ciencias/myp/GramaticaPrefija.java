/* -------------------------------------------------------------------
 * GramaticaPrefija.java
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
 * <p>Esta clase contiene las reglas de gramática prefija.</p>
 *
 * <p>Esta clase crea contiene las reglas de gramática prefija
 que es la base para evaluar funciones.</p>
*/
public class GramaticaPrefija{

    //Variable parentesis para reconocer una expresion bien formada.
    private static int parentesis = 0;
    //Numero de caracter en el que estamos.
    private static int charAt = 0;
    //Cadena original a reconocer.
    private static String original;
    
    /**
     * Método que reconoce una expresión en formato prefijo.
     * @param expresion - es la expresión a reconocer.
     * @throws ExcepcionExpresionIncorrecta - si la gramática es incorrecta.
     */
    public static void reconocerGramaticaPrefija(LinkedList<String> lista, String expresion)
	throws ExcepcionExpresionIncorrecta{
	if(original == null)
	    original = expresion;
	expresion = quitaEspacios(expresion);
	if(expresion.equals("")){
	    if(parentesis != 0)
		throw new ExcepcionExpresionIncorrecta("Cantidad incorrecta de parentesis");
	    return;
	}
	if(expresion.charAt(0) == '(' ||
	   expresion.charAt(0) == ')'){
	    expresion = quitaParentesis(expresion);
	    if(expresion.equals("")){
		if(parentesis != 0)
		    throw new ExcepcionExpresionIncorrecta("Cantidad incorrecta de parentesis");
		return;
	    }
	    reconocerGramaticaPrefija(lista, expresion);
	    return;
	}
	if(EsNumero.esNumero(expresion.substring(0,1)) ||
	   (expresion.length() > 1 && EsNumero.esNumero(expresion.substring(0,2)))){	    
	    int finNumero = casoNumero(expresion);
	    lista.add(expresion.substring(0,finNumero-1));	
	    if(parentesis != 0){
		reconocerGramaticaPrefija(lista,expresion.substring(finNumero-1));
	    }
	    return;
	}
	switch(detectarOperador(expresion)){
	case 0:
	    lista.add(expresion.substring(0,1));
	    charAt++;
	    if(parentesis != 0){	       
		reconocerGramaticaPrefija(lista,expresion.substring(1));	    

	    }
	    return;
	case 1:
	    lista.add(expresion.substring(0,3));
	    reconocerGramaticaPrefija(lista,expresion.substring(3));
	    return;
	case 2:
	    lista.add(expresion.substring(0,2));
	    reconocerGramaticaPrefija(lista,expresion.substring(2));	    	    
	    return;
	case 3:
	    lista.add(expresion.substring(0,1));
	    reconocerGramaticaPrefija(lista,expresion.substring(1));
	    return;
	default:
	    throw new ExcepcionExpresionIncorrecta("CARACTER NO VÁLIDO en la expresión\n"
						   +"No es una expresión válida.\n"
						       +"Expresiones válidas: \n"
						       +"<Números>, x, -x, +, -, *, /, ^, "
						       +"sin, sen, cos, tan, cot, sec, csc");
	}
    }
    
    //Método auxiliar que quita los paréntesis.
    private static String quitaParentesis(String expresion){
	int i, j, posicion;
	i = j = posicion = 0;      
	while(expresion.charAt(posicion) == '(' ||
	      expresion.charAt(posicion) == ')' ){
	    if(expresion.charAt(posicion) == '('){
		i++;
		parentesis++;
		posicion++;
		
	    }else if(expresion.charAt(posicion) == ')'){
		j--;
		parentesis--;
		posicion++;		
	    }
	    if(posicion >= expresion.length())
		break;
	}
	if(posicion >= expresion.length())
	    return "";
	expresion = expresion.substring(posicion);
	return expresion;
    }
    
    //Método auxiliar que quita los espacios.
    private static String quitaEspacios(String expresion){
	if((expresion.length() == 1 && expresion.charAt(0) == ' ') ||
	   expresion.equals(""))
	    return "";
	int i = 0;
	while(expresion.charAt(i) == ' ')
	    i++;
	return expresion.substring(i);
    }
    
    //Método auxiliar que nos ayuda a manejar números.
    private static int casoNumero(String expresion){
	if(EsNumero.esNumero(expresion)){
	    return expresion.length()+1;	  
	}
	int i;
	if(expresion.charAt(0) == '-')
	    i = 2;
	else
	    i = 1;
	while(EsNumero.esNumero(expresion.substring(0,i)))
	    i++;
	return i;
    }
    
    //Método auxiliar que nos ayuda a detectar en que operador estamos.
    private static int detectarOperador(String expresion)
    throws ExcepcionExpresionIncorrecta{
	switch(expresion.charAt(0)){
	case 'x':
	case 'X':
	    return 0;
	case 'S':
	case 'C':
	case 'T':
	case 's':
	case 'c':
	case 't':
	    return 1;
	case '-':
	    if(expresion.length() <= 1)
		throw new ExcepcionExpresionIncorrecta("El operador \"-\" tiene que ir "+
						       "acompañado de un número o dos expresiones.");
	    if(expresion.substring(1,2).equalsIgnoreCase("x")){
		return 2;
	    }
	case '+':
	case '*':
	case '/':
	case '^':
	    return 3;
	default: 
	    return -1;
	}
    }
} //Fin de GramaticaPrefija.java
