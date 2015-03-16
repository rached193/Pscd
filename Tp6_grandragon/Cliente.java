package Tp6_grandragon;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class Cliente extends Thread {

    private int imagen;

    public Cliente(int n) {
        imagen = n;
    }

    @Override
    public void run() {
        try {
            String SERVER_ADDRESS = "localhost";
            int SERVER_PORT = 32005;
            String path = System.getProperty("user.dir")
                    + System.getProperty("file.separator");

            String[] ficheros = {"drag.jpg", "inicio.jpg", "foto1.jpg", "foto2.jpg", "code.jpg",
                "bambina.jpg", "espadas doradas.jpg","franky.jpg","geass.jpg","rip.jpg","nuevo.jpg",
            "ludo1.jpg","vrr.png"};

            int tiempo = 20000;
            int accion = 1;
            String visa = "85964869559486473";

            Socket socketParaConectarseAlServidor = null;
            PrintWriter canalSalidaDirigidoAlServidor = null;
            BufferedReader canalEntradaQueVieneDelServidor = null;

            //Creaci�n del socket con el que se llevar� a cabo la comunicaci�n con el servidor.
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

            //Protocolo de comunicaci�n con el Servidor.


            //Cargar la imagen en un buffer
            BufferedReader entradaStandard = new BufferedReader(
                    new InputStreamReader(System.in));
            String userInput = "";
            path = path + ficheros[imagen];
            File lectura = new File(path);
            BufferedImage bi = null;
            try{
            bi = ImageIO.read(lectura);
            }catch(javax.imageio.IIOException e3){
             System.err.println("No se pudo leer imagen "+ficheros[imagen]);
        }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            int i = 0;

            //Peticion, tamaño imagen, tiempo 
            canalSalidaDirigidoAlServidor.println("Inciar Peticion");
            canalSalidaDirigidoAlServidor.println(imageInByte.length);
            canalSalidaDirigidoAlServidor.println(tiempo);
            System.out.println("Inciar Peticion");
            System.out.println(imageInByte.length);
            System.out.println(tiempo);

            String resp = canalEntradaQueVieneDelServidor.readLine();

            if (resp.equals("Peticion Aceptada")) {
                String precio = canalEntradaQueVieneDelServidor.readLine();
                String hora = canalEntradaQueVieneDelServidor.readLine();
                System.out.println("hora"+hora);

                if (accion == 1) { //Acepto
                    canalSalidaDirigidoAlServidor.println("Acepto");
                    canalSalidaDirigidoAlServidor.println(visa);
                    System.out.println("Acepto");
                    System.out.println(visa);
                    while (i < imageInByte.length) {
                        canalSalidaDirigidoAlServidor.println(imageInByte[i]);
                        i++;
                    }
                } else {
                    canalSalidaDirigidoAlServidor.println("Rechazo");
                }

            } else if (resp.equals("Cola LLena")) {
            } else {
                //Mensaje Incorrecto
            }

            //Cierre de los canales del sockect para comunicarse con el servidor.
            canalSalidaDirigidoAlServidor.close();
            canalEntradaQueVieneDelServidor.close();

            //Cierre del Socket para comunicarse con el servidor. 
            socketParaConectarseAlServidor.close();

            //Cerrar el buffer de la entrada standard
            entradaStandard.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
