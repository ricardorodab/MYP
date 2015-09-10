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
public class TestProfesores{
    
    private Profesor profesor;
    
    
    /**
     * Prueba unitaria para el constructor del profesor.
     */
    @Test
	public void testProfesor(){
	profesor = new Profesor();
	Assert.assertFalse(profesor == null);
	Assert.assertTrue(profesor.getId() == -1);
	Assert.assertTrue(profesor.getNombre().equals(""));
	Assert.assertTrue(profesor.getTitulo().equals(""));
	
	profesor.setId(1);
	profesor.setNombre("Profesor");
	profesor.setTitulo("Doc");
	
	Profesor profesor2 = new Profesor(1,"Doc","Profesor");
	Assert.assertTrue(profesor.getId() == profesor2.getId());
	Assert.assertTrue(profesor.getNombre().equals(profesor2.getNombre()));
	Assert.assertTrue(profesor.getTitulo().equals(profesor2.getTitulo()));
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
		ResultSet resultado = new Profesor().getTabla(where);
		while(resultado.next()){
		    String titulo = resultado.getString(2).toUpperCase();
		    String nombre = resultado.getString(3).toUpperCase();
		    Assert.assertTrue(nombre.contains(where) ||
				      titulo.contains(where));
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
	    ResultSet grupoA = new Grupo().getProfesor();
	    while(grupoA.next()){
		int i = grupoA.getInt(1);
		profesor = new Profesor(i, "", "");
		ResultSet resultado =profesor.buscaGrupo();
		while(resultado.next()){
		    Assert.assertTrue(existeProfe(resultado.getInt(1),i));
		}
	    }
	}catch(SQLException ex){
	    Assert.fail();
	}
    }

    /**
     * Prueba unitaria para agregar y eliminar profesores.
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
		profesor = new Profesor(aleatorio*-1,"Lic","Profesor");
		Assert.assertFalse(existeProfe(profesor.getId()));
		profesor.agrega();
		Assert.assertTrue(existeProfe(profesor.getId()));
		profesor.elimina();
		Assert.assertFalse(existeProfe(profesor.getId()));
	    }
	}catch(SQLException ex){
	    Assert.fail();
	}
    }
    
    //Método privado para ver si el grupo tiene a un profesor.
    private boolean existeProfe(int grupo,int profe) throws SQLException {
        Connection conexion = Conexion.abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM grupo_profesor_materia "
	    + "WHERE id == " + grupo + " AND PROFESOR == " + profe + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
	return resultado.next();
    }
    //Método privado para ver si existe el profesor.
    private boolean existeProfe(int profe) throws SQLException {
        Connection conexion = Conexion.abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM profesores "
	    + "WHERE id == " + profe + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
	return resultado.next();
    }
} //Fin de TestProfesores.java
