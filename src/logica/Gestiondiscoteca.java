package logica;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    	
    }


    
    public ArrayList<Producto> getlProductos() {
		return lProductos;
	}


	public void setlProductos(ArrayList<Producto> lProductos) {
		this.lProductos = lProductos;
	}


	public ArrayList<Cliente> getlCLientes() {
		return lClientes;
	}


	public void setlCLientes(ArrayList<Cliente> lCLientes) {
		this.lClientes = lCLientes;
	}


	public ArrayList<Trabajador> getlTrabajadores() {
		return lTrabajadores;
	}


	public void setlTrabajadores(ArrayList<Trabajador> lTrabajadores) {
		this.lTrabajadores = lTrabajadores;
	}

	//guardar fichero en un futuro se cambiara por la base de datos
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
	
	//cargar fichero binario en un futuro se cambiara por la base de datos
    public void cargarFicheroBinarioCliente(ArrayList<Cliente> lClientes, String nombreFic) {
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
    //se crear el log.txt y se añaden los clientes
    public void  LogginMirarFechaDíaHora() throws IOException {
    	
    	Log myLog= new Log("./log.txt");
    	myLog.anadeLinea(lClientes);
    	myLog.close();
    	//se recorre para mostrarlo
    	String [] lines = myLog.getLines();
    	for(int i=0; i < lines.length; i++) {
    		System.out.println(lines[i]);
    	}
    }
    	
    
}
