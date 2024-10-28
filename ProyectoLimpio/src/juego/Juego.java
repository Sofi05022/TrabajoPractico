package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Personaje pep;
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
		this.entorno = new Entorno(this, "Proyecto para TP", 1000, 600);
		this.pep = new Personaje(450,470,entorno);
		this.islas = new Isla[15];
		this.tortuga = new Tortuga[5];
		this.fondo = new Fondo("imagenes/fondo.jpg", entorno);
		this.casita = new Casa(500,50,entorno);
		this.gnomos = new Gnomos[5];
		// Inicializar lo que haga falta para el juego
		// ...
		
		int t = 0; // se encarga de las primeras dos filas de islas.
		int separacionBase = 95;
		for (int i = 1; i <= 2; i++) {
			for (int j = 1; j <= i; j++) {
				double posicionX = (j)*(entorno.ancho())/(i+1);
				if ( i == 2 && j == 1) {
					posicionX = posicionX +50;
				}
				if (j == 2) {
					posicionX = posicionX -50;
				}
				double posicionY = i*separacionBase;
				islas[t++] = new Isla(posicionX,posicionY,entorno);
			}
		}

		int separacionBase2 = 115; // se encarga del resto de filas.
		
		for(int i = 3;i<=5;i++) {
			for(int j = 1; j<=i;j++) {
				double posicionX = (j)*(entorno.ancho())/(i+1);
				if(i == 3 && j == 1) {
					posicionX = posicionX - 30;
				}
				if (i == 3 && j == 3) {
					posicionX = posicionX + 30;
				}
				if (i == 4 && j == 1) {
					posicionX = posicionX - 60;
				}
				if (i == 4 && j == 2) {
					posicionX = posicionX - 20;
				}
				if (i == 4 && j == 3) {
					posicionX = posicionX + 20;
				}
				if (i == 4 && j == 4) {
					posicionX = posicionX + 60;
				}
				if (i == 5 && j == 1) {
					posicionX -= 100;
				}
				if (i == 5 && j == 2) {
					posicionX -= 50;
				}
				if (i == 5 && j == 4) {
					posicionX += 50;
				}
				if (i == 5 && j == 5) {
					posicionX += 100;
				}
				double posicionY = i*separacionBase2;
				islas[t++]= new Isla(posicionX,posicionY,entorno);
			}
		}
//		
		for (int i = 0; i < gnomos.length; i++) {
            gnomos[i] = new Gnomos(455 + i * 10, 55, entorno);
        }

		    // Inicializar tortugas (evitando la primera isla)
		    for (int i = 0; i < tortuga.length; i++) {
		        tortuga[i] = new Tortuga(entorno, islas); // Se inicializa cada tortuga en una posición aleatoria
		    }

		// Inicia el juego!
		this.entorno.iniciar();
	}

		public void tick() {
		    // Procesamiento de un instante de tiempo
		    // ...
		    chequearTeclas();
		    pep.movVertical();
		    fondo.dibujar();
		    casita.dibujarCasa(entorno);
		    verificarGnomosPisandoIsla(gnomos, islas);

		    if (pisandoIsla(pep, islas)) {
		        this.pep.estaApoyado = true;
		    } else {
		        this.pep.estaApoyado = false;
		    }

		    if (tocaTecho(pep, islas)) {
		        pep.cancelarSalto();
		    }

		    // Si hay una bola, lanzarla y dibujarla
		    if (bola != null) {
		        bola.lanzarBola();
		        bola.dibujarBola(entorno);
		    }

		    // Mover y actualizar tortugas
	        for (Tortuga t : tortuga) {
	            if (t != null) {
	                t.actualizar(islas);  // Reemplaza la lógica anterior de movimiento
	                t.mostrar();
	            }
	        }


	     // Comprobar si la bola toca a alguna tortuga
	        if (bola != null) {
	            for (Tortuga t : tortuga) {
	                if (t != null) {
	                    if (bola.getX() >= t.getBordeIzq() && bola.getX() <= t.getBordeDer() &&
	                        bola.getY() >= t.getBordeSup() && bola.getY() <= t.getBordeInf()) {
	                        t.herir(); // Ya no necesitamos llamar a caer() explícitamene
	                        bola = null;
	                        break;
	                    }
	                }
	            }
	        }
	     // Verifica si la bola sale del entorno
	        if (bola != null && (bola.getX() < 0 || bola.getX() > 1000 || bola.getY() < 0 || bola.getY() > 600)) {
	        	bola = null;
	        }
		    
	     // Verificar colisión con Totoro
	        for (Tortuga t : tortuga) {
	            if (pep.getBordeDer() > t.getBordeIzq() && pep.getBordeIzq() < t.getBordeDer() &&
	                pep.getBordeInf() > t.getBordeSup() && pep.getBordeSup() < t.getBordeInf()) {
	                pep.herir();
	            }
	        }
		    // Actualizar la caída si Totoro está herido
		    pep.actualizarCaidaHerido();

		    // Dibujar personajes e islas
		    this.pep.mostrar();
		    for (Isla i : this.islas) {
		        i.mostrar();
		    }
		    for (Gnomos gnomo : gnomos) {
		        if (gnomo != null) {
		            // Verificar colisión con Totoro
		            if (pep.getBordeDer() > gnomo.getBordeIzq() && 
		                pep.getBordeIzq() < gnomo.getBordeDer() &&
		                pep.getBordeInf() > gnomo.getBordeSup() && 
		                pep.getBordeSup() < gnomo.getBordeInf()) {
		                gnomo.iniciarSalto();  // Inicia el salto cuando Totoro lo toca
		            }
		           
		            gnomo.actualizar();  // Actualiza la posición y estado del gnomo
		            gnomo.dibujarGnomo(entorno);
		        }
		    }
		    for(Gnomos gnomo:gnomos) {
		        if (gnomo != null) {
		            for (Tortuga t : tortuga) {
		                if (t.getBordeDer() >= gnomo.getBordeIzq() && 
		                    t.getBordeIzq() <= gnomo.getBordeDer() &&
		                    t.getBordeInf() >= gnomo.getBordeSup() && 
		                    t.getBordeSup() <= gnomo.getBordeInf()) {
		                    gnomo = null; 
		                    break; // Salir del bucle de tortugas una vez encontrada la colisión
		                }
		            }
		        }
		    }
		}
		

	
	private void chequearTeclas() {
		if(entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('d')) {
			this.pep.mover(2);
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)||entorno.estaPresionada('a') ) {
			this.pep.mover(-2);
		}
		if(entorno.sePresiono(entorno.TECLA_ARRIBA) || entorno.sePresiono('w') ) {
			pep.saltar();
		}
		if(entorno.estaPresionado(entorno.BOTON_IZQUIERDO) && bola == null || entorno.estaPresionada('c') && bola == null) {
            bola = new Poder(pep.getBordeDer()-20, pep.getBordeSup() + 30 , entorno, pep.direccion) ; 
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
		        		
		        		if (p.getBordeSup()<=i.getBordeInf()&&p.getBordeInf()>=i.getBordeSup()-5) {
		        			return true;
		        		}
		        		
		        	}
		 }
		return false;
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