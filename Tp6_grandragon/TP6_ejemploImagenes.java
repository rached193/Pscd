/*
 * File:    TP6_ejemploImagenes.java
 *
 * Author:  J. Ezpeleta
 *
 * Date:    8/12/13
 *
 * Coms:    Para TP6 del curso 13-14. Ejemplo de manejo de im�genes en Java.
 *          Consultar documentaci�n para gesti�n de im�genes, ventanas, etc.
 */

package Tp6_grandragon;

import java.awt.Image;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.MediaTracker;

public class TP6_ejemploImagenes {

    static final int NUM_IM_FILS = 2; // ventana con 2x2 im�genes
    static final int NUM_IM_COLS = 2;
    static final int PIX_SEP = 5;    // p�xeles de separaci�n entre im�genes

    static public void main(String args[]) {
        final int NUMFILAS = 400;   // redimensionar im�genes a NUMFILAS p�xeles de altura
        // la anchura mantendr� proporcionalidad con
        // la imagen original

        JFrame frame = new JFrame("Imagenes");  // frame para colocar las im�genes
        Container panel = frame.getContentPane();   // para acceder al panel del frame
        JLabel[] label = new JLabel[NUM_IM_FILS * NUM_IM_COLS]; // un JLabel por imagen
        String path = System.getProperty("user.dir")
                + System.getProperty("file.separator");
        String[] ficheros = {"catsEye.jpg", "pilaresCreacion.jpg",
            "sombrero.jpg", "M42.jpg"}; // listado de im�genes
        ImageIcon[] lasImagenes = new ImageIcon[NUM_IM_FILS * NUM_IM_COLS];

        // Poner propiedades fundamentales del "frame"
        frame.setLocation(60, 0); //desplazar ventana 60 p�xeles
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // -----------------------------------------------------------------------------
        // Preparar infraestructura para colocar las im�genes
        panel.setLayout(new GridLayout(NUM_IM_FILS, NUM_IM_COLS, PIX_SEP,
                PIX_SEP));

        for (int i = 0; i < ficheros.length; i++) {
            lasImagenes[i] = new ImageIcon(path + ficheros[i]);
            if (lasImagenes[i].getImageLoadStatus() != MediaTracker.COMPLETE) { //�Imagen cargada correctamente?
                label[i] = new JLabel();
                label[i].setText("Imagen no encontrada"); //si no encuentra la imagen, a�adir texto
            } else {
                lasImagenes[i] = new ImageIcon(lasImagenes[i].getImage(). // -1: aplicar� en columna mismo factor que en filas
                        getScaledInstance(NUMFILAS, -1, Image.SCALE_SMOOTH));
                label[i] = new JLabel(lasImagenes[i]);
            }
            panel.add(label[i]);
        }

        // dimensionar el "frame" adecuadamente para colocar las im�genes
        // 35 y 55 para dejar algo de margen, vertical y horizontal
        frame.setSize(35 + NUM_IM_COLS * NUMFILAS, 55 + NUM_IM_FILS * NUMFILAS);
        frame.setVisible(true);

        //permuta las im�genes 0 y 1 10 veces
        for (int i = 0; i < 9; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //permutar im�genes 0 y 1
            label[0].setIcon(lasImagenes[i % 2]);
            label[1].setIcon(lasImagenes[(i + 1) % 2]);
        }
    }
}