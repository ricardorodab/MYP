package mx.unam.ciencias.myp.test;

import java.util.Random;
import mx.unam.ciencias.myp.ArbolBinarioDeOperadores;
import mx.unam.ciencias.myp.Expresiones;
import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedList;
import mx.unam.ciencias.myp.ExcepcionExpresionIncorrecta;

/**
 * Clase de prueba unitaria
 */
public class TestExpresiones{

    private Random random = new Random();
    private ArbolBinarioDeOperadores<String> arbol1 = 
	new ArbolBinarioDeOperadores<String>();
    private ArbolBinarioDeOperadores<String> arbol2 = 
	new ArbolBinarioDeOperadores<String>();
    private LinkedList<String> lista = new LinkedList<String>();
    private Expresiones exp;
    private int parentesis = 0;
    
   /**
     * Método que prueba las diferentes expresiones.
     */
    @Test
	public void TestReconoce(){
	try{
	    String expresion = "";
	    int i = random.nextInt(1000);
	    int azar = random.nextInt(1000);
	    int d2 = random.nextInt(1000);
	    lista = new LinkedList<String>();
	    int j = 0;
	    while(j < i){
		switch(j%5){
		case 0:
		    expresion = expresion.concat("(+ ");
		    parentesis++;
		    lista.add("+");
		    Integer a1 = random.nextInt(10);
		    lista.add(a1.toString());
		    expresion = expresion.concat(a1.toString()+ " ");
		    break;
		case 1:
		    expresion = expresion.concat("(/ ");
		    parentesis++;
		    lista.add("/");
		    Integer a2 = random.nextInt(10);
		    lista.add(a2.toString());
		    expresion = expresion.concat(a2.toString()+" ");
		    break;
		case 2:
		    expresion = expresion.concat("(- ");
		    parentesis++;
		    lista.add("-");
		    Integer a3 = random.nextInt(10);
		    lista.add(a3.toString());
		    expresion = expresion.concat(a3.toString()+" ");
		    break;
		case 3:
		    expresion = expresion.concat("(* ");
		    parentesis++;
		    lista.add("*");
		    Integer a4 = random.nextInt(10);
		    lista.add(a4.toString());
		    expresion = expresion.concat(a4.toString()+" ");
		    break;
		case 4:
		    expresion = expresion.concat("(^ ");
		    parentesis++;
		    lista.add("^");
		    lista.add("-x");
		    expresion = expresion.concat("-x ");
		    break;
		}
		j++;
	    }
	    lista.add("sin");
	    expresion = expresion.concat("(sin ");
	    lista.add("cos");
	    expresion = expresion.concat("(cos ");
	    lista.add("tan");
	    expresion = expresion.concat("(tan ");
	    lista.add("sec");
	    expresion = expresion.concat("(sec ");
	    lista.add("cot");
	    expresion = expresion.concat("(cot ");
	    lista.add("csc");
	    expresion = expresion.concat("(csc ");
	    lista.add("x");	
	    expresion = expresion.concat("x))))))");
	    while(parentesis > 0){
		expresion.concat(" )");
		parentesis--;
	    }
	    LinkedList<String> lista2 = new LinkedList<String>();
	    exp = new Expresiones(expresion);
	    //Prueba del tamaño de las listas.
	    Assert.assertTrue(lista.size() == lista2.size());
	    //Prueba de la igualdad.
	    for(int k = 0; k < lista.size(); k++)
		Assert.assertTrue(lista.get(i).equals(lista2.get(i)));
	}catch(ExcepcionExpresionIncorrecta exc){}
    }




} //Fin de TestExpresiones.java