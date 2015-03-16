package Tp6_grandragon;

import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;



/**
 *
 * @author Aron
 */
public class Gestion extends Thread {
    final int NUMFILAS = 400; 
    JLabel label;
    Almacen registro;
    Par<ImageIcon,Integer> estruc;
    ImageIcon imagen;
    int id;
    /*
     * 1-1: Valla principal
     * 2-*: Valla secundaria(1-4) 
     */
    public Gestion (int uid,JLabel ulabel, Almacen uregistro){
        label=ulabel;
        registro=uregistro;
        id=uid;
    }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("Soy "+id+"Y me bloqueo");
                estruc=registro.sacar();               
            } catch (InterruptedException ex) {
                Logger.getLogger(Gestion.class.getName()).log(Level.SEVERE, null, ex);
            }
            imagen= new ImageIcon(estruc.getClave().getImage(). // -1: aplicara en columna mismo factor que en filas
                        getScaledInstance(NUMFILAS, -1, Image.SCALE_SMOOTH));
            label.setIcon(imagen);
            System.out.println("Soy "+id+"Y cambio la imagen");
            try {
                sleep(estruc.getValor());
            } catch (InterruptedException ex) {
                Logger.getLogger(Gestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }               
    }
}
