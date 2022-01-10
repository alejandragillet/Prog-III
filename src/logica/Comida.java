package logica;

public class Comida extends Producto {
	protected EnumComida TipoComida;
	private String tamanio;

	public Comida(String nombre, EnumComida tipoComida, String tamanio, double precio) {
		super(nombre, precio);
		this.TipoComida = tipoComida;
		this.nombre = nombre;
		this.tamanio = tamanio;
	}

	public EnumComida getTipoComida() {
		return TipoComida;
	}

	public void setTipoComida(EnumComida tipoComida) {
		TipoComida = tipoComida;
	}

	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}

	@Override
	public String toString() {
		return " " + nombre;
	}

}
