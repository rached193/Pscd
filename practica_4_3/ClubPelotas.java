/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: ClubPelotas.java
 * Fecha:   24/11/2014
 */

package practica_4_3;

/**
 * La clase gestiona un monitor para reservar pelotas
 * 
 * @author Alex
 *
 */
public class ClubPelotas {

	private int pelotasLibres;

	/**
	 * Constructor del Monitor de gestion de pelotas
	 * 
	 * @param pelotas
	 */
	public ClubPelotas(int pelotas) {
		pelotasLibres = pelotas;
	}

	/**
	 * Operacion reserva de un numero de pelotas
	 * 
	 * @param pelotas
	 */
	public synchronized void reservar(int pelotas) {
		while (pelotasLibres < pelotas) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		pelotasLibres = pelotasLibres - pelotas;
	}

	/**
	 * Devolucion de las pelotas
	 * 
	 * @param pelotas
	 */
	public synchronized void devolver(int pelotas) {
		pelotasLibres = pelotasLibres + pelotas;
		notifyAll();
	}
}
