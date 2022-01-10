package logica;

public class Caja {
	public Trabajador trabajador;
	public Reserva reserva;
	public Producto producto;

	public Caja(Trabajador trabajador, Reserva reserva, Producto producto) {
		super();
		this.trabajador = trabajador;
		this.reserva = reserva;
		this.producto = producto;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "Caja [trabajador=" + trabajador + ", reserva=" + reserva + ", producto=" + producto + "]";
	}

}
