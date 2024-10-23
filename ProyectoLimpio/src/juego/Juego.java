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
	private Poder bola;
	private Fondo fondo;
	private Casa casita;
	private Gnomos[] gnomos;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1150, 700);
		this.totoro = new Personaje(666,250,entorno);
		this.islas = new Isla[15];
		this.tortuga = new Tortuga[5];
		this.fondo = new Fondo("imagenes/fondo.jpg", entorno);
		this.casita = new Casa(580,78,entorno);
		this.gnomos = new Gnomos[5];
		// Inicializar lo que haga falta para el juego
		// ...
		
		int k = 0;
		int separacionBase = 125;
		int totalFilas = 5;
		
		for(int i = 1;i<=totalFilas;i++) {
			for(int j = 1; j<=i;j++) {
				double posicionX = (j)*(1200+i*45)/(i+1)-45*j;
			
				double posicionY = i*separacionBase;
				islas[k++]=new Isla(posicionX,posicionY,entorno,1.0/(i/2));
			}
		}
//		
		for (int i = 0; i < gnomos.length; i++) {
            gnomos[i] = new Gnomos(510 + i * 10, 61, entorno);
        }

		    // Inicializar tortugas (evitando la primera isla)
		    for (int i = 0; i < tortuga.length; i++) {
		        tortuga[i] = new Tortuga(entorno, islas); // Se inicializa cada tortuga en una posición aleatoria
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
		fondo.dibujar();
		casita.dibujarCasa(entorno);
        verificarGnomosPisandoIsla(gnomos, islas);
        
       
		if(pisandoIsla(totoro,islas)) {
			this.totoro.estaApoyado = true;
		}else {
			this.totoro.estaApoyado = false;
		}
		if(tocaTecho(totoro,islas)) {
			totoro.cancelarSalto();
	}
        if(bola != null) {
            bola.lanzarBola();
            bola.dibujarBola(entorno);
        }
	
		 // Mover tortugas
	    for (Tortuga t : tortuga) {
	        if (t != null) {
	            t.movVertical(); // Las tortugas caen hasta tocar una isla
	            if (pisandoIslaTortuga(t, islas)) {
	                t.estaApoyado = true;  // Si está pisando una isla, se apoya
	                t.moverEnIsla(islas[getIslaApoyo(t)]); // Mover la tortuga sobre la isla correspondiente
	            }
	            t.mostrar(); // Mostrar la tortuga en pantalla
	        }
		


		}
//		 Dibujar
		this.totoro.mostrar();
		for(Isla i: this.islas) {
			i.mostrar();   
	}
	    for (Gnomos gnomo : gnomos) {
            if(gnomo!=null){
                gnomo.dibujarGnomo(entorno);
                gnomo.movimientoGnomo();
                gnomo.gravedad();
            }
        }
	}
	private int getIslaApoyo(Tortuga t) {
	    for (int i = 0; i < islas.length; i++) {
	        Isla isla = islas[i];
	        if (t.getBordeInf() >= isla.getBordeSup() && 
	            t.getBordeInf() <= isla.getBordeSup() + 1 && 
	            t.getBordeIzq() <= isla.getBordeDer() && 
	            t.getBordeDer() >= isla.getBordeIzq()) {
	            return i;  // Devuelve el índice de la isla sobre la cual está apoyada
	        }
	    }
	    return -1;  // Si no está apoyada en ninguna isla
	}

	
	
	private void chequearTeclas() {
		if(entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) {
			this.totoro.mover(2);
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)||entorno.estaPresionada('a') ) {
			this.totoro.mover(-2);
		}
		if(entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.sePresiono('w') ) {
			totoro.saltar();
		}
		if(entorno.estaPresionado(entorno.BOTON_IZQUIERDO) || entorno.estaPresionada('c') && bola == null) {
            bola = new Poder(totoro.getBordeDer(), totoro.getBordeSup() + 20 , entorno, totoro.direccion) ; 
        }
	}
	
	   private boolean pisandoIsla(Personaje p, Isla[] i) {
	        for (Isla isla : islas) {

	            if (pisandoIsla(p, isla)) {
	                return true;
	            }

	        }
	        return false;
	    }

	    private boolean pisandoIsla(Personaje p, Isla i) {
	        return ( Math.abs(  p.getBordeInf() - i.getBordeSup()) < 4)
	                && (p.getBordeIzq() < i.getBordeDer()-10)
	                && (p.getBordeDer() > i.getBordeIzq()+15);
	    }
		
	private boolean tocaTecho(Personaje p,Isla[] isla) {
		 for (Isla i : isla) {
		        	if(p.getBordeIzq() < i.getBordeDer() && 
			                p.getBordeDer() > i.getBordeIzq()) {
		        		
		        		if (p.getBordeSup()<=i.getBordeInf()&&p.getBordeInf()>=i.getBordeSup()-2) {
		        			return true;
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
	private void verificarGnomosPisandoIsla(Gnomos[] gnomos, Isla[] islas) {
        for (Gnomos gnomo : gnomos) { 
            boolean estaApoyado = false;
            for (Isla isla : islas) { 
                if (pisandoIsla(gnomo, isla)) {
                    estaApoyado = true;
                    break; // Si está pisando una isla, no necesitamos seguir buscando
                }
            }

            if (estaApoyado && !gnomo.estaApoyado) {
                gnomo.cambiarDireccion(); 
            }
            gnomo.estaApoyado = estaApoyado;
        }
    }
	
    private boolean pisandoIsla(Gnomos g, Isla i) {
        return ( Math.abs(  g.getBordeInf() - i.getBordeSup()) < 1)
                && (g.getBordeIzq() < i.getBordeDer())
                && (g.getBordeDer() > i.getBordeIzq());
    }

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}