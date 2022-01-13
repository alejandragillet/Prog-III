package logica;

public class Bebida extends Producto {
	protected EnumBebida tipoBebida;
	private String alcohol;
	private String mezcla;

	public Bebida(String nombre, EnumBebida tipoBebida, String alcohol, String mezcla, double precio) {
		super(nombre, precio);
		this.tipoBebida = tipoBebida;
		this.alcohol = alcohol;
		this.mezcla = mezcla;
	}

	public EnumBebida getTipoBebida() {
		return tipoBebida;
	}

	public void setTipoBebida(EnumBebida tipoBebida) {
		this.tipoBebida = tipoBebida;
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
}
