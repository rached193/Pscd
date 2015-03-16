/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Club.java
 * Fecha:   23/11/2014
 */

package practica_4_1;

/**
 * Clase Monitor
 * 
 * @author Alex
 *
 */
public class Club {

	int palosLibres, pelotasLibres;

	/**
	 * Constructor del monitor
	 * 
	 * @param nPalosLibres
	 * @param nPelotasLibres
	 */
	public Club(int nPalosLibres, int nPelotasLibres) {
		palosLibres = nPalosLibres;
		pelotasLibres = nPelotasLibres;
	}

	/**
	 * Operacion Reserva Si no hay pelotas o palos disponibles lo bloqueamos.
	 * Despues hacemos la reserva
	 * 
	 * @param palos
	 * @param pelotas
	 */
	public synchronized void reserva(int palos, int pelotas) {
		while (palosLibres < palos || pelotasLibres < pelotas) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		palosLibres -= palos;
		pelotasLibres -= pelotas;
	}

	/**
	 * Operacion Devolver Devuelve los palos y pelotas reservados y notifica al
	 * resto de la liberacion
	 * 
	 * @param palos
	 * @param pelotas
	 */
	public synchronized void devolver(int palos, int pelotas) {
		palosLibres += palos;
		pelotasLibres += pelotas;
		notifyAll();
	}
}