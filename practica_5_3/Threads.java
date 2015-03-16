/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Threads.java
 * Fecha:   06/12/2014
 */

package practica_5_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Hilo que se encarga de procesar la peticion recibida por el servidor
 * 
 * @author Alex
 *
 */
public class Threads implements Runnable {

	private Socket cliente;
	private Vagon vagon;
	
	/**
	 * Constructor 
	 * @param cliente
	 * @param vagon
	 */
	public Threads(Socket cliente, Vagon vagon) {
		this.cliente = cliente;
		this.vagon = vagon;
	}

	/**
	 * Lanzador del Hilo
	 */
	public void run() {
		try {
			PrintWriter salHaciaCliente = new PrintWriter(
					cliente.getOutputStream(), true);
			BufferedReader entDesdeCliente = new BufferedReader(
					new InputStreamReader(cliente.getInputStream()));
			
			String inputLine = "";
			Scanner scan;
			int fila, columna;
			boolean exito = false;
			
			//Hasta que no esta lleno o consigue reservar, realiza peticiones
			while (!exito) {

				inputLine = entDesdeCliente.readLine();

				scan = new Scanner(inputLine);
				fila = scan.nextInt();
				columna = scan.nextInt();

				String respuesta = vagon.Reservar(fila, columna);

				if (respuesta.equals("RESERVADO")) {
					salHaciaCliente.println(respuesta);
					exito = true;
				} else if (respuesta.equals("OCUPADO")) {
					salHaciaCliente.println(respuesta);
				} else {
					salHaciaCliente.println(respuesta);
					exito = true;
				} 
			}
			salHaciaCliente.close();

		} catch (IOException e) {
		}
	}

}
