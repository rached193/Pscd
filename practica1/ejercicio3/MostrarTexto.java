/**
  * Autor: Alejandro Solanas Bonilla
  * Nip: 647647
  * Fecha: 25 de Septiembre de 2014
  */

package practica1.ejercicio3;

import practica1.ejercicio3.Mensaje;

class MostrarTexto {	
	
	/**
	 * Muestra en pantalla 10 veces el mensaje "Soy A",
	 * 15 veces "Soy B" y 90 veces "Soy C".
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException{
		Mensaje A, B, C;
		Thread t1,t2,t3;
		
		A = new Mensaje("A",10);
		B = new Mensaje("B",15);
		C = new Mensaje("C", 9);
		
		t1 = new Thread(A);
		t2 = new Thread(B);
		t3 = new Thread(C);
		
		/*
		 * Establece la prioridad de los hilos, siendo
		 * el hilo con el menor valor el de mayor prioridad.
		 */
		
		t1.start();
		t2.start();
		t3.start();
	}
}
