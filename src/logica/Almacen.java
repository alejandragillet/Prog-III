package logica;

import java.util.ArrayList;
import java.util.Map;

public class Almacen {
	private Map<Producto, Integer> mapaProductoAlmacen;

	public Almacen(Map<Producto, Integer> mapa) {
		this.mapaProductoAlmacen = mapa;
	}

	public Map<Producto, Integer> getMapaProductoAlmacen() {
		return mapaProductoAlmacen;
	}

	public void setMapaProductoAlmacen(Map<Producto, Integer> mapaProductoAlmacen) {
		this.mapaProductoAlmacen = mapaProductoAlmacen;
	}

	@Override
	public String toString() {
		return "Almacen [mapaProductoAlmacen=" + mapaProductoAlmacen + "]";
	}

	public ArrayList<Producto> getProductos() {
		return new ArrayList<>(mapaProductoAlmacen.keySet());
	}
}
