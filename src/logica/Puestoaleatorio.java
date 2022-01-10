package logica;

public class Puestoaleatorio {

	public static String[] Puestoaleatorio(int cantidad) {
		String[] Puestoaleatorio = new String[cantidad];

		String[] Puesto = { "Camarero", "Segurata" };

		for (int i = 0; i < cantidad; i++) {
			Puestoaleatorio[i] = Puesto[(int) (Math.floor(Math.random() * ((Puesto.length - 1) - 0 + 1) + 0))];
		}
		return Puestoaleatorio;
	}

	public static String imprimir(String[] puestoGenerados) {
		String s = "";
		for (int i = 0; i < puestoGenerados.length; i++) {
			s = (puestoGenerados[i]);
		}
		return s;
	}

	public static void main(String[] args) {
		imprimir(Puestoaleatorio(1));
	}
}