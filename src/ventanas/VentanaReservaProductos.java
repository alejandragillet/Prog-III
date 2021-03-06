package ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import logica.Bebida;
import logica.Cliente;
import logica.Comida;
import logica.Discoteca;
import logica.GestionDiscoteca;
import logica.Producto;
import logica.Reserva;

/**
 * Ventana que permite al usuario hacer una reserva/ compra con antelanción para
 * el día de la reserva
 * 
 * @author Maria Perez
 *
 */
public class VentanaReservaProductos extends JFrame {

	private static final long serialVersionUID = 1L;
	// Componentes
	private JList<Producto> productosJList;
	private JLabel nombreInfo;
	private JLabel precioInfo;
	private JLabel tamanioInfo;
	private JLabel mezclaInfo;
	private JLabel alcoholInfo;
	private JLabel importeTotalInfo;
	private JLabel info1;
//	final Reserva reserva;

	// String nombre, Reserva reserva, Almacen almacen, Gestiondiscoteca Gs1
	public VentanaReservaProductos(Discoteca disco, GestionDiscoteca gDisco, Cliente cliente, VentanaReservaEntradas vre, Reserva reserva1) {
		this.setMinimumSize(new Dimension(800, 600));

		// Lista
		productosJList = new JList<Producto>();
		productosJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		JScrollPane productosScrollPane = new JScrollPane(productosJList);
		JPanel productosPanel = new JPanel();
		productosPanel.setLayout(new BorderLayout());
		JPanel nomPanel = new JPanel(new GridBagLayout());
		nomPanel.add(new JLabel("Productos"));
		productosPanel.add(nomPanel, BorderLayout.NORTH);
		productosPanel.add(productosScrollPane, BorderLayout.CENTER);
		add(productosPanel, BorderLayout.WEST);

		DefaultListModel listModel = new DefaultListModel();

		System.out.println(disco);

		gDisco.cargarFicheroBinarioProductos(gDisco.getlProductos(), "productos.dat");
		System.out.println("lista productos" + gDisco.getlProductos());

		// System.out.println("almacen " + disco.getAlmacen().getMapaProductoAlmacen());
		// Añade al productosJList una serie de productos
		for (Producto producto : gDisco.getlProductos()) {
			listModel.addElement(producto);
		}
		productosJList.setModel(listModel);

		// Actualiza las características del producto
		productosJList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				actualizarInfo();
			}
		});

		// FIn lista

		// Panel botones
		JPanel panelCentral = new JPanel(new BorderLayout());
		JPanel panelInferior = new JPanel();
		JButton bFinalizar = new JButton("Continuar");
		panelInferior.add(bFinalizar);
		JLabel importeTotal = new JLabel("Importe a pagar:");
		importeTotalInfo = new JLabel();
		panelInferior.add(importeTotal);
		panelCentral.add(panelInferior, BorderLayout.SOUTH);

		// Panel Información
		JPanel panelInformacionProductos = new JPanel();
		panelInformacionProductos.setLayout(new GridLayout(5, 2));
		Border border = BorderFactory.createTitledBorder("Información productos");
		panelInformacionProductos.setBorder(border);

		// Creación panel
		JPanel nombrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nombreLabel = new JLabel("Nombre: ");
		nombreInfo = new JLabel();
		nombrePanel.add(nombreLabel);
		nombrePanel.add(nombreInfo);
		panelInformacionProductos.add(nombrePanel);

		JPanel precioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel precioLabel = new JLabel("Precio: € ");
		precioInfo = new JLabel();
		precioPanel.add(precioLabel);
		precioPanel.add(precioInfo);
		panelInformacionProductos.add(precioPanel);

		JPanel tamanioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel tamanioLabel = new JLabel("Tamaño:  ");
		tamanioInfo = new JLabel();
		tamanioPanel.add(tamanioLabel);
		tamanioPanel.add(tamanioInfo);
		panelInformacionProductos.add(tamanioPanel);

		JPanel mezclaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel mezclaLabel = new JLabel("mezcla:  ");
		mezclaInfo = new JLabel();
		mezclaPanel.add(mezclaLabel);
		mezclaPanel.add(mezclaInfo);
		panelInformacionProductos.add(mezclaPanel);

		JPanel alcoholPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel alcoholLabel = new JLabel("alcohol:  ");
		alcoholInfo = new JLabel();
		alcoholPanel.add(alcoholLabel);
		alcoholPanel.add(alcoholInfo);
		panelInformacionProductos.add(alcoholPanel);

		JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel infoLabel = new JLabel(" ");
		info1 = new JLabel();
		infoPanel.add(infoLabel);
		infoPanel.add(info1);
		panelInformacionProductos.add(infoPanel);

		JButton bAnadir = new JButton("Añadir");
		JButton bEliminar = new JButton("Eliminar");
		JButton bGraficaStock = new JButton("Ver el stock");
		JButton bComprasPosibles = new JButton("Compras posibles");
		panelInformacionProductos.add(bAnadir);
		panelInformacionProductos.add(bEliminar);
		panelInformacionProductos.add(bGraficaStock);
		panelInformacionProductos.add(bComprasPosibles);

		panelCentral.add(panelInformacionProductos, BorderLayout.NORTH);

		add(panelCentral, BorderLayout.CENTER);

		// Panel productos añadidos
		JPanel panelMapa = new JPanel();
		Border border2 = BorderFactory.createTitledBorder("Carrito");
		panelMapa.setBorder(border2);
		panelCentral.add(panelMapa, BorderLayout.CENTER);

		// Actualización de los métodos de tanto el carrito como el importe
//		reserva = new Reserva();
//		reserva = reserva1;
//		System.out.println(reserva);
		actualizarCarrito(reserva1, panelMapa);
		// actualizarImporteTotal(reserva, panelInferior);
		actualizarImporteTotal(reserva1, panelInferior, vre);

		// Finaliza la búsqueda para comprar los productos del carrito
		bFinalizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Mia "+ reserva1);
				VentanaCompra vc = new VentanaCompra(reserva1.getMapaProducto(), VentanaReservaProductos.this, cliente,
						reserva1, disco, vre);
				vc.setVisible(true);
				VentanaReservaProductos.this.setVisible(false);
			}
		});
		// Añade al carrito (mapa) los productos seleccionados del productosJlist que
		// van apareciendo en el panel
		bAnadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Producto productoSeleccionado = productosJList.getSelectedValue();
				if (productoSeleccionado != null) {

					for (Map.Entry<Producto, Integer> entry : disco.getAlmacen().getMapaProductoAlmacen().entrySet()) {
						Producto key = entry.getKey();
						Integer value = entry.getValue();
						if (productoSeleccionado.equals(key)) {
							if (value != 0) {
								disco.actualizarAlmacenDico(productoSeleccionado);
								reserva1.anadirAlMapa(productoSeleccionado);
								actualizarCarrito(reserva1, panelMapa);
								actualizarImporteTotal(reserva1, panelInferior, vre);

								VentanaReservaProductos.this.repaint();

							} else {
								JOptionPane.showMessageDialog(VentanaReservaProductos.this, "No hay stock");
							}
							break;

						}

					}

				} else {
					JOptionPane.showMessageDialog(VentanaReservaProductos.this,
							"Ningún producto seleccionado. Porfavor seleccione alguno.");
				}
			}
		});

		// Elimina los productos seleccionados del carrito (mapa)
		bEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Producto productoSeleccionado = productosJList.getSelectedValue();
				System.out.println(disco.getAlmacen().getMapaProductoAlmacen());
				loop: for (Map.Entry<Producto, Integer> entry : reserva1.getMapaProducto().entrySet()) {
					Producto key = entry.getKey();
					Integer value = entry.getValue();
					if (productoSeleccionado.equals(key)) {
						if (value > 0) {
							disco.actualizarAlmacenDiscoBorrar(productosJList.getSelectedValue());
							reserva1.eliminarDelMapa(productosJList.getSelectedValue());
						} else {
							break loop;
						}
					}
				}
				System.out.println(disco.getAlmacen().getMapaProductoAlmacen());
				actualizarCarrito(reserva1, panelMapa);
				actualizarImporteTotal(reserva1, panelInferior, vre);
				VentanaReservaProductos.this.repaint();
			}

		});

		bGraficaStock.addActionListener(l -> { // java funcional
			VentanaGraficaStock v = new VentanaGraficaStock(disco.getAlmacen().getMapaProductoAlmacen());
			v.setVisible(true);
		});

		bComprasPosibles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String resp = JOptionPane.showInputDialog(VentanaReservaProductos.this, "Dinero disponible:", "5");
					if (resp == null)
						return; // No definida cantidad
					double dinero = Double.parseDouble(resp);
					gDisco.calcularComprasPosibles(dinero);
				} catch (NumberFormatException e2) {
				} // Error en cantidad - no se calcula nada
			}
		});
		setTitle("PRODUCTOS");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setMaximumSize(new Dimension(700, 500));
		this.repaint();

	}

	/**
	 * Actualiza la información
	 * 
	 */
	public void actualizarInfo() {
		if (productosJList.getSelectedIndex() != -1) {
			Producto producto = productosJList.getSelectedValue();
			System.out.println("Producto seleccionado:" + producto + "   Precio" + producto.getPrecio());
			nombreInfo.setText(producto.getNombre());
			precioInfo.setText(" " + producto.getPrecio());
			if (producto instanceof Comida) {
				mezclaInfo.setText(" ");
				alcoholInfo.setText(" ");
				tamanioInfo.setText(((Comida) producto).getTamanio());
			} else if (producto instanceof Bebida) {
				alcoholInfo.setText(((Bebida) producto).getAlcohol());
				mezclaInfo.setText(((Bebida) producto).getMezcla());
				tamanioInfo.setText(" ");

			}
		}
		VentanaReservaProductos.this.pack();
	}

	/**
	 * Actualiza el carrito a medida que se van añadiendo los productos al mapa
	 * 
	 * @param reserva
	 * @param jpanel
	 */
	public void actualizarCarrito(Reserva reserva, JPanel jpanel) {
		jpanel.removeAll();
		jpanel.setLayout(new GridLayout(reserva.getMapaProducto().size() + 1, 2));
		if (reserva.getMapaProducto().size() == 0) {
			jpanel.removeAll();
			jpanel.revalidate();

		} else {
			for (Map.Entry<Producto, Integer> entry : reserva.getMapaProducto().entrySet()) {
				Producto key = entry.getKey();
				Integer value = entry.getValue();
				JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JLabel valueLabel = new JLabel(" " + key);
				JLabel valueInfo = new JLabel(" " + value);
				valuePanel.add(valueLabel);
				valuePanel.add(valueInfo);
				jpanel.add(valuePanel);
			}
		}
		jpanel.revalidate();
	}

	/**
	 * Actualiza el importe a pagar cada vez que se va añadiendo productos al
	 * carrito (mapa)
	 * 
	 * @param reserva
	 * @param jpanel
	 */
	public void actualizarImporteTotal(Reserva reserva, JPanel jpanel, VentanaReservaEntradas vre) {
		double precioProducto = reserva.calcImporte();
		precioProducto = precioProducto + vre.calcularPrecioEntradas();
		importeTotalInfo.setText(Double.toString(precioProducto));
		jpanel.add(importeTotalInfo);
	}

	public void clear() {
		nombreInfo.setText(" ");
		precioInfo.setText(" ");
		tamanioInfo.setText(" ");
		mezclaInfo.setText(" ");
		alcoholInfo.setText(" ");
	}

}
