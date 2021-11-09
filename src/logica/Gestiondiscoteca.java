package logica;
import java.util.ArrayList;

import ventanas.VentanaUsuario;

public class Gestiondiscoteca {
    protected ArrayList<Producto> lProductos;
    protected ArrayList<Cliente> lClientes;
    protected ArrayList<Trabajador> lTrabajadores;
    
    
    public void main(String[] args) {
    	Gestiondiscoteca Gs1 = new Gestiondiscoteca();
    	Gs1.init(Gs1);
    	
    }
    
    public void init(Gestiondiscoteca Gs1) {
    	lProductos = new ArrayList<Producto>();
    	lClientes = new ArrayList<Cliente>();
    	lTrabajadores = new ArrayList<Trabajador>();
    	
    	VentanaUsuario v1 = new VentanaUsuario();

    }
}
