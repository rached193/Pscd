/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Servidor.java
 * Fecha:   06/12/2014
 */

package practica_5_2;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * Clase Servidor, que recibe las peticiones de uno o varios clientes, y lanza
 * un nuevo hilo de la clase Threads que gestiona la petición de forma
 * individual
 * 
 * @author naxsel
 *
 */
public class Servidor {
	/**
	 * Main que lanza el Servidor asociado a un puerto
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int SERVER_PORT = 32005;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			System.err.println("Problemas en el puerto: " + SERVER_PORT);
			System.exit(1);
		}

		Socket clientSocket = null;
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				Threads cliente = new Threads(clientSocket);
				Thread t = new Thread(cliente);
				t.start();
			} catch (IOException e) {
				System.err.println("Accept failed");
				System.exit(1);
			}
		}
	}
}
