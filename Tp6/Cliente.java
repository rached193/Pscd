/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: Cliente.java
 * Fecha: 08/01/2015
 * Descripción: Clase cliente, realiza una peticion al servidor de vallas 
 * 				publicitarias, interesado en mostrar una imagen de una URL 
 * 				un determinado tiempo. 
 * 		
 */
package Tp6;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread {

    private ArrayList<String> URLs;
    private int SERVER_PORT;
    private Random azar = new Random();
    private String SERVER_ADDRESS;
    private int id;
    private boolean debug;

    /**
     * Constructor del Cliente, id, direccion y puerto
     *
     * @param i
     * @param address
     * @param port
     */
    public Cliente(int i, String address, int port, boolean test) {
        id = i;
        SERVER_ADDRESS = address;
        SERVER_PORT = port;
        URLs = new ArrayList<String>();
        debug = test;
    }

    /**
     * Ejecucion del cliente
     */
    public void run() {
        try {
            String path = System.getProperty("user.dir")
                    + System.getProperty("file.separator");
            File archivo = new File(path + "url.txt");
            Scanner scan = new Scanner(archivo);
            while (scan.hasNextLine()) {
                URLs.add(scan.nextLine());
            }
            String URL = URLs.get(azar.nextInt(URLs.size()));
            String visa = "";
            for (int i = 0; i <= 16; i++) {
                int n = azar.nextInt(10);
                visa = visa + n;
            }

            int tiempo = (azar.nextInt(6) + 5) * 1000;
            int accion;
            int valla = azar.nextInt(2) + 1;

            Socket socketParaConectarseAlServidor = null;
            PrintWriter canalSalidaDirigidoAlServidor = null;
            BufferedReader canalEntradaQueVieneDelServidor = null;
            try {
                socketParaConectarseAlServidor = new Socket(SERVER_ADDRESS,
                        SERVER_PORT);
            } catch (UnknownHostException e) {
                if (debug) {
                    System.out.println("Don't know about host:" + SERVER_ADDRESS);
                }
                System.exit(1);
            }

            try {
                canalSalidaDirigidoAlServidor = new PrintWriter(
                        socketParaConectarseAlServidor.getOutputStream(), true);
                canalEntradaQueVieneDelServidor = new BufferedReader(
                        new InputStreamReader(socketParaConectarseAlServidor
                                .getInputStream()));
            } catch (IOException e) {
                if (debug) {
                    System.out.println("Couldn't get I/O for "
                            + "the connection to: " + SERVER_ADDRESS);
                }
                System.exit(1);
            }
            canalSalidaDirigidoAlServidor.println("INICIAR PETICION");
            canalSalidaDirigidoAlServidor.println(tiempo);
            canalSalidaDirigidoAlServidor.println(valla);
            if (debug) {
                System.out.println("Cliente " + id + ": Inciar Peticion");
            }

            String resp = canalEntradaQueVieneDelServidor.readLine();

            if (resp.equals("PETICION ACEPTADA")) {
                int intentos = 0;
                String precio;
                String hora;
                while (intentos < 3) {
                    precio = canalEntradaQueVieneDelServidor.readLine();
                    hora = canalEntradaQueVieneDelServidor.readLine();
                   if (debug) {
                            System.out.println("Client " + id + ": [ hora precio ] [ "+ hora+" "+precio+" ]");
                        }
                    accion = azar.nextInt(3) + 1;
                    accion = 3;
                    if (accion == 1) {// Negocio
                        if (debug) {
                            System.out.println("Client " + id + ": Inicia Negociacion");
                        }
                        if (valla == 1) {
                            valla = 2;
                        } else {
                            tiempo = tiempo * 2 / 3;
                        }
                        canalSalidaDirigidoAlServidor.println("NEGOCIO");
                        canalSalidaDirigidoAlServidor.println(tiempo);
                        canalSalidaDirigidoAlServidor.println(valla);
                        intentos++;
                    } else if (accion == 2) {// Rechazo
                        canalSalidaDirigidoAlServidor.println("RECHAZO");
                        if (debug) {
                            System.out.println("Client " + id + ": Peticion Rechazada");
                        }
                        intentos = 3;
                    } else {// Acepto
                        canalSalidaDirigidoAlServidor.println("ACEPTO");
                        canalSalidaDirigidoAlServidor.println(visa);
                        canalSalidaDirigidoAlServidor.println(URL);
                        canalSalidaDirigidoAlServidor.println(id);
                         if (debug) {
                            System.out.println("Client " + id + ": Peticion Aceptada");
                        }
                        System.out.println(canalEntradaQueVieneDelServidor
                                .readLine());
                        intentos = 3;
                    }
                }
                if (intentos == 3) {
                    canalSalidaDirigidoAlServidor.println("RECHAZO");
                    if (debug) {
                        System.out.println("Client " + id + ": Negociacion Terminado");
                    }
                }
            } else if (resp.equals("LLENO")) {
                if (debug) {
                    System.out.println("Client " + id + ": Servicio Saturado");
                }
            } else if (debug) {
                System.out.println("Client " + id + ": Error de Comunicacion");

            }
            canalSalidaDirigidoAlServidor.close();
            canalEntradaQueVieneDelServidor.close();
            socketParaConectarseAlServidor.close();

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
}
