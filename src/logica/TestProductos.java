package logica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import basedatos.BaseDeDatos;


public class TestProductos {
	@Test
	public void test() {
		BaseDeDatos.initBD("GestionDiscoteca.db");
		
		ArrayList<Producto> l_productos = BaseDeDatos.getProductos();
		
		ArrayList<Producto> l_producto1 = new ArrayList<Producto>();
		for (Producto producto : l_productos) {
			l_producto1.addAll(BaseDeDatos.getProductos());
		}
		assertEquals(l_productos.size(), l_producto1.size());

		for (Producto producto1 :l_productos) {
			boolean encontrado = false;
			for (Producto producto2 : l_producto1) {
				if (producto1.equals(producto2)) {
					encontrado = true;
					assertEquals(producto1.getNombre(), producto2.getNombre());
					assertEquals(producto1.getPrecio(), producto2.getPrecio());
				}
			}
			assertTrue(encontrado);
		}

		BaseDeDatos.cerrarBD(null, null);
	}

}
