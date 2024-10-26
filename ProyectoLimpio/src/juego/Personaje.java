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
    Image imagenHerido;  
    double escala;
    double velocidad;
    Entorno e;
    boolean estaApoyado;
    boolean estaSaltando;
    boolean herido;  
    private int contadorSalto;
    private double posicionInicialX;
    private double posicionInicialY;

    public Personaje(double x, double y, Entorno ent) {
        this.x = x;
        this.y = y;
        this.posicionInicialX = x;  
        this.posicionInicialY = y;
        this.direccion = false;
        this.escala = 0.18;
        this.velocidad = 1.0;
        this.imagenDer = entorno.Herramientas.cargarImagen("imagenes/Totoro2.png");
        this.imagenIzq = entorno.Herramientas.cargarImagen("imagenes/Totoro.png");
        this.imagenHerido = entorno.Herramientas.cargarImagen("imagenes/TotoroHerido.png");  // Imagen de herido
        this.e = ent;
        this.ancho = imagenDer.getWidth(null) * this.escala;
        this.alto = imagenDer.getHeight(null) * this.escala;
        this.estaApoyado = false;
        this.estaSaltando = false;
        this.herido = false;  // Inicialmente no está herido
        this.contadorSalto = 0;
    }
    
    
    public void mostrar() {
		if (herido) {
            this.e.dibujarImagen(imagenHerido, x, y, 0, escala);  // Si está herido, se muestra la imagen de herido
        } else if (direccion) {
            this.e.dibujarImagen(imagenDer, x, y, 0, escala);
        } else {
            this.e.dibujarImagen(imagenIzq, x, y, 0, escala);
        }
	}

    public void herir() {
        herido = true;
    }

    public void actualizarCaidaHerido() {
        if (herido) {
            y += 4;  // El personaje cae rápidamente
            if (y > e.alto() + alto) {
                // Cuando sale de la pantalla, reiniciamos el personaje
                reiniciar();
            }
        }
    }

    public void reiniciar() {
        herido = false;
        x = posicionInicialX;  // Reaparece en la posición inicial
        y = posicionInicialY;
        direccion = false;
    }


	public void movVertical() {
		if(!this.estaApoyado && !estaSaltando) {
			y+=2;
		}
		if(estaSaltando) {
			y-=4;
			contadorSalto ++;
		}
		if(contadorSalto == 45) {
			estaSaltando = false;
			contadorSalto = 0;
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
	
	public void saltar() {
		this.estaSaltando = true;
		this.estaApoyado = true;
	}
	
	public void cancelarSalto() {
		estaSaltando = false;
		contadorSalto = 0;
	}
	
}

