package Tp6_old;


import java.io.*;
import java.net.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.util.*; //scaner random calendar

public class ClienteAutomatico implements Runnable {

	// M�todo que escribe la hora de publicacion de la foto
	public static void hora(int tiempo) {
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
		//Direccion y puerto del proceso servidor
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

		String userInput = "";

		// Interaccion con el usuario para la seleccion de foto
		// asi como el tiempo en el que se mostrara.
		try {
			Random Azar = new Random();
			int p = Azar.nextInt(2000);
			p = p % 4;
			String nombre = null;
			switch (p) {
			case 0:
				nombre = "catsEye";
				break;
			case 1:
				nombre = "M42";
				break;
			case 2:
				nombre = "pilaresCreacion";
				break;
			case 3:
				nombre = "sombrero";
			}
			int tiempo = Azar.nextInt(30000) + 10000;
			// Definici�n de la variable tama�o imagen 	
			BufferedImage foto = ImageIO.read(new File(nombre + ".jpg"));
			ByteArrayOutputStream salidaImagen = new ByteArrayOutputStream();
			ImageIO.write(foto, "jpg", salidaImagen);
			byte[] bytesImagen = salidaImagen.toByteArray();
			int tamano = (int) bytesImagen.length;

			// Protocolo de comunicaci�n con el Servidor.
			// Envio de solicitud, recepcion confirmacion y datos.

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
				//Llamada al m�todo hora
				hora(h);
				int d = Azar.nextInt(20);
				if (d != 19) {
					String visa = "";
					for (int i = 0; i <= 16; i++) {
						int n = Azar.nextInt(10);
						visa = visa + n;
					}
					System.out.println(visa);
					canalSalidaDirigidoAlServidor.println(visa);
					//Env�o de la imagen
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
			if (r == 2) {//Peticion rechazada
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
		} catch (java.io.FileNotFoundException e) {
			System.out.println("No se ha introducido correctamente el fichero");
		} catch (IOException excepcionES) {
			excepcionES.printStackTrace();
		}catch(java.lang.NullPointerException e){
			
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