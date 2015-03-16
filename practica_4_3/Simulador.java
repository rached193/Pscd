/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Simulador.java
 * Fecha:   24/11/2014
 */

package practica_4_3;

/**
 * Simulador del Club de golf
 * @author Alex
 *
 */
public class Simulador {
	
	private static ClubPalos reservarPalos;
	private static ClubPelotas reservarPelotas;
	private static Jugador jugadores[];
	private static ClubJugador encontrarPareja;
	
	/**
	 * Lanzador de los Threads (Jugadores)
	 * @param args
	 */
	public static void main(String args[]){
		
		Thread t[]= new Thread[14];		 
		reservarPalos = new ClubPalos(20);
		reservarPelotas = new ClubPelotas(20);
		encontrarPareja = new ClubJugador();
		jugadores = new Jugador[14];
		
		 //Inicia los jugadores, novatos en este caso
		for(int i=0;i<jugadores.length; i++){
			jugadores[i] = new Jugador(reservarPalos, reservarPelotas, encontrarPareja, i+1,5);
			t[i] = new Thread(jugadores[i]);
			t[i].start();
		}
	}
}
