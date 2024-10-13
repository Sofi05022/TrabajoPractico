package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;

public class Isla {
	double x;
	double y;
	double ancho;
	double alto;
	boolean direccion;
	Image imagen;
	double escala;
	double velocidad;
	Entorno e;
	
	public Isla(double x,double y,Entorno e) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/isla.png");
		this.direccion = false;
		this.escala = 0.25;
		this.velocidad = 1;
		this.ancho = 120;
		this.alto = 40;
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
	
	
	
	public void mostrar() {
		this.e.dibujarImagen(imagen, x, y, 0, escala);	}
	
}
	
