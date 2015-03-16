package Tp6_grandragon;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorJefe extends Thread {

    private Almacen registro;

    public ServidorJefe(Almacen uRegistro) {
        registro = uRegistro;
    }

    @Override
    public void run() {
        int SERVER_PORT = 32005;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + SERVER_PORT);
            System.exit(1);
        }
        boolean funciona = true;

        while (funciona) {
            Socket clientSocket = null;
            Servidor server = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Accept failed.");
                System.exit(1);
            }
            server = new Servidor(registro,clientSocket);
            server.start();
        }
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Bye, bye�.");
    }
}
