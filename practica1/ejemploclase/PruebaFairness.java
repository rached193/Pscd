package practica1.ejemploclase;

//====================================================================
// Fichero:	PruebaFairness.java
// Tema:	para ver ejemplo de comportamiento
// Version:
// Fecha:	agosto-11
// Autor:   J. Ezpeleta
// Com.:compilar como "javac PruebaFairness.java"
//      ejecutar como "java practica1.ejemploclase.PruebaFairness | wc -l"
//      para ver cuantas veces sigue
//====================================================================
class PruebaFairness {

	public static void main(String args[]){
		DatosComunes dC;
		Sigo s;
		Acabo a;

		dC = new DatosComunes();
		s = new Sigo(dC);
		a = new Acabo(dC);

		s.start();
		a.start();
	}
}

