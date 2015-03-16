/*
 * Autor:   Alejandro Solanas Bonilla
 * NIA:     647647
 * Fichero: Main.java
 * Fecha:   06/12/2014
 */

package practica_5_3;

/**
 * Monitor del vagon
 * 
 * @author naxsel
 *
 */
public class Vagon {

	private boolean ocupados[][];
	private int libres;
	private final boolean debug = true;

	/**
	 * Constructor del Monitor
	 * 
	 * @param filas
	 * @param columnas
	 */
	public Vagon(int filas, int columnas) {
		libres = filas * columnas;
		ocupados = new boolean[filas][columnas];
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				ocupados[i][j] = false;
			}
		}
	}

	/**
	 * Operacion Reservar asiento
	 * 
	 * @param fila
	 * @param columna
	 * @return
	 */
	public synchronized String Reservar(int fila, int columna) {
		if (libres == 0) {
			System.out.println("No quedan asientos");
			return "VAGON LLENO";
		} else if (ocupados[fila][columna]) {
			if (debug) {
				System.out.printf("Lista de asientos libres restantes: %d \n",
						libres);
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 4; j++) {
						if (ocupados[i][j] == false) {
							System.out.printf(
									"El asiento[%d][%d] sigue vacio\n", i + 1,
									j + 1);
						}
					}
				}
			}
			return "OCUPADO";
		} else {
			ocupados[fila][columna] = true;
			System.out.println(libres);
			libres--;
			return "RESERVADO";
		}
	}
}
