package juego;

import java.awt.Color;
import java.awt.Image;
import java.awt.color.*;
import entorno.Entorno;

public class Tortuga {
	double x;
	double y;
	double ancho;
	double alto;
	boolean direccion;
//	Image imagenDer;
//	Image imagenIzq;
	double escala;
	double velocidad;
	Entorno e;
	boolean estaApoyado;
	
	public Tortuga (double x,double y,Entorno ent) {
		this.x = x;
		this.y = y;
		this.direccion = false;
		this.escala = 0.18;
		this.velocidad = 1.0;
//		this.imagenDer = entorno.Herramientas.cargarImagen("imagenes/Totoro2.png");
//		this.imagenIzq = entorno.Herramientas.cargarImagen("imagenes/Totoro.png");
		this.e = ent; 
		this.ancho = 100; 
		this.alto = 10;
		this.estaApoyado = false;
}
	public void mostrar() {
		this.e.dibujarRectangulo(x, y, 50, 50, 0, Color.BLUE);;
	}
	public void movVertical(Tortuga[] t) {
		if(!this.estaApoyado) {
			y++;
		}
	}
	
		public double getBordeDer() {
			return this.x+(this.ancho/2);
		}
		public double getBordeIzq() {
			return this.x-(this.ancho/2);
		}
		public double getBordeSup() {
			return y -(this.alto/2);
		}
		public double getBordeInf() {
			return y +(this.alto/2);
			
}
}	
