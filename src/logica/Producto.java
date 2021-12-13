package logica;
public abstract class Producto {
	protected String nombre;
	protected double precio;
	
	public Producto(String nombre, double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
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

	public double getPrecio() {
		return precio;
	}
		
	

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Producto)) return false;
		Producto p2 = (Producto) obj;
		return nombre.equals(p2.nombre);
			
		
	}
    
}
