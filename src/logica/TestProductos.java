package logica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import basedatos.BaseDeDatos;




public class TestProductos {
	
	@Before
	public void init() throws Exception {
		BaseDeDatos.initBD(null);
	}

	@After
	public void finit() throws Exception {
		BaseDeDatos.cerrarBD(null, null);
	}
	@Test
	public void testDiscotecas() {
		ArrayList<Producto> pT = BaseDeDatos.getProductos();
		assertEquals( 11, pT.size() );
		
	}
}
