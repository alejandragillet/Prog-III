package logica;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reserva {
	protected double importe;
	protected String fecha;
	protected HashMap<Producto, Integer> mapaProducto = new HashMap<Producto, Integer>();
	protected int numeroPersonas;
	protected EnumZona zona;
	private String nombre;
	protected String discoteca;

	public Reserva() {
		this.importe = 0;
		this.mapaProducto = mapaProducto;
		this.numeroPersonas = numeroPersonas;
		this.zona = zona;
		this.discoteca = discoteca;

	}


	public Reserva(String fecha, String nombre, String discoteca, EnumZona zona, int numeroPersonas, int importe) {
		this.fecha = fecha;
		this.nombre = nombre;
		this.discoteca = discoteca;
		this.zona = zona;
		this.numeroPersonas = numeroPersonas;
		this.importe = importe;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public HashMap<Producto, Integer> getMapaProducto() {
		return mapaProducto;
	}

	public void setMapaProducto(HashMap<Producto, Integer> mapaProducto) {
		this.mapaProducto = mapaProducto;
	}

	public int getNumeroPersonas() {
		return numeroPersonas;
	}

	public void setNumeroPersonas(int numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}

	public EnumZona getZona() {
		return zona;
	}

	public void setZona(EnumZona zona) {
		zona = zona;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDiscoteca() {
		return discoteca;
	}

	public void setDiscoteca(String discoteca) {
		this.discoteca = discoteca;
	}
	public double calcImporte() {
		importe = 0;
		for (Map.Entry<Producto, Integer> entry : mapaProducto.entrySet()) {
			Producto key = entry.getKey();
			Integer value = entry.getValue();
			importe = importe + (key.getPrecio() * value);

		}
		return importe;
	}

	public void anadirAlMapa(Producto pNuevo) {
		
		if (mapaProducto.size() == 0) {

			mapaProducto.put(pNuevo, 1);

		} else {
			try {
				for (Map.Entry<Producto, Integer> entry : mapaProducto.entrySet()) {
					Producto key = entry.getKey();
					Integer value = entry.getValue();
					if (pNuevo.equals(key)) {
						mapaProducto.replace(key, value + 1);
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mapaProducto.put(pNuevo, 1);
		

	}

	public void eliminarDelMapa(Producto producto) {
		try {
			// arraylist to save the products to be removed
			ArrayList<Producto> productosAEliminar = new ArrayList<Producto>();
			for (Map.Entry<Producto, Integer> entry : mapaProducto.entrySet()) {
				Producto key = entry.getKey();
				Integer value = entry.getValue();
				if (producto.getNombre().equals(key.getNombre())) {
					if (value == 1) {
						productosAEliminar.add(key); // para prevenir java.util.ConcurrentModificationException
						return;
					} else {
						mapaProducto.replace(key, value - 1);
						return;
					}
				}
			}

			for (Producto productoAEliminar : productosAEliminar) {
				mapaProducto.remove(productoAEliminar);
			}
		} catch (Exception e) {

		}
	}

}
