package logica;
public class Trabajador {
    protected String nombre;
    protected String contrasenia;
    protected int horasTrabajadas;
    protected int precioHora;
    protected double sueldo;
    
    protected EnumPuesto puesto;
	
    
    public Trabajador(String nombre, String contrasenia, double sueldo, EnumPuesto puesto) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.sueldo = sueldo;
		this.puesto = puesto;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getHorasTrabajadas() {
		return horasTrabajadas;
	}


	public void setHorasTrabajadas(int horasTrabajadas) {
		this.horasTrabajadas = horasTrabajadas;
	}


	public int getPrecioHora() {
		return precioHora;
	}


	public void setPrecioHora(int precioHora) {
		this.precioHora = precioHora;
	}


	public String getContrasenia() {
		return contrasenia;
	}


	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}


	public double getSueldo() {
		return horasTrabajadas * precioHora;
	}


	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}


	public EnumPuesto getPuesto() {
		return puesto;
	}


	public void setPuesto(EnumPuesto puesto) {
		this.puesto = puesto;
	}
	
	


	@Override
	public String toString() {
		return "Trabajador [nombre=" + nombre + ", sueldo=" + sueldo + ", puesto=" + puesto + "]";
	}
    
    
}
