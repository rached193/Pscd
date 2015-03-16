package practica1.ejemplo1;

class SaludoThread extends Thread{
    private String saludo;
    private int retardo;

    //Constructor para almacenar el saludo y el retardo
    public SaludoThread(String s, int r){
        this.saludo = s;
        this.retardo = r;
    }

    //El metodo run() es similar al main(), pero para threads. 
    //Cuando run() termina el thread muere.
    
    public void run(){
        //Dormimos la ejecuci�n durante el tiempo especificado
        try{
             sleep( retardo );
        } catch(InterruptedException e){
            ;
        }
        //Ahora imprimimos el saludo
        System.out.println( "Saludo de la siguiente forma: " 
            +  this.saludo + " despu�s de haber dormido" +
             this.retardo);
    }
}
