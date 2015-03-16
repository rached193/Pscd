/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Jugador.java
 * Fecha:   23/11/2014
 */

package practica_4_1;

import java.util.Random;

/**
 * Clase proceso que realiza operaciones de reserva y devolucion
 * 
 * @author Alex
 *
 */
public class Jugador implements Runnable {

	private boolean novato;
	private int identificador;
	private int repeticiones;
	private Club alquiler;

	/**
	 * Constructor del Jugador, principalmente diferenciar si es novato o
	 * experimentado
	 * 
	 * @param nNovato, true-> novato, false -> experimentado.
	 * @param nIdentificador
	 * @param nRepeticiones
	 * @param nAlquiler
	 */
	public Jugador(boolean nNovato, int nIdentificador, int nRepeticiones,
			Club nAlquiler) {
		novato = nNovato;
		identificador = nIdentificador;
		repeticiones = nRepeticiones;
		alquiler = nAlquiler;
	}

	/**
	 * Lanzador del Thread
	 */
	public void run() {
		int numPalos, numPelotas;
		Random aleatorio;
		aleatorio = new Random();

		for (int i = 0; i < repeticiones; i++) {

			// Reserva
			if (novato) {
				numPelotas = aleatorio.nextInt(5) + 1;
				numPalos = 2;
			} else {
				numPelotas = 1;
				numPalos = 2 + aleatorio.nextInt(4);
			}

			System.out.println(identificador + " - " + "Solicita[" + numPelotas
					+ "," + numPalos + "]");
			alquiler.reserva(numPalos, numPelotas);
			System.out.println(identificador + " - " + "Reserva [" + numPelotas
					+ "," + numPalos + "]");

			// Juego
			try {
				Thread.sleep(aleatorio.nextInt(1000));
			} catch (InterruptedException e) {
			}

			// Devolucion
			System.out.println(identificador + " - " + "Se cansa[" + numPelotas
					+ "," + numPalos + "]");
			alquiler.devolver(numPalos, numPelotas);
			System.out.println(identificador + " - " + "Devuelve[" + numPelotas
					+ "," + numPalos + "]");
			try {
				Thread.sleep(aleatorio.nextInt(1000));
			} catch (InterruptedException e) {
			}
		}
	}
}