package Tp6_old;
//Crea las dos ventanas y ejecuta el bucle de escucha del servidor




import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.JFrame;

public class Servidor implements Runnable {
	private Colamensajes mensajes;
	private final int max = 10;
	private Valla valla;
	private Estado estado;

	public Servidor() {
//Creaci�n de la ventana de estado
		JFrame frame = new JFrame("Estado");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		estado = new Estado();
		Component contents = estado.createComponents();
		frame.getContentPane().add(contents, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(900, 150);
		frame.setVisible(true);
//
		this.mensajes = new Colamensajes(max, estado);//creaci�n del monitor
		estado.addMonitor(mensajes);
		this.valla = new Valla(this.mensajes);//creaci�n de la valla publicitaria
		new Thread(this.valla).start();
	}

	public void run() {

		int SERVER_PORT = 32010;

		// Inicializar el socket donde escucha el servidor en
		// local y en el puerto SERVER_PORT

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			System.err.println("Problemas en puertoA: " + SERVER_PORT);
			System.exit(1);
		}
		ArrayList<Thread> hilos = new ArrayList<Thread>();
		int i = 0;
		while (this.mensajes.isAlive()) {//mientras deba prestar servicio

			// Inicializar el socket del cliente con el que se va a
			// comunicar el servidor, es decir se acepta la
			// conexi�n de un cliente al servidor mediante
			// el m�todo accept()

			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			//Invoco al hilo que crear� el hilo de comunicaci�n
			Contador contador = new Contador(clientSocket, mensajes);
			
			hilos.add(new Thread(contador));//lista de hilos para esperar a la finalizaci�n de todos
			i = hilos.size();
			hilos.get(i - 1).start();

		}
		for (int j = 0; j < hilos.size(); j++) {//Espero a que todos los hilos iniciados finalicen
			try {
				hilos.get(j).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}