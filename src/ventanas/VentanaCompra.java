package ventanas;

import javax.swing.table.DefaultTableModel;

import logica.*;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;

public class VentanaCompra extends JFrame{
		public JPanel panelSuperior;
		public DefaultTableModel mDatos;
		public JTable tDatos;
		public JPanel panelInferior;
		public JPanel panelImporte;
		public JPanel panelCentral;
		
		public VentanaCompra(Reserva reserva, VentanaReservaProductos vPro,  Cliente cliente, GestionDiscoteca GestDisc) {
			this.setMinimumSize(new Dimension(400,400));
			
			// Panel superior
			panelSuperior = new JPanel();
			add(panelSuperior,BorderLayout.NORTH);
			JLabel lTitulo = new JLabel("A un paso de finalizar la reserva.");
			panelSuperior.add(lTitulo);
			
			// Panel inferior
			panelInferior = new JPanel();
			add(panelInferior, BorderLayout.SOUTH);
			JButton bVolver = new JButton("Volver a la reserva");
			JButton bFinalizar = new JButton ("Comprar");
			panelInferior.add(bVolver);
			panelInferior.add(bFinalizar);
			
			tDatos = new JTable();
			tDatos.setFont(new Font("Arial", Font.PLAIN, 14));
			panelImporte = new JPanel();
			panelCentral = new JPanel();
			getContentPane().add(panelCentral, BorderLayout.CENTER);
			getContentPane().add( new JScrollPane(tDatos), BorderLayout.CENTER );
			panelCentral.setLayout(new BorderLayout());
			
			panelCentral.add(panelImporte, BorderLayout.SOUTH);
			JLabel jPro = new JLabel("Productos seleccionados");
			panelCentral.add(jPro, BorderLayout.NORTH);
			JLabel jImporte =new JLabel("Importe final (€): ");
			panelImporte.add(jImporte);
			
			
			
			Vector<String> cabeceras = new Vector<String>(Arrays.asList("Prodcutos", "Cantidad"));
			mDatos = new DefaultTableModel( 
					new Vector<Vector<Object>>(),
					cabeceras 
				);
			toTableModel(reserva.getMapaProducto(),mDatos);
			
			tDatos.setModel(mDatos);
			panelCentral.add(tDatos, BorderLayout.CENTER);
			tDatos.getColumnModel().getColumn(0).setMinWidth(40);
			tDatos.getColumnModel().getColumn(0).setMaxWidth(40);
			tDatos.getColumnModel().getColumn(1).setMinWidth(40);
			tDatos.getColumnModel().getColumn(1).setMaxWidth(40);
			System.out.println("TDatos aa:" + tDatos);
			
			
			setTitle("RESERVA");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			// Vuelve a la ventanaLista donde puede seguir modificando su reserva de productos
			bVolver.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					vPro.setVisible(true);
					dispose();
				}
			});

			
			}
		
		
		
		public  DefaultTableModel toTableModel(HashMap<Producto, Integer> map, DefaultTableModel mDatos) {
			 for (Map.Entry<Producto, Integer> entry : map.entrySet()) {
			        mDatos.addRow(new Object[] { entry.getKey(), entry.getValue() });
			    }
			 return mDatos;
		}
}
