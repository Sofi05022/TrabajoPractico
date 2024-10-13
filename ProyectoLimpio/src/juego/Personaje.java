package juego;

import java.awt.Image;

import entorno.Entorno;

public class Personaje {
	double x;
	double y;
	double ancho;
	double alto;
	boolean direccion;
	Image imagenDer;
	Image imagenIzq;
	double escala;
	double velocidad;
	Entorno e;
	boolean estaApoyado;
	public Personaje(double x,double y,Entorno ent) {
		this.x = x;
		this.y = y;
		this.direccion = false;
		this.escala = 0.23;
		this.velocidad = 1.0;
		this.imagenDer = entorno.Herramientas.cargarImagen("imagenes/Totoro2.png");
		this.imagenIzq = entorno.Herramientas.cargarImagen("imagenes/Totoro.png");
		this.e = ent; 
		this.ancho = imagenDer.getWidth(null)*this.escala;
		this.alto = imagenDer.getHeight(null)*this.escala;
		this.estaApoyado = false;
		
	}
	public void movVertical() {
		if(!this.estaApoyado) {
			y++;
		}
	}
	
	public void mostrar() {
		if(direccion) {
			this.e.dibujarImagen(imagenDer, x, y, 0, escala);
		}
		else {
			this.e.dibujarImagen(imagenIzq, x, y, 0, escala);}

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
	
	public void mover(double v) {
		this.x = this.x +v;
		if(getBordeDer()> this.e.ancho()) {
			this.x = e.ancho() - (this.ancho/2);
		}
		if(getBordeIzq()<0) {
			this.x = 0 + (this.ancho /2);
		}
		if(v >= 0) {
			this.direccion = false;
		}else {
			this.direccion = true;
		}
	}
	
	
}

