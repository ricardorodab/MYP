package mx.unam.ciencias.myp.tablas.test;

import java.util.Random;
import java.sql.ResultSet;
import java.sql.Connection; 
import java.sql.SQLException;
import java.sql.Statement;
import javafx.concurrent.Task;
import mx.unam.ciencias.myp.tablas.*;
import mx.unam.ciencias.myp.interfazGrafica.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 * Clase de prueba unitaria
 */
public class TestMaterias{
    
    private Materia materia;

    /**
     * Prueba unitaria para el constructor de materia.
     */
    @Test
	public void testMateria(){
	try{
	    materia = new Materia();
	    Assert.assertFalse(materia == null);
	    Assert.assertTrue(materia.getId() == -1);
	    Assert.assertTrue(materia.getNombre().equals(""));
	    Assert.assertTrue(materia.getSemestre() == 0);
	    
	    materia.setId(1);
	    materia.setNombre("Materia");
	    materia.setSemestre(1);
	    
	    Materia materia2 = new Materia(1,"Materia",1);
	    Assert.assertTrue(materia.getId() == materia2.getId());
	    Assert.assertTrue(materia.getNombre().equals(materia2.getNombre()));
	    Assert.assertTrue(materia.getSemestre() == materia2.getSemestre());
	}catch(SQLException ex){
	    Assert.fail();
	}
    }
    
    /**
     * Prueba unitaria para revisar el metodo para llamar la tabla.
     */
    @Test
	public void testGetTabla(){
	for(int i = 0; i < 10; i++){
	    try{
		Random random = new Random();		
		char letra1,letra2 = letra1 = 'A';
		int clave = -1;
		while(clave < 0 || clave > 25)
		    clave = random.nextInt()%26;
		letra1 = (char)((clave%26)+65);
		if(letra1 == 'A' || letra1 == 'E' || letra1 == 'I'
		   || letra1 == 'O' || letra1 == 'U')
		    clave = -1;
		do{
		    clave = random.nextInt()%26;
		    letra2 = (char)((clave%26)+65);
		    if(letra2 != 'A' && letra2 != 'E' && letra2 != 'I'
		       && letra2 != 'O' && letra2 != 'U')
			clave = -1;
		}while(clave < 0 || clave > 25);
		
		char[] arreglo = {letra1,letra2};
		String where = new String(arreglo);
		ResultSet resultado = new Materia().getTabla(where);
		while(resultado.next()){
		    String nombre = resultado.getString(2).toUpperCase();
		    Assert.assertTrue(nombre.contains(where));
		}
	    }catch(SQLException ex){
		Assert.fail();
	    }
	}
    }
    
    /**
     * Prueba unitaria para ver que funciona la búsqueda de grupos.
     */
    @Test
	public void testBuscaGrupo(){
	try{
	    ResultSet grupoA = new Grupo().getMateria();
	    while(grupoA.next()){
		int i = grupoA.getInt(1);
		materia = new Materia(i, "", -1);
		ResultSet resultado =materia.buscaGrupo();
		while(resultado.next()){
		    Assert.assertTrue(existeMateria(resultado.getInt(1),i));
		}
	    }
	}catch(SQLException ex){
	    Assert.fail();
	}
    }
    
    /**
     * Prueba unitaria para agregar y eliminar materias.
     */
    @Test
	public void testAgregaYElimina(){	
	try{
	    for(int i = 0; i < 10; i++){
		Random random = new Random();
		int aleatorio = random.nextInt();
		if(aleatorio < 0)
		    aleatorio = Math.abs(aleatorio);
		int grupo = aleatorio%7;
		materia = new Materia(aleatorio*-1,"Materia",aleatorio);
		Assert.assertFalse(existeMateria(materia.getId()));
		materia.agrega();
		Assert.assertTrue(existeMateria(materia.getId()));
		materia.elimina();
		Assert.assertFalse(existeMateria(materia.getId()));
	    }
	}catch(SQLException ex){
	    Assert.fail();
	}
    }
    
    //Método privado para ver si el grupo tiene a un materia.
    private boolean existeMateria(int grupo,int materia) throws SQLException {
        Connection conexion = Conexion.abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM grupo_profesor_materia "
	    + "WHERE id == " + grupo + " AND MATERIA == " + materia + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
	return resultado.next();
    }
    
    //Método privado para ver si existe la materia.
    private boolean existeMateria(int materia) throws SQLException {
        Connection conexion = Conexion.abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM materias "
	    + "WHERE id == " + materia + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
	return resultado.next();
    }
} //Fin de TestMaterias.java