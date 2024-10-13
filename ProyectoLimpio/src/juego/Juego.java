package juego;


import java.awt.Color;


import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Personaje totoro;
	private Isla isla;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		this.totoro = new Personaje(100,100,entorno);
		this.isla = new Isla(300,400,entorno);
		entorno.colorFondo(Color.CYAN);
		// Inicializar lo que haga falta para el juego
		// ...

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
		if(pisandoIsla(totoro,isla)) {
			this.totoro.estaApoyado = true;
		}else {
			this.totoro.estaApoyado = false;
		}
		// Dibujar
		this.totoro.mostrar();
		this.isla.mostrar();

	}
	
	private void chequearTeclas() {
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			this.totoro.mover(2);
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			this.totoro.mover(-2);
		}
	}
	private boolean pisandoIsla(Personaje p,Isla i) {
		//return (Math.abs(p.getBordeInf()- i.getBordeSup()-4) <2) 
		//&& (p.getBordeInf() < i.getBordeSup()+3) 
		//&& (p.getBordeDer()<i.getBordeDer()-3);
		return (p.getBordeInf() >= i.getBordeSup()) && // El borde inferior del personaje está encima del borde superior de la isla
		           (p.getBordeInf() <= i.getBordeSup()+1) && // Un pequeño margen de tolerancia
		           (p.getBordeIzq() < i.getBordeDer()) && // El borde izquierdo del personaje está a la izquierda del borde derecho de la isla
		           (p.getBordeDer() > i.getBordeIzq()); // El borde derecho del personaje está a la derecha del borde izquierdo de la isla

	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
