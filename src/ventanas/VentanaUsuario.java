package ventanas;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

import javax.swing.*;

import logica.*;
import basedatos.BaseDeDatos;



public class VentanaUsuario extends JFrame {
	private JButton crearCliente;
	private JButton crearTrabajador;
	private GestionDiscoteca gs = new GestionDiscoteca();
	
	
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
				
				
				
				VentanaCliente vent= new VentanaCliente("Regis", gs);
				vent.setLocation( 620, 300 );
				vent.setVisible( true );
				
				
			}
		});
		
		crearCliente.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				
				VentanaCliente vent= new VentanaCliente("Regis", gs);
				vent.setLocation( 620, 300 );
				vent.setVisible( true );
				
				
			}
			
		});
		
		crearTrabajador.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				
				
				VentanaTrabajador vent= new VentanaTrabajador("Regis");
				vent.setLocation( 620, 300 );
				vent.setVisible( true );
				
				
			}
			
		});
		//Hilo para clicar y esperar unos segundos
		crearCliente.addActionListener(new ActionListener() {
			
			
			private void esperarXsegundos(int segundos) {
				try {
					Thread.sleep(segundos * 1000);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO LLAMADA A LA BASE DE DATOS PARA CARGAR DATOS
				GestionDiscoteca.lClientes = BaseDeDatos.clienteSelectAll(BaseDeDatos.usarBD(BaseDeDatos.initBD("DeustoDiscoBBDD")));
				GestionDiscoteca.lTrabajadores = BaseDeDatos.trabajadorSelectAll(BaseDeDatos.usarBD(BaseDeDatos.initBD("DeustoDiscoBBDD")));
				GestionDiscoteca.lDiscotecas = BaseDeDatos.DiscotecaSelectAll(BaseDeDatos.usarBD(BaseDeDatos.initBD("DeustoDiscoBBDD")));
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO ENVIAR DATOS/MODIFICACIONES A LA BBDD
				BaseDeDatos.guardarClientes(BaseDeDatos.usarBD(BaseDeDatos.initBD("DeustoDiscoBBDD")), gs.getlClientes());
				BaseDeDatos.guardarTrabajadores(BaseDeDatos.usarBD(BaseDeDatos.initBD("DeustoDiscoBBDD")), gs.getlTrabajadores());
				BaseDeDatos.guardarDiscotecas(BaseDeDatos.usarBD(BaseDeDatos.initBD("DeustoDiscoBBDD")), gs.getlDiscotecas());
			}

			
			
		});
	}
		
		
		//Main
		public static void main(String[] args) {
			VentanaUsuario ventana = new VentanaUsuario();
			ventana.setLocation( 620, 300 );
			ventana.setVisible( true );
			

	}
}

