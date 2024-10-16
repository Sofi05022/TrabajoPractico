package juego;


import java.awt.Color;


import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Personaje totoro;
	private Isla[] islas;
	private Tortuga[] tortuga;
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1050, 600);
		this.totoro = new Personaje(220,465,entorno);
		this.islas = new Isla[15];
		this.tortuga = new Tortuga[5];
		entorno.colorFondo(Color.CYAN);
		// Inicializar lo que haga falta para el juego
		// ...
		int k = 0;
//		int separacion =  50;
		for(int i=1;i<6;i++) {
			for(int j=1;j<=i;j++) {
				islas[k++] = new Isla((j)*this.entorno.ancho()/(i+1),100*i+50,entorno,1.0/(i+2));
			}
		}
		int r = 0;
		for(int i = 1;i <6;i++) {
			tortuga[r++] = new Tortuga(this.entorno.ancho(),0,entorno);
		}

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		chequearTeclas();
		totoro.movVertical();
		tortuga.movVertical();
		if(pisandoIsla(totoro,islas)) {
			this.totoro.estaApoyado = true;
		}else {
			this.totoro.estaApoyado = false;
		}
		if(tocaTecho(totoro,islas)) {
			totoro.cancelarSalto();
	}
		if(pisandoIslaTortuga(tortuga,islas)) {
			this.tortuga.estaApoyado = true;
		}else {
			this.tortuga.estaApoyado = false;
		}
//		 Dibujar
		this.totoro.mostrar();
		for(Isla i: this.islas) {
			i.mostrar();
		}
		this.tortuga.mostrar();

	}
	
	private void chequearTeclas() {
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			this.totoro.mover(2);
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			this.totoro.mover(-2);
		}
		if(entorno.sePresiono(entorno.TECLA_ARRIBA) && totoro.estaApoyado)  {
			totoro.saltar();
		}
	}
	
	private boolean pisandoIsla(Personaje p,Isla[] i) {
		 for (Isla isla : islas) {
		        if (isla != null) {
		            // Verifica si el personaje está sobre la isla actual
		            if (p.getBordeInf() >= isla.getBordeSup() &&  // El borde inferior del personaje está sobre la isla
		                p.getBordeInf() <= isla.getBordeSup() + 1 && // Pequeño margen
		                p.getBordeIzq() < isla.getBordeDer() &&   // El borde izquierdo del personaje no está más allá del borde derecho de la isla
		                p.getBordeDer() > isla.getBordeIzq()) {   // El borde derecho del personaje no está más allá del borde izquierdo de la isla
		                
		                return true; // Si está pisando una isla, regresa true
		            }
		        }
		    }
		    return false; // Si no está pisando ninguna isla, regresa false
		}
	private boolean tocaTecho(Personaje p,Isla[] isla) {
		 for (Isla i : islas) {
		        if (isla != null) {
		        	if(p.getBordeIzq() <= i.getBordeDer() && 
			                p.getBordeDer() >= i.getBordeIzq()) {
		        		
		        		if (p.getBordeSup()<=i.getBordeInf()&&p.getBordeInf()>=i.getBordeSup()-1) {
		        			return true;
		        		}
		        		
		        	}
		        }
		 }
		return false;
	}
	
	private boolean pisandoIslaTortuga(Tortuga t,Isla[] i) {
		 for (Isla isla : islas) {
		        if (isla != null) {
		            // Verifica si el personaje está sobre la isla actual
		            if (t.getBordeInf() >= isla.getBordeSup() &&  // El borde inferior del personaje está sobre la isla
		                t.getBordeInf() <= isla.getBordeSup() + 1 && // Pequeño margen
		                t.getBordeIzq() < isla.getBordeDer() &&   // El borde izquierdo del personaje no está más allá del borde derecho de la isla
		                t.getBordeDer() > isla.getBordeIzq()) {   // El borde derecho del personaje no está más allá del borde izquierdo de la isla
		                
		                return true; // Si está pisando una isla, regresa true
		            }
		        }
		    }
		    return false; // Si no está pisando ninguna isla, regresa false
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
