
public abstract class Bebida extends Producto{

	protected EnumBebida TipoBebida;

	
	
	public Bebida(String nombre, EnumBebida tipoBebida) {
		super(nombre);
		TipoBebida = tipoBebida;
	}

	public EnumBebida getTipoBebida() {
		return TipoBebida;
	}

	public void setTipoBebida(EnumBebida tipoBebida) {
		TipoBebida = tipoBebida;
	}

	@Override
	public String toString() {
		return "Bebida [TipoBebida=" + TipoBebida + "]";
	}
	
	
	
	


}
