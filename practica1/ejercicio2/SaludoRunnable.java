package practica1.ejercicio2;

class SaludoRunnable implements Runnable{
    private String saludo;
    private int retardo;

    //Constructor para almacenar el saludo y el retardo
    public SaludoRunnable(String s, int r){
        this.saludo = s;
        this.retardo = r;
    }

    //El metodo run() es similar al main(), pero para threads. 
    //Cuando run() termina el thread muere.
    
    public void run(){
        //Dormimos la ejecucion durante el tiempo especificado
        try{
               Thread.sleep(retardo);
        } catch(InterruptedException e){
            ;
        }
        //Ahora imprimimos el saludo
        System.out.println( "Saludo de la siguiente forma: " 
            +  this.saludo + " despues de haber dormido " +
             this.retardo);
    }
}
