package ventanas;

import logica.Cliente;
import logica.Discoteca;
import logica.EnumZona;
import logica.Producto;
import logica.Reserva;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//public class VentanaCompra extends JFrame{

//for (Map.Entry<Producto, Integer> entry : mapaStock.entrySet()) {
//    panel.addHistogramColumn(entry.getKey().getNombre(), entry.getValue(), Color.BLACK);
//}
//}
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import basedatos.BaseDeDatos;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class VentanaCompra extends JFrame {

	DefaultTableModel tbModel = new DefaultTableModel();
	public JPanel panelSuperior;
	public DefaultTableModel mDatos;
	public JTable tDatos;
	public JPanel panelInferior;
	public JPanel panelImporte;
	public JPanel panelCentral;

	public VentanaCompra(HashMap<Producto, Integer> mapaStock, VentanaReservaProductos vPro, Cliente cliente,
			Reserva reserva, Discoteca disco, VentanaReservaEntradas vre) {

		// Panel superior
		panelSuperior = new JPanel();
		add(panelSuperior, BorderLayout.NORTH);
		JLabel lTitulo = new JLabel("Resumen de tu reserva.");
		panelSuperior.add(lTitulo);

		// Panel inferior
		panelInferior = new JPanel();
		add(panelInferior, BorderLayout.SOUTH);
		JButton bVolver = new JButton("Volver a la reserva");
		JButton bFinalizar = new JButton("Finalizar");
		panelInferior.add(bVolver);
		panelInferior.add(bFinalizar);
		panelImporte = new JPanel();
		panelInferior.add(panelImporte, BorderLayout.NORTH);
		JLabel jImporte = new JLabel("Importe final (€): ");
		panelImporte.add(jImporte);
		vPro.actualizarImporteTotal(reserva, panelImporte, vre);

		setSize(500, 500);
		tbModel.addColumn("nombre producto");
		tbModel.addColumn("cantidad");
		tbModel.addColumn("precio");

		for (Map.Entry<Producto, Integer> entry : mapaStock.entrySet()) {
			tbModel.addRow(new String[] { entry.getKey().getNombre(), entry.getValue().toString(),
					new Double(entry.getKey().getPrecio()).toString() });
			// tbModel.addRow(new Object[] {entry.getKey().getNombre(), entry.getValue(),
			// entry.getKey().getPrecio()});
		}

		int numeroPersonas = vre.numeroPersonas();
		EnumZona zona = vre.zonaSelec();
		Discoteca discoteca = vre.discoSelec();
		double precio = vre.calcularPrecioEntradas();
		double precioPro = reserva.calcImporte();

		tbModel.addRow(new String[] { "Precio de los Productos: ", precioPro + " ", null });
		tbModel.addRow(new String[] { "Discoteca: ", discoteca + " ", null });
		tbModel.addRow(new String[] { "Numero de Personas: ", numeroPersonas + " ", null });
		tbModel.addRow(new String[] { "Zona: ", zona + " ", null });
		tbModel.addRow(new String[] { "Precio de las Entradas: ", precio + " ", null });

		// array de String's con los títulos de las columnas

		// se crea la Tabla
		final JTable table = new JTable(tbModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));

		// table.setModel(tbModel); le mete a la tabla el model

		// Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(table);

		// Agregamos el JScrollPane al contenedor
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		setTitle("RESERVA");

		// Vuelve a la ventanaLista donde puede seguir modificando su reserva de
		// productos
		bVolver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vPro.setVisible(true);
				dispose();
			}
		});

		bFinalizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// cliente.getlReservas().add(reserva);

				Thread hilo = new Thread() {
					@Override
					public void run() {
						int tiempo = 0;
						getContentPane().removeAll();
						panelInferior.removeAll();
						JLabel lReserva = new JLabel();
						getContentPane().add(lReserva, BorderLayout.CENTER);

						while (tiempo < 5) {
							VentanaCompra.this.setMaximumSize(new Dimension(100, 100));
							if (tiempo == 0) {
								lReserva.setText("                                          Verificando compra...");
							} else if (tiempo == 3) {
								lReserva.setText("     Finalizada la compra. Le llegará un mensaje de verificación.");
							}

							VentanaCompra.this.repaint();
							tiempo += 1;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}

					}
				};

				hilo.start();
				System.out.println("Reserva Enum" + reserva.getZona());
				BaseDeDatos.registraReserva(cliente, reserva, disco);
				for (Map.Entry<Producto, Integer> entry : mapaStock.entrySet()) {
					Producto key = entry.getKey();
					Integer value = entry.getValue();
					//BaseDeDatos.registrarMapaProducto(key, reserva, value);
				}
				//BaseDeDatos.actualizarAforoDisco(disco, vre);
				//BaseDeDatos.actualizarDiscoteca(disco);
				
				
			}
		});
		cliente.setNombre(reserva.getNombreC());
	}


}
