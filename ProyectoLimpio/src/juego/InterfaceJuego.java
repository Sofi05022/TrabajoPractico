package juego;

import entorno.Entorno;

public class InterfaceJuego {
	Entorno e;
    private int gnomosSalvados;
    private int tortugasMatadas;
    private long tiempoJuego;
    
    public InterfaceJuego(){
    	this.gnomosSalvados = 0;
    	this.tortugasMatadas = 0;
    	this.tiempoJuego = 0;
     }
    public void incrementarGnomosSalvados() {
        gnomosSalvados++;
    }

    public void incrementarTortugasMatadas() {
        tortugasMatadas++;
    }

    public long getTiempoJuego() {
        return tiempoJuego;
    }

    public void actualizarTiempo() {
    }

}
