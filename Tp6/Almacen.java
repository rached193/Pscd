/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: Almacen.java
 * Fecha: 08/01/2015
 * Descripción: Clase que gestiona un almacen para imagenes que se publicaran en
 * 				una de las vallas en ejecucion, listas y metodos individuales
 * 				para cada una de las vallas			
 */
package Tp6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase almacen de la valla
 */
public class Almacen {

    private int total1, total2; // Numero de imagenes en el almacen
    private ArrayList<ContenedorImagen> list1, list2; // Colas de elementos
    private int tiempofin1, tiempofin2;
    private AtomicInteger encola; // Gente hablando con el servidor segun valla
    private final int maxCLientes = 20;
    private Estado estado;

    /**
     * Constructor del almacen
     */
    public Almacen(Estado uEstado) {
        list1 = new ArrayList<ContenedorImagen>();
        list2 = new ArrayList<ContenedorImagen>();
        total1 = 0;
        total2 = 0;
        encola = new AtomicInteger(0);
        tiempofin1 = 0;
        this.estado = uEstado;
    }

    /**
     * Metodo que añade un elemento a la cola 1
     *
     * @param Ide
     */
    public synchronized void meter1(ContenedorImagen Ide) {
        list1.add(Ide);
        tiempofin1 = tiempofin1 + Ide.getTiempo();
        total1++;
        estado.actualizar(list1, list2, encola.get());
        notifyAll();
    }

    /**
     * Metodo que elimina un elemento a la cola 1
     *
     * @return
     * @throws InterruptedException
     */
    public synchronized ContenedorImagen sacar1() throws InterruptedException {
        while (total1 == 0) {
            wait();
        }
        total1--;
        ContenedorImagen Aux = list1.remove(0);
        tiempofin1 = tiempofin1 - Aux.getTiempo();
        if ((total1 == 0) && (total2 == 0) && (!estado.funciona())) {
            estado.iniciarFinalizar();
        }
        estado.actualizar(list1, list2, encola.get());
        return Aux;
    }

   /**
     * Devuelve el tiempo estimado del que tardara en llegar alultimo elemento acutal de la cola1
     */
    public int tiempoCola1() {
        return tiempofin1;
    }

    /**
     * Devuelve el tiempo estimado del que tardara en llegar al ultimo elemento acutal de la cola2
     */
    public int tiempoCola2() {
        return tiempofin2;
    }

    /**
     * Comprueba si el sistema no esta saturado
     *
     * @return
     */
    public synchronized boolean QuieroEntrar() {
        if (encola.get() < maxCLientes) {
            encola.incrementAndGet();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fin de negociacion
     */
    public synchronized void rechazarPeticion() {
        encola.decrementAndGet();
    }

    /**
     * Metodo que añade un elemento a la cola 2
     *
     * @param Ide
     */
    public synchronized void meter2(ContenedorImagen Ide) {
        list2.add(Ide);
        tiempofin2 = tiempofin2 + Ide.getTiempo();
        total2++;
        estado.actualizar(list1, list2, encola.get());
        notifyAll();
    }

    /**
     * Metodo que extrae un elemento de la cola 2
     *
     * @return
     * @throws InterruptedException
     */
    public synchronized ContenedorImagen sacar2() throws InterruptedException {
        while (total2 == 0) {
            wait();
        }
        total2--;
        ContenedorImagen Aux = list2.remove(0);
        tiempofin2 = tiempofin2 - Aux.getTiempo();
        if ((total1 == 0) && (total2 == 0) && ((!estado.funciona()))) {
            estado.iniciarFinalizar();
        }
        estado.actualizar(list1, list2, encola.get());
        return Aux;
    }

}
