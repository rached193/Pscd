/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Tp6_grandragon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import java.util.concurrent.atomic.AtomicInteger;

public class Almacen {
    private AtomicInteger total;
    private List<Par<ImageIcon,Integer>>list;
    int tiempofin;
    private AtomicInteger encola;
    
    public Almacen(){
        list = Collections.synchronizedList(new ArrayList());
         total=new AtomicInteger(0);
         encola=new AtomicInteger(0);
         tiempofin=0;
    }
    
    public synchronized void meter (Par<ImageIcon,Integer> Ide){
        list.add(Ide);
        tiempofin=tiempofin+Ide.getValor();
        total.incrementAndGet();
        notifyAll();
    }
    
    public synchronized  Par<ImageIcon,Integer>  sacar() throws InterruptedException{
        while(total.get()==0){
            wait();
        }
        total.decrementAndGet();
        encola.decrementAndGet();
        Par<ImageIcon,Integer> Aux=list.remove(0);
        tiempofin=tiempofin-Aux.getValor();
        return Aux;
    }
    
    public synchronized  boolean QuieroEntrar(){
        if(encola.get()<10){
            encola.incrementAndGet();
            return true;
        }
        else{
            return false;
        }
    }
    
    public  void rechazarPeticion(){
        encola.decrementAndGet();
    }
    
}
