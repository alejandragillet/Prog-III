package logica;

import java.io.Serializable;

public abstract class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String nombre;
	protected double precio;
	protected Integer id;

	/**
	 * Crea un producto con nombre y precio
	 * 
	 * @param nombre
	 * @param precio
	 */
	public Producto(String nombre, double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Producto))
			return false;
		Producto p2 = (Producto) obj;
		return nombre.equals(p2.nombre);

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
