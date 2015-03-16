/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Threads.java
 * Fecha:   06/12/2014
 */

package practica_5_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Hilo que se encarga de procesar la peticion recibida por el servidor
 * 
 * @author Alex
 *
 */
public class Threads implements Runnable {

	private Socket cliente;
	/**
	 * Constructor que recibe el socket del cliente
	 * @param cliente
	 */
	public Threads(Socket cliente) {
		this.cliente = cliente;
	}
	/**
	 * Metodo devuelve el número de vocales que contiene el texto introducido
	 * @param frase
	 * @return
	 */
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
				// ignoramos las demas letras
			}
		}
		return res;
	}

	/**
	 * Lanzador del hilo
	 */
	public void run() {
		try {
			PrintWriter salHaciaCliente = new PrintWriter(
					cliente.getOutputStream(), true);
			BufferedReader entDesdeCliente = new BufferedReader(
					new InputStreamReader(cliente.getInputStream()));

			String inputLine = "";
			inputLine = entDesdeCliente.readLine();

			while ((inputLine != null) && (!inputLine.equals("END OF SERVICE"))) {
				// Calcular el n�mero de vocales que
				// tiene la respuesta.
				String respuesta = "'" + inputLine + "' has "
						+ +numeroDeVocales(inputLine) + " vowels";

				// Enviar la respuesta al cliente
				salHaciaCliente.println(respuesta);

				// Recibir nueva petici�n del cliente
				inputLine = entDesdeCliente.readLine();
			}
			salHaciaCliente.close();
			cliente.close();

		} catch (IOException e) {
		}
	}

}
