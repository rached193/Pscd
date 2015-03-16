/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Main.java
 * Fecha:   10/11/2014
 */

package practica_3_1;

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
	private static Semaphore mutex;
	private static Thread t[];
	private static Escritor escritores[];
	private static double media;
	private static double desviacion;
	private static long tiempoInicio, tiempoTotal;
	private static final int veces = 40;

	/**
	 * Metodo que realiza la insercion de los 100 elementos aleatorios a la base
	 * de datos, crea y lanza las tareas pra su modificion y posteriormente
	 * muestra el tiempo que ha trancurrido desde el inicio hasta el final de la
	 * modificacion.
	 */
	public static void main(String args[]) {

		// Insercion en la base de datos.
		int clave;
		for (int i = 1; i <= 100; i++) {
			clave = 1000 + i;
			base.insertRecord(clave, nombre + clave + "_0", apellidos + clave
					+ "_0", clave + "_0", direccion + clave + "_0");
		}

		mutex = new Semaphore(1);
		escritores = new Escritor[10];
		t = new Thread[10];

		for (int j = 0; j < veces; j++) {
			// Creacion de los escritores e hilos
			for (int i = 0; i < 10; i++) {
				escritores[i] = new Escritor(base, mutex);
				t[i] = new Thread(escritores[i]);
			}

			tiempoInicio = System.currentTimeMillis();
			// Lanzamiento de los hilos
			for (int i = 0; i < 10; i++) {
				t[i].start();
			}

			// Espera a la finalizacion de todos los hilos.
			for (int i = 0; i < 10; i++) {
				try {
					t[i].join();
				} catch (InterruptedException e) {
				}
			}
			tiempoTotal+= System.currentTimeMillis() - tiempoInicio;
			desviacion += Math.pow(System.currentTimeMillis() - tiempoInicio,2);

		}
		media = tiempoTotal / veces;
		desviacion = Math.sqrt((desviacion/veces)-Math.pow(media,2));
		System.out.printf("El tiempo medio de calculo es: %.2f\n ",media);
		System.out.printf("La desviacion tipica es: %.2f\n ",desviacion);
	}

}
