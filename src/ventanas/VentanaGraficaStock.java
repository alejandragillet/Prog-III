package ventanas;

import javax.swing.JFrame;

import logica.Producto;

import java.awt.*;
import java.util.Map;

class VentanaGraficaStock extends JFrame {

    public VentanaGraficaStock(Map<Producto, Integer> mapaStock) {
        super("Stock"); // llamo al constructor de JFrame con el titulo "Stock"
        setSize(800, 600);

        Grafica panel = new Grafica(); // Clase cogida de internet (como si fuera una librería)
        for (Map.Entry<Producto, Integer> entry : mapaStock.entrySet()) {
            panel.addHistogramColumn(entry.getKey().getNombre(), entry.getValue(), Color.BLACK);
        }

        panel.layoutHistogram(); // pintar el histograma

        this.add(panel); // añadimos el panel a la ventana
        this.pack();
    }
}