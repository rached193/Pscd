package Tp6_old;


import javax.swing.ImageIcon;

public class Imagen {

	private ImageIcon imagen;
	private int tiempo;

	public Imagen(ImageIcon imagen, int tiempo) {
		this.imagen = imagen;
		this.tiempo = tiempo;
	}

	public ImageIcon getImagen() {
		return this.imagen;
	}

	public int getTiempo() {
		return this.tiempo;
	}
}
