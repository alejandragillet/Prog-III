package ventanas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import logica.Cliente;
import logica.Discoteca;
import logica.EnumZona;
import logica.GestionDiscoteca;
import logica.Reserva;

@SuppressWarnings("serial")
// Ventana en la cual el cliente puede elegir la discotecaque quiere y reservar
// su entrada
// Idea futura : crear nueva clase Sesión para añadir la fecha de la reserva y
// que hayas varias sesiones distintos días
public class VentanaReservaEntradas extends JFrame {

	private JComboBox<Integer> numeroPersonas1; // combo numero de personas
	private JComboBox<Discoteca> comboDiscoteca; // combo con las discotecas
	private JComboBox<EnumZona> comboZona; // combo con la zona de la discoteca
	public static Discoteca disco2;
	public  Integer numeroPersonas;
	private JPanel panelSuperior;
	private JPanel panelCentral;
	private JPanel panelInferior;

	// Discoteca discoteca
	public VentanaReservaEntradas(GestionDiscoteca gDisco, Cliente cliente, Reserva reserva) throws CloneNotSupportedException {
		Container cp = this.getContentPane();
		this.setMinimumSize(new Dimension(400, 500));

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar calendar = Calendar.getInstance();

		Date dateObj = calendar.getTime();
		String formattedDate = dtf.format(dateObj);
		reserva.setFecha(formattedDate);
		

		// Creación comboBox
		numeroPersonas1 = new JComboBox<Integer>();
		comboDiscoteca = new JComboBox<Discoteca>();
		comboZona = new JComboBox<EnumZona>();
		disco2 = new Discoteca();

		// Creación paneles
		JPanel panelSuperior = new JPanel();
		JPanel panelInferior = new JPanel();
		JPanel panelCentral = new JPanel();
		panelSuperior.setLayout(new GridLayout(5, 1));
		panelCentral.setLayout(new GridLayout(4, 1));

		// Creación JButtons
		JButton jSeleccionar = new JButton("Seleccionar");
		jSeleccionar.setSize(new Dimension(50, 50));
		JButton jSeleccionar2 = new JButton("Seleccionar");
		jSeleccionar2.setSize(new Dimension(50, 50));
		JButton jSeleccionar3 = new JButton("Seleccionar");
		jSeleccionar3.setSize(new Dimension(50, 50));

		// Asignación de componentes a la ventana
		panelSuperior.add(new JLabel("Proceso reserva entradas del dia " + formattedDate));
		panelSuperior.add(new JLabel("Selecciona discoteca"));
		panelSuperior.add(comboDiscoteca);
		panelSuperior.add(jSeleccionar);
		cp.add(panelSuperior, BorderLayout.NORTH);
		cp.add(panelCentral);
		cp.add(panelInferior, BorderLayout.SOUTH);

		// Añadir al comboBox las discotecas que hay en la lista
		for (Discoteca discoteca : gDisco.getlDiscotecas()) {
			Discoteca dis = discoteca.clone();
			System.out.println(dis);
			comboDiscoteca.addItem(dis);
		}

		// Añade el nuevo combobox y guarda la discoteca seleccionada
		jSeleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelCentral.removeAll();
				disco2 = (Discoteca) comboDiscoteca.getSelectedItem();
				reserva.setDiscoteca(disco2);
				panelCentral.add(new JLabel("Zona discoteca: "
						+ "Mesa 12€ / Pista 17€ / VIP 25€"));
				panelCentral.add(comboZona);
				panelCentral.add(jSeleccionar2);
				VentanaReservaEntradas.this.repaint();
				comboZona.addItem(EnumZona.MESA);
				comboZona.addItem(EnumZona.PISTA);
				comboZona.addItem(EnumZona.VIP);
				VentanaReservaEntradas.this.pack();
			}
		});

		// Añade otro combobox para el numero de personas de la reserva
		jSeleccionar2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelInferior.removeAll();
				panelInferior.add(new JLabel("Numero de personas:"));
				for (int i = 1; i < 11; i++) {
					numeroPersonas1.addItem(i);
				}
				reserva.setZona((EnumZona) comboZona.getSelectedItem());
				panelInferior.add(numeroPersonas1);
				panelInferior.add(jSeleccionar3);
				VentanaReservaEntradas.this.pack();
			}
		});
		

		// compara a ver si el aforo de la discoteca se ha llenado y aparece un mensaje
		// en el caso de que este lleno
		jSeleccionar3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numeroPersonas = (Integer)numeroPersonas1.getSelectedItem();
				comprobarAforo(disco2, numeroPersonas);
				reserva.setNumeroPersonas((int) numeroPersonas1.getSelectedItem());
				VentanaReservaEntradas.this.repaint();
				VentanaReservaProductos vr = new VentanaReservaProductos(disco2, gDisco, cliente, VentanaReservaEntradas.this);
				vr.setVisible(true);
				dispose();

			}
		});

	}
	public void comprobarAforo(Discoteca disco2, Integer numeroPersonas) {
		Integer aforoDiscoteca = disco2.getAforo();
		aforoDiscoteca = aforoDiscoteca + numeroPersonas;
		if(aforoDiscoteca > disco2.getAforoMax()) {
			JOptionPane.showMessageDialog(VentanaReservaEntradas.this, "No quedan entradas disponibles.No es posible hacer la reserva. ");
		}
		else {
			disco2.setAforo(aforoDiscoteca);
			System.out.println("Aforo de mometo: " + aforoDiscoteca);
		}
		
		
	}
	
	public double calcularPrecioEntradas() {
		double precioEntrada = 0.0;
		if (comboZona.getSelectedItem().equals(EnumZona.VIP)) {
			precioEntrada = 25* (Integer) numeroPersonas1.getSelectedItem();
		}else if(comboZona.getSelectedItem().equals(EnumZona.PISTA)){
			precioEntrada = 17 * (Integer) numeroPersonas1.getSelectedItem();
		}else {
			precioEntrada = 12 * (Integer) numeroPersonas1.getSelectedItem();
		}
		return precioEntrada;
	}
	
	public int numeroPersonas() {
		int numeroP = (int) numeroPersonas1.getSelectedItem();
		return numeroP;
	}
	
	public EnumZona zonaSelec() {
		EnumZona zonaSelec = (EnumZona) comboZona.getSelectedItem();
		return zonaSelec;
	}
	
	public Discoteca discoSelec() {
		Discoteca discoSelec = (Discoteca) comboDiscoteca.getSelectedItem();
		return discoSelec;
	}


	
	

}
