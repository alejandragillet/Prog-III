package logica;
import java.util.ArrayList;
import java.util.HashMap;

public class Almacen {
	private HashMap<Producto, Integer> mapaProductoAlmacen;

	public Almacen(HashMap<Producto, Integer> mapa) {
		this.mapaProductoAlmacen = mapaProductoAlmacen;
	}

	public HashMap<Producto, Integer> getMapaProductoAlmacen() {
		return mapaProductoAlmacen;
	}

	public void setMapaProductoAlmacen(HashMap<Producto, Integer> mapaProductoAlmacen) {
		this.mapaProductoAlmacen = mapaProductoAlmacen;
	}

	@Override
	public String toString() {
		return "Almacen [mapaProductoAlmacen=" + mapaProductoAlmacen + "]";
	}
	
	
	
	
	
	
	
}
