package logica;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import basedatos.BaseDeDatos;

import comunicacion.Comunicador;
import ventanas.VentanaCliente;

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
    	GestionDiscoteca gs1 = new GestionDiscoteca();
		gs1.initConexiones();
    	gs1.init(gs1);

		VentanaCliente vc = new VentanaCliente("Registros", gs1);
		vc.setVisible(true);

    	VentanaReservaEntradas vre = new VentanaReservaEntradas(gs1);
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
	
	
	
	private void initConexiones() {
		BaseDeDatos.initBD(BaseDeDatos.nombreBD);
		Comunicador.lanzaCliente();
	}


	// inicialización de los productos
	 public void init(GestionDiscoteca Gs1) {
	    	lProductos = new ArrayList<Producto>();
	    	lClientes = new ArrayList<Cliente>();
	    	lTrabajadores = new ArrayList<Trabajador>();
	    	lDiscotecas = new ArrayList<Discoteca>();

	    	
	    	
	  
	    	// Crear productos
	    	Bebida Bebida1 = new Bebida("cerveza", EnumBebida.CERVEZA, "cerveza", " ", 2.5);
	    	Bebida Bebida2 = new Bebida("Chupito tequila", EnumBebida.CHUPITO, "tequila", " ", 3 );
	    	Bebida Bebida3 = new Bebida("Alexander" , EnumBebida.COCTEL, "tequila", "crema de cacao y nata líquida",9);
	    	Bebida Bebida4 = new Bebida ("RonCola", EnumBebida.COPA, "ron", "coca-cola", 7);
	    	Bebida Bebida5 = new Bebida("Kalimotxo", EnumBebida.COPA, "vino", "Coca-Cola", 4);
	    	Bebida Bebida6 = new Bebida("Apple Martini" ,EnumBebida.COCTEL, "Vodka", "Licor de Manzana", 9);
	    	Bebida Bebida7 = new Bebida("GinTonic" ,EnumBebida.COPA, "Ginebra", "Tónica", 8);
	    	
	      
	    	
	    	
	    	Comida Comida1 = new Comida ("Hamburguesa queso y bacon", EnumComida.HAMBURGUESA, "pequeña", 8.00 );
	    	Comida Comida2 = new Comida("HotDog", EnumComida.HOTDOG,"mediano", 6.00 );
	    	Comida Comida3 = new Comida ("Pizza Jamón y Queso", EnumComida.PIZZA, "grande", 12.00);
	    	Comida Comida4 = new Comida("Pizza barbacoa" , EnumComida.PIZZA, "pequeña", 8.00);
	    	
	    	lProductos.addAll(Arrays.asList(Bebida1,Bebida2,Bebida3,Bebida4,Bebida5, Bebida6, Bebida7, Comida1, Comida2, Comida3, Comida4));
	    	
	    	HashMap<Producto,Integer> mapaProductoAlmacenBudha = new HashMap<Producto,Integer >();
	    	mapaProductoAlmacenBudha.put(Bebida1, 4);
	    	mapaProductoAlmacenBudha.put(Bebida2, 9);
	    	mapaProductoAlmacenBudha.put(Bebida3, 0);
	    	mapaProductoAlmacenBudha.put(Bebida4, 15);
	    	mapaProductoAlmacenBudha.put(Bebida5, 0);
	    	mapaProductoAlmacenBudha.put(Bebida6, 1);
	    	mapaProductoAlmacenBudha.put(Bebida7, 2);
	    	mapaProductoAlmacenBudha.put(Comida1, 1);
	    	mapaProductoAlmacenBudha.put(Comida2, 10);
	    	mapaProductoAlmacenBudha.put(Comida3, 90);
	    	mapaProductoAlmacenBudha.put(Comida4, 1);
	    	
	    	
	    	HashMap<Producto,Integer> mapaProductoAlmacenMoma = new HashMap<Producto,Integer >();
	    	mapaProductoAlmacenMoma.put(Bebida1, 90);
	    	mapaProductoAlmacenMoma.put(Bebida2, 12);
	    	mapaProductoAlmacenMoma.put(Bebida3, 9);
	    	mapaProductoAlmacenMoma.put(Bebida4, 3);
	    	mapaProductoAlmacenMoma.put(Bebida5, 11);
	    	mapaProductoAlmacenMoma.put(Bebida6, 0);
	    	mapaProductoAlmacenMoma.put(Bebida7, 45);
	    	mapaProductoAlmacenMoma.put(Comida1, 43);
	    	mapaProductoAlmacenMoma.put(Comida2, 15);
	    	mapaProductoAlmacenMoma.put(Comida3, 86);
	    	mapaProductoAlmacenMoma.put(Comida4, 0);
	    	
	    	System.out.println(mapaProductoAlmacenBudha);
	    	
	    	Almacen almacenBudha = new Almacen(mapaProductoAlmacenBudha);
	    	Almacen almacenMoma = new Almacen(mapaProductoAlmacenMoma);
	    	
	    	Discoteca Budha = new Discoteca ("aaa", 700, 90, "Budha",almacenMoma); 
	    	Discoteca Moma = new Discoteca("bbb", 200, 30, "Moma", almacenBudha);
	    	
	    	
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
    /// Esta mal este METODO RECUSIVO/////////////
//    private ArrayList<Producto> producto_max = null;
//    ArrayList<Producto> productos = BaseDeDatos.getProductos();
//    public void findMaxImporte() {
//		ArrayList<Producto> l_pro = BaseDeDatos.getProductos();
//		Producto productoM = findMaxImporte(l_pro, 0, l_pro.size() - 1);
//		double imp_max = productoM.getPrecio();
//		producto_max = new ArrayList<Producto>();
//		for (Producto p : l_pro) {
//			double imp = p.getPrecio();
//			if (imp == imp_max) {
//				producto_max.add(p);
//			}
//		}
//	}
//	
//	public Producto findMaxImporte(ArrayList<Producto> compras, int i, int j){
//		
//		int med;
//		Producto max_left, max_right;
//		if (i==j) {
//			return productos.get(i);
//		} else {
//			med = (i + j) / 2;
//		}
//		max_left = findMaxImporte(productos, i, med);
//		max_right =	findMaxImporte (productos, med+1, j);
//	
//		double imp_left =  max_left.getPrecio();
//		double imp_right = max_right.getPrecio();
//		
//		if (imp_left > imp_right) 
//			return max_left;
//		else
//			return max_right;
//	 }

    
}
