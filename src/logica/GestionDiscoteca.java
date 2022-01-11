
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
import comunicacion.Servidor;
import ventanas.VentanaCliente;

import ventanas.VentanaReservaEntradas;
import ventanas.VentanaUsuario;

public class GestionDiscoteca {
	public ArrayList<Producto> lProductos = new ArrayList<>();
	public ArrayList<Discoteca> lDiscotecas = new ArrayList<>();
	public ArrayList<Cliente> lClientes = new ArrayList<>();
	public ArrayList<Trabajador> lTrabajadores = new ArrayList<>();

	private final static Logger LOG_RAIZ = Logger.getLogger("inicio");

	public static void main(String[] args) throws CloneNotSupportedException {
		GestionDiscoteca gs1 = new GestionDiscoteca();
		gs1.initConexiones();
		GestionDiscoteca.init(gs1);

		VentanaUsuario vU = new VentanaUsuario(gs1);
		vU.setVisible(true);
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
		(new Thread() {
			@Override
			public void run() {
				Servidor.lanzaServidor();
			}
		}).start();
		(new Thread() {
			@Override
			public void run() {
				Comunicador.lanzaCliente();
			}
		}).start();

	}

	/**
	 * Inicializa una GestionDiscoteca con datos de ejemplo
	 * 
	 * @param gs1
	 */
	public static void init(GestionDiscoteca gs1) {
		// Crear productos
		Bebida bebida1 = new Bebida("cerveza", EnumBebida.CERVEZA, "cerveza", " ", 2.5);
		Bebida bebida2 = new Bebida("Chupito tequila", EnumBebida.CHUPITO, "tequila", " ", 3);
		Bebida bebida3 = new Bebida("Alexander", EnumBebida.COCTEL, "tequila", "crema de cacao y nata líquida", 9);
		Bebida bebida4 = new Bebida("RonCola", EnumBebida.COPA, "ron", "coca-cola", 7);
		Bebida bebida5 = new Bebida("Kalimotxo", EnumBebida.COPA, "vino", "Coca-Cola", 4);
		Bebida bebida6 = new Bebida("Apple Martini", EnumBebida.COCTEL, "Vodka", "Licor de Manzana", 9);
		Bebida bebida7 = new Bebida("GinTonic", EnumBebida.COPA, "Ginebra", "Tónica", 8);

		Comida comida1 = new Comida("Hamburguesa queso y bacon", EnumComida.HAMBURGUESA, "pequeña", 8.00);
		Comida comida2 = new Comida("HotDog", EnumComida.HOTDOG, "mediano", 6.00);
		Comida comida3 = new Comida("Pizza Jamón y Queso", EnumComida.PIZZA, "grande", 12.00);
		Comida comida4 = new Comida("Pizza barbacoa", EnumComida.PIZZA, "pequeña", 8.00);

		gs1.lProductos.addAll(
				Arrays.asList(
						bebida1,
						bebida2,
						bebida3,
						bebida4,
						bebida5,
						bebida6,
						bebida7,
						comida1,
						comida2,
						comida3,
						comida4));
		gs1.guardarFicheroBinarioProductos(gs1.lProductos, "productos.dat");

		HashMap<Producto, Integer> mapaProductoAlmacenBudha = new HashMap<Producto, Integer>();
		mapaProductoAlmacenBudha.put(bebida1, 4);
		mapaProductoAlmacenBudha.put(bebida2, 9);
		mapaProductoAlmacenBudha.put(bebida3, 0);
		mapaProductoAlmacenBudha.put(bebida4, 15);
		mapaProductoAlmacenBudha.put(bebida5, 0);
		mapaProductoAlmacenBudha.put(bebida6, 1);
		mapaProductoAlmacenBudha.put(bebida7, 2);
		mapaProductoAlmacenBudha.put(comida1, 1);
		mapaProductoAlmacenBudha.put(comida2, 10);
		mapaProductoAlmacenBudha.put(comida3, 90);
		mapaProductoAlmacenBudha.put(comida4, 1);

		HashMap<Producto, Integer> mapaProductoAlmacenMoma = new HashMap<Producto, Integer>();
		mapaProductoAlmacenMoma.put(bebida1, 90);
		mapaProductoAlmacenMoma.put(bebida2, 12);
		mapaProductoAlmacenMoma.put(bebida3, 9);
		mapaProductoAlmacenMoma.put(bebida4, 3);
		mapaProductoAlmacenMoma.put(bebida5, 11);
		mapaProductoAlmacenMoma.put(bebida6, 0);
		mapaProductoAlmacenMoma.put(bebida7, 45);
		mapaProductoAlmacenMoma.put(comida1, 43);
		mapaProductoAlmacenMoma.put(comida2, 15);
		mapaProductoAlmacenMoma.put(comida3, 86);
		mapaProductoAlmacenMoma.put(comida4, 0);

		Almacen almacenBudha = new Almacen(mapaProductoAlmacenBudha);
		Almacen almacenMoma = new Almacen(mapaProductoAlmacenMoma);

		Discoteca budha = new Discoteca("Urkixo Zumarkalea, 88, 48013 Bilbo, Bizkaia", 700, 70,"Budha", almacenMoma);
		Discoteca moma = new Discoteca("Rodríguez Arias Kalea, 66, 48013 Bilbo, Bizkaia", 200, 30, "Moma",
				almacenBudha);

		gs1.lDiscotecas.add(budha);
		gs1.lDiscotecas.add(moma);

	}

	/**
	 * Guarda los datos de la aplicación en un fichero
	 * 
	 * @param lClientes
	 * @param nombreFic
	 */
	public void guardarFicheroBinarioCliente(ArrayList<Cliente> lClientes, String nombreFic) {
		try {
			File sFichero = new File(nombreFic);
			sFichero.delete();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreFic));
			oos.writeObject(lClientes);
			oos.close();
			System.out.println("Se ha guardado correctamente");
		} catch (IOException e) {
			System.out.println("Error en escritura de fichero (Cliente)" + nombreFic);

		}
	}

	/**
	 * Carga los datos de la aplicación en un fichero
	 * 
	 * @param lClientes
	 * @param nombreFic
	 */
	public void cargarFicheroBinarioCliente(ArrayList<Cliente> lClientes, String nombreFic) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreFic));
			ArrayList<Cliente> lCargada = (ArrayList<Cliente>) ois.readObject();
			ois.close();
			lClientes.clear();
			lClientes.addAll(lCargada);
			System.out.println("Despues de cargar fichero binario de CLientes" + lClientes);
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error de lectura de fichero" + nombreFic);
		}
	}

	/**
	 * Guardar productos en un fichero binario
	 * 
	 * @param lProductos
	 * @param nombreFic
	 */
	public void guardarFicheroBinarioProductos(ArrayList<Producto> lProductos, String nombreFic) {
		try {
			File sFichero = new File(nombreFic);
			sFichero.delete();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreFic));
			oos.writeObject(lProductos);
			oos.close();
		} catch (IOException e) {
			System.out.println("Error en escritura de fichero (Producto)" + nombreFic);

		}
	}

	/**
	 * Cargar productos en un fichero binario
	 * 
	 * @param lProductos
	 * @param nombreFic
	 */
	public void cargarFicheroBinarioProductos(ArrayList<Producto> lProductos, String nombreFic) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreFic));
			ArrayList<Producto> lCargada = (ArrayList<Producto>) ois.readObject();
			ois.close();
			lProductos.clear();
			lProductos.addAll(lCargada);
			System.out.println("Despues de cargar fichero binario de Productos" + lProductos);
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error en lectura de fichero " + nombreFic);
		}
	}

	public void calcularComprasPosibles(double disponible) {
		ArrayList<Producto> lProds = new ArrayList<>();
		calcularComprasPosibles(lProductos, disponible, lProds);
	}

	public void calcularComprasPosibles(ArrayList<Producto> prods, double dineroQueda,
			ArrayList<Producto> lProdsComprados) {
		if (dineroQueda < 0) { // Caso base: compra imposible (no hay suficiente dinero)
			return;
		} else if (dineroQueda < 5) { // Caso base: compra posible con menos de 100 euros sobrantes
			System.out.println(
					"Posible compra (sobran " + String.format("%.2f", dineroQueda) + " euros): " + lProdsComprados);
		} else { // Caso general - probar por combinatoria todos los productos posibles para
					// comprar
			for (Producto p : prods) {
				lProdsComprados.add(p);
				calcularComprasPosibles(prods, dineroQueda - p.getPrecio(), lProdsComprados);
				lProdsComprados.remove(lProdsComprados.size() - 1);
			}
		}
	}
}
