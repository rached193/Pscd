package practica1.ejemploclase;
//====================================================================
// Fichero:	DatosComunes.java
// Tema:	para ver ejemplo de comportamiento
// Version:
// Fecha:	agosto-11
// Autor:   J. Ezpeleta
// Com.:    clase con los datos compartidos
//====================================================================


class DatosComunes{
	private boolean seguir = true;

	public void setSeguir(boolean valor){
		this.seguir = valor;
	}

	public boolean getSeguir(){
		return seguir;
	}
}
