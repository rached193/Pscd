/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Jugador.java
 * Fecha:   23/11/2014
 */

package practica_4_1;

/**
 * Clase de gestion del club
 * @author Alex
 *
 */
public class Simulador {
	
	private static Jugador jugadoresNovatos[];
	private static Jugador jugadoresExperimentados[];
	private static Club club;
	
	/**
	 * Lanzador de los Jugadores
	 * @param args
	 */
	public static void main(String args[]){
		
		Thread t[];		
		club = new Club(20,20);
		
		jugadoresNovatos = new Jugador[7];
		jugadoresExperimentados = new Jugador[7];
		
		//Inicializa jugadores novatos
		for(int i = 0;i<jugadoresNovatos.length; i++){
			jugadoresNovatos[i] = new Jugador(true, i+1, 5, club);
		}
		
		//Inicializa jugadores experimentados
		for(int i = 0; i<jugadoresExperimentados.length; i++){
			jugadoresExperimentados[i] = new Jugador(false, i+8, 5, club);
		}
		
		//Lanza hilos
		t = new Thread[14];
		for(int i=0; i<jugadoresNovatos.length; i++){
			t[i] = new Thread(jugadoresNovatos[i]);
		}
		for(int i=0; i<jugadoresExperimentados.length; i++){
			t[i+7] = new Thread(jugadoresExperimentados[i]);
		}
		
		//Espera a la finalización de los hilos
		for(int i=0; i<t.length; i++){
			t[i].start();
		}
	}
}

