package practica1.ejercicio2;

import practica1.ejercicio2.SaludoRunnable;

public class SaludoMultipleConRunnable{
    public static void main(String args[]) throws InterruptedException{
         Thread t1,t2,t3;
         SaludoRunnable r1,r2,r3;
	
         //Creamos los objetos Runnable
         r1 = new SaludoRunnable ("Hola soy R1", 4000);
         r2 = new SaludoRunnable ("Hola soy R2", 2000);
         r3 = new SaludoRunnable ("Hola soy R3", 100);

        //Creamos los threads para que se ejecuten los 
        //objetos Runnable
        t1 = new Thread(r1);
        t2 = new Thread(r2);
        t3 = new Thread(r3);

        // Arrancamos los threads
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
    }   
}
