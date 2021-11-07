import java.util.ArrayList;

public class Cliente {
    protected String nombre;
    protected String contrasenia;
    protected ArrayList<Reserva> lReservas;
    
	
	public Cliente(String nombre, String contrasenia, ArrayList<Reserva> lReservas) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.lReservas = lReservas;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public ArrayList<Reserva> getlReservas() {
		return lReservas;
	}
	public void setlReservas(ArrayList<Reserva> lReservas) {
		this.lReservas = lReservas;
	}
    
	@Override
	public String toString() {
		return "Cliente: nombre=" + nombre  +  "Reservas=" + lReservas ;
	}
    
}
