package mx.unam.ciencias.myp.test;

import java.util.Random;
import mx.unam.ciencias.myp.ArbolBinarioDeOperadores;
import mx.unam.ciencias.myp.Evaluador;
import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedList;
import mx.unam.ciencias.myp.ExcepcionExpresionIncorrecta;

/**
 * Clase de prueba unitaria
 */
public class TestEvaluador{

    private Random random = new Random();
    private ArbolBinarioDeOperadores<String> arbol = 
	new ArbolBinarioDeOperadores<String>();
    private LinkedList<String> lista = new LinkedList<String>();
    private Evaluador evaluador;

    /**
     * Método que prueba el evaluador por default.
     */
    @Test
	public void TestEvaluadorDefault(){
	try{
	    int i = random.nextInt(1000);
	    evaluador = new Evaluador();
	    double[] valores = evaluador.tabular();
	    Assert.assertTrue(valores.length == 1);
	    Assert.assertTrue(valores[0] == 0);
	    lista = new LinkedList<String>();
	    int j = 0;
 	    while(j < i){
		lista.add("+");
		lista.add("5");
		lista.add("/");
		lista.add("4");
		lista.add("-");
		lista.add("2");
		j++;
	    }
	    lista.add("x");	
	    arbol = arbol.generarDeLista(lista);
	    evaluador.setArbol(arbol);
	    valores = evaluador.tabular();
	    double[] valoresTest = new double[20*100];
	    double valorTemp = -10;
	    double valorSuma = 0.01;
	    Assert.assertTrue(valoresTest.length == valores.length);
	    for(int k = 0; k < valoresTest.length; k++){
		valoresTest[k] = arbol.evaluaX(valorTemp);
		valorTemp += valorSuma;
	    }
	    for(int l = 0; l < valores.length; l++)
		Assert.assertTrue(valoresTest[l] == valores[l]);	
	}catch(ExcepcionExpresionIncorrecta expr){}
    }
    
    /**
     * Método que prueba el evaluador con dominio.
     */
    @Test
	public void TestEvaluadorDominio(){
	try{
	    int i = random.nextInt(1000);
	    int d1 = random.nextInt(1000);
	    int d2 = random.nextInt(1000);
	    int max = Math.max(d1,d2);
	    int min = max == d1 ? d2 : d1;
	    lista = new LinkedList<String>();
	    int j = 0;
	    while(j < i){
		lista.add("+");
		lista.add("5");
		lista.add("/");
		lista.add("4");
		lista.add("-");
		lista.add("2");
		j++;
	    }
	    lista.add("x");	
	    arbol = arbol.generarDeLista(lista);
	    evaluador = new Evaluador(arbol, d1, d2);
	    double [] valores = evaluador.tabular();
	    double[] valoresTest = new double[(max-min)*100];
	    double valorTemp = min;
	    double valorSuma = (double)(max-min)/valoresTest.length;
	    Assert.assertTrue(valoresTest.length == valores.length);
	    for(int k = 0; k < valoresTest.length; k++){
		valoresTest[k] = arbol.evaluaX(valorTemp);
		valorTemp += valorSuma;
	    }
	    for(int l = 0; l < valores.length; l++)
		Assert.assertTrue(valoresTest[l] == valores[l]);	
	}catch(ExcepcionExpresionIncorrecta expr){}
    }
} //Fin de TestEvaluador.java