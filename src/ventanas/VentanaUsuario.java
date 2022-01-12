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

	public VentanaUsuario(GestionDiscoteca gs) {

		Container cp = this.getContentPane();

		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(6, 2));

		crearCliente = new JButton("Eres usuario");
		crearTrabajador = new JButton("Eres trabajador");

		panelCentral.add(crearCliente);
		panelCentral.add(crearTrabajador);

		cp.add(panelCentral);

		setTitle("Eleccion usuario/trabajador");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();

		crearCliente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VentanaCliente vent = new VentanaCliente("Regis", gs);
				vent.setLocation(620, 300);
				vent.setVisible(true);

			}
		});

		crearTrabajador.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				VentanaTrabajador vent = new VentanaTrabajador("Regis");
				vent.setLocation(620, 300);
				vent.setVisible(true);

			}

		});
		// Hilo para clicar y esperar unos segundos
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
				try {
					if (gs.lClientes.size() == 0)
						gs.lClientes = BaseDeDatos.clienteSelectAll(BaseDeDatos.usarBD(BaseDeDatos.conexion));
					if (gs.lTrabajadores.size() == 0)
						gs.lTrabajadores = BaseDeDatos.trabajadorSelectAll(BaseDeDatos.usarBD(BaseDeDatos.conexion));
					if (gs.lDiscotecas.size() == 0)
						gs.lDiscotecas = BaseDeDatos.DiscotecaSelectAll(BaseDeDatos.usarBD(BaseDeDatos.conexion));
				} catch (NullPointerException e2) {
					
					e2.printStackTrace();
				}

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO ENVIAR DATOS/MODIFICACIONES A LA BBDD
				try {
					BaseDeDatos.reiniciarBD(BaseDeDatos.conexion); // no tiene sentido borrar la BD al cerral el
					// programa
					BaseDeDatos.usarCrearTablasBD(BaseDeDatos.conexion); // same
					BaseDeDatos.guardarClientes(BaseDeDatos.usarBD(BaseDeDatos.conexion), gs.getlClientes(), gs);
					BaseDeDatos.guardarTrabajadores(BaseDeDatos.usarBD(BaseDeDatos.conexion), gs.getlTrabajadores(), gs);
					BaseDeDatos.guardarDiscotecas(BaseDeDatos.usarBD(BaseDeDatos.conexion), gs.getlDiscotecas(), gs);
					BaseDeDatos.cerrarBD(BaseDeDatos.conexion, BaseDeDatos.usarBD(BaseDeDatos.conexion));
				} catch (NullPointerException e2) {
					// NUNCA dejar los catch sin nada, porque si falla no te enteras
					e2.printStackTrace();
				}
			}

		});
	}
}
