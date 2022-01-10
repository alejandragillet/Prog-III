package logica;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TrabajadorTest {
	private Trabajador trabajador;

	@Before
	public void setUp() {
		trabajador = new Trabajador("manuel", "17", 12, 6, EnumPuesto.SEGURATA);

	}

	@Test
	public void testGetNombre() {
		assertEquals("manuel", trabajador.getNombre());
	}

	@Test
	public void testGetContrasenia() {
		assertEquals("17", trabajador.getContrasenia());
	}

	@Test
	public void testGetHorasTrabajadas() {
		assertEquals(12, trabajador.getHorasTrabajadas());
	}

	@Test
	public void testGetPrecioHora() {
		assertEquals(6, trabajador.getPrecioHora());
	}

	@Test
	public void testGetPuesto() {
		assertEquals(EnumPuesto.SEGURATA, trabajador.getPuesto());
	}

}
