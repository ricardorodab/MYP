package mx.unam.ciencias.myp.test;

import java.util.Random;
import mx.unam.ciencias.myp.EsNumero;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase de prueba unitaria
 */
public class TestEsNumero{

    private Random random = new Random();

    /**
     * Crea una cantidad grande de números y revisa que sí sean números.
     */
    @Test
	public void testPrueba(){
	int i = 0;
	while(i < 100000){
	    Double db = random.nextDouble()*Double.MAX_VALUE;
	    String str = db.toString();
	    Assert.assertTrue(EsNumero.esNumero(str));
	    i++;
	}
	String uno = "Uno";
	Assert.assertFalse(EsNumero.esNumero(uno));
    }
} //Fin de TestEsNumero.java