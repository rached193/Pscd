package Tp6_grandragon;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class Servidor extends Thread {

    private Almacen registro;
    private Socket clientSocket;

    public Servidor(Almacen uRegistro,Socket cliente) {
        registro = uRegistro;
        this.clientSocket = cliente;
    }

    @Override
    public void run() {
        PrintWriter salidaHaciaElCliente = null;
        try {
            String path = System.getProperty("user.dir")
                    + System.getProperty("file.separator");
            path = path + ("sofia") + System.getProperty("file.separator") + "datos.jpg";

            
            //Inicializaci�n de los flujos de datos del socket con los que se lleva a cabo la
            // comunicaci�n con el servidor
            salidaHaciaElCliente = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader entradaDesdeElCliente = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            FileOutputStream fos = new FileOutputStream(path);

            //Protocolo de comunicaci�n con el Servidor.
            int inputLine = 0;
            int num = -1;
            int tiempo = -1;

            //Inicio Peticion
            String msg;
            msg = entradaDesdeElCliente.readLine();
            if (msg.equals("Inciar Peticion")) {
                try {
                    num = Integer.parseInt(entradaDesdeElCliente.readLine());
                    tiempo = Integer.parseInt(entradaDesdeElCliente.readLine());
                } catch (NumberFormatException e) {
                    salidaHaciaElCliente.println("Mensaje Incorrecto");

                }
                if (registro.QuieroEntrar()) {
                    salidaHaciaElCliente.println("Peticion Aceptada");
                    salidaHaciaElCliente.println(tiempo * 110 / 30);
                    salidaHaciaElCliente.println(registro.tiempofin);

                    msg = entradaDesdeElCliente.readLine();
                    if (msg.equals("Acepto")) {
                        String visa = entradaDesdeElCliente.readLine();
                        
                        //Recivir la Imagen
                        byte[] imageInByte = new byte[num];
                        int i = 0;
                        while (i < num) {
                            inputLine = Integer.parseInt(entradaDesdeElCliente.readLine());
                            imageInByte[i] = (byte) inputLine;
                            fos.write(imageInByte[i]);
                            i++;
                        }
                        ImageIcon ic = new ImageIcon(imageInByte);
                        Par<ImageIcon,Integer> aux= new Par<ImageIcon,Integer>(ic,tiempo);
                        registro.meter(aux);
                            

                    } else if (msg.equals("Rechazo")) {
                        registro.rechazarPeticion();
                    } else {
                        registro.rechazarPeticion();
                        //Mensaje Incorrecto
                    }

                } else {
                    salidaHaciaElCliente.println("Cola Llena");
                }


            } else {
                //Mensaje Incorrecto
            }

            //Enviar la respuesta al cliente
            //salidaHaciaElCliente.println(respuesta);
            //Recibir nueva petici�n del cliente
            //Cerrar los canales de comunicaci�n usados en el socket
            salidaHaciaElCliente.close();
            entradaDesdeElCliente.close();
            //Cerrar los sockets del cliente y del servidor
            clientSocket.close();
            System.out.println("Muere al atender peti");
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
