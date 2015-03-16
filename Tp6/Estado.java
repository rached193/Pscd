/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: Estado.java
 * Fecha: 08/01/2015
 * Descripción: Clase que gestiona el JPanel que monitoriza el sistema
 * 		
 */


package Tp6;

//Muestra las estadisticas del sistema en una ventana
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Estado implements ActionListener {

    private JPanel panel;
    private JButton boton;
    private JLabel hora;
    private JLabel colaListado1;
    private JLabel colaListado2;
    private JLabel clientes;
    private Semaphore mutex;
    private boolean ejecucion;

    public Estado() {
        mutex = new Semaphore(0);
        ejecucion = true;

    }

    public void addMonitor() {
        Reloj reloj = new Reloj();
        Thread clock = new Thread(reloj);
        clock.start();
    }

    public Component createComponents() {
        // Devuelve un Jpanel con las componentes que muestran las estad�sticas
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        boton = new JButton("Terminar servicio");
        boton.setMnemonic(KeyEvent.VK_C);
        boton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        constraints.weightx = 0.15;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.CENTER;
        panel.add(boton, constraints);

        JLabel label = new JLabel(" ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.15;
        constraints.weighty = 0.9;
        panel.add(label, constraints);

        clientes = new JLabel("Numero de clientes conectados:   ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.20;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(clientes, constraints);

        hora = new JLabel();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        hora.setText(sdf.format(new Date(System.currentTimeMillis())));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.15;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(hora, constraints);

        JLabel cola1 = new JLabel("Proximos servicios Cola 1: ");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = 1;
        constraints.weightx = 0.70;
        constraints.weighty = 0;
        panel.add(cola1, constraints);

        colaListado1 = new JLabel("");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        constraints.weightx = 0.70;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(colaListado1, constraints);

        JLabel cola2 = new JLabel("Proximos servicios Cola 2: ");
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = 1;
        constraints.weightx = 0.70;
        constraints.weighty = 0;
        panel.add(cola2, constraints);

        colaListado2 = new JLabel("");
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        constraints.weightx = 0.70;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(colaListado2, constraints);

        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
        // Informa al sistema de que el servidor tiene que finalizar su servicio
        // al pulsar
        // el bot�n
        ejecucion = false;
        System.out.println("boton pulsado");

    }

    public void iniciarFinalizar() {
        mutex.release();
    }

    public boolean funciona() {
        return ejecucion;
    }

    public void actualizar(ArrayList<ContenedorImagen> cola1,
            ArrayList<ContenedorImagen> cola2, int clientes) {
		// actualiza la informacion del sistema, invocado cada vez
        // que ocurre un cambio en el estado del monitor
        this.clientes.setText("Nº de clientes conectados: " + clientes);

        String servicios1 = "";
        if (cola1 != null) {
            for (int i = 0; i < cola1.size(); i++) {
                servicios1 = servicios1 + ' ' + cola1.get(i).getNombre();

            }

            this.colaListado1.setText(servicios1);
        }

        String servicios2 = "";
        if (cola2 != null) {
            for (int i = 0; i < cola2.size(); i++) {
                servicios2 = servicios2 + ' ' + cola2.get(i).getNombre();

            }

            this.colaListado2.setText(servicios2);
        }

    }

    public void esperarTerminar() {
        try {
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Almacen.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

    public class Reloj implements Runnable {

        // hilo encargado de actualizar el reloj cada medio segundo

        public void run() {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
					// TODO Auto-generated catch block
                    // e.printStackTrace();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                hora.setText(sdf.format(new Date(System.currentTimeMillis())));
            }

        }
    }

}
