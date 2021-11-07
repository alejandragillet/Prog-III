import java.util.ArrayList;

public class Discoteca {
    protected String dirección;
    protected int aforo;
    protected int numeroTrabajadores;
    protected String nombre;
    protected ArrayList<Reserva> lReserva;
    
    
	public Discoteca(String dirección, int aforo, int numeroTrabajadores, String nombre) {
		super();
		this.dirección = dirección;
		this.aforo = aforo;
		this.numeroTrabajadores = numeroTrabajadores;
		this.nombre = nombre;
	}
	
	public String getDirección() {
		return dirección;
	}
	public void setDirección(String dirección) {
		this.dirección = dirección;
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
		return "Discoteca [dirección=" + dirección + ", aforo=" + aforo + ", numeroTrabajadores=" + numeroTrabajadores
				+ ", nombre=" + nombre + "]";
	} 
    
}
