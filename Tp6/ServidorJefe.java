/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: Almacen.java
 * Fecha: 9/01/2015
 * Descripción: Clase que gestiona un almacen para imagenes que se publicaran en
 * 				una de las vallas en ejecucion, listas y metodos individuales
 * 				para cada una de las vallas			
 */

package Tp6;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorJefe extends Thread {

    private Almacen registro;
    private int BANK_PORT;
    private String BANK_ADDRESS;
    private Estado estado;
    private boolean debug;
    
    /**
     * Constructor
     * @param uEstado
     * @param uRegistro
     * @param address
     * @param port
     * @param test
     */
    public ServidorJefe(Estado uEstado, Almacen uRegistro, String address,
            int port, boolean test) {
        registro = uRegistro;
        BANK_PORT = port;
        BANK_ADDRESS = address;
        estado = uEstado;
        debug = test;
    }

    @Override
    public void run() {
        if (debug) {
            System.out.println("ServidorJefe: Iniciando Servidor");
        }
        int SERVER_PORT = 32005;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            if (debug) {
                System.out.println("Could not listen on port: " + SERVER_PORT);
            }
            System.exit(1);
        }
        if (debug) {
            System.out.println("ServidorJefe: Atendiendo en el puerto: " + SERVER_PORT);
        }
        estado.addMonitor();
        while (estado.funciona()) {
            Socket clientSocket = null;
            Servidor server = null;
            try {
                if (debug) {
                    System.out.println("ServidorJefe: Conexion Aceptada");
                }
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                if (debug) {
                    System.out.println("ServidorJefe: Fallo de Conexion");
                }
                System.exit(1);
            }
            server = new Servidor(registro, clientSocket, BANK_ADDRESS, BANK_PORT, debug);
            server.start();
        }
        if (debug) {
            System.out.println("ServidorJefe: ya no acepta mas peticiones");
        }
        estado.esperarTerminar();

        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        if (debug) {
            System.out.println("Cerrando Sistema");
        }
        System.exit(-1);
    }
}
