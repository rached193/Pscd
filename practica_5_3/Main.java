/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Main.java
 * Fecha:   06/12/2014
 */

package practica_5_3;

public class Main {

	private static final String SERVER_ADDRESS = "localHost";
	private static final int PORT = 32000;
	private static final int users = 10;

	/**
	 * Simulacion del llenado del vagon 50 clientes intentan reservar un asiento
	 * hasta que se llenan los 40 asientos y termina el servicio
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		Servidor server = new Servidor(PORT);
		Cliente[] clientes = new Cliente[users];
		Thread t[] = new Thread[users + 1];
		t[0] = new Thread(server);

		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new Cliente(SERVER_ADDRESS, PORT);
			t[i + 1] = new Thread(clientes[i]);
		}

		for (int i = 0; i < t.length; i++) {
			t[i].start();
		}
	}
}
