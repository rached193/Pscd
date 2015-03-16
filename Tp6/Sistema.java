/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: Sistema.java
 * Fecha: 08/01/2015
 * Descripción: Clase que gestiona el inicio del sistema.		
 */

package Tp6;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Aron
 */
public class Sistema {

    private static boolean debug = false;

    public static void main(String[] args) throws IOException,
            InterruptedException {
        if (args.length < 0 || args.length > 5) {
            System.err
                    .println("Formato Incorrecto%n"
                            + "El formato para lanzar un cliente es: -C nºclientes ip port [-V]%n"
                            + "El formato para lanzar el servdior es: -S ip port [-V]%n"
                            + "El formato para lanzar el banco es: -B [-V]%n");
            System.exit(-1);
        }
        if (args[0].equals("-C")) {

            if (args.length ==5 && args[4].equals("-V")) {
                debug = true;
            }
            int n = Integer.parseInt(args[1]);
            Thread[] hilos = new Thread[n];
            Cliente[] procesos = new Cliente[n];

            for (int i = 0; i < n; i++) {
                procesos[i] = new Cliente(i, args[2], Integer.parseInt(args[3]),debug);
                hilos[i] = new Thread(procesos[i]);
                hilos[i].start();
            }

        } else if (args[0].equals("-S")) {

            if (args.length ==4 && args[3].equals("-V")) {
                debug = true;
            }
            Estado estado = new Estado();
            Almacen almacen = new Almacen(estado);

            JFrame frame = new JFrame("Estado");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Component contents = estado.createComponents();
            frame.getContentPane().add(contents, BorderLayout.CENTER);
            frame.pack();
            frame.setSize(900, 150);
            frame.setVisible(true);

            ServidorJefe psev = new ServidorJefe(estado, almacen, args[1],
                    Integer.parseInt(args[2]), debug);
            Thread tsev = new Thread(psev);
            tsev.start();

            Pantalla pll = new Pantalla(almacen,debug);
            PantallaUnica pll2 = new PantallaUnica(almacen,debug);
            Thread tpll = new Thread(pll);
            Thread tpll2 = new Thread(pll2);
            pll.start();
            pll2.start();
        } else if (args[0].equals("-B")) {
            if (args.length ==2 &&  args[1].equals("-V")) {
                debug = true;
            }
            BancoJefe pban = new BancoJefe(debug);
            Thread tban = new Thread(pban);
            tban.start();
        } else {
            System.err
                    .println("Formato Incorrecto%n"
                            + "El formato para lanzar un cliente es: -C nºclientes ip port  [-V]%n"
                            + "El formato para lanzar el servdior es: -S ip port  [-V]%n"
                            + "El formato para lanzar el banco es: -B  [-V]%n");
            System.exit(-1);
        }

    }
}
