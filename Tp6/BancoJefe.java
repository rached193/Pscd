/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: BancoJefe.java
 * Fecha: 08/01/2015
 * Descripción: Clase que gestiona el proceso banco y lanza nuevos hilos que
 * 				atienden las peticiones del servidor
 * 		
 */
package Tp6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BancoJefe extends Thread {

	private boolean debug;

	/**
	 * Ejecucion del Servidor banco que atiende peticiones y lanza nuevos hilos
	 * para realizar la comunicacion
	 */
	public BancoJefe(boolean test) {
		debug = test;
	}

	public void run() {
		if (debug) {
			System.out.println("BancoJefe: Iniciar Servidor");
		}
		int SERVER_PORT = 32010;
		ServerSocket bancoSocket = null;
		try {
			bancoSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			if (debug) {
				System.out.println("Could not listen on port: " + SERVER_PORT);
			}
			System.exit(1);
		}
		boolean funciona = true;

		while (funciona) { // Hasta Fin de Servicio
			Socket clientSocket = null;
			Banco server = null;
			try {
				clientSocket = bancoSocket.accept();
				if (debug) {
					System.out.println("ServidorJefe: Conexion Aceptada");
				}
			} catch (IOException e) {
				if (debug) {
					System.out.println("ServidorJefe: Fallo de Conexion");
				}
				System.exit(0);
			}
			server = new Banco(clientSocket, debug);
			server.start();
		}
		try {
			if (debug) {
				System.out.println("BancorJefe: ya no acepta mas peticiones");
			}
			bancoSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		if (debug) {
			System.out.println("BancoJefe: Cerrando Server");
		}
	}
}
