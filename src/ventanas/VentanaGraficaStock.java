package ventanas;

import javax.swing.JFrame;

import logica.Producto;

import java.awt.*;
import java.util.Map;

class VentanaGraficaStock extends JFrame {

    public VentanaGraficaStock(Map<Producto, Integer> mapaStock) {
        super("Stock");
        setSize(800, 600);

        Grafica panel = new Grafica();
        for (Map.Entry<Producto, Integer> entry : mapaStock.entrySet()) {
            panel.addHistogramColumn(entry.getKey().getNombre(), entry.getValue(), Color.BLACK);
        }
        panel.layoutHistogram();

        this.add(panel);
        this.pack();
    }
}