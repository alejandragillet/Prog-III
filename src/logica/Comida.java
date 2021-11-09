package logica;

public abstract class Comida extends Producto {
	protected EnumComida TipoComida;
	protected EnumTamaño Tamaño;

	public Comida(String nombre, EnumComida tipoComida, String tamaño, double precio ) {
		super(nombre);
		TipoComida = tipoComida;
	}

	public EnumComida getTipoComida() {
		return TipoComida;
	}

	public void setTipoComida(EnumComida tipoComida) {
		TipoComida = tipoComida;
	}

	@Override
	public String toString() {
		return "Comida [TipoComida=" + TipoComida + "]";
	}

}
