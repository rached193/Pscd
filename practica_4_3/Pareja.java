/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Pareja.java
 * Fecha:   24/11/2014
 */

package practica_4_3;

import java.util.Random;
/**
 * La clase gestiona la pareja
 * @author Alex
 *
 */
public class Pareja {

	private int materiales, jugando, idPareja;
	private Jugador jugadorUno, jugadorDos;
	
	/**
	 * Constructor de la clase pareja
	 * @param nJugadorUno
	 * @param nJugadorDos
	 * @param nId
	 */
	public Pareja(Jugador nJugadorUno, Jugador nJugadorDos, int nId){
		jugadorUno = nJugadorUno;
		jugadorDos = nJugadorDos;
		materiales = 0;
		jugando = 0;
		idPareja = nId;
	}
	
	/**
	 * Garantiza que la pareja ha reservado materiales
	 */
	public synchronized void reservarMateriales(){
		materiales++;
		while(materiales!=2){
			try {
				wait();
			} catch (InterruptedException e) {}
			System.out.println("La pareja "+idPareja+"-Juega");
		}
		notifyAll();
	}
	
	/**
	 * Garantiza que ambos jugadores han jugado
	 */
	public synchronized void jugar(){
		jugando++;
		while(jugando!=2){
			System.out.println("La pareja "+idPareja+"-Deja de jugar");
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		notifyAll();
	}
	
	/**
	 * Comprueba que ambos jugadores han dejado los materiales
	 */
	public synchronized void dejarMateriales(){
		materiales--;
		while(materiales!=0){
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		notifyAll();
	}
	
	/**
	 * Devuelve el jugador uno de la pareja
	 * @return
	 */
	public synchronized Jugador jugadorUno(){
		return jugadorUno;
	}
	
	/**
	 * Devuelve el jugador dos de la pareja
	 * @return
	 */
	public synchronized Jugador jugadorDos(){
		return jugadorDos;
	}
	
	/**
	 * Devuelve un numero aleatorio de pelotas[1-5]
	 * @return
	 */
	public synchronized int decidirPelotas(){
		Random aleatorio = new Random();
		return aleatorio.nextInt(5)+1;
	}
	
	/**
	 * Devuelve el id de la pareja
	 * @return
	 */
	public synchronized int numPareja(){
		return idPareja;
	}
}
