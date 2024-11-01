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
	    double velocidad;
	    boolean enColision;
	    
	    private boolean saltando;
	    private double velocidadSalto;
	    private static final double VELOCIDAD_SALTO_INICIAL = -8.0;
	    private static final double GRAVEDAD = 0.5;

	    public Gnomos(double x, double y, Entorno ent) {
	        this.x = x;
	        this.y = y;
	        this.tamanio = 0.1;
	        this.direccion = Math.random() < 0.5;
	        this.estaApoyado = false;
	        this.e = ent;
	        this.imagenDer = entorno.Herramientas.cargarImagen("imagenes/gnomoIzq.png");
	        this.imagenIzq = entorno.Herramientas.cargarImagen("imagenes/gnomoDer.png");
	        this.ancho = imagenDer.getHeight(null) * tamanio;
	        this.alto = imagenDer.getWidth(null) * tamanio;
	        this.velocidad = 0.3;
	        this.velocidadSalto = 0;
	        this.enColision = false;
	    }

	    public void cambiarDireccion() {
	        this.direccion = Math.random() < 0.5;
	    }

	    public void movimientoGnomo() {
	        if (!saltando) {
	            if (direccion) {
	                x += velocidad;
	            } else {
	                x -= velocidad;
	            }
	        }
	    }
	    
	    public void actualizar() {
	        if (saltando) {
	            // Aplicar movimiento de salto
	            y += velocidadSalto;
	            velocidadSalto += GRAVEDAD;
	            
	            // Si el gnomo sube lo suficiente, preparar para reaparecer en isla 0
	            if (velocidadSalto > 0 && y > -50) {  // -50 es una altura arbitraria sobre la pantalla
	                reaparecerEnIsla0();
	            }
	        } else {
	            // Comportamiento normal
	            movimientoGnomo();
	            gravedad();
	        }
	    }

	    public void gravedad() {
	        if(!estaApoyado) {
	            y += 2;
	        }
	    }
	    
	    public void iniciarSalto() {
	        saltando = true;
	        velocidadSalto = VELOCIDAD_SALTO_INICIAL;
	        estaApoyado = false;
	    }
	    
	    private void reaparecerEnIsla0() {
	        // Coordenadas de la isla 0 
	        x = 455;  // Posición X de la isla 0
	        y = 50;  // Posición Y de la isla 0
	        saltando = false;
	        velocidadSalto = 0;
	        estaApoyado = true;
	        direccion = Math.random() < 0.5;
	    }

	    public void dibujarGnomo(Entorno e) {
	        if(direccion) {
	            e.dibujarImagen(imagenIzq, x, y, 0, tamanio);
	        } else {
	            e.dibujarImagen(imagenDer, x, y, 0, tamanio);
	        }
	    }
	    public boolean estaEnColision() {
	    	return enColision;
	    }
	    
	    public void setEnColision(boolean enColision) {
	    	this.enColision = enColision;
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

