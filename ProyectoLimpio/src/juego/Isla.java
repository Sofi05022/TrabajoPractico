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
	boolean estaApoyado;
	
	public Isla(double x,double y,Entorno e, double d) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/isla.png");
		this.direccion = false;
		this.escala = 0.2;
		this.velocidad = 1;
		this.ancho = 110;
		this.alto = 40;
		this.estaApoyado = false;
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
	
