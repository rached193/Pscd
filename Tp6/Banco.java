/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: Banco.java
 * Fecha: 08/01/2015
 * Descripción: Hilo que gestiona la conversacion del banco con las peticiones 
 * del servidor 		
 */
package Tp6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Banco extends Thread {

    private Socket clientSocket;
    private boolean debug;

    /**
     * Constructor del banco
     *
     * @param clientSocket
     */
    public Banco(Socket clientSocket, boolean test) {
        this.clientSocket = clientSocket;
        debug = test;
    }

    /**
     * Ejecucion del metodo
     */
    public void run() {
        PrintWriter salidaHaciaElServidor = null;
        try {
            salidaHaciaElServidor = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader entradaDesdeElCliente = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String msg, visa;
            int precio;
            msg = entradaDesdeElCliente.readLine();
            if (msg.equals("VISA")) {
                if (debug) {
                        System.out.println("Banco: Peticion Cobro");
                    }
                try {
                    visa = entradaDesdeElCliente.readLine();
                    precio = Integer.parseInt(entradaDesdeElCliente.readLine());
                } catch (NumberFormatException e) {
                    salidaHaciaElServidor.println("MENSAJE INCORRECTO");
                    if (debug) {
                        System.out.println("Banco: Mensaje Incorrecto");
                    }
                    clientSocket.close();
                    System.exit(0);
                }
                Random Azar = new Random();
                int comp = Azar.nextInt(20);
                if (comp == 0) {
                    salidaHaciaElServidor.println("SIN FONDOS");
                    if (debug) {
                        System.out.println("Banco: Cliente Sin Fondos");
                    }
                } else {
                    salidaHaciaElServidor.println("PAGO CORRECTO");
                    if (debug) {
                        System.out.println("Banco: Trasaccion Correcta");
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }
}
