/*
 * File:     Server.java
 * Author:   PSCD-Unizar
 * Modified: Alejandro Solanas - 647647
 * Date:     04/11/14
 * Coms:     Programación de Sistemas Concurrentes y 
 *           Distribuidos Curso 2014-2015.
 *           Ejemplo de servidor con comunicaci�n por sockets
 */

package practica_5_1;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Servidor {

	public static void main(String[] args) {
		int SERVER_PORT = Integer.parseInt(args[0]);

		ServerSocket serverSocket = null; 
		Socket clientSocket = null;

		PrintWriter salHaciaCliente = null;
		BufferedReader entDesdeCliente = null;

		while(true){
		
		serverSocket = creaListenSocket(SERVER_PORT);
		clientSocket = creaClientSocket(serverSocket);



		try {
			salHaciaCliente = new PrintWriter(clientSocket.getOutputStream(),
					true);
			entDesdeCliente = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
		}

		// Contar vocales de frases enviadas por el cliente
		String inputLine = "";
		try {	
			inputLine = entDesdeCliente.readLine();

			while ((inputLine != null) && (!inputLine.equals("END"))) {
				// Calcular el n�mero de vocales que
				// tiene la respuesta.
				String respuesta = "'" + inputLine + "' has "
						+ +numeroDeVocales(inputLine) + " vowels";

				// Enviar la respuesta al cliente
				salHaciaCliente.println(respuesta);

				// Recibir nueva petici�n del cliente
				inputLine = entDesdeCliente.readLine();
			}
			// Al cerrar cualquier canal de comunicaci�n
			// usado por un socket, el socket se cierra.
			// Para asegurarse que se env�n las respuestas que
			// est� en el buffer cerramos el OutputStream.
			salHaciaCliente.close();

			// Cierra el servidor de sockets
			serverSocket.close();
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
		}

		}
	}

	// Crea un socket de servidor
	// Aborta programa si no lo logra
	private static ServerSocket creaListenSocket(int serverSockNum) {
		ServerSocket server = null;

		try {
			server = new ServerSocket(serverSockNum);
		} catch (IOException e) {
			System.err.println("Problems in port: " + serverSockNum);
			System.exit(-1);
		}
		return server;
	}

	// Establece conexi�n con server y devuelve socket
	// Aborta programa si no lo logra
	private static Socket creaClientSocket(ServerSocket server) {
		Socket res = null;

		try {
			res = server.accept();
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}
		return res;
	}

	// Devuelve num vocales de frase
	private static int numeroDeVocales(String frase) {
		int res = 0;
		String fraseMin = frase.toLowerCase();

		for (int i = 0; i < fraseMin.length(); ++i) {
			switch (fraseMin.charAt(i)) {
			case 'a':			case 'á':			case 'ü':
			case 'e':			case 'é':
			case 'i':			case 'í':
			case 'o':			case 'ó':
			case 'u':			case 'ú':
				res++;
				break;
			default:
				// ignoramos las dem�s letras
			}
		}
		return res;
	}
}
