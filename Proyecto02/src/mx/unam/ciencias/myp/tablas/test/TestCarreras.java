package mx.unam.ciencias.myp.tablas.test;

import java.util.Random;
import java.sql.ResultSet;
import java.sql.Connection; 
import java.sql.SQLException;
import java.sql.Statement;
import mx.unam.ciencias.myp.tablas.*;
import mx.unam.ciencias.myp.interfazGrafica.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Clase de prueba unitaria
 */
public class TestCarreras{
    
    private Carrera carrera;
    
    
    /**
     * Prueba unitaria para el constructor de carrera.
     */
    @Test
	public void testCarrera(){
	carrera = new Carrera();
	Assert.assertFalse(carrera == null);
	Assert.assertTrue(carrera.getId() == -1);
	Assert.assertTrue(carrera.getNombre().equals(""));
	
	carrera.setId(1);
	carrera.setNombre("Carrera");
	
	Carrera carrera2 = new Carrera(1,"Carrera");
	Assert.assertTrue(carrera.getId() == carrera2.getId());
	Assert.assertTrue(carrera.getNombre().equals(carrera2.getNombre()));
    }
} //Fin de TestCarreras.java