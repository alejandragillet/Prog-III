package logica;
public abstract class Producto {
	protected String nombre;
	protected double precio;
	
	public Producto(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Producto() {
		super();
		this.nombre = "";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public abstract double getPrecio();
	
	

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + "]";
	}
    
}
