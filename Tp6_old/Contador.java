package Tp6_old;
//hilo que ejecuta un hilo de comunicaci�n y se mantiene a la espera para finalizarlo
//si este lleva demasiado tiempo ejecut�ndose


import java.net.Socket;

public class Contador implements Runnable {
	private Socket clientSocket;
	private Colamensajes mensajes;

	public Contador(Socket clientSocket, Colamensajes mensajes) {
		this.clientSocket = clientSocket;
		this.mensajes = mensajes;

	}

	public void run() {
		Comunicacion comunicacion = new Comunicacion(clientSocket, mensajes);
		Thread hilo = new Thread(comunicacion);
		hilo.start();

		try {
			
			synchronized (hilo) {
				hilo.wait(40000);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		if (hilo.isAlive()) {
			comunicacion.safeStop();
		}
		
		
	}

}
