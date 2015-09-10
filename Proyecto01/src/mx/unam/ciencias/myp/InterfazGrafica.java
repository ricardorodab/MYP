/* -------------------------------------------------------------------
 * InterfazGrafica.java
 * versión 2.0
 * Copyright (C) 2014  José Ricardo Rodríguez Abreu.
 * Facultad de Ciencias,
 * Universidad Nacional Autónoma de México, Mexico.
 *
 * Este programa es software libre; se puede redistribuir
 * y/o modificar en los términos establecidos por la
 * Licencia Pública General de GNU tal como fue publicada
 * por la Free Software Foundation en la versión 2 o
 * superior.
 *
 * Este programa es distribuido con la esperanza de que
 * resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho
 * sin la garantía implícita de COMERCIALIZACIÓN o
 * ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la
 * Licencia Pública General de GNU para mayores detalles.
 *
 * Con este programa se debe haber recibido una copia de la
 * Licencia Pública General de GNU, de no ser así, visite el
 * siguiente URL:
 * http://www.gnu.org/licenses/gpl.html
 * o escriba a la Free Software Foundation Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * -------------------------------------------------------------------
 */
package mx.unam.ciencias.myp;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 2.0
 * @since Aug 27 2014.
 * <p>Esta clase contiene una interfaz gráfica.</p>
 *
 * <p>Nos proporciona una intefaz gráfica para poder
graficar funciones de forma intuitiva.</p>
*/
public class InterfazGrafica extends Thread{
    
    //El ancho por default.
    public static final int x = 640;
    //El largo por default.
    public static final int y = 480;
    //Nuestro panel que tendrá la gráfica.
    public static PanelGrafica jpanel;
    //Dos botones con nombres descriptivos.
    private JButton limpiar, guardarComo;
    //Seis Spinners para obtener las medidas.
    private JSpinner s1,s2,s3,s4,s5,s6;
    //Siete cajas de texto que contendrá la interfaz.
    private JTextArea t1,t2,t3,t4,t5,t6,t7;
    //Cinco cajas para obtener los límites.
    private SpinnerModel sm0,sm1,sm2,sm3,sm4,sm5;
    //Cajas para organizar la intefáz.
    private FlowLayout botonesP,sub,funcionF;
    //Paneles para mostrar.
    private JPanel botones,val,funcionPanel;
    //Nuestra función de entrada.
    protected String funcionEntrada;

    /**
     * Constructor único que crea una InterfazGrafica.
     * @param nombre - el nombre de la inferfaz.
     */
    public InterfazGrafica(String nombre){
	super(nombre);
    }
    
    /**
     * Método para correr nuestra interfaz.
     */
    @Override
	public void run(){
	//Lo necesario para crear archivos.
	final JFileChooser fc= new JFileChooser();
	final FileFilter filtro1 = new FileNameExtensionFilter("SVG (*.svg)", "svg");
	final FileFilter filtro2 = new FileNameExtensionFilter("PNG (*.png)", "png");
	fc.addChoosableFileFilter(filtro1);
	fc.addChoosableFileFilter(filtro2);
	fc.setFileFilter(filtro1);
	fc.setAcceptAllFileFilterUsed(false);
 
	//Mensaje de error.
	final JOptionPane error = new JOptionPane();

	jpanel = new PanelGrafica();    
	final JFrame frame = new JFrame("GRAFICADOR");
	final JTextField funcion = new JTextField();
	//Número escogido convenientemente no al azar.
    	sm0 = new SpinnerNumberModel(0.0,0,
				     9786789, 1);
	sm1 = new SpinnerNumberModel(0.0, 0,
				     9786789, 1);
	sm2 = new SpinnerNumberModel(0.0, -9786789,
				     9786789, .1);
	sm3 = new SpinnerNumberModel(0.0, -9786789,
				     9786789, .1);
	sm4 = new SpinnerNumberModel(0.0,-9786789,
				     9786789, .1);
	sm5 = new SpinnerNumberModel(0.0, -9786789,
				     9786789, .1);
	//Botones:
	limpiar = new JButton("Limpiar");
	guardarComo = new JButton("Guardar Como...");
	botonesP = new FlowLayout();
	botones = new JPanel();
	botones.setLayout(botonesP);
	botones.add(limpiar);
	botones.add(guardarComo);
	
	//Texto y Spinners:
	t1 = new JTextArea("Ancho =");
	t1.setEditable(false);
	t2 = new JTextArea("Alto =");
	t2.setEditable(false);
	t3 = new JTextArea("X1 =");
	t3.setEditable(false);
	t4 = new JTextArea("Y1 =");
	t4.setEditable(false);
	t5 = new JTextArea("X2 =");
	t5.setEditable(false);
	t6 = new JTextArea("Y2 =");
	t6.setEditable(false);
	t7 = new JTextArea("f(x) = ");
	t7.setEditable(false);
	s1 = new JSpinner(sm0);
	s1.setValue(640);
	s1.setPreferredSize(new Dimension(64, 20));
	s2 = new JSpinner(sm1);
	s2.setValue(480);
	s2.setPreferredSize(new Dimension(64, 20));
	s3 = new JSpinner(sm2);
	s3.setValue(-10);
	s3.setPreferredSize(new Dimension(64, 20));
	s4 = new JSpinner(sm3);
	s4.setValue(-10);
	s4.setPreferredSize(new Dimension(64, 20));
	s5 = new JSpinner(sm4);
	s5.setValue(10);
	s5.setPreferredSize(new Dimension(64, 20));
	s6 = new JSpinner(sm5);  
	s6.setValue(10);
	s6.setPreferredSize(new Dimension(64, 20));
	sub = new FlowLayout();
	val = new JPanel();
	val.setLayout(sub);
	val.add(t1);
	val.add(s1);
	val.add(t2);
	val.add(s2);
	val.add(t3);
	val.add(s3);
	val.add(t4);
	val.add(s4);
	val.add(t5);
	val.add(s5);
	val.add(t6);
	val.add(s6);
	
	funcionF = new FlowLayout(FlowLayout.LEFT); 
	funcionPanel = new JPanel();
	funcion.setPreferredSize(new Dimension(x-(x/10),20));
	funcionPanel.setLayout(funcionF);
	funcionPanel.add(t7);
	funcionPanel.add(funcion);
  	
	//Acomodar y hacer MAGIA negra para el usuario.
	final JPanel border = new JPanel();
	border.setLayout(new BoxLayout(border, BoxLayout.Y_AXIS));
	border.add(jpanel);
	border.add(funcionPanel);
	border.add(val);
	border.add(botones);
	
	//Definimos la acción principal:
	funcion.addActionListener(new ActionListener() {
		@Override
		    public void actionPerformed(ActionEvent e) {       
	 	    try{
			funcionEntrada = funcion.getText();
			Expresiones exp = new Expresiones(funcionEntrada);
			ArbolBinarioDeOperadores<String> arbol = exp.construyeArbol();
			Evaluador eva;
			int ancho, largo;
			double x1,y1,x2,y2;
			if(s1.getValue() instanceof Double){
			    double anchoD = (double)s1.getValue();
			    ancho = (int)anchoD;
			}else{
			    ancho = (int)s1.getValue();	      
			}
			if(s2.getValue() instanceof Double){
			    double largoD = (double)s2.getValue();
			    largo = (int)largoD;
			}else{
			    largo = (int)s2.getValue();
			}
			if(s3.getValue() instanceof Double)
			    x1 = (double)s3.getValue();
			else{
			    int x1T = (int)s3.getValue();
			    x1 = x1T;	      
			}
			if(s4.getValue() instanceof Double)
			    y1 = (double)s4.getValue();
			else{
			    int y1T = (int)s4.getValue();
			    y1 = y1T;	      
			}
			if(s5.getValue() instanceof Double)
			    x2 = (double)s5.getValue();
			else{
			    int x2T = (int)s5.getValue();
			    x2 = x2T;	      
			}
			if(s6.getValue() instanceof Double)
			    y2 = (double)s6.getValue();
			else{
			    int y2T = (int)s6.getValue();
			    y2 = y2T;	      
			}
			if(arbol.arbolNull())
			    eva = new Evaluador(x1, x2);
			else
			    eva = new Evaluador(arbol, x1, x2);	  
			jpanel.setDeltaY(y1,y2);
			jpanel.setDeltaX(x1,x2);
			jpanel.setPreferredSize(new Dimension(ancho,largo));
			jpanel.validate();
			jpanel.repaint();
			jpanel.agregaFuncion(jpanel.getGraphics(), eva.tabular());
			funcion.setText("");		   
		    }catch(ExcepcionExpresionIncorrecta expre){
			error.showMessageDialog(frame, "Expresión mal formada: \n"+ expre.getMessage()+"\nHasta luego.",
						"FUNCION NO VÁLIDA", JOptionPane.ERROR_MESSAGE);
			System.exit(1); //Cambiar esto.
		    }
		}
	    });
	limpiar.addActionListener(new ActionListener() {
		@Override
		    public void actionPerformed(ActionEvent e) {
		    jpanel.limpia(jpanel.getGraphics());
		    s1.setValue(640);
		    s2.setValue(480);
		    s3.setValue(-10);
		    s4.setValue(-10);
		    s5.setValue(10);
		    s6.setValue(10);
		}
	    });
	guardarComo.addActionListener(new ActionListener() {
		@Override
		    public void actionPerformed(ActionEvent e){
		    int returnVal = fc.showSaveDialog(frame);
		    if(returnVal == JFileChooser.APPROVE_OPTION){
			int ancho, largo;
			double x1,y1,x2,y2;
			if(s1.getValue() instanceof Double){
			    double anchoD = (double)s1.getValue();
			    ancho = (int)anchoD;
			}else{
			    ancho = (int)s1.getValue();	      
			}
			if(s2.getValue() instanceof Double){
			    double largoD = (double)s2.getValue();
			    largo = (int)largoD;
			}else{
			    largo = (int)s2.getValue();
			}
			if(s3.getValue() instanceof Double)
			    x1 = (double)s3.getValue();
			else{
			    int x1T = (int)s3.getValue();
			    x1 = x1T;	      
			}
			if(s4.getValue() instanceof Double)
			    y1 = (double)s4.getValue();
			else{
			    int y1T = (int)s4.getValue();
			    y1 = y1T;	      
			}
			if(s5.getValue() instanceof Double)
			    x2 = (double)s5.getValue();
			else{
			    int x2T = (int)s5.getValue();
			    x2 = x2T;	      
			}
			if(s6.getValue() instanceof Double)
			    y2 = (double)s6.getValue();
			else{
			    int y2T = (int)s6.getValue();
			    y2 = y2T;	      
			}  
			if(fc.getFileFilter().equals(filtro2)){
			    try{
				BufferedImage bi = new BufferedImage(ancho, largo,BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bi.createGraphics();
				Graphics2D im = (Graphics2D)jpanel.getGraphics();			  
				jpanel.pinta(g2d);
				File png = new File(fc.getSelectedFile()+".png");
				ImageIO.write(bi, "png", png);
			    }catch(IOException io){				
				error.showMessageDialog(frame, "Ocurrió un error mientras guardabas, lo lamento",
						      "ERROR DE GUARDADO", JOptionPane.ERROR_MESSAGE); 
			    }
			}else{
			    try(FileWriter file = new FileWriter(fc.getSelectedFile()+".svg")){
				    GraficaSVG svg = new GraficaSVG(jpanel.getFunciones(),ancho, largo,x1,x2,y1,y2);
				    file.write(svg.generaImagenSVG());
				    file.close();
				} 
			    catch(IOException io){
				System.exit(1);
			    }
			}
		    }
		}
	    });
	
	//Mostrar contenido.
	frame.getContentPane().add(border, BorderLayout.CENTER);
	frame.setSize(x, y);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	frame.pack();
	frame.setVisible(true);
    }
} //Fin de InterfazGrafica.java
