package juego;

import java.awt.Image;

import entorno.Entorno;

public class Poder {
	double x;
    double y;
    double tamanio;
    double velocidad;
    double ancho;
    double alto;
    boolean direccion;
    Image bolaIzq;
    Image bolaDer;
    Entorno e;

    public Poder(double x, double y, Entorno ent, boolean direccion) {
        this.x = x;
        this.y = y;
        this.tamanio = 0.50;
        this.velocidad = 5;
        this.direccion = direccion;
        this.bolaDer = entorno.Herramientas.cargarImagen("imagenes/bolaDer.png");
        this.bolaIzq = entorno.Herramientas.cargarImagen("imagenes/bolaIzq.png");
        this.ancho = bolaDer.getHeight(null) * tamanio;
        this.alto = bolaDer.getWidth(null) * tamanio;
        this.e = ent;
    }

    public void dibujarBola(Entorno e) {
        if(direccion) {
            e.dibujarImagen(bolaIzq, x, y, 0, tamanio);
        } else {
            e.dibujarImagen(bolaDer, x, y, 0, tamanio);
        }
    }

    public void lanzarBola() {
        if(direccion) {
            x -= velocidad;
        } else {
            x += velocidad;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

