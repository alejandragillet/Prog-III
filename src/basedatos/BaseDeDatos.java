
package basedatos;

import java.sql.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.*;

import logica.*;
import ventanas.VentanaReservaEntradas;

/**
 * @author saioa
 *
 */
public class BaseDeDatos {
	private static Exception lastError = null; // Informaci�n de �ltimo error SQL ocurrido
	public static Connection conexion;
	public static String nombreBD = "discotecaBD";

	public static GestionDiscoteca gs = new GestionDiscoteca();
	public static ArrayList<Discoteca> discotecas = gs.getlDiscotecas();
	public static ArrayList<Cliente> clientes = gs.getlClientes();
	public static ArrayList<Trabajador> trabajaderes = gs.getlTrabajadores();

	public static Connection getConexion(String nombreBD) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection cc = DriverManager.getConnection("jdbc:sqlite:" + nombreBD + ".db");
			return cc;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Inicializa una BD SQLITE y devuelve una conexi�n con ella
	 * 
	 * @param nombreBD Nombre de fichero de la base de datos
	 * @return Conexi�n con la base de datos indicada. Si hay alg�n error, se
	 *         devuelve null
	 */
	public static Connection initBD(String nombreBD) {
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection("jdbc:sqlite:" + nombreBD + ".db");
			log(Level.INFO, "Conectada base de datos " + nombreBD, null);
			usarCrearTablasBD(conexion);
			return conexion;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en conexi�n de base de datos " + nombreBD, e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Devuelve statement para usar la base de datos
	 * 
	 * @param con Conexi�n ya creada y abierta a la base de datos
	 * @return sentencia de trabajo si se crea correctamente, null si hay cualquier
	 *         error
	 */
	public static Statement usarBD(Connection con) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30); // poner timeout 30 msg
			return statement;
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en uso de base de datos", e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Crea las tablas de la base de datos. Si ya existen, las deja tal cual
	 * 
	 * @param con Conexi�n ya creada y abierta a la base de datos
	 * @return sentencia de trabajo si se crea correctamente, null si hay cualquier
	 *         error
	 */
	public static void usarCrearTablasBD(Connection con) {
		Statement statement = usarBD(con);

		try {
			statement.executeUpdate("create table trabajador " +
					"(nombre VARCHAR(45), puesto VARCHAR(45), sueldo VARCHAR(45), contrasenaT VARCHAR(45), precioHora integer);");
		} catch (SQLException e) {
		} // Tabla ya existe. Nada que hacer
		try {
			statement.executeUpdate("create table cliente " +
					"(nombre VARCHAR(45), apellido VARCHAR(45), dni VARCHAR(45) PRIMARY KEY, contrasena VARCHAR(45));");
		} catch (SQLException e) {
		} // Tabla ya existe. Nada que hacer
		try {
			statement.executeUpdate("create table reserva " +
					"(id INTEGER PRIMARY KEY AUTOINCREMENT, cliente_nombre varchar(20), discoteca_nombre varchar(20), fecha varchar(20), zona varchar(20), numPers int, importe double);");
		} catch (SQLException e) {
		} // Tabla ya existe. Nada que hacer
		try {
			statement.executeUpdate(
					"CREATE TABLE discoteca ( nombre VARCHAR(45), aforoMax integer, aforo integer, numeroTrab integer, direccion VARCHAR(45), id INTEGER, PRIMARY KEY(id AUTOINCREMENT));");
		} catch (SQLException e) {
		} // Tabla ya existe. Nada que hacer
		try {
			statement.executeUpdate(
					"CREATE TABLE producto ( nombre TEXT, precio REAL, id INTEGER, tipoBebida TEXT, tipoComida TEXT, tamanio TEXT, mezcla TEXT, alcohol TEXT, PRIMARY KEY(id AUTOINCREMENT));");
		} catch (SQLException e) {
		} // Tabla ya existe. Nada que hacer

		try {
			statement.executeUpdate(
					"CREATE TABLE almacen ( producto INTEGER, cantidad INTEGER, discoteca INTEGER, PRIMARY KEY(producto,discoteca,cantidad), FOREIGN KEY(producto) REFERENCES producto(id), FOREIGN KEY(discoteca) REFERENCES discoteca(id));");
		} catch (SQLException e) {
		} // Tabla ya existe. Nada que hacer

		try {
			statement.executeUpdate(
					"CREATE TABLE carrito ( producto INTEGER, cantidad INTEGER, reserva INTEGER, PRIMARY KEY(producto,reserva,cantidad), FOREIGN KEY(producto) REFERENCES producto(id), FOREIGN KEY(reserva) REFERENCES reserva(id));");
		} catch (SQLException e) {
		} // Tabla ya existe. Nada que hacer

		try {
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log(Level.INFO, "Creada base de datos", null);
	}

	/**
	 * Reinicia en blanco las tablas de la base de datos.
	 * UTILIZAR ESTE METODO CON PRECAUCION. Borra todos los datos que hubiera ya en
	 * las tablas
	 * 
	 * @param con Conexi�n ya creada y abierta a la base de datos
	 * @return sentencia de trabajo si se borra correctamente, null si hay cualquier
	 *         error
	 */
	public static void reiniciarBD(Connection con) {
		// tabla de reservas no se debe de borrar
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30); // poner timeout 30 msg
			statement.execute("drop table if exists discoteca");
			statement.execute("drop table if exists cliente");
			statement.execute("drop table if exists almacen");
			statement.execute("drop table if exists producto");
			statement.close();
			log(Level.INFO, "Reiniciada base de datos", null);
			usarCrearTablasBD(con);
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en reinicio de base de datos", e);
			lastError = e;
			e.printStackTrace();
		}
	}

	/**
	 * Cierra la base de datos abierta
	 * 
	 * @param con Conexi�n abierta de la BD
	 * @param st  Sentencia abierta de la BD
	 */
	public static void cerrarBD(Connection con, Statement st) {
		try {
			if (st != null)
				st.close();
			if (con != null)
				con.close();
			log(Level.INFO, "Cierre de base de datos", null);
		} catch (SQLException e) {
			lastError = e;
			log(Level.SEVERE, "Error en cierre de base de datos", e);
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve la informaci�n de excepci�n del �ltimo error producido por
	 * cualquiera
	 * de los m�todos de gesti�n de base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}

	/**
	 * A�ade un Discoteca a la tabla abierta de BD, usando la sentencia INSERT de
	 * SQL
	 * 
	 * @param st Sentencia ya abierta de Base de Datos (con la estructura de tabla
	 *           correspondiente a la discoteca)
	 * @param d  Discoteca a a�adir en la base de datos
	 * @return true si la inserci�n es correcta, false en caso contrario
	 */
	public static boolean discotecaInsert(Statement st, Discoteca d) {
		String sentSQL = "";
		try {
			sentSQL = String.format(
					"insert into discoteca (nombre, aforoMax, aforo, numeroTrab, direccion) values('%s',%d,%d,%d,'%s');",
					secu(d.getNombre()), d.getAforoMax(), d.getAforo(), d.getNumeroTrabajadores(), d.getDireccion());
			int val = st.executeUpdate(sentSQL);

			sentSQL = String.format("Select id from discoteca where nombre='%s'",
					secu(d.getNombre()));
			ResultSet rs = st.executeQuery(sentSQL);

			log(Level.INFO, "BD tabla Discoteca a�adida " + val + " fila\t" + sentSQL, null);
			if (val != 1 || rs.getInt("id") == 0) { // Se tiene que a�adir 1 - error si no
				log(Level.SEVERE, "Error en insert de BD\t" + sentSQL, null);
				return false;
			}

			d.setId(rs.getInt("id")); // guardar el id generado para esa discoteca

			for (Entry<Producto, Integer> e : d.getAlmacen().getMapaProductoAlmacen().entrySet()) {
				Producto p = e.getKey();
				productoInsert(st, p); // id del producto es anadido en esta funcion al insertarse
			}

			for (Entry<Producto, Integer> e : d.getAlmacen().getMapaProductoAlmacen().entrySet()) {
				almacenInsert(st, d, e.getKey(), e.getValue());
			}

			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	public static Producto productoInsert(Statement st, Producto p) {
		if (p instanceof Bebida) {
			return bebidaInsert(st, (Bebida) p);
		} else if (p instanceof Comida) {
			return comidaInsert(st, (Comida) p);
		} else {
			return null;
		}
	}

	public static Producto comidaInsert(Statement st, Comida d) {
		try {
			// IGNORE: si ya existe ignora el error de duplicado
			String sql = String.format(
					"INSERT OR IGNORE INTO producto (precio, nombre, tipoBebida, tipoComida, tamanio, mezcla, alcohol) VALUES ('%f', '%s', '%s', '%s', '%s', '%s', '%s')",
					d.getPrecio(), secu(d.getNombre()), null, d.getTipoComida().name(), d.getTipoComida().name(), null,
					null);
			int val = st.executeUpdate(sql);
			if (val != 1) { // Se tiene que a�adir 1 - error si no
				log(Level.SEVERE, "Error en insert de BD\t" + sql, null);
				return null;
			}
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				d.setId(generatedKeys.getInt(1));
			}
			return d;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD", e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public static Producto bebidaInsert(Statement st, Bebida d) {
		try {
			String sql = String.format(
					"INSERT OR IGNORE INTO producto (precio, nombre, tipoBebida, tipoComida, tamanio, mezcla, alcohol) VALUES ('%f', '%s', '%s', '%s', '%s', '%s', '%s')",
					d.getPrecio(), secu(d.getNombre()), secu(d.getTipoBebida().name()), null, null, secu(d.getMezcla()),
					secu(d.getAlcohol()));
			int val = st.executeUpdate(sql);
			if (val != 1) { // Se tiene que a�adir 1 - error si no
				log(Level.SEVERE, "Error en insert de BD\t" + sql, null);
				return null;
			}
			ResultSet generatedKeys = st.getGeneratedKeys();
			if (generatedKeys.next()) {
				d.setId(generatedKeys.getInt(1));
			}

			return d;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD", e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public static boolean almacenInsert(Statement st, Discoteca d, Producto p, int cantidad) {
		try {
			String sql = String.format(
					"INSERT INTO almacen (producto, cantidad, discoteca) VALUES ('%d', '%d', '%d')", p.getId(),
					cantidad, d.getId());
			int val = st.executeUpdate(sql);
			if (val != 1) { // Se tiene que a�adir 1 - error si no
				log(Level.SEVERE, "Error en insert de BD\t" + sql, null);
				return false;
			}
			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD", e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Recoge la lista de discotecas del gestor y la inserta en la base de datos
	 * 
	 * @param discotecas recoge la lista de discotecas del gestor y la recorre
	 * @param st         sentencia abierta con la base de datos
	 */

	public static boolean guardarDiscotecas(Statement st, ArrayList<Discoteca> discotecas, GestionDiscoteca gs) {

		discotecas = gs.getlDiscotecas();
		try {
			for (Discoteca d : discotecas) {
				discotecaInsert(st, d);
			}
		} catch (Exception e) {
			log(Level.SEVERE, "Error al guardar discotecas en base de datos", e);
		}
		System.out.println("Ventana cerrada, disoctecas subidas a la base de datos correctamente");

		return false;
	}

	/**
	 * Realiza una consulta a la tabla abierta de Discotecaes de la BD, usando la
	 * sentencia SELECT de SQL
	 * 
	 * @param st           Sentencia ya abierta de Base de Datos (con la estructura
	 *                     de tabla correspondiente al usuario)
	 * @param codigoSelect Sentencia correcta de WHERE (sin incluirlo) para filtrar
	 *                     la b�squeda (vac�a si no se usa)
	 * @return lista de nombres de Discotecas cargadas desde la base de datos, null
	 *         si hay cualquier error
	 */
	public static ArrayList<String> discotecaSelect(Statement st, String codigoSelect) {
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from Discoteca";
			if (codigoSelect != null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where " + codigoSelect;
			// System.out.println( sentSQL ); // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery(sentSQL);
			while (rs.next()) {
				ret.add(rs.getString("nombre"));
			}
			rs.close();
			log(Level.INFO, "BD\t" + sentSQL, null);
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Discoteca> discotecaSelectAll(Statement st) {
		String sentSQL = "";
		ArrayList<Discoteca> ret = new ArrayList<>();
		try {
			sentSQL = "select * from discoteca;";

			ResultSet rs = st.executeQuery(sentSQL);
			while (rs.next()) {
				Discoteca d = new Discoteca(rs.getString("nombre"), rs.getInt("aforoMax"), rs.getInt("aforo"),
						rs.getInt("numeroTrab"), rs.getString("direccion"), rs.getInt("id"));
				Almacen almacen = new Almacen(selectAlmacenDeDiscoteca(st, d)); // almacen de la discoteca
				d.setAlmacen(almacen);
				ret.add(d);
			}

			rs.close();
			log(Level.INFO, "BD\t" + sentSQL, null);
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Producto> productosSelect(Statement st, Integer idProducto) {
		String placement = idProducto == null ? "'.' OR 1 = 1" : idProducto.toString();

		String sentSQL = "";
		ArrayList<Producto> ret = new ArrayList<>();
		try {
			sentSQL = String.format("SELECT * FROM producto WHERE id = %s;", placement);
			ResultSet rs = st.executeQuery(sentSQL);
			while (rs.next()) {
				String enumBebidaStr = rs.getString("tipoBebida");
				if (!enumBebidaStr.equals("null")) { // debería utilizarse !rs.wasNull() para comprobar si
														// verdaderamente es null y no solo "null"
					// Bebida
					Bebida b = new Bebida(rs.getString("nombre"), EnumBebida.valueOf(enumBebidaStr),
							rs.getString("alcohol"), rs.getString("mezcla"), rs.getDouble("precio"));
					ret.add(b);
				} else {
					// Comida
					Comida c = new Comida(rs.getString("nombre"), EnumComida.valueOf(rs.getString("tipoComida")),
							rs.getString("tamanio"), rs.getDouble("precio"));
					ret.add(c);
				}
			}
			rs.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Producto getProductoIdPorNombre(Statement st, Producto p) {
		try {
			if (p.getId() != null) {
				st.close();
				return p;
			}

			String sql = String.format("SELECT * FROM producto WHERE nombre = '%s';", p.getNombre());

			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				p.setId(rs.getInt("id"));
			}
			rs.close();
			st.close();
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<Producto, Integer> selectAlmacenDeDiscoteca(Statement st, Discoteca d) {
		String sentSQL = "";
		Map<Producto, Integer> ret = new HashMap<>();
		try {
			sentSQL = "select * from almacen where discoteca = " + d.getId();
			System.out.println(sentSQL); // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery(sentSQL);
			while (rs.next()) {
				Statement st2 = usarBD(conexion); // necesitamos un st2 para poder hacer una consulta a la tabla
													// producto mientras que estamos recorriendo un rs que "pertenece" a
													// st.
				Producto p = productosSelect(st2, rs.getInt("producto")).get(0);
				ret.put(p, rs.getInt("cantidad"));
			}
			return ret;
		} catch (Exception e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * A�ade un cliente a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * 
	 * @param st         Sentencia ya abierta de Base de Datos (con la estructura
	 *                   de tabla correspondiente a la habitaci�n)
	 * @param nombre     nombre del cliente que queremos a�adir
	 * @param contrasena contrasena del nuevo cliente
	 * @return true si la inserci�n es correcta, false en caso contrario
	 */
	public static boolean clienteInsert(Statement st, String nombre, String apellido, String DNI, String contrasena) {
		String sentSQL = "";
		try {
			sentSQL = String.format("insert into cliente values('%s','%s','%s','%s');",
					secu(nombre), secu(apellido), secu(DNI), secu(contrasena));
			int val = st.executeUpdate(sentSQL);
			log(Level.INFO, "BD tabla cliente anadida " + val + " fila\t" + sentSQL, null);
			if (val != 1) { // Se tiene que anadir 1 - error si no
				log(Level.SEVERE, "Error en insert de BD\t" + sentSQL, null);
				return false;

			}
			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Recoge la lista de discotecas del gestor y la insrta en la base de datos
	 * 
	 * @param clientes recoge la lista de discotecas del gestor y la recorre
	 * @param st       sentencia abierta con la base de datos
	 */
	public static boolean guardarClientes(Statement st, ArrayList<Cliente> clientes, GestionDiscoteca gs) {

		clientes = gs.getlClientes();
		try {
			for (Cliente c : clientes) {
				clienteInsert(st, c.getNombre(), c.getApellido(), c.getDNI(), c.getContrasenia());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Realiza una consulta a la tabla abierta de clientes de la BD, usando la
	 * sentencia SELECT de SQL
	 * 
	 * @param st           Sentencia ya abierta de Base de Datos (con la estructura
	 *                     de tabla correspondiente al usuario)
	 * @param c            cliente que se busca seleccionar (no null)
	 * @param codigoSelect Sentencia correcta de WHERE (sin incluirlo) para filtrar
	 *                     la b�squeda (vac�a si no se usa)
	 * @return lista de clientes cargados desde la base de datos, null si hay
	 *         cualquier error
	 */
	public static ArrayList<Cliente> clienteSelect(Statement st, Cliente c, String adicional) {
		if (c == null)
			return null;
		String sentSQL = "";
		ArrayList<Cliente> ret = new ArrayList<>();
		try {
			sentSQL = "select * from cliente";

			String where = "nombre='" + c.getNombre() + "'";
			if (adicional != null && !adicional.equals(""))
				sentSQL = sentSQL + " where " + where + " AND " + adicional + ";";
			else
				sentSQL = sentSQL + " where " + where;

			System.out.println(sentSQL); // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery(sentSQL);
			while (rs.next()) {
				ret.add(new Cliente(rs.getString("nombre"), rs.getString("apellido"), rs.getString("contrasena"),
						rs.getString("dni")));
			}
			rs.close();
			log(Level.INFO, "BD\t" + sentSQL, null);
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Cliente> clienteSelectAll(Statement st) {

		String sentSQL = "";
		ArrayList<Cliente> ret = new ArrayList<>();
		try {
			sentSQL = "select * from cliente;";
			System.out.println(sentSQL); // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery(sentSQL);
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				Cliente c = new Cliente(rs.getString("nombre"), rs.getString("contrasena"), rs.getString("apellido"),
						rs.getString("dni"));
				ArrayList<Reserva> reservas = reservaSelectAll(usarBD(conexion), nombre);
				c.setlReservas(reservas);
				ret.add(c);
			}
			rs.close();
			log(Level.INFO, "BD\t" + sentSQL, null);
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Realiza una consulta a la tabla abierta de clientes de la BD, usando la
	 * sentencia SELECT de SQL
	 * 
	 * @param st           Sentencia ya abierta de Base de Datos (con la estructura
	 *                     de tabla correspondiente al usuario)
	 * @param t            trabajador que se busac seleccionar (no null)
	 * @param codigoSelect Sentencia correcta de WHERE (sin incluirlo) para filtrar
	 *                     la b�squeda (vac�a si no se usa)
	 * @return lista de clientes cargados desde la base de datos, null si hay
	 *         cualquier error
	 */

	public static ArrayList<String> trabajadorSelect(Statement st, Trabajador t, String codigoSelect) {
		if (t == null)
			return null;
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from trabajador";
			if (t != null) {
				String where = "nombre='" + t.getNombre() + "'";
				if (codigoSelect != null && !codigoSelect.equals(""))
					sentSQL = sentSQL + " where " + where + " AND " + codigoSelect;
				else
					sentSQL = sentSQL + " where " + where;
			}
			if (codigoSelect != null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where " + codigoSelect;
			System.out.println(sentSQL); // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery(sentSQL);
			while (rs.next()) {
				ret.add(rs.getString("nombre"));
			}
			rs.close();
			log(Level.INFO, "BD\t" + sentSQL, null);
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Trabajador> trabajadorSelectAll(Statement st) {

		String sentSQL = "";
		ArrayList<Trabajador> ret = new ArrayList<>();
		try {
			sentSQL = "select * from trabajador;";
			System.out.println(sentSQL); // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery(sentSQL);
			while (rs.next()) {
				EnumPuesto puesto = null;
				if (rs.getString("puesto") == "CAMARERO" || rs.getString("puesto") == "camarero") {
					puesto = EnumPuesto.CAMARERO;
				} else if (rs.getString("puesto") == "DJ" || rs.getString("puesto") == "dj") {
					puesto = EnumPuesto.DJ;
				} else {
					puesto = EnumPuesto.SEGURATA;
				}
				ret.add(new Trabajador(rs.getString("nombre"), rs.getString("contrasenaT"), rs.getInt("sueldo"),
						puesto));
			}
			rs.close();
			log(Level.INFO, "BD\t" + sentSQL, null);
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Realiza una sentencia INSERT de SQL a la tabla trabajador
	 * 
	 * @param st     Sentencia ya abierta de Base de Datos (con la estructura de
	 *               tabla correspondiente al usuario)
	 * @param dni    dni del trabajador que se inserta en la tabla
	 * @param sueldo sueldo del respactivo trabajador
	 *               aram contrasena contrasena de acceso del respectivo trabajador
	 * @return lista de clientes cargados desde la base de datos, null si hay
	 *         cualquier error
	 */
	private static boolean trabajadorInsert(Statement st, Trabajador t) {
		String sentSQL = "";
		try {
			sentSQL = String.format(
					"insert into trabajador (nombre, contrasenaT, sueldo, puesto, precioHora) values ('%s', '%s', %d, '%s', %d);",
					t.getNombre(),
					t.getContrasenia(), t.getSueldo(), t.getPuesto().toString(), t.getPrecioHora());
			int val = st.executeUpdate(sentSQL);
			log(Level.INFO, "BD tabla trabajador a�adida " + val + " fila\t" + sentSQL, null);
			if (val != 1) { // Se tiene que a�adir 1 - error si no
				log(Level.SEVERE, "Error en insert de BD\t" + sentSQL, null);
				return false;
			}
			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Inserta en la base de datos todos los trabajadores que hay en la lista del
	 * gestor
	 * 
	 * @param st           sentencia ya abierta con la base de datos
	 * @param trabajadores lista de trabajadores que se guarda
	 * @return
	 */
	public static boolean guardarTrabajadores(Statement st, ArrayList<Trabajador> trabajadores, GestionDiscoteca gs) {

		trabajadores = gs.getlTrabajadores();
		try {
			for (Trabajador t : trabajadores) {
				trabajadorInsert(st, t);
			}
		} catch (Exception e) {
			log(Level.SEVERE, "Error al guardar trabajadoes en base de datos", e);
		}
		System.out.println("trabajadores insertados en la bbdd");

		return false;
	}

	/**
	 * A�ade una reserva a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * 
	 * @param st   Sentencia ya abierta de Base de Datos (con la estructura de tabla
	 *             correspondiente a la reserva)
	 * @param d    Discoteca en la que se realiza la reserva
	 * @param nomC nombre del Cliente
	 * @param rf   Rango de fechas de la reserva nueva
	 * @return true si la inserci�n es correcta, false en caso contrario
	 */
	public static boolean reservaInsert(Statement st, String discoteca, String nomCliente, int fecha, EnumZona zona,
			int numPers, int importe) {
		String sentSQL = "";
		try {
			sentSQL = "insert into reserva values(" +
					"'" + secu(nomCliente) + "'," + "'" + secu(discoteca) + "'," + "'" + fecha + "'," + "'" + zona
					+ "'," + "'" + numPers + "',"
					+ "'" + importe + "'" + ");";
			// cliente_nombre string, discoteca_nombre string, fecha integer, string zona,
			// numPers integer, importe integer
			int eu = st.executeUpdate(sentSQL);
			log(Level.INFO, "BD tabla reserva a�adida " + eu + " fila\t" + sentSQL, null);
			if (eu != 1) { // Se tiene que a�adir 1 - error si no
				log(Level.SEVERE, "Error en insert de BD\t" + sentSQL, null);
				return false;
			}
			return true;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<Reserva> reservaSelectAll(Statement st, String nombreCliente) {
		String placement = nombreCliente == null ? "'.' OR 1 = 1"
				: String.format("'%s'", obtenerDNICliente(nombreCliente));
		String sentSQL = "";
		ArrayList<Reserva> ret = new ArrayList<>();
		try {
			sentSQL = String.format("SELECT * FROM reserva WHERE cliente_nombre = %s", placement);
			System.out.println(sentSQL); // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery(sentSQL);
			while (rs.next()) {
				EnumZona zona = null;
				if (rs.getString("zona") == "VIP" || rs.getString("zona") == "vip") {
					zona = EnumZona.VIP;
				} else if (rs.getString("zona") == "MESA" || rs.getString("zona") == "mesa") {
					zona = EnumZona.MESA;
				} else {
					zona = EnumZona.PISTA;
				}

				Discoteca d = new Discoteca(rs.getString("discoteca_nombre"));
				ret.add(new Reserva(rs.getString("cliente_nombre"), rs.getString("fecha"), d, zona,
						rs.getInt("numPers"), rs.getInt("importe")));
			}
			rs.close();
			log(Level.INFO, "BD\t" + sentSQL, null);
			return ret;
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	public static int obtenerDNICliente(String nombreCliente) {
		int dni = -1;
		try {
			PreparedStatement s = conexion.prepareStatement("select * from cliente where nombre = ?");
			s.setString(1, nombreCliente);
			ResultSet rs = s.executeQuery();
			if (rs.next())
				dni = rs.getInt("DNI");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dni;
	}

	public static void registraReserva(Cliente cliente, Reserva reserva, Discoteca disco) {
		try {
			PreparedStatement s = conexion.prepareStatement(
					"insert into reserva (cliente_nombre, discoteca_nombre, fecha, importe,  numPers, zona ) values (?, ?, ?, ?, ?, ?) ");
			s.setInt(1, obtenerDNICliente(cliente.getNombre()));
			s.setString(2, reserva.getFecha());
			s.setString(3, disco.getNombre());
			s.setDouble(4, reserva.getImporte());
			s.setInt(5, reserva.getNumeroPersonas());
			s.setString(6, reserva.getZona().name());

			s.executeUpdate();

			ResultSet gk = s.getGeneratedKeys(); // saca la id de la reserva que se acaba de insertar
			if (gk.next()) {
				reserva.setId(gk.getInt(1));
			}
			s.close();

			for (Entry<Producto, Integer> d : reserva.getMapaProducto().entrySet()) {
				PreparedStatement s1 = conexion.prepareStatement(
						"insert into carrito values (?, ?, ?)");
				// producto, cantidad, reserva
				Producto productoConId = getProductoIdPorNombre(usarBD(conexion), d.getKey()); // saco el id de la bd si existiera
				if (productoConId.getId() == null) {
					// no existe en la bd
					productoConId = productoInsert(usarBD(conexion), d.getKey()); // mete en la bd
				}
				s1.setInt(1, productoConId.getId());
				s1.setInt(2, d.getValue()); // cantidad
				s1.setInt(3, reserva.getId());

				s1.executeUpdate();
				s1.close();
			}

			log(Level.INFO, "Se ha registrado la reserva del cliente: " + cliente, null);
		} catch (SQLException e) {
			log(Level.SEVERE, "Error al registrar la reserva del cliente: " + cliente, e);
		}
	}

	public static void actualizarAforoDisco(Discoteca disco, VentanaReservaEntradas vre) {
		Integer aforoDiscoteca = disco.getAforo();
		aforoDiscoteca = aforoDiscoteca + vre.numeroPersonas;
	}

	public static void actualizarDiscoteca(Discoteca disco) {
		try {
			PreparedStatement s = conexion.prepareStatement(
					"UPDATE discoteca SET aforo = " + disco.getAforo() + "where nombre = " + disco.getNombre() + ";");
			s.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void registrarMapaProducto(Producto producto, Reserva reserva, Integer cantidad) {
		try {
			PreparedStatement s = conexion.prepareStatement(
					"insert into productoMapa (idReserva, producto, cantidad) values (?, ?, ?) ");
			s.setInt(1, reserva.getId());
			s.setString(2, producto.getNombre());
			s.setInt(3, cantidad);
			s.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String secu(String string) {
		StringBuffer sb = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ��������������.,:;-_(){}[]-+*=<>'\"�?�!&%$@#/\\0123456789"
					.indexOf(c) >= 0)
				sb.append(c);
		}
		return sb.toString();
	}

	/////////////////////////////////// LOGGER DE LA BASE DE DATOS
	/////////////////////////////////// ////////////////////////////////////////////////////

	public static Logger logger = null;

	/**
	 * Asigna un logger ya creado para que se haga log de las operaciones de base de
	 * datos
	 * 
	 * @param logger Logger ya creado
	 */
	public static void setLogger(Logger logger) {
		BaseDeDatos.logger = logger;
	}

	private static void log(Level level, String msg, Throwable exception) {
		if (logger == null) {
			logger = Logger.getLogger(BaseDeDatos.class.getName());
			logger.setLevel(Level.ALL);
			try {
				logger.addHandler(new FileHandler("bd.log.xml", true));
			} catch (Exception e) {
				logger.log(Level.SEVERE, "No se pudo crear fichero de log", e);
			}
		}
		if (exception == null) {
			logger.log(level, msg);
		} else {
			logger.log(level, msg, exception);
		}
	}
}
