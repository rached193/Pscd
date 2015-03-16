/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: PanallaUnica.java
 * Fecha: 08/01/2015
 * Descripción: Clase que inicializa la Valla Principal e inicia su gestion
 *
 */


package Tp6;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PantallaUnica extends Thread {

    private final int NUM_IM_FILS = 1; // ventana con 2x2 imagenes
    private final int NUM_IM_COLS = 1;
    private final int PIX_SEP = 5;
    private Almacen registro;
    private boolean debug;
    private final int n = 1;

    public PantallaUnica(Almacen uregistro, boolean test) {
        registro = uregistro;
        debug = test;
    }

    @Override
    public void run() {
        final int NUMFILAS = 400;   
        JFrame frame = new JFrame("Imagenes"); 
        Container panel = frame.getContentPane(); 
        JLabel[] label = new JLabel[NUM_IM_FILS * NUM_IM_COLS]; 
        String path = System.getProperty("user.dir")
                + System.getProperty("file.separator");
        String[] ficheros = {"gatitos.jpg"}; 
        ImageIcon[] lasImagenes = new ImageIcon[NUM_IM_FILS * NUM_IM_COLS];

        frame.setLocation(60, 0); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(new GridLayout(NUM_IM_FILS, NUM_IM_COLS, PIX_SEP,
                PIX_SEP));

        for (int i = 0; i < ficheros.length; i++) {
            lasImagenes[i] = new ImageIcon(path + ficheros[i]);
            if (lasImagenes[i].getImageLoadStatus() != MediaTracker.COMPLETE) { 
                label[i] = new JLabel();
                label[i].setText("Imagen no encontrada"); 
            } else {
                lasImagenes[i] = new ImageIcon(lasImagenes[i].getImage().
                        getScaledInstance(NUMFILAS, -1, Image.SCALE_SMOOTH));
                label[i] = new JLabel(lasImagenes[i]);
            }
            panel.add(label[i]);
        }

        frame.setSize(35 + NUM_IM_COLS * NUMFILAS, 55 + NUM_IM_FILS * NUMFILAS);
        frame.setVisible(true);

        if (debug) {
            System.out.println("VallaPrincipal Pantalla Creada");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (debug) {
            System.out.println("VallaPrincipal: Lanzar Procesos Gestion");
        }
        Thread[] hilos = new Thread[n];
        Gestion[] procesos = new Gestion[n];

        for (int i = 0; i < hilos.length; i++) {
            procesos[i] = new Gestion(11, label[i], registro, debug);
            hilos[i] = new Thread(procesos[i]);
            hilos[i].start();

        }

    }
}
