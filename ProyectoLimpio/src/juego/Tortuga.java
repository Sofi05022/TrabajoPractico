package juego;

import java.awt.Image;
import java.util.Random;
import entorno.Entorno;

public class Tortuga {
    double x;
    double y;
    double ancho;
    double alto;
    boolean direccion; // true para derecha, false para izquierda
    double escala;
    double velocidad;
    Entorno e;
    boolean estaApoyado;
    Image imagenDer;
    Image imagenIzq;
    Image imagenHerida;
    boolean herida;
    
    private static final double VELOCIDAD_CAIDA_NORMAL = 1.0;
    private static final double VELOCIDAD_CAIDA_HERIDA = 4.0;

    public Tortuga(Entorno ent, Isla[] islas) {
        this.e = ent; 
        this.y = 0;
        this.escala = 0.15;
        Random rand = new Random();
        this.imagenDer = entorno.Herramientas.cargarImagen("imagenes/Tortuga.png");
        this.imagenIzq = entorno.Herramientas.cargarImagen("imagenes/Tortuga2.png");    
        this.imagenHerida = entorno.Herramientas.cargarImagen("imagenes/TortugaHerida.png");
        this.ancho = imagenDer.getWidth(null) * escala; 
        this.alto = imagenIzq.getHeight(null) * escala;
        this.estaApoyado = false;
        this.velocidad = 0.5;
        this.direccion = rand.nextBoolean();
        this.herida = false;
        inicializarPosicion(islas);
    }
    private void inicializarPosicion(Isla[] islas) {
        Random rand = new Random();
        double limiteIzquierdo = 10;
        double limiteDerecho = e.ancho() - 10;

        boolean posicionValida = false;
        while (!posicionValida) {
            this.x = rand.nextDouble() * (limiteDerecho - limiteIzquierdo) + limiteIzquierdo;
            this.y = -alto;
            // Verificar que no caiga sobre las primeras tres islas
            posicionValida = true;
            for (int i = 0; i < 3; i++) {
                Isla isla = islas[i];
                if (this.x >= isla.getBordeIzq() && this.x <= isla.getBordeDer()) {
                    posicionValida = false;
                    break;
                }
            }
        }
    }

    public void mostrar() {
        if (herida) {
            this.e.dibujarImagen(imagenHerida, x, y, 0, escala);
        } else if (direccion) {
            this.e.dibujarImagen(imagenDer, x, y, 0, escala);
        } else {
            this.e.dibujarImagen(imagenIzq, x, y, 0, escala);
        }
    }

    public void actualizar(Isla[] islas) {
        if (herida) {
            // Si está herida, simplemente cae y no verifica apoyo en islas
            y += VELOCIDAD_CAIDA_HERIDA;
        } else {
            verificarApoyo(islas);
            if (!estaApoyado) {
                // Si no está apoyada, cae normalmente
                y += VELOCIDAD_CAIDA_NORMAL;
            } else {
                // Si está apoyada, busca en qué isla está y se mueve sobre ella
                for (Isla isla : islas) {
                    if (estaEnIsla(isla)) {
                        moverEnIsla(isla);
                        break;
                    }
                }
            }
        }

        // Verificar si salió de la pantalla
        if (y > e.alto()) {
            reiniciar(islas);
        }
    }

    private boolean estaEnIsla(Isla isla) {
        return getBordeInf() >= isla.getBordeSup() && 
               getBordeInf() <= isla.getBordeInf() &&
               getBordeIzq() >= isla.getBordeIzq() && 
               getBordeDer() <= isla.getBordeDer();
    }

    public void herir() {
        herida = true;
        estaApoyado = false; // Inmediatamente pierde el apoyo al ser herida
    }

    private void verificarApoyo(Isla[] islas) {
        estaApoyado = false;
        if (!herida) { // Solo verifica apoyo si no está herida
            for (Isla isla : islas) {
                if (estaEnIsla(isla)) {
                    estaApoyado = true;
                    y = isla.getBordeSup() - (alto / 2);
                    break;
                }
            }
        }
    }

    private void reiniciar(Isla[] islas) {
        Random rand = new Random();
        double limiteIzquierdo = 10;
        double limiteDerecho = e.ancho() - 10;

        boolean posicionValida = false;

        while (!posicionValida) {
            this.x = rand.nextDouble() * (limiteDerecho - limiteIzquierdo) + limiteIzquierdo;
            this.y = -alto;

            posicionValida = true;

            for (int i = 0; i < 3; i++) {
                Isla isla = islas[i];
                if (this.x >= isla.getBordeIzq() && this.x <= isla.getBordeDer()) {
                    posicionValida = false;
                    break;
                }
            }
        }

        this.herida = false;
        this.estaApoyado = false;
        this.direccion = rand.nextBoolean();
    }

    private void moverEnIsla(Isla isla) {
        if (direccion) {
            x += velocidad;
        } else {
            x -= velocidad;
        }
        
        if (x >= isla.getBordeDer() - (ancho / 2)) {
            x = isla.getBordeDer() - (ancho / 2);
            direccion = !direccion;
        } else if (x <= isla.getBordeIzq() + (ancho / 2)) {
            x = isla.getBordeIzq() + (ancho / 2);
            direccion = !direccion;
        }
    }
    
    public double getBordeDer() {
        return this.x + (this.ancho / 2);
    }
    
    public double getBordeIzq() {
        return this.x - (this.ancho / 2);
    }
    
    public double getBordeSup() {
        return y - (this.alto / 2);
    }
    
    public double getBordeInf() {
        return y + (this.alto / 2);
    }
}