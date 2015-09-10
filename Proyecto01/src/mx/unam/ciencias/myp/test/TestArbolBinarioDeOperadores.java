package mx.unam.ciencias.myp.test;

import java.util.Random;
import mx.unam.ciencias.myp.ArbolBinarioDeOperadores;
import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedList;
import mx.unam.ciencias.myp.ExcepcionExpresionIncorrecta;

/**
 * Clase de prueba unitaria
 */
public class TestArbolBinarioDeOperadores{

    private Random random = new Random();
    private ArbolBinarioDeOperadores<String> arbol = null;
    private LinkedList<String> lista = new LinkedList<String>();

    /**
     * Prueba que el constructor se crea correctamente.
     */
    @Test
	public void testArbolBinarioDeOperadores(){
	arbol = new ArbolBinarioDeOperadores<String>();
	Assert.assertFalse(arbol == null);
    }

    /**
     * Nos dice si el árbol es vacío
     */
    public void testArbolNull(){
	Assert.assertTrue(arbol.arbolNull());
	lista.add("0"); 
	arbol = arbol.generarDeLista(lista);
	Assert.assertFalse(arbol.arbolNull());
    }

    /**
     * Nos da los valores del árbol.
     */
    public void testEvaluaX(){
	int i = random.nextInt(100000);
	int j,k;
	j = k = 0;
	double e = random.nextDouble()*100000;
	lista = new LinkedList<String>();
	while(j < i){
	    lista.add("+");
	    lista.add("5");
	    lista.add("/");
	    lista.add("4");
	    lista.add("-");
	    lista.add("2");
	    j++;
	}
	try{
	    arbol = arbol.generarDeLista(lista);
	    double x = arbol.evaluaX(e);
	    Assert.fail();
	}catch(ExcepcionExpresionIncorrecta expr){}
	lista.add("x");
	try{
	arbol = arbol.generarDeLista(lista);
	double xArbol = arbol.evaluaX(e);
	double xTest = 5+(4/(2-e));
	while(k < i){
	    xTest = 5+(4/(2-xTest));
	    k++;
	}
	Assert.assertTrue(xArbol == xTest);
	xArbol = arbol.evaluaX(e+i);
	Assert.assertFalse(xArbol == xTest);
	}catch(ExcepcionExpresionIncorrecta expr){}
    }
    

} //Fin de TestArbolBinarioDeOperadores.java