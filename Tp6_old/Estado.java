package Tp6_old;
//Muestra las estad�sticas del sistema en una ventana


import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Estado implements ActionListener {

	private JPanel panel;
	private JButton boton;
	private JLabel hora;
	private JLabel cola;
	private JLabel clientes;
	private Colamensajes mensajes;

	public Estado() {
		super();
	}

	public void addMonitor(Colamensajes mensajes) {
		//recibe el monitor del sistema y pone en funcionamiento el reloj
		this.mensajes = mensajes;
		Reloj reloj = new Reloj();
		Thread clock = new Thread(reloj);
		clock.start();
	}

	public Component createComponents() {
		//Devuelve un Jpanel con las componentes que muestran las estad�sticas
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		boton = new JButton("Terminar servicio");
		boton.setMnemonic(KeyEvent.VK_C);
		boton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 1;
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.weightx = 0.15;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.CENTER;
		panel.add(boton, constraints);

		JLabel label = new JLabel(" ");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.15;
		constraints.weighty = 0.9;
		panel.add(label, constraints);

		clientes = new JLabel("N� de clientes conectados:   ");
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0.20;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.BOTH;
		panel.add(clientes, constraints);

		hora = new JLabel();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		hora.setText(sdf.format(new Date(System.currentTimeMillis())));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0.15;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.BOTH;
		panel.add(hora, constraints);

		JLabel cola1 = new JLabel("Pr�ximos servicios: ");
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = 1;
		constraints.weightx = 0.80;
		constraints.weighty = 0;
		panel.add(cola1, constraints);
		
		cola = new JLabel("");
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.weightx = 0.80;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		panel.add(cola, constraints);

		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Informa al sistema de que el servidor tiene que finalizar su servicio al pulsar
		//el bot�n
		mensajes.kill();

	}

	public void actualizar(ArrayList<String> cola, int clientes) {
		//actualiza la informaci�n del sistema, invocado cada vez
		//que ocurre un cambio en el estado del monitor
		this.clientes.setText("N� de clientes conectados: " + clientes);

		String servicios = "";
		if (cola != null) {
			for (int i = 0; i < cola.size(); i++) {
				servicios = servicios + '\n' + cola.get(i);

			}

			this.cola.setText(servicios);
		}

	}

	public class Reloj implements Runnable {
//hilo encargado de actualizar el reloj cada medio segundo
		public void run() {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

				hora.setText(sdf.format(new Date(System.currentTimeMillis())));
			}

		}
	}

}
