package Tp6_old;
//Monitor del sistema, se encarga de que el estado del sistema as� como la cola
//de anuncios se accedan y actualicen en exclusi�n mutua.


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Colamensajes {
	private int max; //m�ximo de la cola
	private ArrayList<Imagen> ColaImagenes; //cola de anuncios
	private ArrayList<String> ColaTiempos;//cola de estimaciones
	private int total;// imagenes en cola
	private boolean alive; //con alive=true se sigue prestando servicio
	private int hilos;//n�mero de hilos ejecut�ndose en el sistema
	private Estado estado;//Frame con los datos sobre el estado del sistema

	public Colamensajes(int max, Estado estado) {
		this.estado = estado;
		this.max = max;
		this.ColaImagenes = new ArrayList<Imagen>();
		this.ColaTiempos = new ArrayList<String>();
		this.alive = true;
		this.hilos = 0;
		this.total = 0;
	}

	
	
	public synchronized boolean isAlive() {
		return this.alive;
	}
	
	

	public synchronized void kill() {
		//finaliza el servivicio, pone el flag a false y finaliza el servidor
		this.alive = false;
		Terminar fin = new Terminar();
		Thread killer = new Thread(fin);
		killer.start();

	}

	public synchronized Imagen CargarImagen() {
		//Espera a que haya anuncios disponibles y devuelve el primero de ellos
		//sac�ndolo de lacola
		while (this.total == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.total--;

		this.ColaTiempos.remove(0);
		estado.actualizar(ColaTiempos, hilos);
		return ColaImagenes.remove(0);

	}

	public synchronized void AddImagen(Imagen imagen) {
		//A�ade un anuncio a la cola
		this.ColaImagenes.add(imagen);
		this.total++;
		this.ColaTiempos.add(hora(estimar()));
		estado.actualizar(ColaTiempos, hilos);
		notifyAll();
	}

	public synchronized int TotalImagenes() {
		return this.total;
	}

	public synchronized int getMax() {
		return this.max;
	}

	public synchronized int estimar() {
		//devuelve una estimaci�n en ms de cu�nto tardar�a en publicarse un nuevo anuncio
		int estimacion = 0;
		for (int i = 0; i < this.total; i++) {
			estimacion = estimacion + ColaImagenes.get(i).getTiempo();
		}
		estimacion = estimacion / 4;
		return estimacion;
	}

	public synchronized void addHilo() {
		this.hilos++;
		estado.actualizar(ColaTiempos, hilos);
	}

	public synchronized void subHilo() {
		this.hilos--;
		notifyAll();
		estado.actualizar(ColaTiempos, hilos);
	}

	/*public void EsperarHilos() {
		while (this.hilos != 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/

	public String hora(int tiempo) {
		//Devuelve un string con la hoa que ser� dentro de tiempo segundos
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

		return String.format("%02d:%02d:%02d %n", hora, minuto, segundo);
	}

	public class Terminar implements Runnable {
		//finaliza el servidor, despert�ndolo de su espera y mand�ndole
		//un mensaje de finalizaci�n
		public void run() {

			// Varibales para almacenar la direcci�n
			// y n�mero de puerto donde escucha el
			// proceso servidor
			String SERVER_ADDRESS = "localhost";
			int SERVER_PORT = 32010;

			// Creaci�n del socket con el que se llevar� a cabo
			// la comunicaci�n con el servidor.
			Socket socketParaConectarseAlServidor = null;
			try {
				socketParaConectarseAlServidor = new Socket(SERVER_ADDRESS,
						SERVER_PORT);
			} catch (UnknownHostException e) {
				System.err.println("Don't know about host:" + SERVER_ADDRESS);
				//System.exit(1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}

			// Inicializaci�n de los flujos de datos del socket con los
			// que se lleva a cabo la comunicaci�n con el servidor

			PrintWriter canalSalidaDirigidoAlServidor = null;

			try {
				canalSalidaDirigidoAlServidor = new PrintWriter(
						socketParaConectarseAlServidor.getOutputStream(), true);

			} catch (IOException e) {
				System.err.println("Problemas I/O para " + "conexion "
						+ SERVER_ADDRESS);
				//System.exit(1);
			}

			String userInput = "";

			userInput = ("4");//mensaje de finalizaci�n

			canalSalidaDirigidoAlServidor.println(userInput);

			try {
				socketParaConectarseAlServidor.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}

	}

}
