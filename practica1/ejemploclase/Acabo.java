package practica1.ejemploclase;

class Acabo extends Thread implements Runnable {
	private DatosComunes datosComunes;

	public Acabo(DatosComunes d){
		this.datosComunes = d;
	}

	public void run(){
		datosComunes.setSeguir(false);
	}
}
