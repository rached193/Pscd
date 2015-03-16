/**
  * Autor: Alejandro Solanas Bonilla
  * Nip: 647647
  * Fecha: 25 de Septiembre de 2014
  */
package practica1.ejercicio3;

/**
 * Gestiona Objetos de la clase Mensaje
 */

class Mensaje implements Runnable{
	/* Atributos */
	private String txt;
	private int veces;
	
	/**
	 * Crea un objeto de la clase Texto
	 * @param a = String a mostrar después de Soy
	 * @param n = Numero de veces a mostrar el mensaje.
	 */
	
	public Mensaje(String a, int n){
		this.txt = a;
		this.veces = n;
	}
	
	/**
	 * Muestra en pantalla el texto las veces indicadas
	 * por el parámetro "veces"
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		int i = 0;
		while(i<veces){
			i++;
			System.out.println("Soy "+txt);
		}
	}

}
