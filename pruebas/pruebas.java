package pruebas;

import java.util.Calendar;

public class pruebas {
	
	public static void main (String args[] ){
		long aux = System.currentTimeMillis();
		long hora = (aux / 3600000);
		long minutos = aux % 3600000;
		double segundos = ((aux / 360000) - (minutos * 60000))/1000; 
		System.out.println(System.currentTimeMillis()/3600*1000);
		System.out.println(Calendar.getInstance());
		System.out.printf("%d:%d:%f",hora,minutos,segundos);
	}

}
