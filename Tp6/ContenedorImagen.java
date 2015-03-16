/*
 * Autores: Aron Collados Torres, Alejandro Solanas Bonilla
 * NIAs:	626558,647647
 * Fichero: ContenedorImagen.java
 * Fecha: 08/01/2015
 * Descripción: Clase que encapsula un objeto ImagenIcon y su informacion asociada a el
 * 		
 */

package Tp6;

import javax.swing.ImageIcon;

public class ContenedorImagen {

    private ImageIcon imagen;
    private int tiempo;
    private String name;

    /**
     * Metodo constructor
     */
    public ContenedorImagen(ImageIcon clave, int valor, String nombre) {
        this.imagen = clave;
        this.tiempo = valor;
        this.name = nombre;
    }

    /**
     * Devuelve valor de Par
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * Devuelve la clave de Par
     */
    public ImageIcon getImagen() {
        return imagen;
    }

    public String getNombre() {
        return name;
    }
}
