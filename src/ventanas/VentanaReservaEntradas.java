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

import javax.swing.JFrame;
import javax.swing.JLabel;

import logica.Discoteca;
import logica.EnumZona;
import logica.GestionDiscoteca;
import logica.Reserva;
@SuppressWarnings("serial")
public class VentanaReservaEntradas extends JFrame {
	
	private JTextField numeroPersonas;
	private JComboBox<Discoteca> comboDiscoteca;
	private JComboBox<EnumZona> comboZona;
	private static GestionDiscoteca disco;
	
	
	private JPanel panelSuperior;
	private JPanel panelCentral; 
	private JPanel panelInferior;
	// Discoteca discoteca
	public VentanaReservaEntradas(GestionDiscoteca disco) {
		Container cp= this.getContentPane();
		this.setMinimumSize(new Dimension(400, 400));
		
		
		// Creación comboBox
		numeroPersonas = new JTextField();
		comboDiscoteca = new JComboBox<Discoteca>();
		comboZona = new JComboBox<EnumZona>();
		
		
		//Creación paneles
		JPanel panelSuperior = new JPanel();
		JPanel panelInferior = new JPanel();
		JPanel panelCentral = new JPanel(); 
		panelSuperior.setLayout(new GridLayout(5,1));
		panelCentral.setLayout(new GridLayout(4,1));
		
		//Creación JButtons
		JButton JSeleccionar = new JButton("Seleccionar");
		JSeleccionar.setSize(new Dimension(50,50));
		JButton JSeleccionar2 = new JButton ("Seleccionar");
		JSeleccionar2.setSize(new Dimension(50,50));
		JButton JSeleccionar3 = new JButton ("Seleccionar");
		JSeleccionar3.setSize(new Dimension(50,50));
		
		//Asignación de componentes a la ventana
		panelSuperior.add(new JLabel("Proceso reserva entradas"));
		panelSuperior.add(new JLabel("Selecciona discoteca"));
		panelSuperior.add(comboDiscoteca);
		
		
		panelSuperior.add(JSeleccionar);
		cp.add(panelSuperior, BorderLayout.NORTH);
		cp.add(panelCentral);
		cp.add(panelInferior, BorderLayout.SOUTH);
		for (Discoteca discoteca : disco.getlDiscotecas()) {
			comboDiscoteca.addItem(discoteca);
		}
		
		JSeleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelCentral.removeAll();
				Discoteca discoSelec = (Discoteca) comboDiscoteca.getSelectedItem();
				panelCentral.add(new JLabel("Zona discoteca")); 
				panelCentral.add(comboZona);
				panelCentral.add(JSeleccionar2);
				VentanaReservaEntradas.this.repaint();
				comboZona.addItem(EnumZona.MESA);
				comboZona.addItem(EnumZona.PISTA);
				comboZona.addItem(EnumZona.VIP);
			}
		});
		//JComboBox inferior en función de la zona deseada 
		JSeleccionar2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelInferior.removeAll();
				panelInferior.add(new JLabel("Numero de personas:"));
				panelInferior.add(numeroPersonas);
				panelInferior.add(JSeleccionar3);
				VentanaReservaEntradas.this.repaint();
				
			}
		});
		
		
		JSeleccionar3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		

		
//		JSeleccionar2.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int aforo2 = 0;
//				aforo2 = aforo2 + comboNumeroPersonas1.getSelectedItem();
//				if(discoteca.getAforo())
//			}
//		});
		
		
		
	}
	public static void main(String[] args) {
		Reserva reserva = new Reserva();
		VentanaReservaEntradas ventana = new VentanaReservaEntradas( disco);
		ventana.setVisible(true);
		
	}
	
	

}
