package logica;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import ventanas.VentanaReservaEntradas;
import ventanas.VentanaUsuario;

public class GestionDiscoteca {
    protected ArrayList<Producto> lProductos;
    private ArrayList<Discoteca> lDiscotecas;
    protected ArrayList<Cliente> lClientes;
    protected ArrayList<Trabajador> lTrabajadores;
    
    private final static Logger LOG_RAIZ = Logger.getLogger("inicio");
    
    public GestionDiscoteca() {
    	//this.lProductos;
    }
    
    
    public static void main(String[] args) throws CloneNotSupportedException {
    	GestionDiscoteca Gs1 = new GestionDiscoteca();
    	Gs1.init(Gs1);
    	VentanaReservaEntradas vre = new VentanaReservaEntradas(Gs1);
    	vre.setVisible(true);
    }
    
    
    
    public ArrayList<Discoteca> getlDiscotecas() {
		return lDiscotecas;
	}


	public void setlDiscotecas(ArrayList<Discoteca> lDiscotecas) {
		this.lDiscotecas = lDiscotecas;
	}


	public ArrayList<Cliente> getlClientes() {
		return lClientes;
	}


	public void setlClientes(ArrayList<Cliente> lClientes) {
		this.lClientes = lClientes;
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
	
	 public void init(GestionDiscoteca Gs1) {
	    	lProductos = new ArrayList<Producto>();
	    	lClientes = new ArrayList<Cliente>();
	    	lTrabajadores = new ArrayList<Trabajador>();
	    	lDiscotecas = new ArrayList<Discoteca>();
	    	
	    	
	  
	    	
	    	Bebida Bebida1 = new Bebida("cerveza", EnumBebida.CERVEZA, "cerveza", " ", 2.5);
	    	Bebida Bebida2 = new Bebida("Chupito tequila", EnumBebida.CHUPIT0, "tequila", " ", 3 );
	    	Bebida Bebida3 = new Bebida("Alexander" , EnumBebida.COCTEL, "tequila", "crema de cacao y nata líquida",9);
	    	Bebida Bebida4 = new Bebida ("RonCola", EnumBebida.COPA, "ron", "coca-cola", 7);
	    	Bebida Bebida5 = new Bebida("Kalimotxo", EnumBebida.COPA, "vino", "Coca-Cola", 4);
	    	Bebida Bebida6 = new Bebida("Apple Martini" ,EnumBebida.COCTEL, "Vodka", "Licor de Manzana", 9);
	    	Bebida Bebida7 = new Bebida("GinTonic" ,EnumBebida.COPA, "Ginebra", "Tónica", 8);
	    	
	      
	    	
	    	
	    	Comida Comida1 = new Comida ("Hamburguesa Simple", EnumComida.HAMBURGUESA, "pequeña", 8.00 );
	    	Comida Comida2 = new Comida("HotDog", EnumComida.HOTDOG,"mediano", 6.00 );
	    	Comida Comida3 = new Comida ("Pizza Jamón y Queso", EnumComida.PIZZA, "grande", 12.00);
	    	Comida Comida4 = new Comida("Pizza barbacoa" , EnumComida.PIZZA, "pequeña", 8.00);
	    	
	    	HashMap<Producto,Integer> mapaProductoAlmacenBudha = new HashMap<Producto,Integer >();
	    	mapaProductoAlmacenBudha.put(Bebida1, 4);
	    	mapaProductoAlmacenBudha.put(Bebida2, 9);
	    	mapaProductoAlmacenBudha.put(Bebida3, 0);
	    	mapaProductoAlmacenBudha.put(Comida1, 1);
	    	
	    	
	    	HashMap<Producto,Integer> mapaProductoAlmacenMoma = new HashMap<Producto,Integer >();
	    	mapaProductoAlmacenMoma.put(Bebida5, 8);
	    	mapaProductoAlmacenMoma.put(Comida4, 4);
	    	mapaProductoAlmacenMoma.put(Comida3, 0);
	    	
	    	System.out.println(mapaProductoAlmacenBudha);
	    	
	    	Almacen almacenBudha = new Almacen(mapaProductoAlmacenBudha);
	    	Almacen almacenMoma = new Almacen(mapaProductoAlmacenMoma);
	    	
	    	
	    	Discoteca Moma = new Discoteca("bbb", 200, 30, "Moma", almacenBudha);
	    	Discoteca Budha = new Discoteca ("aaa", 700, 90, "Budha",almacenMoma); 
	    	
	    	lDiscotecas.add(Budha);
	    	lDiscotecas.add(Moma);
	    	Gs1.lDiscotecas = lDiscotecas;
	    	System.out.println(lDiscotecas);

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
