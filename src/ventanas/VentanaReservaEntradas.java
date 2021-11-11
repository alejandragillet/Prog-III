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
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import logica.Discoteca;
import logica.EnumZona;
import logica.GestionDiscoteca;
import logica.Reserva;
@SuppressWarnings("serial")
public class VentanaReservaEntradas extends JFrame {
	
	private JComboBox<Integer> numeroPersonas1;
	private JComboBox<Discoteca> comboDiscoteca;
	private JComboBox<EnumZona> comboZona;
	private static Discoteca disco2;
	private static GestionDiscoteca GDisco;
	
	
	private JPanel panelSuperior;
	private JPanel panelCentral; 
	private JPanel panelInferior;
	// Discoteca discoteca
	public VentanaReservaEntradas(GestionDiscoteca gDisco) throws CloneNotSupportedException {
		Container cp= this.getContentPane();
		this.setMinimumSize(new Dimension(400, 400));
		
		
		// Creación comboBox
		numeroPersonas1 = new JComboBox<Integer>();
		comboDiscoteca = new JComboBox<Discoteca>();
		comboZona = new JComboBox<EnumZona>();
		disco2 = new Discoteca();
		
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
		

		for (Discoteca discoteca : gDisco.getlDiscotecas()) {
			Discoteca dis = discoteca.clone();
			System.out.println(dis);
			comboDiscoteca.addItem(dis);
			
			
		}
		

		JSeleccionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelCentral.removeAll();
				disco2 =(Discoteca) comboDiscoteca.getSelectedItem();
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
					for (int i = 1; i < 11; i++) {
						numeroPersonas1.addItem(i);
					}
				panelInferior.add(numeroPersonas1);
				panelInferior.add(JSeleccionar3);
				VentanaReservaEntradas.this.repaint();
			}
		});

		JSeleccionar3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(disco2);
				Integer aforoDiscoteca = disco2.getAforo();
				aforoDiscoteca = aforoDiscoteca + (Integer)numeroPersonas1.getSelectedItem();
				if(aforoDiscoteca > disco2.getAforoMax()) {
					JOptionPane.showMessageDialog(VentanaReservaEntradas.this, "No quedan entradas disponibles.No es posible hacer la reserva. ");
					
				}
				else {
					disco2.setAforo(disco2.getAforo() +1);
				}
				VentanaReservaEntradas.this.repaint();
				VentanaReservaProductos vr = new VentanaReservaProductos(disco2,gDisco);
				vr.setVisible(true);
				dispose();
				
			}
		});
		
		

		
		
		
		
	}
//	public static void main(String[] args) {
//		Reserva reserva = new Reserva();
//		GestionDiscoteca GestionDisco = new GestionDiscoteca();
//		VentanaReservaEntradas ventana = new VentanaReservaEntradas(GDisco, disco2);
//		ventana.setVisible(true);
//		
//	}
	
	

}
