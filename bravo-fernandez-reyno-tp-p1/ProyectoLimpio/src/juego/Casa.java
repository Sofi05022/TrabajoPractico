package juego;
import java.awt.Image;
import entorno.Entorno;

public class Casa { double x;
double y;
double tamanio;
double ancho;
double alto;

Entorno e;
Image imgCasa;

public Casa(double x, double y, Entorno ent) {
    this.x = x;
    this.y = y;
    this.tamanio = 0.15;
    this.e = ent;
    this.imgCasa = entorno.Herramientas.cargarImagen("imagenes/casita.png");
    this.ancho = imgCasa.getHeight(null) * tamanio;
    this.alto = imgCasa.getWidth(null) * tamanio;
}

//Mostrar la Casa

public void dibujarCasa(Entorno e) {
    e.dibujarImagen(imgCasa, x, y, 0, tamanio);
}

public double getX() {
    return this.x;
}

public double getY() {
    return this.y;
}
	

}
