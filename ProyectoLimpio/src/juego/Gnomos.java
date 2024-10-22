package juego;

import java.awt.Image;

import entorno.Entorno;

public class Gnomos{
	 double x;
	    double y;
	    double tamanio;
	    double ancho;
	    double alto;
	    boolean direccion;
	    boolean estaApoyado;
	    Image imagenDer;
	    Image imagenIzq;
	    Entorno e;

	    public Gnomos(double x, double y, Entorno ent) {
	        this.x = x;
	        this.y = y;
	        this.tamanio = 0.10;
	        this.direccion = Math.random() < 0.5;
	        this.estaApoyado = false;
	        this.e = ent;
	        this.imagenDer = entorno.Herramientas.cargarImagen("imagenes/gnomoDer.png");
	        this.imagenIzq = entorno.Herramientas.cargarImagen("imagenes/gnomoIzq.png");
	        this.ancho = imagenDer.getHeight(null) * tamanio;
	        this.alto = imagenDer.getWidth(null) * tamanio;
	    }

	    public void cambiarDireccion() {
	        this.direccion = Math.random() < 0.5;
	    }

	    public void movimientoGnomo() {
	        if(this.direccion) {
	            x --;
	        } else {
	            x ++;
	        }
	    }

	    public void gravedad() {
	        if(!estaApoyado) {
	            y += 2;
	        }
	    }

	    public void dibujarGnomo(Entorno e) {
	        if(direccion) {
	            e.dibujarImagen(imagenIzq, x, y, 0, tamanio);
	        } else {
	            e.dibujarImagen(imagenDer, x, y, 0, tamanio);
	        }
	    }

	    public double getBordeDer() {
	        return x + this.ancho/2;
	    }

	    public double getBordeIzq() {
	        return x - this.ancho/2;
	    }

	    public double getBordeSup() {
	        return y - this.alto/2;
	    }

	    public double getBordeInf() {
	        return y + this.alto/2;
	    }
	}

