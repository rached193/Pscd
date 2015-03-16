package Tp6_old;
//La clase Anuncio se en carga de la actualizaci�n de los anuncios en uno de los huecos
//de la valla


import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Anuncio implements Runnable {
	private int id; //identificador para saber qu� label actualizar
	private Colamensajes mensajes; //monitor del sistema
	private JLabel[] label; //vector de labels
	private ImageIcon muestra;//imagen por defecto
	private int filas;//n�mero de filas de la valla para aplicar el escalado

	public Anuncio(int id, Colamensajes mensajes, JLabel[] label,
			ImageIcon muestra, int filas) {
		this.filas = filas;
		this.id = id;
		this.mensajes = mensajes;
		this.label = label;
		this.muestra = muestra;
		this.label[id].setIcon(muestra);

	}

	public void run() {
		ImageIcon imagen;
		Imagen anuncio;
		int tiempo;
		//mientras est� prestando servicio o a�n queden anuncios por mostrar
		//sigo actualizando mi hueco
		while (mensajes.isAlive() || mensajes.TotalImagenes() > 0) {
			label[id].setIcon(muestra);
			anuncio = mensajes.CargarImagen();
			imagen = anuncio.getImagen();
			ImageIcon imfinal = new ImageIcon(imagen.getImage()
					.getScaledInstance(filas, -1, Image.SCALE_SMOOTH));//aplicar escalado
			tiempo = anuncio.getTiempo();

			label[id].setIcon(imfinal);
			try {
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}

		}
		label[id].setIcon(muestra);
	}

}
