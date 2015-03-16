/**
  * Autor: Alejandro Solanas Bonilla
  * Nip: 647647
  * Fecha: 25 de Septiembre de 2014
  */

/**
  * Gestiona Objetos de la clase Mensaje
  */

package practica1.ejercicio5;

class Mensaje implements Runnable{
	/* Atributos */
	private String txt;
	private int veces;
	private int tiempo;
	
	/**
	 * Crea un objeto de la clase Texto
	 * @param a = String a mostrar despu�s de Soy
	 * @param n = Numero de veces a mostrar el mensaje.
	 * @param j = tiempo de espera.
	 */
	
	public Mensaje (String a, int n, int j){
		txt = a;
		veces = n;
		tiempo = j;
	}
	
	/**
	 * Muestra en pantalla el texto las veces necesarias
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		int i = 0;
		while(i<veces){
			try{
				Thread.sleep(tiempo);
			}
			catch(InterruptedException e){
	            ;
	        }
			i = i + 1;
			System.out.println("Soy "+txt);
		}
	}

}
