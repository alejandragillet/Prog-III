package ventanas;

import java.awt.*;
import java.awt.event.*;

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

				VentanaTrabajador vent = new VentanaTrabajador("Regis", gs);
				vent.setLocation(620, 300);
				vent.setVisible(true);

			}

		});
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				GestionDiscoteca.LOG_RAIZ.info("Cargando datos desde la BD");

				try {
					if (gs.lClientes.size() == 0)
						gs.lClientes = BaseDeDatos.clienteSelectAll(BaseDeDatos.usarBD(BaseDeDatos.conexion));
					if (gs.lTrabajadores.size() == 0)
						gs.lTrabajadores = BaseDeDatos.trabajadorSelectAll(BaseDeDatos.usarBD(BaseDeDatos.conexion));
					if (gs.lDiscotecas.size() == 0)
						gs.lDiscotecas = BaseDeDatos.discotecaSelectAll(BaseDeDatos.usarBD(BaseDeDatos.conexion));
					if (gs.lProductos.size() == 0)
						gs.lProductos = BaseDeDatos.productosSelect(BaseDeDatos.usarBD(BaseDeDatos.conexion), null);

					GestionDiscoteca.LOG_RAIZ.info("DATOS CARGADOS desde la BD!");
				} catch (NullPointerException e2) {
					e2.printStackTrace();
				}

			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					BaseDeDatos.cerrarBD(BaseDeDatos.conexion, null);
					BaseDeDatos.reiniciarBD(BaseDeDatos.getConexion(BaseDeDatos.nombreBD)); // prevenir errores de
																							// conexiones pendientes
					BaseDeDatos.initBD(BaseDeDatos.nombreBD);

					BaseDeDatos.guardarClientes(BaseDeDatos.usarBD(BaseDeDatos.conexion), gs.getlClientes(), gs);
					BaseDeDatos.guardarTrabajadores(BaseDeDatos.usarBD(BaseDeDatos.conexion), gs.getlTrabajadores(),
							gs);
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
