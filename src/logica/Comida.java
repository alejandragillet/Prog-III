package logica;

public class Comida extends Producto {
	protected EnumComida TipoComida;
	

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

	@Override
	public double getPrecio() {
		// TODO Auto-generated method stub
		return 0;
	}

}
