package mx.unam.ciencias.myp.test;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import mx.unam.ciencias.myp.Tokens;

/**
 * Clase de prueba unitaria
 */
public class TestTokens{

    private Random random = new Random();

   /**
     * Método que prueba las diferentes expresiones.
     */
    @Test
	public void TestToken(){
	Integer lim = random.nextInt(100000);
	String toke[] = {"+","-","*","/","^","sin","sen","cos","tan","cot","sec","csc","x","-x"};
	int i = 0;
	int caso;
	while(i < lim){	    
	    Integer integer = random.nextInt(100000);
	    caso = Tokens.token(toke[integer%14]);	
	    Assert.assertFalse(caso == 0);
	    switch(integer%14){
	    case 0:
		Assert.assertTrue(caso == 10);
		break;
	    case 1:
		Assert.assertTrue(caso == 11);
		break;
	    case 2:
		Assert.assertTrue(caso == 12);
		break;
	    case 3:
		Assert.assertTrue(caso == 13);
		break;
	    case 4:
		Assert.assertTrue(caso == 14);
		break;
	    case 5:
		Assert.assertTrue(caso == 4);
		break;
	    case 6:
		Assert.assertTrue(caso == 3);
		break;
	    case 7:
		Assert.assertTrue(caso == 5);
		break;
	    case 8:
		Assert.assertTrue(caso == 6);
		break;
	    case 9:
		Assert.assertTrue(caso == 7);
		break;
	    case 10:
		Assert.assertTrue(caso == 8);
		break;
	    case 11:
		Assert.assertTrue(caso == 9);
		break;
	    case 12:
		Assert.assertTrue(caso == 1);
		break;
	    case 13:
		Assert.assertTrue(caso == 2);
		break;
	    }
	    i++;
	}    	
    }
} //Fin de TestTokens.java