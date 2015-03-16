package Tp6_grandragon;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public class ClienteRunnable extends Thread {

    private int id;

    public ClienteRunnable(int uid) {
        id = uid;
    }

    @Override
    public void run() {
        String SERVER_ADDRESS = "localhost";
        int SERVER_PORT = 32005;

        Socket socketParaConectarseAlServidor = null;
        PrintWriter canalSalidaDirigidoAlServidor = null;
        BufferedReader canalEntradaQueVieneDelServidor = null;

        //Creaci�n del socket con el que se llevar� a cabo la comunicaci�n con el servidor.
        try {
            try {
                socketParaConectarseAlServidor = new Socket(SERVER_ADDRESS, SERVER_PORT);
            } catch (UnknownHostException e) {
                System.out.println("Don't know about host:" + SERVER_ADDRESS);
                System.exit(1);
            }


            //Inicializaci�n de los flujos de datos del socket con los que se lleva a cabo la
            // comunicaci�n con el servidor
            try {
                canalSalidaDirigidoAlServidor =
                        new PrintWriter(socketParaConectarseAlServidor.getOutputStream(), true);
                canalEntradaQueVieneDelServidor =
                        new BufferedReader(new InputStreamReader(
                        socketParaConectarseAlServidor.getInputStream()));
            } catch (IOException e) {
                System.out.println("Couldn't get I/O for "
                        + "the connection to: " + SERVER_ADDRESS);
                System.exit(1);
            }

            boolean comprar = true;

            while (comprar) {
                Random rnd = new Random();
                int fila = (int) (rnd.nextDouble() * 10 + 1);
                int columna = (int) (rnd.nextDouble() * 4 + 1);

                String userInput = "";
                userInput = userInput.concat(fila + " " + columna);
                if (userInput != null) {
                    canalSalidaDirigidoAlServidor.println(userInput);
                    String respuesta = canalEntradaQueVieneDelServidor.readLine();
                    String[] fields = respuesta.split(" ");
                    if ("Reservado".equals(fields[0])) {
                        System.out.println(id + "-La compra de " + fila + "-" + columna + " se ha realizado correctamente");
                        comprar = false;
                    } else if ("Vagon".equals(fields[0]) && "Completo".equals(fields[1])) {
                        System.out.println(id + "-El vagon esta completo");
                        comprar = false;
                    } else if ("Ocupado".equals(fields[0])) {
                        respuesta = canalEntradaQueVieneDelServidor.readLine();
                        System.out.println(id + "-Ocupado");
                        System.out.println(respuesta);
                    }
                }
            }

            //Cierre de los canales del sockect para comunicarse con el servidor.
            //canalSalidaDirigidoAlServidor.close();
            canalEntradaQueVieneDelServidor.close();

            //Cierre del Socket para comunicarse con el servidor. 
            socketParaConectarseAlServidor.close();


        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
