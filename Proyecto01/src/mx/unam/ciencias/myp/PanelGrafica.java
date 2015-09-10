/* -------------------------------------------------------------------
 * PanelGrafica.java
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

import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Point;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 2.0
 * @since Aug 27 2014.
 * <p>Clase que crea un panel con una gráfica.</p>
 *
 * <p>Esta clase crea una panel que contiene un objeto gráfico
 para poder pintar una o varias funciones.</p>
 */
public class PanelGrafica extends JPanel{
    
    //Conjunto de puntos en el rango a graficar.
    protected double[] rango;
    //Conjunto de funciones totales.
    private LinkedList<double[]> funciones;
    //Límite inferior de Y.
    private static double yInicio = -10;
    //Límite superior de Y.
    private static double yFinal = 10;
    //Límite inferior de X.
    private static double xInicio = -10;
    //Límite superior de X.
    private static double xFinal = 10;
    
    /**
     * Constructor vacío de un PanelGrafica.
     */
    public PanelGrafica(){
	this.rango = new double[1];     
	borde();
	funciones = new LinkedList<double[]>();
	setPreferredSize(new Dimension(640,480));
    }
    
    /**
     * Contructor de un PanelGrafica.
     * @param rango - Es la imagen a una función a graficar.
     * @param w - Es el ancho del panel.
     * @param h - Es el largo del panel.
     */
    public PanelGrafica(double[] rango, int w, int h){
	this.rango = rango;
	borde();
	funciones = new LinkedList<double[]>();
	funciones.add(rango);
	setPreferredSize(new Dimension(w, h));
    }
    
    /**
     * Constructor de un PanelGrafica.
     * @param rango - Es la imagen a una función a graficar.
     */
    public PanelGrafica(double[] rango){
	this.rango = rango;
	borde();
	funciones = new LinkedList<double[]>();
	funciones.add(rango);
        setPreferredSize(new Dimension(640, 480));
    }
    
    /**
     * Pinta el objeto en la ventana de trazado.
     * @param g - Es el objeto gráfico usado para la operación de trazado.
     */
    @Override
	public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Dimension tam = getSize();
	g2d.setColor(Color.WHITE);
	g2d.fill(new Rectangle(0,0,tam.width,tam.height));
	g2d.setColor(Color.BLACK);
	ejes(g2d,xInicio,xFinal, yInicio, yFinal);
	int color = 0;
	for(double[] arreglo : funciones){
	    pinta(g2d,arreglo, color++);
	}
    }
    
    /**
     * Método que pinta a la ventana sin funciones.
     */
    public void limpia(Graphics g){
	this.funciones = new LinkedList<double[]>();
	setTamaño(640,480);
	setDeltaX(-10,10);
	setDeltaY(-10,10);
	paintComponent(g);
    }
    
    /**
     * Método que agrega una función a la ventana actual.
     * @param g - Es el objeto gráfico que se le agrega la función.
     * @param rango - es la imagen de la función a graficar.
     */
    public void agregaFuncion(Graphics g, double[] rango){
	this.funciones.add(rango);
	paintComponent(g);
    }
    
    /**
     * Método que cambia el tamaño de la ventana actual.
     * @param width - es el nuevo ancho.
     * @param height - es el nuevo largo.
     */
    public void setTamaño(int width, int height){
	setPreferredSize(new Dimension(width, height));
    }
    
    /**
     * Método que cambia la variable global deltaX.
     * @param y1 - es la nueva Y1.
     * @param y2 - es la nueva Y2.
     */
    public void setDeltaY(double y1, double y2){
	double min = Math.min(y1, y2);
	double max = min == y1 ? y2 : y1;
	if(max != yFinal || min != yInicio){
	    this.funciones = new LinkedList<double[]>();
	    paintComponent(getGraphics());
	}
	yInicio = min;	
	yFinal = max;
	ejes((Graphics2D)getGraphics(),xInicio,xFinal,yInicio,yFinal);
    }    

    /**
     * Método que cambia la variable global deltaX.
     * @param x1 - es la nueva X1.
     * @param x2 - es la nueva X2.
     */
    public void setDeltaX(double x1, double x2){
	double min = Math.min(x1, x2);
	double max = min == x1 ? x2 : x1;
	if(max != xFinal || min != xInicio){
	    this.funciones = new LinkedList<double[]>();
	    paintComponent(getGraphics());	
	}
	xInicio = min;	
	xFinal = max;
    }

    /**
     * Método que nos regresa las funciones.
     * @return las funciones en arreglo de doubles.
     */
    public LinkedList<double[]> getFunciones(){
	return this.funciones;
    }
    
    /**
     * Método que copia tal cual la gráfica a otro Graphics.
     * @param g2d - es la nueva gráfica.
     */
    public void pinta(Graphics2D g2d){
        Dimension tam = getSize();
	g2d.setColor(Color.WHITE);
	g2d.fill(new Rectangle(0,0,tam.width,tam.height));
	g2d.setColor(Color.BLACK);
	g2d.drawLine(0, tam.height/2, tam.width, tam.height/2);
	g2d.drawLine(tam.width/2, 0, tam.width/2, tam.height);
	int color = 0;
	for(double[] arreglo : funciones){
	    pinta(g2d,arreglo,color++);
	}
    }	
    
    //Método auxiliar que manda a pintar nuestras funciones.
    private void pinta(Graphics2D g2d, double[] valores, int color){
	Dimension tam = getSize();
	Color[] colores = colores();
	color = color % colores.length;
	g2d.setColor(colores[color]);
	trazaFuncion(g2d, valores,new Point(tam.width,tam.height));
    }
    
    //Método auxiliar que pinta nuestras funciones.
    private static void trazaFuncion(Graphics2D g2,
				     double[] rango,
				     Point p){
	double xTemp1, xTemp2, yTemp1, yTemp2;
        xTemp1 = xTemp2 = yTemp1 = yTemp2 =0;
	double yMin = Math.min(yInicio, yFinal);
	double yMax = (yMin == yInicio) ? yFinal : yInicio; 
	double ydelta = ((double)yMax-yMin);
	int i = 0;
	while(i < rango.length){
	    yTemp1 = (double)((rango[i]+yMax)*p.y)/ydelta;
	    yTemp1 = (yTemp1*-1)+p.y;
            if(i < rango.length-1){
		yTemp2 = (double)((rango[i+1]+yMax)*p.y)/ydelta;
		yTemp2 = (yTemp2*-1)+p.y;
		xTemp2 =(double)xTemp1 + ((double)p.x/rango.length);
	    }else{
		yTemp2 = (double)((rango[rango.length-1]+yMax)*p.y)/ydelta;
		yTemp2 = (yTemp2*-1)+p.y;
		xTemp2 = p.x;
	    }
            g2.drawLine((int)xTemp1, (int)yTemp1 ,(int)xTemp2,(int) yTemp2);
	    xTemp1 = xTemp2;
            i++;
	}
    }
    
    //Método auxiliar que pinta un borde a nuestro panel.
    private void borde(){
	this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    //Método privado que nos regresa un arreglo con los posibles colores de las aristas.
    private static Color[] colores(){
	Color[] colores = {Color.BLUE,Color.CYAN,Color.GRAY,Color.GREEN,
			   Color.MAGENTA,Color.ORANGE,Color.PINK,
			   Color.RED,Color.YELLOW,Color.BLACK};
	return colores;
    }

    //Método auxiliar que nos pinta los ejes del plano.
    private void ejes(Graphics2D g2d, double x1, double x2, double y1, double y2){
	Dimension tam = getSize();
	if(Math.abs(x1) == Math.abs(x2) && x1 != x2 &&
	   Math.abs(y1) == Math.abs(y2) && y1 != y2){
	    g2d.drawLine(tam.width/2, 0, tam.width/2, tam.height); 
	    g2d.drawLine(0, tam.height/2, tam.width, tam.height/2);
	    return;
	}
	double max, min, deltaX,dif,cero,finY;
	max = Math.abs(x1) > Math.abs(x2) ? x1 : x2;
	min = max == x1 ? x2 : x1; 
	deltaX = Math.abs(x1 - x2);
	dif = Math.abs(deltaX - max);
	cero = tam.width/2;
	finY = Math.abs((double)((double)(dif*tam.width)/deltaX));
	if(finY > cero){
	    cero +=finY-cero;	    
	}else if(finY < cero){
	    cero -= cero-finY;
	}	
	double maxY,minY,deltaY,difY,ceroY, ceroY2,finX;
	maxY = Math.abs(y1) > Math.abs(y2) ? y1 : y2;
	minY = maxY == y1 ? y2 : y1; 
	deltaY = Math.abs(y1 - y2);
	difY = Math.abs(deltaY - maxY);
	ceroY2 = ceroY = tam.height/2;
	finX = Math.abs((double)((double)(difY*tam.height)/deltaY));
	if(finX > ceroY){
	    ceroY +=finX-ceroY;	    
	}else if(finX < ceroY){
	    ceroY -= ceroY-finX;
	}
	ceroY2 = tam.height - ceroY;
	g2d.drawLine((int)cero, 0, (int)cero, tam.height);        
	g2d.drawLine(0,(int)ceroY2, tam.width, (int)ceroY2);	    
    }
} //Fin de la clase PanelGrafica.java
