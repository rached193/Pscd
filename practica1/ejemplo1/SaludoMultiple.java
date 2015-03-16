package practica1.ejemplo1;

import practica1.ejemplo1.SaludoThread;

public class SaludoMultiple{
    public static void main(String args[]){
        SaludoThread t1,t2,t3;

        //Creamos los threads
        t1 = new SaludoThread("Hola, soy Thread 1", 4000);
        t2 = new SaludoThread("Hola, soy Thread 2", 2000);
        t3 = new SaludoThread("Hola, soy Thread 3", 100);

        //Arrancamos los threads
        t1.start();
        t2.start();
        t3.start();
    }
}
