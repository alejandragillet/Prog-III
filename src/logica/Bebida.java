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
	


	public String getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(String alcohol) {
		this.alcohol = alcohol;
	}

	public String getMezcla() {
		return mezcla;
	}

	public void setMezcla(String mezcla) {
		this.mezcla = mezcla;
	}

	@Override
	public String toString() {
		return " "+nombre ;
	}
	

	@Override
	public double getPrecio() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	


}
