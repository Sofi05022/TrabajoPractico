package juego;

import entorno.Entorno;
import java.awt.Color;
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
	private int gnomosRescatados;
	private int gnomosPerdidos;
	private int tortugasEliminadas;
	private boolean finJuego;
	private String mensajeFinal;
	private boolean pepVivo;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 1000, 600);
		
		
		// Inicializar lo que haga falta para el juego
		// ...
		iniciarJuego();
		
//	

		    

		// Inicia el juego!
		this.entorno.iniciar();
	}

		public void tick() {
		    // Procesamiento de un instante de tiempo
		    // ...
			
			if(finJuego){

	            // Mostrar mensaje de finalización del juego o detener otras acciones
	            double rectX = entorno.ancho() / 2;
	            double rectY = entorno.alto() / 2;
	            double rectAncho = 400;
	            double rectAlto = 300; 
	            fondo.dibujar();
	            casita.dibujarCasa(entorno);
	            for (Gnomos gnomo : gnomos) {
	                if (gnomo != null) {
	                gnomo.dibujarGnomo(entorno);
	                }
	            }
	            for (Isla isla : islas) {
	                isla.mostrar();
	            }
	            for (Tortuga tortuga : tortuga) {
	                if (tortuga != null) {
	                    tortuga.mostrar();
	                }
	            }
	            if (pepVivo) {
	                pep.mostrar();
	        }
	        entorno.dibujarRectangulo(rectX, rectY, rectAncho, rectAlto, 0, Color.WHITE);
	        entorno.cambiarFont("Arial", 26, Color.BLACK);
	        entorno.escribirTexto("¡Juego Terminado!", rectX-130,rectY-50);
	        entorno.escribirTexto(mensajeFinal,rectX-130,rectY);
	        entorno.escribirTexto("Presione R para volver a jugar",rectX-160,rectY+50);


	        if(entorno.sePresiono('r')){
	            iniciarJuego();

	        }
	        return; // Detiene el procesamiento adicional

	        }
		    chequearTeclas();
		    pep.movVertical();
		    fondo.dibujar();
		    casita.dibujarCasa(entorno);
		    verificarGnomosPisandoIsla(gnomos, islas);
		    dibujarEstadisticas();
		    

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
	                        t.herir(); // Ya no necesitamos llamar a caer() explícitamente
	                        this.tortugasEliminadas ++;
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
		    
	     // Verificar colisión entre Tortuga y  Totoro
//	        for (Tortuga t : tortuga) {
//	            if (pep.getBordeDer() > t.getBordeIzq() && pep.getBordeIzq() < t.getBordeDer() &&
//	                pep.getBordeInf() > t.getBordeSup() && pep.getBordeSup() < t.getBordeInf()) {
//	                //pep.herir();
//	            }
//	        }
		    // Actualizar la caída si Totoro está herido
		    //pep.actualizarCaidaHerido();

		    // Dibujar personajes e islas
		    this.pep.mostrar();
		    for (Isla i : this.islas) {
		        i.mostrar();
		    }
		    
		    //colision entre gnomo y personaje
		  //colision entre gnomo y personaje apartir de la tercer fila de islas
		    for (int i = 0;i < gnomos.length;i++) {
		        if (gnomos[i] != null) {
		        	 boolean enTercerFila = false;
		          for(Isla isla: islas) {
		        	  if(isla.y >= 400 && pisandoIsla(gnomos[i],isla)) {
		        		  enTercerFila = true;
		        		  break;
		        	  }
                      }
		          if(enTercerFila) {
		        	// Verificar colisión con pep
		        	  boolean enColisionActual = pep.getBordeDer() > gnomos[i].getBordeIzq() && 
                              pep.getBordeIzq() < gnomos[i].getBordeDer() &&
                              pep.getBordeInf() > gnomos[i].getBordeSup() && 
                              pep.getBordeSup() < gnomos[i].getBordeInf();

                              if (enColisionActual && !gnomos[i].estaEnColision()) {
                            	  	gnomos[i].iniciarSalto(); // Inicia el salto cuando Totoro lo toca
                            	  	this.gnomosRescatados++; // Incrementa el contador al detectar una nueva colisión
                            	  	gnomos[i].setEnColision(true); // Marca al gnomo como en colisión
                              } else if (!enColisionActual) {
                            	  // Si ya no están en colisión, resetea la marca
                            	  gnomos[i].setEnColision(false);
                              }
		        	  		}
		          gnomos[i].actualizar();  // Actualiza la posición y estado del gnomo
                  gnomos[i].dibujarGnomo(entorno);
                  }
		        }
		    
		 // Colisión entre gnomo y tortuga
		    for (int i = 0; i < gnomos.length; i++) {
		        Gnomos gnomo = gnomos[i];
		        if (gnomo != null && !gnomo.estaEnColision()) {
		            for (Tortuga t : tortuga) {
		                if (t != null) {
		                    boolean enColisionActual = gnomo.getBordeDer() > t.getBordeIzq() &&
		                                               gnomo.getBordeIzq() < t.getBordeDer() &&
		                                               gnomo.getBordeInf() > t.getBordeSup() &&
		                                               gnomo.getBordeSup() < t.getBordeInf();

		                    if (enColisionActual) {
		                        gnomo.iniciarSalto();
		                        gnomo.setEnColision(true); // Marca al gnomo en colisión
		                        this.gnomosPerdidos++; // Incrementa el contador
		                        gnomos[i] = null; // El gnomo se hace null al colisionar con una tortuga
		                        break; // Sale del bucle de tortugas una vez que colisiona. 
		                    }
		                }
		            }
		        }
		    }
		        
		        
		        //verifica si los gnomos caen al vacio y los cuenta perdidos
		        for (Gnomos gnomo : gnomos) {
		        	if (gnomo != null && gnomo.getBordeSup() > 670) {
		        		gnomo.iniciarSalto();
		        		gnomo = null;
		        		this.gnomosPerdidos ++;
		        	}
		        }
		        //regenera los gnomos perdidos en los anteriores metodos.
		        regenerarGnomos();
		       

		        
		        finJuego();
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
            	
                if (gnomo != null && pisandoIsla(gnomo, isla)) {
                    estaApoyado = true;
                    break; // Si está pisando una isla, no necesitamos seguir buscando
                }
            }

            if (estaApoyado && !gnomo.estaApoyado) {
                gnomo.cambiarDireccion(); 
            }
            if (gnomo != null) {
                gnomo.estaApoyado = estaApoyado;
            }
        }
    }
	
	 private void regenerarGnomos() {
	        for (int i = 0; i < gnomos.length; i++) {
	            if (gnomos[i] == null) { 
	                // Crea un nuevo gnomo en una posición inicial definida
	                gnomos[i] = new Gnomos(455 + i * 10, 55, entorno);
	                }
	            }
	 }
	
	
	
    private boolean pisandoIsla(Gnomos g, Isla i) {
        return ( Math.abs(  g.getBordeInf() - i.getBordeSup()) < 1)
                && (g.getBordeIzq() < i.getBordeDer())
                && (g.getBordeDer() > i.getBordeIzq());
    }
    
 
    
    
    private void dibujarEstadisticas() {
        double TotalSeg = (entorno.tiempo() / 1000.0);
        int minutos = (int) (TotalSeg / 60);
        int segundos = (int) (TotalSeg % 60);
        
        entorno.cambiarFont("Arial", 18, Color.BLACK);
        entorno.escribirTexto("Tiempo: " + minutos + ":" + segundos, 10, 30);
        entorno.escribirTexto("Gnomos Rescatados: " + gnomosRescatados, 10, 50);
        entorno.escribirTexto("Gnomos Perdidos: " + gnomosPerdidos, 10, 70);
        entorno.escribirTexto("Tortugas Eliminadas: " + tortugasEliminadas, 10, 90);
        }
    
    
    private void iniciarJuego() {
    	this.pep = new Personaje(450,470,entorno);
		this.islas = new Isla[15];
		this.tortuga = new Tortuga[5];
		this.fondo = new Fondo("imagenes/fondo.jpg", entorno);
		this.casita = new Casa(500,50,entorno);
		this.gnomos = new Gnomos[5];
		this.gnomosRescatados = 0;
		this.gnomosPerdidos = 0;
		this.tortugasEliminadas = 0;
		this.pepVivo = true;
		this.mensajeFinal = "";
		this.finJuego = false;
		
		
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
		
		for (int i = 0; i < gnomos.length; i++) {
            gnomos[i] = new Gnomos(455 + i * 10, 55, entorno);
        }
		
		// Inicializar tortugas (evitando la primera isla)
	    for (int i = 0; i < tortuga.length; i++) {
	        tortuga[i] = new Tortuga(entorno, islas); // Se inicializa cada tortuga en una posición aleatoria
	    }
    }
    
    private boolean pepMuerte(Personaje p, Tortuga[] tortugas){
        for(Tortuga t : tortugas){
        	if (pep.getBordeDer() > t.getBordeIzq() && pep.getBordeIzq() < t.getBordeDer() &&
	                pep.getBordeInf() > t.getBordeSup() && pep.getBordeSup() < t.getBordeInf()) {
        		return true;
	       
	            }
        }
        return false;
    }
    
    private void finJuego() {

        if (gnomosRescatados == 5) {
            finJuego = true;
            mensajeFinal = "Ganaste, ¡has rescatado 5 gnomos!";
        } else if (pepMuerte(pep, tortuga)) {
            finJuego = true;
            mensajeFinal = "Perdiste, Pep ha muerto.";
        } else if (gnomosPerdidos == 3) {
            finJuego = true;
            mensajeFinal = "Perdiste,"+" "+ gnomosPerdidos+" "+"gnomos murieron.";
        }else if(pep!=null && pep.y>670){
            finJuego=true;
            mensajeFinal="Perdiste, Pep ha muerto.";
        }

    }
    
    
    
    

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}