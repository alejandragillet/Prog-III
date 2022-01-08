package ventanas;

import logica.Producto;
import javax.swing.JFrame;

//public class VentanaCompra extends JFrame{

//for (Map.Entry<Producto, Integer> entry : mapaStock.entrySet()) {
//    panel.addHistogramColumn(entry.getKey().getNombre(), entry.getValue(), Color.BLACK);
//}
//}
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class VentanaCompra extends JFrame {

    DefaultTableModel tbModel = new DefaultTableModel();

    public VentanaCompra(HashMap<Producto, Integer> mapaStock) {
        super("Ejemplo 1");

        tbModel.addColumn( "nombre producto");
        tbModel.addColumn( "cantidad");
        tbModel.addColumn( "precio");


        for (Map.Entry<Producto, Integer> entry : mapaStock.entrySet()) {
            tbModel.addRow(new String[] {entry.getKey().getNombre(), entry.getValue().toString(), new Double(entry.getKey().getPrecio()).toString()});
            //tbModel.addRow(new Object[] {entry.getKey().getNombre(), entry.getValue(),  entry.getKey().getPrecio()});


        }

        // array de String's con los títulos de las columnas


        // se crea la Tabla
        final JTable table = new JTable(tbModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));


        //table.setModel(tbModel); le mete a la tabla el model
      

        // Creamos un JscrollPane y le agregamos la JTable
        JScrollPane scrollPane = new JScrollPane(table);

        // Agregamos el JScrollPane al contenedor
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
