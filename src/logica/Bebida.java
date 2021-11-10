package logica;



public class Bebida extends Producto{
	protected double cl;
	protected EnumBebida TipoBebida;
	private String alcohol;
	private String mezcla;

	
	
	public Bebida(String nombre, EnumBebida tipoBebida, String alcohol, String mezcla, double precio) {
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

	@Override
	public double getPrecio() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	


}
