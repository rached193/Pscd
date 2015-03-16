/*
 * File:     Cliente.java
 * Author:   PSCD-Unizar
 * Modified: Alejandro Solanas - 647647
 * Date:     04/11/14
 * Coms:     Programacion de Sistemas Concurrentes y 
 *           Distribuidos Curso 2014-2015.
 *           Ejemplo de cliente con comunicaci�n por sockets
 */

package practica_5_1;

import java.io.IOException;
import java.net.Socket; 
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cliente {

	// Para almacenar la direccion
	// y n�mero de puerto donde escucha el
	// proceso servidor
	static private String SERVER_ADDRESS;
	static private int SERVER_PORT;

	// Creacion del socket con el que se llevar� a cabo
	// la comunicación con el servidor.
	static private Socket socketAlServidor = null;


	public static void main(String[] args) {
		SERVER_ADDRESS=args[0];
		SERVER_PORT=Integer.parseInt(args[1]);
		
		boolean exito; //¿conectado?

		exito = conectarServidor(10); //10 intentos

		if(!exito){
			System.err.println("Don't know about host:"
					+ SERVER_ADDRESS);
			System.exit(1); //abortar si hay problemas
		}
		

		// Ya hay conexion
		// Inicializacion de los flujos de datos del socket
		// para la comunicacion con el servidor

		PrintWriter canalSalidaAlServidor = null;
		BufferedReader canalEntradaDelServidor = null;
		try {
			canalSalidaAlServidor = new PrintWriter(
					socketAlServidor.getOutputStream(),
					true
			);
			canalEntradaDelServidor = new BufferedReader(
					new InputStreamReader(
							socketAlServidor.getInputStream()
					)
			);
		} catch (IOException e) { //abortar si hay problemas
			System.err.println("I/O problem:" + SERVER_ADDRESS);
			System.exit(1);
		}

		// Definicion de un buffer de entrada para leer
		// de la entrada standard.
		BufferedReader entradaStandard = new BufferedReader(
				new InputStreamReader(System.in));
		String userInput = "";

		// Protocolo de comunicacion con el Servidor.
		// Mientras no se reciba la secuencia 
		// "END OF SERVICE"el servidor contar� el n�mero
		// de vocales que aparecen en las frases que le 
		// env�a el cliente. El cliente obtiene las frases 
		// que le pasa al servidordel usuario que lo
		// est� ejecutando.
		try{
			while (!(userInput.equals("END"))) {
				System.out.print("Text: ");
				userInput = entradaStandard.readLine();
				if (userInput != null) {
					canalSalidaAlServidor.println(userInput);
					String respuesta = canalEntradaDelServidor
							.readLine();
					if (respuesta != null) {
						System.out.println("Server answer: "
							+ respuesta);
					} else {
						System.out.println("Comm. is closed!");
					}
				} else {
					System.err.println("Wrong input!");
				}
			}

			// Al cerrar cualquiera de los canales de
			// comunicaci�n usados por un socket, el socket 
			// se cierra. Como no nos importa perder informaci�n 
			// cerramos el canal de entrada.
			canalEntradaDelServidor.close();

			// Cierre del Socket para comunicarse con el servidor.
			socketAlServidor.close();
		} catch (Exception e){
			System.err.println(e);
		}
	}

	static private boolean conectarServidor(int maxIntentos){
		//hasta maxIntentos intentos de conexion, para
		//darle tiempo al servidor a arrancar
		boolean exito = false; //¿no hay servidor?
		int van = 0;

		while((van<maxIntentos) && !exito){
			try {
				socketAlServidor = new Socket(
					SERVER_ADDRESS, SERVER_PORT);
				exito = true;
			} catch (Exception e) {
				van++;
				System.err.println("Failures:" + van);
				try { //esperar 1 seg
    				Thread.sleep(1000);
				} catch (InterruptedException e2) {
    				e2.printStackTrace();
				}
			}
		}
		return exito;
	}
}