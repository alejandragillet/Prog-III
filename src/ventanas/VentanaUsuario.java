package ventanas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import logica.GestionDiscoteca;
import logica.Reserva;




public class VentanaUsuario extends JFrame {
	private JButton crearCliente;
	private JButton crearTrabajador;
	
	
	
	public VentanaUsuario() {
		
		
		
		Container cp=this.getContentPane();
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(6, 2));
		
		crearCliente = new JButton("Eres usuario");
		crearTrabajador = new JButton("Eres trabajador");
		
		panelCentral.add(crearCliente);
		panelCentral.add(crearTrabajador);
		
		cp.add(panelCentral);
		
		setTitle("Eleccion usuario/trabajador");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		
		setVisible(true);
		
		crearCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				GestionDiscoteca gs = new GestionDiscoteca();
				
				VentanaCliente vent= new VentanaCliente("Regis", gs);
				vent.setLocation( 620, 300 );
				vent.setVisible( true );
				
				
			}
		});
		crearTrabajador.addActionListener (new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaTrabajador vent=new VentanaTrabajador("Inicio");
				vent.setLocation( 820, 500 );
				vent.setVisible( true );
			}
		});
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO LLAMADA A LA BASE DE DATOS PARA CARGAR DATOS
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO ENVIAR DATOS/MODIFICACIONES A LA BBDD
				
				
			}

			
			
		});
	}
		
		//Posible hilo en el momento de clicar boton para cliente
	
			
		

		
	
		
		
		//Main
		public static void main(String[] args) {
			VentanaUsuario ventana = new VentanaUsuario();
			ventana.setLocation( 620, 300 );
			ventana.setVisible( true );
			

	}
}

