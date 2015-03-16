/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Main.java
 * Fecha:   06/12/2014
 */

package practica_5_3;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * Servidor que recibe las peticiones de los clientes y lanza hilos individuales
 * para procesar cada uno de ellos
 * 
 * @author naxsel
 *
 */
public class Servidor implements Runnable {

	private int SERVER_PORT;
	private ServerSocket server;
	private Vagon vagon;

	public Servidor(int port) {
		SERVER_PORT = port;
		vagon = new Vagon(10, 4);
		try {
			server = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			System.err.println("Problemas en el puerto: " + SERVER_PORT);
		}
	}

	public void run() {
		Socket clientSocket = null;
		while (true) {
			try {
				clientSocket = server.accept();
				Threads cliente = new Threads(clientSocket, vagon);
				Thread t = new Thread(cliente);
				t.start();
			} catch (IOException e) {
				System.err.println("Accept failed");
				System.exit(1);
			}
		}
	}
}