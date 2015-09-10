package mx.unam.ciencias.myp.test;

import java.util.Random;
import mx.unam.ciencias.myp.GramaticaPrefija;
import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedList;
import mx.unam.ciencias.myp.ExcepcionExpresionIncorrecta;

/**
 * Clase de prueba unitaria
 */
public class TestGramaticaPrefija{

    private Random random = new Random();
    private int parentesis = 0;
    private LinkedList<String> lista;

    /**
     * Método que prueba el analizador de gramática.
     */
    @Test
	public void TestReconocerGramaticaPrefija(){
	try{
	    lista = new LinkedList<String>();
	    String expresion = "";
	    int i = random.nextInt(1000);
	    int j = 0;
	    while(j < i){
		int ran = random.nextInt(1000);
		switch(ran%5){
		case 0:
		    lista.add("+");
		    expresion = expresion.concat("(+ ");
		    Integer a1 = random.nextInt(10);
		    expresion = expresion.concat(a1.toString()+" ");
		    lista.add(a1.toString());
		    parentesis++;
		    break;
		case 1:
		    lista.add("/");
		    expresion = expresion.concat("(/ ");
		    Integer a2 = random.nextInt(10);
		    expresion = expresion.concat(a2.toString()+" ");
		    lista.add(a2.toString());
		    parentesis++;
		    break;
		case 2:
		    lista.add("-");
		    expresion = expresion.concat("(- ");
		    Integer a3 = random.nextInt(10);
		    expresion = expresion.concat(a3.toString()+" ");
		    lista.add(a3.toString());
		    parentesis++;
		    break;
		case 3:
		    lista.add("*");
		    expresion = expresion.concat("(* ");
		    Integer a4 = random.nextInt(10);
		    expresion = expresion.concat(a4.toString()+" ");
		    lista.add(a4.toString());
		    parentesis++;
		    break;
		case 4:
		    lista.add("^");
		    lista.add("-x");
		    expresion = expresion.concat("(^ ");
		    expresion = expresion.concat(" -x ");
		    parentesis++;
		    break;
		}
		j++;
	    }
	    expresion.concat("(sin");
	    expresion.concat("(cos");
	    expresion.concat("(tan");
	    expresion.concat("(sec");
	    expresion.concat("(cot");
	    expresion.concat("(csc ");
	    expresion.concat("x))))))");	
	    while(parentesis > 0){
		expresion.concat(" )");
		parentesis--;
	    }
	    LinkedList<String> lista2 = new LinkedList<String>();
	    GramaticaPrefija.reconocerGramaticaPrefija(lista2,expresion);
	    Assert.assertTrue(lista.size() == lista2.size());	    
	    for(int p = 0; p < lista.size(); p++)
		Assert.assertTrue(lista.get(p).equals(lista2.get(p)));
	}catch(ExcepcionExpresionIncorrecta exc){}	    
    }
    //Assert.assertTrue();
}