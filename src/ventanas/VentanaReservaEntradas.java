package ventanas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

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
@SuppressWarnings("serial")
public class VentanaReservaEntradas extends JFrame {
	
	private JComboBox<Integer> comboNumeroPersonas1;
	private JComboBox<Integer> comboNumeroPersonas2;
	private JComboBox<Integer> comboNumeroPersonas3;
	private JComboBox<Discoteca> comboDiscoteca;
	private JComboBox<EnumZona> comboZona;
	
	private JPanel panelSuperior;
	private JPanel panelCentral; 
	private JPanel panelInferior;
	
	public VentanaReservaEntradas(Discoteca discoteca) {
		Container cp= this.getContentPane();
		this.setMinimumSize(new Dimension(400, 200));
		
		// Creación comboBox
		comboNumeroPersonas1 = new JComboBox<Integer>();
		comboNumeroPersonas2 = new JComboBox<Integer>();
		comboNumeroPersonas3 = new JComboBox<Integer>();
		comboDiscoteca = new JComboBox<Discoteca>();
		comboZona = new JComboBox<EnumZona>();
		
		
		//Creación paneles
		JPanel panelSuperior = new JPanel();
		JPanel panelInferior = new JPanel();
		JPanel panelCentral = new JPanel(); 
		panelCentral.setLayout(new GridLayout(3,2));
		
		//Creación JButtons
		JButton JSeleccionar = new JButton("Seleccionar");
		JSeleccionar.setSize(new Dimension(50,50));
		JButton JSeleccionar2 = new JButton ("Seleccionar");
		JSeleccionar2.setSize(new Dimension(50,50));
		JButton JSeleccionar3 = new JButton ("Seleccionar");
		JSeleccionar3.setSize(new Dimension(50,50));
		
		//Asignación de componentes a la ventana
		panelSuperior.add(new JLabel("Selecciona discoteca"));
		panelCentral.add(new JLabel("Zona de reserva en la discoteca")); 
		panelCentral.add(comboDiscoteca);
		panelCentral.add(JSeleccionar3);
		panelCentral.add(comboZona);
		panelCentral.add(JSeleccionar);
		cp.add(panelSuperior, BorderLayout.NORTH);
		cp.add(panelCentral);
		cp.add(panelInferior, BorderLayout.SOUTH);
		
		
		
		JSeleccionar3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboDiscoteca.getSelectedItem().equals("Moma")) {
					panelCentral.add(new JLabel("Zona discoteca:"));
					panelCentral.add(comboZona);
					panelCentral.add(JSeleccionar);
				}else if (comboDiscoteca.getSelectedItem().equals("Budha")) {
					panelCentral.add(new JLabel("Zona discoteca:"));
					panelCentral.add(comboZona);
					panelCentral.add(JSeleccionar);
				}
				
			}
		});
		//JComboBox inferior en función de la zona deseada 
		JSeleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboZona.getSelectedItem().equals("VIP")) {
					panelInferior.removeAll();
					panelInferior.add(new JLabel("Precio: 20€/persona"));
					panelInferior.add(comboNumeroPersonas1);
					panelInferior.add(JSeleccionar2);
				}else if(comboZona.getSelectedItem().equals("Pista")) {
					panelInferior.removeAll();
					panelInferior.add(new JLabel("Precio: 10€/persona"));
					panelInferior.add(comboNumeroPersonas2);
					panelInferior.add(JSeleccionar2);
				}else if (comboZona.getSelectedItem().equals("Mesa")) {
					panelInferior.removeAll();
					panelInferior.add(new JLabel("Precio: 15€/persona"));
					panelInferior.add(JSeleccionar2);
					panelInferior.add(comboNumeroPersonas3);
				}
				VentanaReservaEntradas.this.repaint();
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
	
	

}
