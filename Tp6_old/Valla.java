package Tp6_old;


import java.awt.Image;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.MediaTracker;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Valla implements Runnable {
	static final int NUM_IM_FILS = 2; // ventana con 2x2 im�genes
	static final int NUM_IM_COLS = 2;
	static final int PIX_SEP = 5; // p�xeles de separaci�n entre im�genes
	static final int IMAGENES=4;
	private Colamensajes mensajes;

	public Valla(Colamensajes mensajes) {
		this.mensajes = mensajes;

	}

	public void run() {
		final int NUMFILAS = 400; // redimensionar im�genes a NUMFILAS p�xeles
									// de altura
									// la anchura mantendr� proporcionalidad con
									// la imagen original

		JFrame frame = new JFrame("Imagenes"); // frame para colocar las
												// im�genes
		JFrame estado = new JFrame("Estado"); // Ventada de estado
		Container panel = frame.getContentPane(); // para acceder al panel del
													// frame
		Container panelestado = estado.getContentPane();
		JLabel[] label = new JLabel[NUM_IM_FILS * NUM_IM_COLS]; // un JLabel por
																// imagen
		String path = System.getProperty("user.dir")
				+ System.getProperty("file.separator");

		// Poner propiedades fundamentales del "frame"
		frame.setLocation(60, 0); // desplazar ventana 60 p�xeles
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				mensajes.kill();//finalizar el servicio si se cierra la ventana
			}
		});

		// -----------------------------------------------------------------------------
		// Preparar infraestructura para colocar las im�genes
		panel.setLayout(new GridLayout(NUM_IM_FILS, NUM_IM_COLS, PIX_SEP,
				PIX_SEP));
		Anuncio[] espacios = new Anuncio[NUM_IM_FILS * NUM_IM_COLS];
		Thread[] hilos = new Thread[NUM_IM_FILS * NUM_IM_COLS];
		for (int i = 0; i < IMAGENES; i++) {

			ImageIcon prueba = new ImageIcon(path + "prueba.jpg");//cargar imagen de prueba
			label[i] = new JLabel();
			//creo y ejecuto los hilos que se encargar�n de actualizar cada espacio de la valla
			espacios[i] = new Anuncio(i, mensajes, label, prueba, NUMFILAS);
			hilos[i] = new Thread(espacios[i]);
			panel.add(label[i]);
			hilos[i].start();

		}

		// dimensionar el "frame" adecuadamente para colocar las im�genes
		// 35 y 55 para dejar algo de margen, vertical y horizontal
		frame.setSize(35 + NUM_IM_COLS * NUMFILAS, 55 + NUM_IM_FILS * NUMFILAS);
		frame.setVisible(true);

	}
}
