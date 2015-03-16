/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: ClubJugador.java
 * Fecha:   24/11/2014
 */

package practica_4_3;

/**
 * La clase gestiona un monitor para poder encontrar pareja
 * 
 * @author Alex
 *
 */
public class ClubJugador {

	private int esperando, numParejas;
	private Jugador jugadorUno;
	private Pareja nPareja;

	public ClubJugador() {
		numParejas = 0;
		esperando = 0;
		jugadorUno = null;
		nPareja = null;
	}

	/**
	 * Da al jugador nJugador una pareja, en caso de no ser posible lo deja en
	 * espera. Despues notifica por pantalla la formacion de la pareja.
	 * 
	 * @param nJugador
	 * @return
	 */
	public synchronized Pareja obtenerPareja(Jugador nJugador) {
		while (esperando == 0) {
			esperando = 1;
			jugadorUno = nJugador;
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		if (esperando == 1) {
			numParejas++;
			nPareja = new Pareja(jugadorUno, nJugador, numParejas);
			esperando = -1;
			notifyAll();
		} else {
			esperando = 0;
		}
		System.out.println(nJugador.id() + "-Ha formado la pareja "
				+ numParejas);
		return nPareja;
	}
}
