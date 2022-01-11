package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Discoteca implements Cloneable {
	protected String direccion;
	protected int aforoMax;
	protected int aforo;
	protected int numeroTrabajadores;
	protected String nombre;
	protected ArrayList<Reserva> lReserva;
	private Almacen almacen;

	public Discoteca(String direccion, int aforoMax, int numeroTrabajadores, String nombre, Almacen almacen) {
		this.direccion = direccion;
		this.aforoMax = aforoMax;
		this.numeroTrabajadores = numeroTrabajadores;
		this.nombre = nombre;
		this.aforo = 0;
		this.almacen = almacen;
	}

	public Discoteca(String nombre) {
		this.nombre = nombre;
	}

	public Discoteca() {

	}

	public Discoteca(String nombre, int aforoMax, int aforo, int numeroTrabajadores, String direccion) {
		this.direccion = direccion;
		this.aforoMax = aforoMax;
		this.numeroTrabajadores = numeroTrabajadores;
		this.nombre = nombre;
		this.aforo = aforo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getAforo() {
		return aforo;
	}

	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

	public int getNumeroTrabajadores() {
		return numeroTrabajadores;
	}

	public void setNumeroTrabajadores(int numeroTrabajadores) {
		this.numeroTrabajadores = numeroTrabajadores;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAforoMax() {
		return aforoMax;
	}

	public void setAforoMax(int aforomax) {
		this.aforoMax = aforomax;
	}

	public ArrayList<Reserva> getlReserva() {
		return lReserva;
	}

	public void setlReserva(ArrayList<Reserva> lReserva) {
		this.lReserva = lReserva;
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public Discoteca clone() throws CloneNotSupportedException {
		Discoteca b = (Discoteca) super.clone();
		return b;
	}

	public void actualizarAlmacenDico(Producto producto) {
		Integer stock = almacen.getMapaProductoAlmacen().get(producto);
		if (stock != null) {
			almacen.getMapaProductoAlmacen().put(producto, stock - 1);
		}
		// for (Map.Entry<Producto, Integer> entry :
		// almacen.getMapaProductoAlmacen().entrySet()) {
		// Producto key = entry.getKey();
		// Integer value = entry.getValue();
		// if (producto.equals(key)) {
		// almacen.getMapaProductoAlmacen().replace(key, value - 1);
		// }
		// }
	}

	public void actualizarAlmacenDiscoBorrar(Producto producto) {
		for (Map.Entry<Producto, Integer> entry : almacen.getMapaProductoAlmacen().entrySet()) {
			Producto key = entry.getKey();
			Integer value = entry.getValue();
			if (producto.equals(key)) {
				almacen.getMapaProductoAlmacen().replace(key, value + 1);
			}

		}
	}

}
