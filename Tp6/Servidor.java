/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: Servidor.java
 * Fecha: 08/01/2015
 * Descripción: Hilo que gestiona la conversacion del Servidor con los clientes
 */
package Tp6;

import java.awt.MediaTracker;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

public class Servidor extends Thread {

    private Almacen registro;
    private Socket clientSocket;
    private final int coste2 = 40;
    private final int coste1 = coste2 * 4;
    private boolean debug;
    private String address;
    private int port;
    /**
     * Constructor
     * @param uRegistro
     * @param cliente
     * @param addressBanco
     * @param portBanco
     * @param test
     */
    public Servidor(Almacen uRegistro, Socket cliente, String addressBanco, int portBanco, boolean test) {
        this.registro = uRegistro;
        this.clientSocket = cliente;
        this.debug = test;
        this.address = addressBanco;
        this.port = portBanco;
    }

    @Override
    public void run() {
        PrintWriter salidaHaciaElCliente = null;
        int valla = 0;
        try {

            salidaHaciaElCliente = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader entradaDesdeElCliente = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            int inputLine = 0;
            int tiempo = -1;

            boolean lleno;
            ImageIcon imagen;

            if (debug) {
                System.out.println("Servidor: Iniciar Conexion con el cliente");
            }
            String msg = "";
            msg = entradaDesdeElCliente.readLine();
            if (msg.equals("INICIAR PETICION")) {
                try {
                    tiempo = Integer.parseInt(entradaDesdeElCliente.readLine());
                    valla = Integer.parseInt(entradaDesdeElCliente.readLine());
                    lleno = registro.QuieroEntrar();

                    if (lleno) {
                        if (debug) {
                            System.out.println("Servidor: Conexion Aceptada");
                            System.out.println("Servidor: Peticion Cliente:  [ tiempo valla ] [ " + tiempo + " " + valla + " ]");
                        }
                        int precio = 0;
                        salidaHaciaElCliente.println("PETICION ACEPTADA");
                        if (valla == 1) {
                            precio = tiempo * coste1;
                            salidaHaciaElCliente.println(precio);
                            salidaHaciaElCliente
                                    .println(registro.tiempoCola1());
                        } else {
                            precio = tiempo * coste2;
                            salidaHaciaElCliente.println(precio);
                            salidaHaciaElCliente
                                    .println(registro.tiempoCola2());
                        }
                        msg = entradaDesdeElCliente.readLine();
                        boolean exito = true;
                        while (exito) {
                            if (msg.equals("ACEPTO")) {
                                exito = false;
                                if (debug) {
                                    System.out.println("Servidor: Comprobando Fondos");
                                }
                                String visa = entradaDesdeElCliente.readLine();
                                String URL = entradaDesdeElCliente.readLine();
                                String id = entradaDesdeElCliente.readLine();

                                if (debug) {
                                    System.out.println("Servidor: Datos Cliente: [ id visa URL ] [ " + id + " " + visa + " " + URL + " ]");
                                }
                                Socket socketParaConectarseAlBanco = null;
                                PrintWriter canalSalidaDirigidoAlBanco = null;
                                BufferedReader canalEntradaQueVieneDelBanco = null;
                                try {
                                    socketParaConectarseAlBanco = new Socket(address,
                                            port);
                                } catch (UnknownHostException e) {
                                    if (debug) {
                                        System.out.println("Don't know about host:" + address);
                                    }
                                    System.exit(1);
                                }
                                try {
                                    canalSalidaDirigidoAlBanco = new PrintWriter(
                                            socketParaConectarseAlBanco.getOutputStream(), true);
                                    canalEntradaQueVieneDelBanco = new BufferedReader(
                                            new InputStreamReader(socketParaConectarseAlBanco
                                                    .getInputStream()));
                                } catch (IOException e) {
                                    if (debug) {
                                        System.out.println("Couldn't get I/O for "
                                                + "the connection to: " + address);
                                    }
                                    System.exit(1);
                                }
                                canalSalidaDirigidoAlBanco.println("VISA");
                                canalSalidaDirigidoAlBanco.println(visa);
                                canalSalidaDirigidoAlBanco.println(precio);
                                msg = canalEntradaQueVieneDelBanco.readLine();
                                if (msg.equals("PAGO CORRECTO")) {
                                    imagen = new ImageIcon(new URL(URL));
                                    ContenedorImagen aux = new ContenedorImagen(
                                            imagen, tiempo, id);
                                    if (imagen.getImageLoadStatus() == MediaTracker.COMPLETE) {
                                        if (valla == 1) {
                                            registro.meter1(aux);
                                            if (debug) {
                                                System.out.println("Servidor: Encolar en la Valla 1");
                                            }
                                        } else {
                                            registro.meter2(aux);
                                            if (debug) {
                                                System.out.println("Servidor: Encolar en la Valla 2");
                                            }
                                        }
                                        if (debug) {
                                            System.out.println("Servidor: Transaccion Aceptada");
                                        }
                                        salidaHaciaElCliente
                                                .println("OPERACION ACEPTADA");
                                    } else {
                                        salidaHaciaElCliente
                                                .println("FALLO AL CARGAR IMAGEN");
                                        if (debug) {
                                            System.out.println("Servidor: Fallo al Cargar Imagen");
                                        }
                                    }                                    
                                } else if (msg.equals("SIN FONDOS")) {
                                    salidaHaciaElCliente.println("SIN FONDOS");
                                    if (debug) {
                                        System.out.println("Servidor: Cliente Sin Fondos");
                                    }
                                } else {
                                    if (debug) {
                                        System.out.println("Servidor: Fallo de Comunicacion con el Banco");
                                    }
                                    salidaHaciaElCliente.println("ERROR");
                                }
                                socketParaConectarseAlBanco.close();
                                canalSalidaDirigidoAlBanco.close();
                                canalEntradaQueVieneDelBanco.close();
                            } else if (msg.equals("RECHAZO")) {
                                exito = false;
                                if (debug) {
                                    System.out.println("Sevidor: Operacion Rechazada");
                                }
                            } else if (msg.equals("NEGOCIO")) {
                                if (debug) {
                                    System.out.println("Sevidor: Operacion Modificada");
                                }
                                tiempo = Integer.parseInt(entradaDesdeElCliente
                                        .readLine());
                                valla = Integer.parseInt(entradaDesdeElCliente
                                        .readLine());
                                if (valla == 1) {
                                    precio = tiempo * coste1;
                                    salidaHaciaElCliente.println(precio);
                                    salidaHaciaElCliente.println(registro
                                            .tiempoCola1());
                                } else {
                                    precio = tiempo * coste2;
                                    salidaHaciaElCliente.println(precio);
                                    salidaHaciaElCliente.println(registro
                                            .tiempoCola2());
                                }
                                msg = entradaDesdeElCliente.readLine();

                            } else if (debug) {
                                System.out.println("Peticion del Cliente Desconocida: Conexion Rechazada");
                            }
                        }
                        if (debug) {
                            System.out.println("Servidor: Conexion Finalizada");
                        }
                        registro.rechazarPeticion();
                    } else {
                        if (debug) {
                            System.out.println("Servicio Saturado: Conexion Rechazada");
                        }
                        salidaHaciaElCliente.println("LLENO");
                    }
                } catch (NumberFormatException e) {
                    if (debug) {
                        System.out.println("Mensaje Incorrecto: Conexion Rechazada");
                    }
                    salidaHaciaElCliente.println("ERROR");
                }
            } else {
                salidaHaciaElCliente.println("ERROR");
                if (debug) {
                    System.out.println("Error Iniciar Conexion: Conexion Rechazada");
                }
            }
            salidaHaciaElCliente.close();
            entradaDesdeElCliente.close();
            clientSocket.close();
            if (debug) {
                System.out.println("Servidor: Cierre de Conexion");
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null,
                    ex);
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
