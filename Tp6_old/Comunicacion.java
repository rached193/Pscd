package Tp6_old;
//Hilo encargado de la comunicaci�n del servidor con un cliente


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Comunicacion implements Runnable {
	private Socket clientSocket;
	private Colamensajes mensajes;// monitor

	public Comunicacion(Socket clientSocket, Colamensajes mensajes) {
		this.clientSocket = clientSocket;
		this.mensajes = mensajes;

	}

	public void run() {

		PrintWriter salidaHaciaElCliente;
		mensajes.addHilo();// actualizo el n�mero de clientes conectados
		try {
			salidaHaciaElCliente = new PrintWriter(
					clientSocket.getOutputStream(), true);
			BufferedReader entradaDesdeElCliente = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));

			String inputLine = "";

			inputLine = entradaDesdeElCliente.readLine();// recibo primer
															// mensaje

			int op, tamanyo;
			int tiempo = 0;
			boolean terminar = false;
			Scanner sc = new Scanner(inputLine);
			// parseo la petici�n
			op = sc.nextInt();
			
			if (op == 4) {
				terminar = true;// mensaje de finalizaci�n
			} else {

				if (op == 1) {
					if (mensajes.TotalImagenes() < mensajes.getMax()) {
						try {
							tamanyo = sc.nextInt();
							tiempo = sc.nextInt();
							int precio = tiempo * 3;
							int estimada = mensajes.estimar();
							estimada = 45;
							salidaHaciaElCliente.println("3 " + precio + " "
									+ estimada);
						}
						// si ejecuta un catch, mensaje en formato err�neo
						catch (InputMismatchException e) {
							salidaHaciaElCliente.println("2 2");
							terminar = true;
						} catch (NoSuchElementException e) {
							salidaHaciaElCliente.println("2 2");
							terminar = true;
						}

					} else {//demasiadas im�genes en cola
						salidaHaciaElCliente.println("2 1");
						terminar = true;
					}

				} else {
					// mensaje de formato err�neo
					salidaHaciaElCliente.println("2 2");
					terminar = true;
				}
				sc.close();
				if (!terminar) {//lectura de la visa o denegaci�n por parte del cliente
					inputLine = entradaDesdeElCliente.readLine();

					String visa = inputLine;
					if (visa == "2") {//el cliente no acepta el servicio
						terminar = true;
					} else {
						//lectura de la imagen y cobro del servicio
						BufferedImage bufferedImage = ImageIO.read(clientSocket
								.getInputStream());

						boolean cobrobien = true;
						cobrobien = cobrar(visa);
						if (cobrobien) {

							try {
								ImageIcon icon = new ImageIcon(bufferedImage);
								Imagen imagen = new Imagen(icon, tiempo);
								salidaHaciaElCliente.println("3");
								mensajes.AddImagen(imagen);

							} catch (java.lang.NullPointerException e) {
								salidaHaciaElCliente.println("2");//informo al cliente de fallo 
							}

						} else {
							salidaHaciaElCliente.println("2");//informo al cliente de fallo
						}
					}
				} else {
					terminar = true;
					salidaHaciaElCliente.println("2");//informo al cliente de fallo
				}

			}

			// }

			salidaHaciaElCliente.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		mensajes.subHilo();//actualizo n�mero de clientes conectados
		synchronized (this) {//informo al padre de que termino
			this.notifyAll();
		}

	}

	public boolean cobrar(String visa) {
		//simula el cobro haciendo que fallen 3 de cada 50 mensajes
		boolean cobro = true;
		int prob;
		Random aleatorio = new Random();
		prob = aleatorio.nextInt(50);
		if (prob <= 2) {
			cobro = false;
		}

		return cobro;

	}

	public void safeStop() {
		//proceso que cierra el hilo, provocando una excepci�n al cerrarle la conexi�n, 
		//invocado por el padre
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

}
