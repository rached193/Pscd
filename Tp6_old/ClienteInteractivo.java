package Tp6_old;


import java.io.*;
import java.net.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*; //scaner random calendar

public class ClienteInteractivo implements Runnable {

	private Scanner entrada;

	// M�todo que escribe la hora de publicacion de la foto
	public void hora(int tiempo) {
		tiempo = tiempo / 1000;
		Calendar fecha = new GregorianCalendar();
		int hora = fecha.get(Calendar.HOUR_OF_DAY);
		int minuto = fecha.get(Calendar.MINUTE);
		int segundo = fecha.get(Calendar.SECOND);
		segundo += tiempo;
		if (segundo >= 60) {
			segundo = segundo % 60;
			minuto += 1;
			if (minuto >= 60) {
				minuto = minuto % 60;
				hora += 1;
				if (hora >= 24) {
					hora = hora % 24;
				}
			}
		}
		System.out.printf("La imagen se mostrara a las: %02d:%02d:%02d %n",
				hora, minuto, segundo);
	}

	public void run() {
		// Direccion y puerto del proceso servidor
		String SERVER_ADDRESS = "localhost";
		int SERVER_PORT = 32010;

		// Creaci�n del socket con el que se llevar� a cabo
		// la comunicaci�n con el servidor.
		Socket socket = null;
		try {
			socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:" + SERVER_ADDRESS);
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Inicializaci�n de los flujos de datos del socket con los
		// que se lleva a cabo la comunicaci�n con el servidor

		PrintWriter canalSalidaDirigidoAlServidor = null;
		BufferedReader canalEntradaQueVieneDelServidor = null;

		try {
			canalSalidaDirigidoAlServidor = new PrintWriter(
					socket.getOutputStream(), true);
			canalEntradaQueVieneDelServidor = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

		} catch (IOException e) {
			System.err.println("Problemas I/O para " + "conexion "
					+ SERVER_ADDRESS);
			System.exit(1);
		}
		// Definicion de variables necesarias
		String userInput = "";
		boolean abierto = true;
		BufferedImage foto = null;
		int tiempo = 0;

		// Interaccion con el usuario para la seleccion de foto
		// asi como el tiempo en el que se mostrara.

		System.out
				.println("Introduzca el nombre de la foto que quiere enviar:");
		String nombre = null;
		entrada = new Scanner(System.in);
		nombre = entrada.nextLine();

		try {
			foto = ImageIO.read(new File(nombre + ".jpg"));
		} catch (java.io.FileNotFoundException e) {
			System.out.println("No se ha introducido correctamente el fichero");
			abierto = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (!abierto) {
			entrada = new Scanner(System.in);
			nombre = entrada.nextLine();

			try {
				foto = ImageIO.read(new File(nombre + ".jpg"));
				abierto = true;
			} catch (java.io.FileNotFoundException e) {
				System.out
						.println("No se ha introducido correctamente el fichero");
				abierto = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ByteArrayOutputStream salidaImagen = new ByteArrayOutputStream();
		try {
			ImageIO.write(foto, "jpg", salidaImagen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Definici�n de la variable tama�o imagen
		byte[] bytesImagen = salidaImagen.toByteArray();
		int tamano = (int) bytesImagen.length;
		System.out
				.println("Escriba el tiempo que va a perdurar la imagen (ms)");
		entrada = new Scanner(System.in);
		tiempo = entrada.nextInt();

		// Protocolo de comunicaci�n con el Servidor.
		// Envio de solicitud, recepcion confirmacion y datos.

		try {
			String solicitud = ("1 " + tamano + " " + tiempo);
			System.out.println(solicitud);
			String respuesta = null;
			userInput = solicitud;
			canalSalidaDirigidoAlServidor.println(userInput);
			respuesta = canalEntradaQueVieneDelServidor.readLine();
			@SuppressWarnings("resource")
			Scanner resp = new Scanner(respuesta);
			int r = resp.nextInt();
			if (r == 3) {// Peticion aceptada
				System.out.println("Peticion procesada correctamente");
				int precio = resp.nextInt();
				System.out.printf("la imagen costara %d euros", precio);
				//
				int h = resp.nextInt();
				System.out.println();
				// Llamada al m�todo hora
				hora(h);
				System.out.println("�Est� de acuerdo con el precio?");
				System.out.println("S/N");
				entrada = new Scanner(System.in);
				nombre = entrada.nextLine();
				System.out.println(nombre);
				if (nombre.equals("S")) {// Pago aceptado
					String visa = "";
					System.out.println("Escriba el numero de visa");
					entrada = new Scanner(System.in);
					visa = entrada.nextLine();
					entrada.close();
					System.out.println(visa);
					canalSalidaDirigidoAlServidor.println(visa);
					// Env�o de la imagen
					ImageIO.write(foto, "jpg", socket.getOutputStream());
					socket.getOutputStream().flush();
					System.out.println("Se ha enviado la imagen");
				} else {
					System.out
							.println("Ha decidido no proceder con el pago, cerrando conexi�n");
					canalSalidaDirigidoAlServidor.println("2"); // No se ha
																// aceptado el
																// pago;
				}
			}

			if (r == 2) {
				r = resp.nextInt();
				if (r == 1) {
					System.out
							.println("Cola llena, procediendo a finalizar comunicacion");
				}
				if (r == 2) {
					System.out
							.println("Error al entender el mensaje, procediendo a finalizar comunicacion");
				}
			}
		} catch (IOException excepcionES) {
			excepcionES.printStackTrace();
		} catch (java.lang.NullPointerException e) {

		}

		try {
			canalEntradaQueVieneDelServidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Cierre del Socket para comunicarse con el servidor.
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}