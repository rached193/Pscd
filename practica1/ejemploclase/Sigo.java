package practica1.ejemploclase;

class Sigo extends Thread{
	private DatosComunes datosComunes;

	public Sigo(DatosComunes d){
		this.datosComunes = d;
	}

	public void run(){
		int i = 0;
		while(datosComunes.getSeguir()){
			i++;
			System.out.println("Sigo: "+i);
		}
		System.out.println("Sigo: "+i);
	}
}

