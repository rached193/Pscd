/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Cliente.java
 * Fecha:   06/12/2014
 */

package practica_5_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * Clase cliente, que realiza una conexion a un servicio para reservar un
 * asiento en un vagon de 10x4 asientos
 * 
 * @author naxsel
 *
 */
public class Cliente implements Runnable {

	private String SERVER_ADDRESS;
	private int PORT;
	private Socket socket;
	
	/**
	 * Constructor del cliente
	 * @param address
	 * @param port
	 */
	public Cliente(String address, int port) {
		SERVER_ADDRESS = address;
		PORT = port;
		try {
			socket = new Socket(SERVER_ADDRESS, PORT);
		} catch (UnknownHostException e) {
			System.err.println("Error en el Host");
		} catch (IOException e) {}
	}

	/**
	 * Lanzador del hilo	
	 */
	public void run() {

		Random aleatorio = new Random();
		boolean exito = false;
		int fila, columna;
		String respuesta;
		PrintWriter Salida;
		BufferedReader Entrada;

		try {
			Salida = new PrintWriter(socket.getOutputStream(), true);
			Entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
			//Hasta que no esta lleno o consigue reservar realiza peticiones
			while (!exito) {

				fila = aleatorio.nextInt(10);
				columna = aleatorio.nextInt(4);

				Salida.println(fila + " " + columna);
				respuesta = Entrada.readLine();

				if (respuesta.equals("RESERVADO")) {
					exito = true;
					System.out.printf("El asiento[%d][%d] se ha reservado\n",
							fila + 1, columna + 1);
				} else if (respuesta.equals("OCUPADO")) {
					System.out.println("Try again");

				} else if (respuesta.equals("VAGON LLENO")) {
					System.out.println("Llegas tarde, todo lleno");
					exito = true;
				} else {
					System.out.println("Aron dice: MIAU MIAU MIAU");
				}
			}
			Entrada.close();
			socket.close();

		} catch (IOException e) {
			System.err.println("Problema I/P server");
		}
	}
}