package Tp6_old;


import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Servidor server = new Servidor();
		Thread thread = new Thread(server);
		thread.start();

		System.out
				.println("Indica el numero de clientes que se quiere ejecutar");
		System.out.println("(Un cliente versi�n interactiva)");

		Scanner entrada = new Scanner(System.in);
		int clientes = entrada.nextInt();
		if (clientes == 1) {
			Thread cliente = new Thread(new ClienteInteractivo());
			cliente.start();
		} else {
			Thread[] hilos;
			hilos = new Thread[clientes];
			for (int i = 0; i < clientes; i++) {
				hilos[i] = new Thread(new ClienteAutomatico());
				hilos[i].start();
			}

		}

	}

}
