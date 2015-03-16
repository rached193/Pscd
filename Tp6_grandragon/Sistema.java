package Tp6_grandragon;

import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aron
 */
public class Sistema {
    
    public static void main(String[] args) throws IOException, InterruptedException {
                

        //Monitor Servidor
        Almacen rv = new Almacen();
        

        //Inicializamos y lanzamos el servidor
        ServidorJefe psev = new ServidorJefe(rv);
        Thread tsev = new Thread(psev);
        tsev.start();
        
        Pantalla pll = new Pantalla(rv);
        Thread tpll= new Thread(pll);
        pll.start();
       
        int n=13;

        //Inicializamos los clientes
        Thread[] hilos = new Thread[n];
        Cliente[] procesos = new Cliente[n];


        //Lanzamos los clientes
        for (int i = 0; i < n; i++) {
           procesos[i] = new Cliente(i);
            hilos[i] = new Thread(procesos[i]);
            hilos[i].start();
       }
    }
}

