package basedatos;


import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.*;


import logica.*;

public class BaseDeDatos {
private static Exception lastError = null; // Información de último error SQL ocurrido
private static Connection conexion;
	
	/** Inicializa una BD SQLITE y devuelve una conexión con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexión con la base de datos indicada. Si hay algún error, se devuelve null
	 */
	public static Connection initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			log( Level.INFO, "Conectada base de datos " + nombreBD, null );
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en conexión de base de datos " + nombreBD, e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Devuelve statement para usar la base de datos
	 * @param con	Conexión ya creada y abierta a la base de datos
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
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarCrearTablasBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			try {
				statement.executeUpdate("create table trabajador " +
					"(dni String, salario integer, contrasenaT string)");
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			try {
				statement.executeUpdate("create table cliente " +
					"(nombre string, numTlfn String, contrasena string)");
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			try {
				statement.executeUpdate("create table reserva " +
					"(cliente_nombre string, disc_nombre string, fecha integer)");
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			try {
				statement.executeUpdate("create table discoteca " +
					"(nombre String, aforoMax integer, numeroTrab integer, direccion String)");
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer
			log( Level.INFO, "Creada base de datos", null );
			return statement;
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en creación de base de datos", e );
			e.printStackTrace();
			return null;
		}
	}
	
	/** Reinicia en blanco las tablas de la base de datos. 
	 * UTILIZAR ESTE METODO CON PRECAUCION. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexión ya creada y abierta a la base de datos
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
	 * @param con	Conexión abierta de la BD
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
	
	/** Devuelve la información de excepción del último error producido por cualquiera 
	 * de los métodos de gestión de base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}
	
	
	/** Añade un Discoteca a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st		Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la discoteca)
	 * @param d			Discoteca a añadir en la base de datos
	 * @return true 	si la inserción es correcta, false en caso contrario
	 */
	public static boolean DiscotecaInsert( Statement st, Discoteca  d ) {
		String sentSQL = "";
		try {
			sentSQL = "insert into cliente values(" +
					"'" + secu(d.getNombre()) + "'," +
					"'" + d.getAforoMax() + "'" +d.getNumeroTrabajadores()+ "'" + d.getDireccion()+")";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD tabla Discoteca añadida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que añadir 1 - error si no
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

	/** Realiza una consulta a la tabla abierta de Discotecaes de la BD, usando la sentencia SELECT de SQL
	 * @param st			Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la búsqueda (vacía si no se usa)
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

	

	/** Añade una habitación a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st		 Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la habitación)
	 * @param nombre	 nombre del cliente que queremos añadir
	 * @param tlfn		 numero de telefono del cliente
	 * @param contrasena contrasena del nuevo cliente
	 * @return true 	 si la inserción es correcta, false en caso contrario
	 */
	public static boolean clienteInsert( Statement st, String nombre , String tlfn, String contrasena  ) {
		String sentSQL = "";
		try {
			sentSQL = "insert into cliente values(" +
					"'" + secu(nombre) + "'," +
					"'" + secu(tlfn) + "'" +secu(contrasena)+ "')";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD tabla cliente añadida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que añadir 1 - error si no
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

	/** Realiza una consulta a la tabla abierta de clientes de la BD, usando la sentencia SELECT de SQL
	 * @param st			Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param c				cliente que se busca seleccionar (no null)
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la búsqueda (vacía si no se usa)
	 * @return				lista de clientes cargados desde la base de datos, null si hay cualquier error
	 */
	public static ArrayList<String> clienteSelect( Statement st, Cliente c, String codigoSelect ) {
		if (c==null) return null;
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from cliente";
			if (c!=null) {
				String where = "Cliente_nombre='" + c.getNombre() + "'";
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
	
	/** Realiza una consulta a la tabla abierta de clientes de la BD, usando la sentencia SELECT de SQL
	 * @param st			Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param t				trabajador que se busac seleccionar (no null)
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la búsqueda (vacía si no se usa)
	 * @return				lista de clientes cargados desde la base de datos, null si hay cualquier error
	 */
	
	public static ArrayList<String> trabajadorSelect( Statement st, Trabajador t, String codigoSelect ) {
		if (t==null) return null;
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from trabajador";
			if (t!=null) {
				String where = "Trabajador_nombre='" + t.getNombre() + "'";
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
	
	/** Realiza una consulta a la tabla abierta de clientes de la BD, usando la sentencia SELECT de SQL
	 * @param st			Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param dni			dni del trabajador que se inserta en la tabla
	 * @param salario		salario del respactivo trabajador
	 * aram contrasena		contrasena de acceso del respectivo trabajador
	 * @return				lista de clientes cargados desde la base de datos, null si hay cualquier error
	 */
	public static boolean trabajadorInsert( Statement st, String dni , int salario, String contrasena  ) {
		String sentSQL = "";
		try {
			sentSQL = "insert into cliente values(" +
					"'" + secu(dni) + "'," +
					"'" + salario +"'"+secu(contrasena)+ "')";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD tabla trabajador añadida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que añadir 1 - error si no
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
	

	/** Añade una reserva a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la reserva)
	 * @param d		Discoteca en la que se realiza la reserva
	 * @param nomC	nombre del Cliente
	 * @param rf	Rango de fechas de la reserva nueva
	 * @return true si la inserción es correcta, false en caso contrario
	 */
	public static boolean reservaInsert( Statement st, Discoteca d, String nomC, int fecha ) {
		String sentSQL = "";
		try {
			sentSQL = "insert into reserva values(" +
					"'" + secu(d.getNombre()) + "'," +
					"'" + secu(nomC) + "'," +
					"," + fecha + ")";
			int eu = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD tabla reserva añadida " + eu + " fila\t" + sentSQL, null );
			if (eu!=1) {  // Se tiene que añadir 1 - error si no
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
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la búsqueda (vacía si no se usa)
	 * @return	lista de reservas cargadas desde la base de datos, null si hay cualquier error
	 */
	public static ArrayList<String> reservaSelect( Statement st, Discoteca d, String cliente, String codigoSelect ) {
		if (d==null || cliente==null || cliente.isEmpty()) return null;
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from reserva";
			if (d!=null) {
				String where = "Discoteca_nombre='" + d.getNombre() + "' AND cliente_nombre='" + cliente + "'";
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

	
	
	private static String secu( String string ) {
		// Implementación (1)
		// return string.replaceAll( "'",  "''" ).replaceAll( "\\n", "" );
		// Implementación (2)
		StringBuffer sb = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZñÑáéíóúüÁÉÍÓÚÚ.,:;-_(){}[]-+*=<>'\"¿?¡!&%$@#/\\0123456789".indexOf(c)>=0) sb.append(c);
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
			//T6
			//logger.log( Level.SEVERE, "Excepción", e );
			procesarError((ex) -> { 
				logger.log( Level.SEVERE, "Excepción en getProductos()");
			    System.out.println("Excepcion: "+ex.getMessage());}, e);
			//
			return null;
		}
	}
	

	/////////////////////////////////// LOGGER DE LA BASE DE DATOS ////////////////////////////////////////////////////
	
	
	public static Logger logger = null;  // cambio en tarea 2 para poderlo utilizar desde allí
	// Método público para asignar un logger externo
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


