
public abstract class Comida extends Producto{
	protected EnumComida TipoComida;

	
	public Comida(String nombre, EnumComida tipoComida) {
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
