package Tp6_grandragon;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Aron
 */
public class Pantalla extends Thread {

    static final int NUM_IM_FILS = 2; // ventana con 2x2 im�genes
    static final int NUM_IM_COLS = 2;
    static final int PIX_SEP = 5;    // p�xeles de separaci�n entre im�genes
    private Almacen registro;

    public Pantalla(Almacen uregistro) {
        registro = uregistro;
    }

    @Override
    public void run() {
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

        System.out.println("Pantalla creada");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Inicializamos los clientes
        System.out.println("Lanzamos procesos");
        int n = 4;
        Thread[] hilos = new Thread[n];
        Gestion[] procesos = new Gestion[n];

        for (int i = 0; i < hilos.length; i++) {
            procesos[i] = new Gestion(i,label[i], registro);
            hilos[i] = new Thread(procesos[i]);
            hilos[i].start();

        }


    }
}
