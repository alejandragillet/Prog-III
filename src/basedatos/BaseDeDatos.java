package basedatos;


import java.sql.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.logging.*;


import logica.*;

/**
 * @author saioa
 *
 */
public class BaseDeDatos {
private static Exception lastError = null; // Informaci�n de �ltimo error SQL ocurrido
public static Connection conexion;
public static String nombreBD = "discotecaBD";

public static GestionDiscoteca gs = new GestionDiscoteca();
//public static ArrayList<Discoteca> discotecas = gs.getlDiscotecas();
//public static ArrayList<Cliente> clientes = gs.getlClientes();
//public static ArrayList<Trabajador> trabajaderes = gs.getlTrabajadores();
	
	/** Inicializa una BD SQLITE y devuelve una conexi�n con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexi�n con la base de datos indicada. Si hay alg�n error, se devuelve null
	 */
	public static Connection initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    conexion = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			log( Level.INFO, "Conectada base de datos " + nombreBD, null );
		    return conexion;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en conexi�n de base de datos " + nombreBD, e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Devuelve statement para usar la base de datos
	 * @param con	Conexi�n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			return statement;
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en uso de base de datos", e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Crea las tablas de la base de datos. Si ya existen, las deja tal cual
	 * @param con	Conexi�n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarCrearTablasBD( Connection con ) {
		Statement statement = usarBD(con);

		try {
			statement.executeUpdate("create table trabajador " +
				"(nombre string, puesto string, sueldo integer, contrasenaT string, precioHora integer)");
		} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
		try {
			statement.executeUpdate("create table cliente " +
				"(nombre string, apellido string, dni string, contrasena string)");
		} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
		try {
			statement.executeUpdate("create table reserva " +
				"(cliente_nombre string, discoteca_nombre string, fecha integer, string zona, numPers integer, importe integer)");
		} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
		try {
			statement.executeUpdate("create table discoteca " +
				"(nombre String, aforoMax integer, aforo integer, numeroTrab integer, direccion String)");
		} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
		
		log( Level.INFO, "Creada base de datos", null );
		return statement;
	}
	
	/** Reinicia en blanco las tablas de la base de datos. 
	 * UTILIZAR ESTE METODO CON PRECAUCION. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexi�n ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se borra correctamente, null si hay cualquier error
	 */
	public static Statement reiniciarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			statement.executeUpdate("drop table if exists Discoteca");
			statement.executeUpdate("drop table if exists cliente");
			statement.executeUpdate("drop table if exists reserva");
			log( Level.INFO, "Reiniciada base de datos", null );
			return usarCrearTablasBD( con );
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en reinicio de base de datos", e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Cierra la base de datos abierta
	 * @param con	Conexi�n abierta de la BD
	 * @param st	Sentencia abierta de la BD
	 */
	public static void cerrarBD( Connection con, Statement st ) {
		try {
			if (st!=null) st.close();
			if (con!=null) con.close();
			log( Level.INFO, "Cierre de base de datos", null );
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en cierre de base de datos", e );
			e.printStackTrace();
		}
	}
	
	/** Devuelve la informaci�n de excepci�n del �ltimo error producido por cualquiera 
	 * de los m�todos de gesti�n de base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}
	
	
	/** A�ade un Discoteca a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st		Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la discoteca)
	 * @param d			Discoteca a a�adir en la base de datos
	 * @return true 	si la inserci�n es correcta, false en caso contrario
	 */
	public static boolean DiscotecaInsert( Statement st, Discoteca  d ) {
		String sentSQL = "";
		try {
			sentSQL = "insert into discoteca values(" +
					"'" + secu(d.getNombre()) + "'," +
					"'" + d.getAforoMax() + "'" +d.getNumeroTrabajadores()+ "'" + d.getDireccion()+")";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD tabla Discoteca a�adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que a�adir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	/**Recoge la lista de discotecas del gestor y la insrta en la base de datos
	 *  @param discotecas recoge la lista de discotecas del gestor y la recorre
	 *  @param st sentencia abierta con la base de datos
	 */
	
	public static boolean guardarDiscotecas(Statement st, ArrayList<Discoteca> discotecas) {
		
		discotecas = gs.getlDiscotecas();
		
		for (Discoteca d : discotecas) {
			DiscotecaInsert(st,d);
		}
		
		return false;	
	}

	/** Realiza una consulta a la tabla abierta de Discotecaes de la BD, usando la sentencia SELECT de SQL
	 * @param st			Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la b�squeda (vac�a si no se usa)
	 * @return				lista de nombres de Discotecas cargadas desde la base de datos, null si hay cualquier error
	 */
	public static ArrayList<String> DiscotecaSelect( Statement st, String codigoSelect ) {
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from Discoteca";
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where " + codigoSelect;
			// System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				ret.add( rs.getString( "nombre" ) );
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Discoteca> DiscotecaSelectAll( Statement st ) {
		String sentSQL = "";
		ArrayList<Discoteca> ret = new ArrayList<>();
		try {
			sentSQL = "select * from Discoteca";
			
			// System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				ret.add( new Discoteca(rs.getString( "nombre" ), rs.getInt("aforomax"), rs.getInt("aforo"), rs.getInt("numTrab"), rs.getString("direccion")) );
				//nombre String, aforoMax integer, aforo integer, numeroTrab integer, direccion String
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	

	/** A�ade un cliente a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st		 Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la habitaci�n)
	 * @param nombre	 nombre del cliente que queremos a�adir
	 * @param tlfn		 numero de telefono del cliente
	 * @param contrasenia contrasena del nuevo cliente
	 * @return true 	 si la inserci�n es correcta, false en caso contrario
	 */
	public static boolean clienteInsert( Statement st, String nombre , String apellido, String DNI, String contrasena  ) {
		String sentSQL = "";
		try {
			sentSQL = "insert into cliente values(" +
					"'" + secu(nombre) + "'," + "'" + secu(apellido) + "',"+
					"'" + secu(DNI) +"'," + "'" + secu(contrasena)+ "')";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD tabla cliente anadida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que anadir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
				
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	/**Recoge la lista de discotecas del gestor y la insrta en la base de datos
	 *  @param clientes recoge la lista de discotecas del gestor y la recorre
	 *  @param st sentencia abierta con la base de datos
	 */
	public static boolean guardarClientes(Statement st, ArrayList<Cliente> clientes) {
		
		clientes = gs.getlClientes();
		
		for (Cliente c : clientes) {
			clienteInsert(st, c.getNombre(),c.getApellido(), c.getContrasenia(), c.getDNI());
		}
		
		return false;	
	}


	/** Realiza una consulta a la tabla abierta de clientes de la BD, usando la sentencia SELECT de SQL
	 * @param st			Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param c				cliente que se busca seleccionar (no null)
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la b�squeda (vac�a si no se usa)
	 * @return				lista de clientes cargados desde la base de datos, null si hay cualquier error
	 */
	public static ArrayList<String> clienteSelect( Statement st, Cliente c, String adicional ) {
		if (c==null) return null;
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from cliente";
			
				String where = "nombre='" + c.getNombre() + "'";
				if (adicional!=null && !adicional.equals(""))
					sentSQL = sentSQL + " where " + where + " AND " + adicional;
				else
					sentSQL = sentSQL + " where " + where;
			
			if (adicional!=null && !adicional.equals(""))
				sentSQL = sentSQL + " where " + adicional;
				System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				ret.add( rs.getString( "nombre" ) );
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	public static ArrayList<Cliente> clienteSelectAll( Statement st) {
		
		String sentSQL = "";
		ArrayList<Cliente> ret = new ArrayList<>();
		try {
			sentSQL = "select * from cliente";
			System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				ret.add(new Cliente( rs.getString( "nombre" ), rs.getString("apellido"), rs.getString("contrasenia "), rs.getString("dni") ));
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	/** Realiza una consulta a la tabla abierta de clientes de la BD, usando la sentencia SELECT de SQL
	 * @param st			Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param t				trabajador que se busac seleccionar (no null)
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la b�squeda (vac�a si no se usa)
	 * @return				lista de clientes cargados desde la base de datos, null si hay cualquier error
	 */
	
	public static ArrayList<String> trabajadorSelect( Statement st, Trabajador t, String codigoSelect ) {
		if (t==null) return null;
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from trabajador";
			if (t!=null) {
				String where = "nombre='" + t.getNombre() + "'";
				if (codigoSelect!=null && !codigoSelect.equals(""))
					sentSQL = sentSQL + " where " + where + " AND " + codigoSelect;
				else
					sentSQL = sentSQL + " where " + where;
			}
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where " + codigoSelect;
				System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				ret.add( rs.getString( "nombre" ) );
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Trabajador> trabajadorSelectAll( Statement st) {

		String sentSQL = "";
		ArrayList<Trabajador> ret = new ArrayList<>();
		try {
			sentSQL = "select * from trabajador";
			System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				EnumPuesto puesto = null;
				if (rs.getString("puesto")== "CAMARERO" || rs.getString("puesto") == "camarero"){
					puesto = EnumPuesto.CAMARERO;
				} else if (rs.getString("puesto") == "DJ" || rs.getString("puesto") == "dj") {
					puesto = EnumPuesto.DJ;
				}else {
					puesto = EnumPuesto.SEGURATA;
				}
				ret.add( new Trabajador( rs.getString( "nombre" ), rs.getString("contrasenaT"),  rs.getInt("sueldo"), puesto));
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Realiza una consulta a la tabla abierta de clientes de la BD, usando la sentencia SELECT de SQL
	 * @param st			Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param dni			dni del trabajador que se inserta en la tabla
	 * @param sueldo		sueldo del respactivo trabajador
	 * aram contrasena		contrasena de acceso del respectivo trabajador
	 * @return				lista de clientes cargados desde la base de datos, null si hay cualquier error
	 */
	private static boolean trabajadorInsert(Statement st, String nombre, String contrasenia, int precioHora, int sueldo) {
		String sentSQL = "";
		try {
			sentSQL = "insert into cliente values(" +
					"'" + secu(nombre) + "'," +
					"'" + "'" + secu(contrasenia) + "',"+ precioHora + "'," + sueldo+"')";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD tabla trabajador a�adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que a�adir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
		
	}
	/**Inserta en la base de datos todos los trabajadores que hay en la lista del gestor
	 * 
	 * @param st			sentencia ya abierta con la base de datos
	 * @param trabajadores	lista de trabajadores que se guarda
	 * @return
	 */
	public static boolean guardarTrabajadores(Statement st, ArrayList<Trabajador> trabajadores) {
			
			trabajadores = gs.getlTrabajadores();
			
			for (Trabajador t  : trabajadores) {
				trabajadorInsert(st, t.getNombre(), t.getContrasenia(), t.getPrecioHora(), t.getSueldo());
			}
			
			return false;	
		}
	

	

	

	/** A�ade una reserva a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la reserva)
	 * @param d		Discoteca en la que se realiza la reserva
	 * @param nomC	nombre del Cliente
	 * @param rf	Rango de fechas de la reserva nueva
	 * @return true si la inserci�n es correcta, false en caso contrario
	 */
	public static boolean reservaInsert( Statement st, String discoteca, String nomCliente, int fecha, EnumZona zona, int numPers, int importe ) {
		String sentSQL = "";
		try {
			sentSQL = "insert into reserva values(" +
					"'" + secu(nomCliente) + "'," + "'" + secu(discoteca) + "'," +"'" + fecha + "'," + "'" + zona + "'," + "'" + numPers + "',"
					+ "'" + importe + "'" + ")";
			//cliente_nombre string, discoteca_nombre string, fecha integer, string zona, numPers integer, importe integer
			int eu = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD tabla reserva a�adida " + eu + " fila\t" + sentSQL, null );
			if (eu!=1) {  // Se tiene que a�adir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/** Realiza una consulta a la tabla abierta de reservas de la BD, usando la sentencia SELECT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param h	Discoteca del que se buscan las reservas (no null)
	 * @param cliente	Cliente del que se buscan las reservas (no null)
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la b�squeda (vac�a si no se usa)
	 * @return	lista de reservas cargadas desde la base de datos, null si hay cualquier error
	 */
	public static ArrayList<String> reservaSelect( Statement st, Discoteca d, String cliente, String codigoSelect ) {
		if (d==null || cliente==null || cliente.isEmpty()) return null;
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from reserva";
			if (d!=null) {
				String where = "discoteca_nombre='" + d.getNombre() + "' AND cliente_nombre='" + cliente + "'";
				if (codigoSelect!=null && !codigoSelect.equals(""))
					sentSQL = sentSQL + " where " + where + " AND " + codigoSelect;
				else
					sentSQL = sentSQL + " where " + where;
			}
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where " + codigoSelect;
			 System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				ret.add(rs.getString( "fecha" ));
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Reserva> reservaSelectAll( Statement st) {		
		String sentSQL = "";
		ArrayList<Reserva> ret = new ArrayList<>();
		try {
			sentSQL = "select * from reserva";			
			System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				EnumZona zona = null;
				if (rs.getString("zona")== "VIP" || rs.getString("zona") == "vip"){
					zona = EnumZona.VIP;
				} else if (rs.getString("zona") == "MESA" || rs.getString("zona") == "MESA") {
					zona = EnumZona.MESA;
				}else {
					zona = EnumZona.PISTA;
				}
				ret.add(new Reserva (rs.getString( "cliente_nombre"),rs.getString("fecha"), rs.getString("discoteca_nombre"), zona, rs.getInt("numPers"), rs.getInt("importe")));
			}
			rs.close();
			log( Level.INFO, "BD\t" + sentSQL, null );
			return ret;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	private static String secu( String string ) {
		StringBuffer sb = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ��������������.,:;-_(){}[]-+*=<>'\"�?�!&%$@#/\\0123456789".indexOf(c)>=0) sb.append(c);
		}
		return sb.toString();
	}
	
	public static ArrayList<Producto> getProductos() {
		try (Statement statement = conexion.createStatement()) {
			ArrayList<Producto> ret = new ArrayList<>();
			String sent = "select * from producto;";
			logger.log( Level.INFO, "Statement: " + sent );
			ResultSet rs = statement.executeQuery( sent );
			while( rs.next() ) { // Leer el resultset
				String nombre = rs.getString("nombre");
				double precio = rs.getDouble("precio");
				//ret.add
			}
			return ret;
		} catch (Exception e) {
			procesarError((ex) -> { 
				logger.log( Level.SEVERE, "Excepci�n en getProductos()");
			    System.out.println("Excepcion: "+ex.getMessage());}, e);
			//
			return null;
		}
	}
	

	/////////////////////////////////// LOGGER DE LA BASE DE DATOS ////////////////////////////////////////////////////
	
	
	public static Logger logger = null;  
	/** Asigna un logger ya creado para que se haga log de las operaciones de base de datos
	 * @param logger	Logger ya creado
	 */
	public static void setLogger(Logger logger) {
		BaseDeDatos.logger=logger;
	}
	private static void log(Level level, String msg, Throwable exception) {
		if (logger == null) {
			logger = logger.getLogger(BaseDeDatos.class.getName());
			logger.setLevel(Level.ALL);
			try {
				logger.addHandler(new FileHandler("bd.log.xml", true));
			} catch(Exception e) {
				logger.log(Level.SEVERE, "No se pudo crear fichero de log", e);
			}
		}
		if (exception == null) {
			logger.log(level, msg);
		}else {
				logger.log(level, msg, exception);
			}
		}
	


/////////////////PROCESAR LOS ERRORES//////////////////////
	private static void procesarError( Consumer<Exception> proceso, Exception msg ) {
			proceso.accept( msg );
		}
	}
