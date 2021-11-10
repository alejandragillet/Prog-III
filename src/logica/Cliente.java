package logica;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    protected String nombre;
    protected String contrasenia;
    protected ArrayList<Reserva> lReservas;
    protected String apellido; 
    protected String DNI;
    private final static Logger LOGGER = Logger.getLogger("inicio.procesos.AltaCliente");
    private final static Logger LOGGER1 = Logger.getLogger("inicio.procesos.BajaCliente");
    
	
	public Cliente(String nombre, String contrasenia, ArrayList<Reserva> lReservas) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.lReservas = lReservas;
		
		
	}
	public Cliente(String nombre, String contrasenia,String apellido, String dNI) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.apellido = apellido;
		DNI = dNI;
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
	
    
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	@Override
	public String toString() {
		return "Cliente: nombre=" + nombre  +  "Reservas=" + lReservas ;
	}
	
	
	public void AltaCliente() {
        // Registro en el log el alta de usuario
        LOGGER.log(Level.INFO, "Usuario dado de alta");
	}
    
	public void BajaUsuario() {
        // Registro en el log el alta de usuario
        LOGGER1.log(Level.INFO, "Usuario dado de baja");
	}

}
