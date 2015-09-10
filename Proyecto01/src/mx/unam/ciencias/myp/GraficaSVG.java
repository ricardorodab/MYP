/* -------------------------------------------------------------------
 * GraficaSVG.java
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
import java.text.*;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 2.0
 * @since Aug 27 2014.
 * <p>Esta clase es para crear imagenes SVG.</p>
 *
 * <p>Nos proporciona métodos para crear imagenes SVG dado
 un conjunto de arreglos..</p>
*/
public class GraficaSVG{
    
    //Nuestra imagen final SVG.
    private String imagenSVG;
    //Un arreglo de puntos de la imagen.
    private LinkedList<double[]> rangos;
    //Tamaño de la imagen SVG y sus coordenadas.
    private int x,y;
    private double x1,x2,y1,y2;
    
    
    /**
     * Constructor de GraficaSVG.
     * @param rangos - es la imagen de la función.
     */
    public GraficaSVG(LinkedList<double[]> rangos){
	this(rangos, 640, 480,-10,10,-10,10);
    }
    
    /**
     * Constructor de GraficaSVG.
     * @param rangos - es la imagen de la función.
     * @param x - es el ancho de la imagen.
     * @param y - es el largo de la imagen.
     * @param x1 - es el límite inferior de las X.
     * @param x2 - es el límite superior de las X.
     * @param y1 - es el límite inferior de las Y.
     * @param y2 - es el límite superior de las Y.
     */
    public GraficaSVG(LinkedList<double[]> rangos, int x, int y, double x1, double x2, double y1, double y2){
	this.rangos = rangos;
	this.x = x;
	this.y = y;
	this.x1 = x1;
	this.x2 = x2;
	this.y1 = y1;
	this.y2 = y2;
	this.imagenSVG = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+
	    "<svg width=\""+this.x+"\" height=\""+this.y+"\">\n"
	    +"<g>\n"
	    +"<line x1=\""+(0)+"\" y1=\""+(this.y/2)
	    +"\" x2=\""+(this.x)+"\" y2=\""+(this.y/2)
	    +"\" stroke=\"black\" stroke-width=\"1.5\" />\n"
	    +"<line x1=\""+(this.x/2)+"\" y1=\""+(0)
	    +"\" x2=\""+(this.x/2)+"\" y2=\""+(this.y)
	    +"\" stroke=\"black\" stroke-width=\"1.5\" />\n";
    }
    
    /**
     * Método que genera nuestra imagen SVG con los parametros de nuestro objeto.
     * @return la imagen en formato String.
     */
    public String generaImagenSVG(){ 
	double xTemp1, yTemp1,xTemp2,yTemp2;
	double yMin = Math.min(this.y1, this.y2);
	double yMax = (yMin == this.y1) ? this.y2 : this.y1; 
	double ydelta = ((double)yMax-yMin);
	for(double[] arreglo : this.rangos){
	    String[] colores = coloresSVG();
	    int r = (int)((Math.random()*(colores.length-1)));	    
	    String color = colores[r];
	    xTemp1 = xTemp2 = yTemp1 = yTemp2 =0;
	    int i = 0;
	    while(i < arreglo.length){
		yTemp1 = (double)((arreglo[i]+yMax)*this.y)/ydelta;
		yTemp1 = (yTemp1*-1)+this.y;
		if(i < arreglo.length-1){
		    yTemp2 = (double)((arreglo[i+1]+yMax)*this.y)/ydelta;
		    yTemp2 = (yTemp2*-1)+this.y;
		    xTemp2 =(double)xTemp1 + ((double)this.x/arreglo.length);
		}else{
		    yTemp2 = (double)((arreglo[arreglo.length-1]+yMax)*this.y)/ydelta;
		    yTemp2 = (yTemp2*-1)+this.y;
		    xTemp2 = this.x;
		}	   
		if(yTemp1 >= 9786789)
		    yTemp1 = 9786789;
		if(yTemp1 <= -9786789)
		    yTemp1 = -9786789;			
		if(yTemp2 >= 9786789)
		    yTemp2 = 9786789;
		if(yTemp2 <= -9786789)
		    yTemp2 = -9786789;
		this.imagenSVG = this.imagenSVG.concat("<line x1=\""+(xTemp1)+"\" y1=\""+(yTemp1)
						       +"\" x2=\""+(xTemp2)+"\" y2=\""+(yTemp2)
						       +"\" stroke=\""+(color)+"\" stroke-width=\"1.5\" />\n");
		xTemp1 = xTemp2;
		i++;
	    }
	}
	this.imagenSVG = this.imagenSVG.concat("</g>\n </svg>");
	return this.imagenSVG;
    }

    //Método privado que nos regresa un arreglo con los posibles colores de las aristas.
    private static String[] coloresSVG(){
	String[] colores = {"black","purple","blue","green","red","yellow","violet","blueviolet","orange",
			    "magenta","darkred","black","brown","burlywood","cadetblue","chartreuse","chocolate",
			    "cornflowerblue","crimson","black","cyan","darkblue","darkcyan","darkgoldenrod","aqua",
			    "darkgray","darkgreen","darkkhaki","black","darkmagenta","darkolivegreen","darkorange",
			    "darkorchid","darksalmon","darkseagreen","black","antiquewhite","darkslateblue","coral",
			    "darkslategrey","darkturquoise","darkviolet","black","deeppink","deepskyblue","firebrick",
			    "forestgreen","fuchsia","gold","gray","greenyellow","black","hotpink","indianred","indigo",
			    "aquamarine","lawngreen","lightblue","lightseagreen","lime","black","maroon","mediumblue",
			    "navy","olive","olivedrab","orangered","orchid","peru","black","pink","plum","powderblue",
			    "rosybrown","royalblue","saddlebrown","salmon","sandybrown","black","seagreen","sienna",
			    "skyblue","slateblue","slategrey","springgreen","steelblue","teal","black","tan","tomato",
			    "turquoise","yellowgreen"};
	return colores;
    }
} //Fin de GraficaSVG.java
