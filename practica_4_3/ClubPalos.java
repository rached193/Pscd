/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: ClubPalos.java
 * Fecha:   24/11/2014
 */

package practica_4_3;

/**
 * La clase gestiona un monitor para reservar palos
 * @author Alex
 *
 */
public class ClubPalos {

	private int palosLibres;
	
	/**
	 * Constructor del Monitor para gestionar palos
	 * @param palos
	 */
	public ClubPalos(int palos){
		palosLibres = palos;
	}
	
	/**
	 * Reserva dos palos para una pareja
	 * @param pelotas
	 */
	public synchronized void reservar(){
		while(palosLibres<2){
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		palosLibres = palosLibres - 2;
	}
	
	/**
	 * Devuelve los palos
	 */
	public synchronized void devolver(){
		palosLibres = palosLibres + 2;
		notifyAll();
	}
}
