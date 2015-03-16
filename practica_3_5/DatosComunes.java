/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: DatosComunes .java
 * Fecha:   15/10/2014
 */

package practica_3_5;

import java.util.concurrent.Semaphore;

public class DatosComunes {

	private boolean[] escribiendo; // estado de las posiciones
	private int[] leyendo; // lectores activos en una posicion
	private int[] lecEsp; // lectores esperando en una posicion
	private int[] escEsp; // escritores esperando en una posicion
	private Semaphore[] mutex;
	private Semaphore[] lees;
	private Semaphore[] escribes;
	private final int total = 100;

	/**
	 * Constructor
	 */
	public DatosComunes() {

		escribiendo = new boolean[total];
		lecEsp = new int[total];
		escEsp = new int[total];
		leyendo = new int[total];
		mutex = new Semaphore[total];
		lees = new Semaphore[total];
		escribes = new Semaphore[total];
		

		for (int i = 0; i < total; i++) {
			escribiendo[i] = false;
			leyendo[i] = 0;
			lecEsp[i] = 0;
			escEsp[i] = 0;
			mutex[i] = new Semaphore(1);
			lees[i] = new Semaphore(0);
			escribes[i] = new Semaphore(0);
		}
	}

	/**
	 * Devuelve el estado de una posicion True -> escribiendo False -> libre
	 * 
	 * @param pos
	 * @return
	 */
	public boolean getEstado(int pos) {
		return escribiendo[pos];
	}

	/**
	 * Cambia el estado de una posicion
	 * 
	 * @param pos
	 * @param estado
	 */
	public void setEstado(int pos, boolean estado) {
		escribiendo[pos] = estado;
	}

	/**
	 * Aumenta los lectores esperando a leer en una posicion
	 * 
	 * @param pos
	 */
	public void LectorEspera(int pos) {
		lecEsp[pos]++;
	}

	/**
	 * Aumenta los escritores esperando a escribir en una posicion
	 * 
	 * @param pos
	 */
	public void EscritorEspera(int pos) {
		escEsp[pos]++;
	}

	/**
	 * Disminuye los lectores en espera en una posicion
	 * 
	 * @param pos
	 */
	public void LectorDeja(int pos) {
		lecEsp[pos]--;
	}

	/**
	 * Disminuye los escritores en espera en una posicion
	 * 
	 * @param pos
	 */
	public void EscritorDeja(int pos) {
		escEsp[pos]--;
	}

	/**
	 * Aumenta los lectores activos en una posicion
	 * 
	 * @param pos
	 */
	public void LectorLee(int pos) {
		leyendo[pos]++;
	}

	/**
	 * Disminuye los lectores activos de una posicion
	 * 
	 * @param pos
	 */
	public void LectorLibera(int pos) {
		leyendo[pos]--;
	}

	/**
	 * Devuelve los lectores esperando en una posicion
	 * 
	 * @param pos
	 * @return
	 */
	public int getLecEsp(int pos) {
		return lecEsp[pos];
	}

	/**
	 * Devuelve los escritores esperando en una posicion
	 * 
	 * @param pos
	 * @return
	 */
	public int getEscEsp(int pos) {
		return escEsp[pos];
	}

	/**
	 * devuelve los lectores activos en una posicion
	 * 
	 * @param pos
	 * @return
	 */
	public int getLeyendo(int pos) {
		return leyendo[pos];
	}

	/**
	 * Wait sobre el semaforo mutex[pos]
	 * 
	 * @param pos
	 */
	public void waitMutex(int pos) {
		try {
			mutex[pos].acquire();
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Signal sobre el semaforo mutex[pos]
	 * 
	 * @param pos
	 */
	public void signalMutex(int pos) {
		mutex[pos].release();
	}

	/**
	 * Wait sobre el semaforo lees[pos]
	 * 
	 * @param pos
	 */
	public void waitLeer(int pos) {
		try {
			lees[pos].acquire();
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Signal sobre el semaforo lees[pos]
	 * 
	 * @param pos
	 */
	public void signalLeer(int pos) {
		lees[pos].release();
	}

	/**
	 * Wait sobre el semaforo escribes[pos]
	 * 
	 * @param pos
	 */
	public void waitEscribir(int pos) {
		try {
			escribes[pos].acquire();
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Signal sobre el semaforo escribes[pos]
	 * 
	 * @param pos
	 */
	public void signalEscribir(int pos) {
		escribes[pos].release();
	}

}
