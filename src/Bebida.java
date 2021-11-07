
public class Bebida extends Producto{
	private double cl;
	private double precioPorCl;
	
	public Bebida(String nombre, double cl, double precioPorCl) {
		super(nombre);
		this.cl = cl;
		this.precioPorCl = precioPorCl;
	}
	
	public Bebida() {
		super();
	}

	public double getCl() {
		return cl;
	}

	public void setCl(double cl) {
		this.cl = cl;
	}

	public double getPrecioPorCl() {
		return precioPorCl;
	}

	public void setPrecioPorCl(double precioPorCl) {
		this.precioPorCl = precioPorCl;
	}

	@Override
	public double getPrecio() {
		return cl * precioPorCl;
	}

	@Override
	public String toString() {
		return getNombre() + " (" + cl + " cl)";
	}

}
