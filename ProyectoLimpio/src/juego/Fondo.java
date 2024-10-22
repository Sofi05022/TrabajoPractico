package juego;
import java.awt.Image;

import entorno.Entorno;
public class Fondo {
	Image fondo;
    Entorno e;

    public Fondo(String ruta, Entorno ent) {
        this.fondo = entorno.Herramientas.cargarImagen(ruta);
        this.e = ent;
    }

    public void dibujar() {
        e.dibujarImagen(fondo, e.ancho() / 2, e.alto() / 2, 0, (double) e.ancho() / fondo.getWidth(null));
    }


}
