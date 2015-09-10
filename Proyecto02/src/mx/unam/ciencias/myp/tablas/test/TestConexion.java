package mx.unam.ciencias.myp.tablas.test;

import java.util.Random;
import mx.unam.ciencias.myp.tablas.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase de prueba unitaria
 */
public class TestConexion{

    private Connection conexion;

    /**
     * Prueba para revisar que la conexión a la base de datos funciona.
     */
    @Test
	public void testConecta(){
	Assert.assertTrue(conexion == null);
	conexion = Conexion.abreCanal();
	Assert.assertFalse(conexion == null);
	Connection conexion2 = Conexion.abreCanal();
	Assert.assertTrue(conexion == conexion2);
	try{
	Assert.assertFalse(conexion.isClosed());	
	Conexion.cierraCanal();
	Assert.assertTrue(conexion.isClosed());
	}catch(SQLException ex){
	    Assert.fail();
	} 
    }
    /**
     * Prueba para revisar que la conexión a la base fue cerrada.
     */
    @Test
	public void testDesconecta(){
	try{
	    conexion = Conexion.abreCanal();
	    Conexion.cierraCanal();
	    Assert.assertTrue(conexion.isClosed());
	    conexion = Conexion.abreCanal();	
	    Assert.assertFalse(conexion.isClosed());	
	    Conexion.cierraCanal();
	    Assert.assertTrue(conexion.isClosed());
	}catch(SQLException ex){
	    	    Assert.fail();
	} 
    }
} //Fin de TestConexion.java
