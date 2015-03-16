/**
  * Autor: Alejandro Solanas Bonilla
  * Nip: 647647
  * Fecha: 25 de Septiembre de 2014
  */

package practica1.ejercicio5;

class MostrarTexto {	
	
	/**
	 * Muestra en pantalla n veces los textos señalados.
	 * @param args
	 */
	public static void main(String[] args){
		Mensaje A, B, C;
		Thread t1,t2,t3;
		
		A = new Mensaje("A",10,1);
		B = new Mensaje("B",15,2);
		C = new Mensaje("C", 9,1);
		
		t1 = new Thread(A);
		t2 = new Thread(B);
		t3 = new Thread(C);
		
		t1.start();
		t2.start();
		t3.start();
	}
}
