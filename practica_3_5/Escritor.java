/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Escritor.java
 * Fecha:   15/10/2014
 */

package practica_3_5;

import java.util.Random;

/**
 * Clase que gestiona los escritores de la base de datos
 * 
 */
public class Escritor implements Runnable {

	// Atributos
	private BaseDeDatos base;
	private DatosComunes datos;

	// Constructor
	public Escritor(BaseDeDatos nBase, DatosComunes ndatos) {
		base = nBase;
		datos = ndatos;
	}

	/**
	 * Método que gestiona la modificación de los datos en la base de datos
	 * aleatoriamente en exclusión mutua.
	 */
	public void run() {
		int i, clave, claveAleatoria;
		String nombre, apellidos, direccion, veces;
		Random aleatorio;
		aleatorio = new Random();

		// Bucle que realiza 110 veces la modificacion de la base de datos
		for (i = 1; i <= 110; i++) {
			claveAleatoria = aleatorio.nextInt(100);
			clave = claveAleatoria + 1001;

			entrada(claveAleatoria);

			// Seccion critica

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

			salida(claveAleatoria);
		}
	}

	private void entrada(int pos) {
		datos.waitMutex(pos);
		if (datos.getLeyendo(pos) > 0 || datos.getEstado(pos)) {
			datos.EscritorEspera(pos);
			datos.signalMutex(pos);
			datos.waitEscribir(pos);
			datos.EscritorDeja(pos);
		}
		datos.setEstado(pos, true);
		datos.signalMutex(pos);
	}

	private void salida(int pos) {
		datos.waitMutex(pos);
		datos.setEstado(pos, false);
		if (datos.getEscEsp(pos) > 0) {
			datos.signalEscribir(pos);
		} else if (datos.getLecEsp(pos) > 0) {
			datos.signalLeer(pos);
		} else
			datos.signalMutex(pos);
	}

}
