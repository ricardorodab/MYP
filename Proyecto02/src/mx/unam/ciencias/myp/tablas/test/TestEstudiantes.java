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
public class TestEstudiantes{
    
    private Estudiante estudiante;
    
    
    /**
     * Prueba unitaria para el constructor del estudiante.
     */
    @Test
	public void testEstudiante(){
	estudiante = new Estudiante();
	Assert.assertFalse(estudiante == null);
	Assert.assertTrue(estudiante.getId() != 0);
	Assert.assertTrue(estudiante.getNombre().equals(""));
	Assert.assertTrue(estudiante.getApellido().equals(""));
	Assert.assertTrue(estudiante.getGenero() == 'H');
	
	estudiante.setId(1);
	estudiante.setNombre("Estudiante");
	estudiante.setApellido("Estudiante");
	estudiante.setGenero('H');
	
	Estudiante estudiante2 = new Estudiante(1,"Estudiante","Estudiante",'H');
	Assert.assertTrue(estudiante.getId() == estudiante2.getId());
	Assert.assertTrue(estudiante.getNombre().equals(estudiante2.getNombre()));
	Assert.assertTrue(estudiante.getApellido().equals(estudiante2.getApellido()));
	Assert.assertTrue(estudiante.getGenero() == estudiante.getGenero());
    }
    
    /**
     * Prueba unitaria para revisar los métodos estudiantes.
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
		ResultSet resultado = new Estudiante().getTabla(where);
		while(resultado.next()){
		    String nombre = resultado.getString(2).toUpperCase();
		    String apellido = resultado.getString(3).toUpperCase();
		    Assert.assertTrue(nombre.contains(where) 
				      || apellido.contains(where));
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
	    ResultSet grupoA = new Grupo().getEstudiantes();
	    while(grupoA.next()){
		int i = grupoA.getInt(1);
		estudiante = new Estudiante(i, "", "", 'H');
		ResultSet resultado =estudiante.buscaGrupo();
		while(resultado.next()){
		    Assert.assertTrue(existeAlumnoEnGrupo(resultado.getInt(1),i));
		}
	    }
	}catch(SQLException ex){
	    Assert.fail();
	}
    }
    
    /**
     * Prueba unitaria para ver que funciona la búsqueda por carrera.
     */
    @Test
	public void testBusquedaCarrera(){
	try{
	    boolean act,bio,comp,tierr,fis,mat;
	    act = bio = comp = tierr = fis = mat = false;
	    ResultSet estudiantes = new Estudiante().getTabla();
	    while(estudiantes.next()){
		if(act && bio && comp && tierr && fis && mat)
		    return;
		int i = estudiantes.getInt(1);
		ResultSet resultado = null;
		estudiante = new Estudiante(i, "", "", 'H');
		switch(estudiante.getCarrera()){
		case "Actuaria":
		    resultado = estudiante.getEstudiantesCarrera(1);
		    while(resultado.next()){
			estudiante.setId(resultado.getInt(1));
			Assert.assertTrue(estudiante.getCarrera().equals("Actuaria"));
		    }
		    act = true;
		    break;
		case "Biologia":
		    resultado = estudiante.getEstudiantesCarrera(2);
		    while(resultado.next()){
			estudiante.setId(resultado.getInt(1));
			Assert.assertTrue(estudiante.getCarrera().equals("Biologia"));
		    }
		    bio = true;
		    break;
		case "Ciencias de la Computacion":
		    resultado = estudiante.getEstudiantesCarrera(3);
		    while(resultado.next()){
			estudiante.setId(resultado.getInt(1));
			Assert.assertTrue(estudiante.getCarrera().equals("Ciencias de la Computacion"));
		    }
		    comp = true;
		    break;
		case "Ciencias de la Tierra":
		    resultado = estudiante.getEstudiantesCarrera(4);
		    while(resultado.next()){
			estudiante.setId(resultado.getInt(1));
			Assert.assertTrue(estudiante.getCarrera().equals("Ciencias de la Tierra"));
		    }
		    tierr = true;
		    break;
		case "Fisica":
		    resultado = estudiante.getEstudiantesCarrera(5);
		    while(resultado.next()){
			estudiante.setId(resultado.getInt(1));
			Assert.assertTrue(estudiante.getCarrera().equals("Fisica"));
		    }
		    fis = true;
		    break;
		case "Matematicas":
		    resultado = estudiante.getEstudiantesCarrera(6);
		    while(resultado.next()){
			estudiante.setId(resultado.getInt(1));
			Assert.assertTrue(estudiante.getCarrera().equals("Matematicas"));
		    }
		    mat = true;
		    break;
		}
	    }
	}catch(SQLException ex){
	    Assert.fail();
	}	
    }
    
    /**
     * Prueba unitaria para agregar y eliminar estudiantes.
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
		estudiante = new Estudiante(aleatorio*-1,"Estudiante","Estudiante",'H');
		Assert.assertFalse(existeAlumnoCarrera(grupo,estudiante.getId()));
		estudiante.agrega(grupo);
		Assert.assertTrue(existeAlumnoCarrera(grupo,estudiante.getId()));
		estudiante.elimina();
		Assert.assertTrue(existeAlumnoCarrera(grupo,estudiante.getId()));
		estudiante.eliminaCarrera();
		Assert.assertFalse(existeAlumnoCarrera(grupo,estudiante.getId()));
	    }
	}catch(SQLException ex){
	    Assert.fail();
	}
    }
    
    //Método privaro para ver si existe el alumno en el grupo.
    private boolean existeAlumnoEnGrupo(int grupo, int alumno) throws SQLException {
        Connection conexion = Conexion.abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM grupo_estudiantes "
	    + "WHERE id == " + grupo + " AND ESTUDIANTE == " + alumno + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado.next();
    }

    //Método privado que nos dice si existe el alumno en la carrera.
    private boolean existeAlumnoCarrera(int carrera, int alumno) throws SQLException{
	Connection conexion = Conexion.abreCanal();
        Statement tablaQuery = conexion.createStatement();
        String sql = "SELECT * FROM estudiantes_carreras "
	    + "WHERE id_estudiantes == " + alumno + " AND id_carreras == " + carrera + ";";
        ResultSet resultado = tablaQuery.executeQuery(sql);
        return resultado.next();
    }
} //Fin de TestEstudiantes.java
