
package Tp6_grandragon;

/**
 * Clase para guardar pares de elementos (clave, valor).
 *
 * @author Marcos Ruiz y Aron Collados
 */
public class Par<K, V> {

    private K clave;
    private V valor;

    /**
     * Metodo constructor
     */
    public Par(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    /**
     * Devuelve valor de Par
     */
    public V getValor() {
        return valor;
    }

    /**
     * Cambia el valor de Par
     */
    public void setValor(V valor) {
        this.valor = valor;
    }

    /**
     * Devuelve la clave de Par
     */
    public K getClave() {
        return clave;
    }

    /**
     * Cambia la clave de Par
     */
    public void setClave(K clave) {
        this.clave = clave;
    }

    /**
     * Devuelve la representacion String de Par
     */
    @Override
    public String toString() {
        return clave.toString() + "; " + valor.toString();
    }
}
