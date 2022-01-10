package logica;

import java.util.Scanner;

public class SueldoTrabajador {
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);

		int horas;
		int precio;
		int sueldo;

		System.out.println("Horas trabajadas");
		horas = entrada.nextInt();
		System.out.println("Precio por hora");
		precio = entrada.nextInt();

		sueldo = horas * precio;
		System.out.println("El sueldo es de" + "\t" + sueldo + "euros");
	}
}
