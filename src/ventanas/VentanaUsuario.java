package ventanas;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

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
			Connection con = BaseDeDatos.initBD(BaseDeDatos.nombreBD);
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO LLAMADA A LA BASE DE DATOS PARA CARGAR DATOS
				try {
					BaseDeDatos.initBD(BaseDeDatos.nombreBD);
					GestionDiscoteca.lClientes = BaseDeDatos.clienteSelectAll(BaseDeDatos.usarBD(con));
					GestionDiscoteca.lTrabajadores = BaseDeDatos.trabajadorSelectAll(BaseDeDatos.usarBD(con));
					GestionDiscoteca.lDiscotecas = BaseDeDatos.DiscotecaSelectAll(BaseDeDatos.usarBD(con));
				} catch (NullPointerException e2) {}
				 
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO ENVIAR DATOS/MODIFICACIONES A LA BBDD
				try {
					BaseDeDatos.reiniciarBD(con);
					BaseDeDatos.usarCrearTablasBD(con);
					BaseDeDatos.guardarClientes(BaseDeDatos.usarBD(con), gs.getlClientes());
					BaseDeDatos.guardarTrabajadores(BaseDeDatos.usarBD(con), gs.getlTrabajadores());
					BaseDeDatos.guardarDiscotecas(BaseDeDatos.usarBD(con), gs.getlDiscotecas());
					BaseDeDatos.cerrarBD(con , BaseDeDatos.usarBD(con));
				} catch (NullPointerException e2) {}
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

