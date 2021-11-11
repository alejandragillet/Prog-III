package logica;
import java.util.ArrayList;

public class Discoteca {
    protected String direccion;
    protected int aforo;
    protected int numeroTrabajadores;
    protected String nombre;
    protected ArrayList<Reserva> lReserva;
    
    
	public Discoteca(String direccion, int aforo, int numeroTrabajadores, String nombre) {
		super();
		this.direccion = direccion;
		this.aforo = aforo;
		this.numeroTrabajadores = numeroTrabajadores;
		this.nombre = nombre;
	}
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getAforo() {
		return aforo;
	}
	public void setAforo(int aforo) {
		this.aforo = aforo;
	}
	public int getNumeroTrabajadores() {
		return numeroTrabajadores;
	}
	public void setNumeroTrabajadores(int numeroTrabajadores) {
		this.numeroTrabajadores = numeroTrabajadores;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return nombre ;
	} 
    
}
