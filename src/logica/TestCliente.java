package logica;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestCliente {
	private Cliente cliente;

	public class ClienteTest{
		@Before
		public void setUp() {
			cliente = new Cliente("Pedro", "rt8910","Ariz", "189932Q");
			
		}
		@Test
		public void testGetNombre() {
			assertEquals("Pedro", cliente.getNombre());
		}
		@Test
		public void testGetContrasenia() {
			assertEquals("rt8910",cliente.getContrasenia());
		}
		@Test
		public void testGetHorasTrabajadas() {
			assertEquals("Ariz",cliente.getApellido());
		}
		@Test
		public void testGetPrecioHora() {
			assertEquals("189932Q",cliente.getDNI());
		}
		
	}
}
