/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Main.java
 * Fecha:   15/10/2014
 */

package practica_3_5;

import java.util.concurrent.Semaphore;

/**
 * Clase que gestiona la insercion de 100 elementos aleatorios en la base de
 * datos, y crea las tareas para la modificacion de la misma
 */
public class Main {

	private static final String nombre = "Nombre_";
	private static final String apellidos = "Apellidos_";
	private static final String direccion = "C/ Maria de Luna ";
	private static BaseDeDatos base = new BaseDeDatos();
	private static DatosComunes datos = new DatosComunes();
	private static Escritor escritores[];
	private static Lector lectores[];
	private static double media;
	private static double desviacion;
	private static long tiempoInicio, tiempoTotal;
	private static final int veces = 40;
	private static final int elementos = 5;

	/**
	 * Metodo que realiza la insercion de los 100 elementos aleatorios a la base
	 * de datos, crea y lanza las tareas pra su modificion y posteriormente
	 * muestra el tiempo que ha trancurrido desde el inicio hasta el final de la
	 * modificacion.
	 */
	public static void main(String args[]) {

		// Insercion en la base de datos.
		int clave;
		Thread[] writer = new Thread[elementos];
		Thread[] reader = new Thread[elementos];
		for (int i = 1; i <= 100; i++) {
			clave = 1000 + i;
			base.insertRecord(clave, nombre + clave + "_0", apellidos + clave
					+ "_0", clave + "_0", direccion + clave + "_0");
		}

		escritores = new Escritor[10];
		lectores = new Lector[10];

		for (int j = 0; j < veces; j++) {
			// Creacion de los escritores, lectores e hilos
			for (int i = 0; i < elementos; i++) {
				escritores[i] = new Escritor(base, datos);
				lectores[i] = new Lector(base, datos);
				writer[i] = new Thread(escritores[i]);
				reader[i] = new Thread(lectores[i]);
			}

			tiempoInicio = System.currentTimeMillis();
			// Lanzamiento de los hilos
			for (int i = 0; i < 5; i++) {
				writer[i].start();
				reader[i].start();
			}

			// Espera a la finalizacion de todos los hilos.
			for (int i = 0; i < 5; i++) {
				try {
					writer[i].join();
					reader[i].join();
				} catch (InterruptedException e) {
				}
			}
			tiempoTotal += System.currentTimeMillis() - tiempoInicio;
			desviacion += Math
					.pow(System.currentTimeMillis() - tiempoInicio, 2);

		}

		media = tiempoTotal / veces;
		desviacion = Math.sqrt((desviacion / veces) - Math.pow(media, 2));
		System.out.printf("El tiempo medio de calculo es: %.2f\n ", media);
		System.out.printf("La desviacion tipica es: %.2f\n ", desviacion);
	}

}
