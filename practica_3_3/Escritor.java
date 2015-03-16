/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Escritor.java
 * Fecha:   10/11/2014
 */

package practica_3_3;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Clase que gestiona los escritores de la base de datos
 * 
 */
public class Escritor implements Runnable {

	// Atributos
	private BaseDeDatos base;
	private Semaphore[] tabla;

	/**
	 * Constructor
	 * 
	 * @param nBase
	 * @param nMutex
	 */
	public Escritor(BaseDeDatos nBase, Semaphore[] nMutex) {
		base = nBase;
		tabla = nMutex;
	}

	/**
	 * Metodo que gestiona la modificacion de los datos en la base de datos
	 * aleatoriamente en exclusion mutua.
	 */
	public void run() {
		int clave, claveAleatoria;
		String nombre, apellidos, direccion, veces;
		Random aleatorio = new Random();

		// Bucle que realiza 110 veces la modificacion de la base de datos
		for (int i = 1; i <= 110; i++) {
			claveAleatoria = aleatorio.nextInt(100);
			clave = claveAleatoria + 1001;
			// SC
			try {
				tabla[claveAleatoria].acquire();
			} catch (InterruptedException e) {
			}
			nombre = base.getNombre(clave);
			veces = nombre.substring(12, nombre.length());
			nombre = nombre.substring(0, 12) + (Integer.parseInt(veces) + 1);
			base.updateNombre(clave, nombre);

			apellidos = base.getApellidos(clave);
			apellidos = apellidos.substring(0, 15)
					+ (Integer.parseInt(veces) + 1);
			veces = apellidos.substring(15, apellidos.length());
			base.updateApellidos(clave, apellidos);

			direccion = base.getDireccion(clave);
			veces = direccion.substring(22, direccion.length());
			direccion = direccion.substring(0, 22)
					+ (Integer.parseInt(veces) + 1);
			base.updateDireccion(clave, direccion);
			tabla[claveAleatoria].release();
		}
	}
}
