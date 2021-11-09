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
    
    public Gestiondiscoteca() {
    	//this.lProductos;
    }
    
    
    public void main(String[] args) {
    	Gestiondiscoteca Gs1 = new Gestiondiscoteca();
    	Gs1.init(Gs1);
    	
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
	
	 public void init(Gestiondiscoteca Gs1) {
	    	lProductos = new ArrayList<Producto>();
	    	lClientes = new ArrayList<Cliente>();
	    	lTrabajadores = new ArrayList<Trabajador>();
//	    	
//	    	Bebida Bebida1 = new Bebida("cerveza", EnumBebida.CERVEZA, "cerveza", " ", 2.5);
//	    	Bebida Bebida2 = new Bebida("Chupito tequila", EnumBebida.CHUPIT0, "tequila", " ", 3 );
//	    	Bebida Bebida3 = new Bebida("Alexander" , EnumBebida.COCTEL, "tequila", "crema de cacao y nata líquida",9);
//	    	Bebida Bebida4 = new Bebida ("RonCola", EnumBebida.COPA, "ron", "coca-cola", 7);
//	    	Bebida Bebida5 = new Bebida("Kalimotxo", EnumBebida.COPA, "vino", "Coca-Cola", 4);
//	    	Bebida Bebida6 = new Bebida("Apple Martini" ,EnumBebida.COCTEL, "Vodka", "Licor de Manzana", 9);
//	    	Bebida Bebida7 = new Bebida("GinTonic" ,EnumBebida.COPA, "Ginebra", "Tónica", 8);
//	    	
//
//	    	
//	    	Comida Comida1 = new Comida ("Hamburguesa Simple", EnumComida.HAMBURGUESA, "pequeña", 8.00 );
//	    	Comida Comida2 = new Comida("HotDog", EnumComida.HOTDOG,"mediano", 6.00 );
//	    	Comida Comida3 = new Comida ("Pizza Jamón y Queso", EnumComida.PIZZA, "grande", 12.00);
//	    	Comida Comida4 = new Comida("Pizza barbacoa" , EnumComida.PIZZA, "pequeña", 8.00);
//	    	
	    	
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
	
	
    public void cargarFicheroBinarioCliente(ArrayList<Cliente> lClientes, String nombreFic) {
    	try {
    		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreFic));
    		ArrayList<Cliente> lCargada= (ArrayList<Cliente>) ois.readObject();
    		ois.close();
    		lClientes.clear();
    		lClientes.addAll(lCargada);
    		System.out.println("Despues de cargar fichero binario de CLientes" + lClientes);
    	}catch(IOException | ClassNotFoundException e) {
    		System.out.println("Error de lectura de fichero" + nombreFic);
    	}

    }
    
	
    
}
