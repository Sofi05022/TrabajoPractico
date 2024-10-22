package juego;

import java.awt.Color;
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
    
    public Tortuga(Entorno ent, Isla[] islas) {
        this.e = ent; 
        this.y = 0; // Comienza desde el borde superior
        
        Random rand = new Random();
        boolean posicionValida = false;
        
        // Evitar caer sobre la isla más alta
        while (!posicionValida) {
            this.x = rand.nextDouble() * this.e.ancho();
            Isla islaMasAlta = islas[0]; // Supone que la primera isla es la más alta
            if (this.x < islaMasAlta.getBordeIzq() || this.x > islaMasAlta.getBordeDer()) {
                posicionValida = true; // Si no cae sobre la isla más alta, la posición es válida
            }
        }
        
        this.ancho = 25; 
        this.alto = 20;
        this.estaApoyado = false;
        this.velocidad = 1.0;
        this.direccion = rand.nextBoolean(); // Dirección inicial aleatoria
    }
    
    public void mostrar() {
        this.e.dibujarRectangulo(x, y, ancho, alto, 0, Color.RED);
    }
    
    public void movVertical() {
        if (!estaApoyado) {
            y += 1; // La tortuga cae
        }
    }
    
    public void moverEnIsla(Isla isla) {
        if (direccion) {
            x += velocidad; // Mover hacia la derecha
        } else {
            x -= velocidad; // Mover hacia la izquierda
        }
        
        // Cambiar de dirección si alcanza el borde de la isla
        if (x <= isla.getBordeIzq() || x >= isla.getBordeDer()) {
            direccion = !direccion; // Cambiar de dirección
        }
    }
    
    // Métodos para obtener los bordes de la tortuga
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
