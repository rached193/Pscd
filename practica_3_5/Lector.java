/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Lector.java
 * Fecha:   15/10/2014
 */

package practica_3_5;

import java.util.Random;

public class Lector implements Runnable {

	// Atributos
	private BaseDeDatos base;
	private DatosComunes datos;

	// Constructor
	public Lector(BaseDeDatos nBase, DatosComunes nDatos) {
		base = nBase;
		datos = nDatos;
	}

	public void run() {
		int i, clave, claveAleatoria;
		Random aleatorio;
		aleatorio = new Random();
		// Lectura de 110 registros aleatorios
		for (i = 1; i <= 110; i++) {
			claveAleatoria = aleatorio.nextInt(100);
			clave = claveAleatoria + 1001;
			// Protocolo de entrada
			entrada(claveAleatoria);

			base.getNombre(claveAleatoria);
			base.getApellidos(claveAleatoria);
			base.getDireccion(claveAleatoria);
			// Protocolo de salida
			salida(claveAleatoria);
		}
	}

	/**
	 * Protocolo de entrada del lector
	 * 
	 * @param pos
	 */
	private void entrada(int pos) {
		/*
		 * Si no hay nadie escribiendo y no hay escritores esperando podemos
		 * leer. Despues aviso a lectores o libero el mutex
		 */
		datos.waitMutex(pos);
		if (datos.getEstado(pos) || datos.getEscEsp(pos) != 0) {
			datos.LectorEspera(pos);
			datos.signalMutex(pos);
			datos.waitLeer(pos);
			datos.LectorDeja(pos);
		}
		datos.LectorLee(pos);
		if (datos.getLecEsp(pos) > 0) {
			datos.signalLeer(pos);
		} else
			datos.signalMutex(pos);

	}

	/**
	 * Protocolo de salida del lector
	 * 
	 * @param pos
	 */
	private void salida(int pos) {
		/*
		 * Libera el lector y avisa a los escritores si no hay mas leyendo y hay
		 * escritores esperando
		 */
		datos.waitMutex(pos);
		datos.LectorLibera(pos);
		if (datos.getLeyendo(pos) == 0 && datos.getEscEsp(pos) > 0) {
			datos.signalEscribir(pos);
		} else
			datos.signalMutex(pos);

	}

}
