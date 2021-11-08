package logica;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Gestiondiscoteca {
    protected ArrayList<Producto> lProductos;
    protected ArrayList<Cliente> lCLientes;
    protected ArrayList<Trabajador> lTrabajadores;
    
    
    public void main(String[] args) {
    	
    }
    
    
    public void guardarFicheroBinarioCliente(ArrayList<Cliente> lClientes, String nombreFic) {
    	try {
    		File sFichero = new File(nombreFic);
    		sFichero.delete();
    		ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(nombreFic));
    		oos.writeObject(lClientes);
    		oos.close();
    		System.out.println("Se ha guardado correctamente");
    	}catch(IOException e) {
    		System.out.println("Error en escritura de fichero (Cliente)" + nombreFic);
    		
    	}
    }
    public void cargarFicheroBinarioUsuario(ArrayList<Cliente> lClientes, String nombreFic) {
    	try {
    		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreFic));
    		ArrayList<Cliente> lCargada= (ArrayList<Cliente>) ois.readObject();
    		ois.close();
    		lClientes.clear();
    		lClientes.addAll(lCargada);
    		System.out.println("Despues de cargar fichero binario de Cliente" + lClientes);
    	}catch(IOException | ClassNotFoundException e) {
    		System.out.println("Error de lectura de fichero" + nombreFic);
    	}
    }
}
