/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: Gestion.java
 * Fecha: 08/01/2015
 * Descripción:
 * 		
 */
package Tp6;

import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Gestion extends Thread {

    private final int NUMFILAS = 400;
    private JLabel label;
    private Almacen registro;
    private ContenedorImagen estruc;
    private ImageIcon imagen;
    private int id;
    private boolean debug;

    /*
     * 11: Valla principal 2*: Valla secundaria(1-4)
     */
    public Gestion(int uid, JLabel ulabel, Almacen uregistro, boolean test) {
        label = ulabel;
        registro = uregistro;
        id = uid;

    }

    @Override
    public void run() {
        while (true) {
            try {
                if (debug) {
                    System.out.println("Gestion " + id + ": Obtengo Imagen");
                }
                if (id == 11) {
                    estruc = registro.sacar1();
                } else {
                    estruc = registro.sacar2();
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Gestion.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            // -1: aplicara en columna mismo factor que en filas
            imagen = new ImageIcon(estruc.getImagen().getImage()
                    .getScaledInstance(NUMFILAS, -1, Image.SCALE_SMOOTH));
            label.setIcon(imagen);
            if (debug) {
                System.out.println("Gestion " + id + ": Cambio Imagen");
            }
            try {
                sleep(estruc.getTiempo());
            } catch (InterruptedException ex) {
                Logger.getLogger(Gestion.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }
}
