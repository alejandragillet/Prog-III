package logica;

public class Comida extends Producto {
	protected EnumComida tipoComida;
	private String tamanio;

	public Comida(String nombre, EnumComida tipoComida, String tamanio, double precio) {
		super(nombre, precio);
		this.tipoComida = tipoComida;
		this.tamanio = tamanio;
	}

	public EnumComida getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(EnumComida tipoComida) {
		this.tipoComida = tipoComida;
	}

	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}
}
