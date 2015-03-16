/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: JugadorNovato.java
 * Fecha:   24/11/2014
 */

package practica_4_3;

import java.util.Random;

/**
 * La clase gestiona el objeto Jugador Novato
 * 
 * @author Alex
 *
 */
public class Jugador implements Runnable {

	private ClubPalos alquilarPalos;
	private ClubPelotas alquilarPelotas;
	private ClubJugador buscarPareja;
	private int id, numPareja, veces;
	private Pareja pareja;

	/**
	 * Constructor del Jugador
	 * 
	 * @param nAlquilarPalos
	 * @param nAlquilarPelotas
	 * @param nBuscarPareja
	 * @param nId
	 * @param nVeces
	 */
	public Jugador(ClubPalos nAlquilarPalos, ClubPelotas nAlquilarPelotas,
			ClubJugador nBuscarPareja, int nId, int nVeces) {
		alquilarPalos = nAlquilarPalos;
		alquilarPelotas = nAlquilarPelotas;
		buscarPareja = nBuscarPareja;
		id = nId;
		veces = nVeces;
	}

	/**
	 * El método se ocupa de buscar una pareja al usuario, reservando uno las
	 * pelotas y otro los palos, y devolviendo el material tras haber jugado un
	 * tiempo
	 */
	public void run() {
		Random aleatorio;
		int i = 0;
		int pelotas = 0;
		aleatorio = new Random();
		for (i = 0; i < veces; i++) {
			
			//Formacion de la pareja
			
			System.out.println(id + "-Buscando pareja");
			pareja = buscarPareja.obtenerPareja(this);
			numPareja = pareja.numPareja();

			// Jugador 1 a por las pelotas y el otro a por los palos
			
			
			if (pareja.jugadorDos().id() == id) {
				pelotas = pareja.decidirPelotas();
				System.out.println("La pareja " + numPareja
						+ "-Solicita pelotas[" + pelotas + "]");
				alquilarPelotas.reservar(pelotas);
				System.out.println("La pareja " + numPareja
						+ "-Reserva pelotas[" + pelotas + "]");
			} else {
				System.out.println("La pareja " + numPareja
						+ "-Solicita palos[2]");
				alquilarPalos.reservar();
				System.out.println("La pareja " + numPareja
						+ "-Reserva palos[2]");
			}
			pareja.reservarMateriales();

			// Tiempo de juego
			
			try {
				Thread.sleep(aleatorio.nextInt(1000));
			} catch (InterruptedException e) {
			}
			pareja.jugar();
			
			// Devolucion del material
			
			if (pareja.jugadorDos().id() == id) {
				System.out.println("La pareja " + numPareja
						+ "-Solicita devolución de pelotas[" + pelotas + "]");
				alquilarPelotas.devolver(pelotas);
				System.out.println("La pareja " + numPareja
						+ "-Devuelve pelotas[" + pelotas + "]");
			} else {
				System.out.println("La pareja " + numPareja
						+ "-Solicita devolución de palos[2]");
				alquilarPalos.devolver();
				System.out.println("La pareja " + numPareja
						+ "-Devuelve palos[2]");
			}
			pareja.dejarMateriales();
			
		}
	}
	/**
	 * Devuelve el id del Jugador
	 * @return
	 */
	public int id() {
		return id;
	}
}
