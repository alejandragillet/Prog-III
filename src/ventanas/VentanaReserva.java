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

import logica.Gestiondiscoteca;
import logica.Producto;
import logica.Reserva;

public class VentanaReserva extends JFrame{
	
	//Componentes
	private JList<Producto> productosJList;
	private JLabel nombreInfo;
	private JLabel precioInfo;
	private JLabel importeTotalInfo;
	
	
	public VentanaReserva(String nombre,  Reserva reserva, Gestiondiscoteca Gs1) {
		this.setMaximumSize(new Dimension(700,500));
		
		//Lista
		productosJList = new JList<Producto>();
		productosJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		JScrollPane productosScrollPane = new JScrollPane(productosJList);
		JPanel productosPanel = new JPanel();
		productosPanel.setLayout(new BorderLayout());
		JPanel nomPanel = new JPanel( new GridBagLayout());
		nomPanel.add(new JLabel("Productos"));
		productosPanel.add(nomPanel,BorderLayout.NORTH);
		productosPanel.add(productosScrollPane, BorderLayout.CENTER);
		add(productosPanel, BorderLayout.WEST);
		
		// Actualiza las características del producto
		productosJList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				actualizarInfo();
				
			}
		});
		
//		DefaultListModel listModel = new DefaultListModel<>();
//		for (int i=0 i<)
		
		//Panel botones
		JPanel panelCentral = new JPanel(new BorderLayout());
		JPanel panelInferior = new JPanel(); 
//		JButton bVolver = new JButton ("Volver");
//		panelInferior.add(bVolver);
		JButton bFinalizar = new JButton ("Finalizar");
		panelInferior.add(bFinalizar);
		JLabel importeTotal = new JLabel ("Importe a pagar:");
		importeTotalInfo = new JLabel();
		panelInferior.add(importeTotal);
		panelCentral.add(panelInferior,BorderLayout.SOUTH);
		
		//Panel Información 
		JPanel panelInformacionProductos = new JPanel();
		panelInformacionProductos.setLayout(new GridLayout(1, 2));
		Border border= BorderFactory.createTitledBorder("Información productos");
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
		precioPanel.add(precioInfo );
		panelInformacionProductos.add(precioPanel);
		
		JButton bAnadir = new JButton ("Añadir");
		JButton bEliminar = new JButton ("Eliminar");
		panelInformacionProductos.add(bAnadir);
		panelInformacionProductos.add(bEliminar);
		
	panelCentral.add(panelInformacionProductos,BorderLayout.NORTH);
		
		add(panelCentral,BorderLayout.CENTER);
		
		// Panel productos añadidos
		JPanel panelMapa = new JPanel();
		Border border2 = BorderFactory.createTitledBorder("Carrito");
		panelMapa.setBorder(border2);
		panelCentral.add(panelMapa,BorderLayout.CENTER);
			
		
		// Actualización de los métodos de tanto el carrito como el importe 
		actualizarCarrito(reserva, panelMapa);
		actualizarImporteTotal(reserva, panelInferior);
		
//		// Vuelve a la ventana de filtros para poder buscar productos distintos
//		bVolver.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				VentanaFiltros vf = new VentanaFiltros("Filtros",Sm1,venta,usuario);
//				vf.setVisible(true);
//				dispose();
//			}
//		});
		
//		// Finaliza la búsqueda para comprar los productos del carrito 
//		bFinalizar.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				VentanaCompra vc = new VentanaCompra(venta,VentanaLista.this, usuario,Sm1);
//				vc.setVisible(true);
//				VentanaLista.this.setVisible(false); 
//			}
//		});
//		
		// Añade al carrito (mapa) los productos seleccionados del productosJlist que van apareciendo en el panel
		bAnadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(productosJList.getSelectedValue()!=null) {
					reserva.anadirAlMapa(productosJList.getSelectedValue());
					actualizarCarrito(reserva,panelMapa);
					actualizarImporteTotal(reserva, panelInferior);
					VentanaReserva.this.repaint();
				}
				else {
					JOptionPane.showMessageDialog(VentanaReserva.this, "Ningún producto seleccionado. Porfavor seleccione alguno.");
				}
				
			}
		});
		
		// Elimina los productos seleccionados del carrito (mapa) 
		bEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reserva.eliminarDelMapa(productosJList.getSelectedValue());
				actualizarCarrito(reserva,panelMapa );
				actualizarImporteTotal(reserva, panelInferior);
				
			}
		});
		setTitle("PRODUCTOS");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		

	}
	
	/** Actualiza la información
	 * 
	 */
	public void actualizarInfo() {
		if (productosJList.getSelectedIndex() !=-1) {
			Producto producto = productosJList.getSelectedValue();
			nombreInfo.setText(producto.getNombre());
			precioInfo.setText( " " + producto.getPrecio());
		}
	}
	
	/** Actualiza el carrito a medida que se van añadiendo los productos al mapa
	 * @param reserva
	 * @param jpanel
	 */
	public void actualizarCarrito(Reserva reserva, JPanel jpanel) {
		jpanel.removeAll();
		jpanel.setLayout(new GridLayout(reserva.getMapaProducto().size() + 1,2));
		if(reserva.getMapaProducto().size() == 0) {
			jpanel.removeAll();
			jpanel.revalidate();
			
		}else {
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
	
	/**Actualiza el importe a pagar cada vez que se va añadiendo productos al carrito (mapa)
	 * @param reserva
	 * @param jpanel
	 */
	public void actualizarImporteTotal(Reserva reserva, JPanel jpanel) {
		importeTotalInfo.setText(Double.toString(reserva.calcImporte()));
		jpanel.add(importeTotalInfo);
	}
	
	public void clear() {
		nombreInfo.setText(" ");
		precioInfo.setText(" ");
	}
	

}
