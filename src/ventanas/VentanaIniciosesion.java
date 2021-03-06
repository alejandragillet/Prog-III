package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logica.EnumPuesto;
import logica.GestionDiscoteca;
import logica.Puestoaleatorio;
import logica.Trabajador;

public class VentanaIniciosesion extends JFrame {
	private String horas = JOptionPane.showInputDialog("Horas trabajadas");
	private int sueldo;

	public VentanaIniciosesion(String titulo, GestionDiscoteca gs, String dni, String contrasenia) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		{
			setSize(600, 400);
			setLocation(200, 10);
			setTitle(titulo);

			JPanel pSuperior = new JPanel();
			JLabel lTitulo = new JLabel("Bienvenido");
			JPanel pCentral = new JPanel();
			JPanel pInferior = new JPanel();
			JButton bCancelar = new JButton("Cancelar");

			int hora = 0;
			try {
				hora = Integer.parseInt(horas);
			} catch (NumberFormatException e) {
			} catch (Exception exc) {
				
			}

			String puesto = Puestoaleatorio.imprimir(Puestoaleatorio.PuestoAleatorio(1));
			EnumPuesto epuesto = EnumPuesto.CAMARERO;
			sueldo = 0;
			if (puesto.equals("Segurata")) {
				sueldo = 15;
				epuesto = EnumPuesto.SEGURATA;
			} else if (puesto.equals("Camarero")) {
				sueldo = 10;
				epuesto = EnumPuesto.CAMARERO;
			}
			sueldo *= hora;
			
			Trabajador t = new Trabajador(dni, contrasenia, hora, sueldo/hora, epuesto);
			gs.getlTrabajadores().add(t);
			gs.guardarFicheroBinarioTrabajador(gs.getlTrabajadores(), "trabajador.dat");

			JLabel lNick = new JLabel("DNI/NOMBRE: " + dni);

			JLabel lPasword = new JLabel("Contrase�a: " + contrasenia);
			JLabel lSueldo = new JLabel("Sueldo por noche: " + sueldo);
			JLabel lPuesto = new JLabel("Puesto de trabajo: " + puesto);

			pSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));
			pCentral.setLayout(new GridLayout(10, 17));

			pSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));

			lTitulo.setFont(new Font("Arial", Font.ITALIC, 26));
			lTitulo.setBackground(Color.GRAY);
			lTitulo.setOpaque(true);

			add(pSuperior, BorderLayout.NORTH);
			add(pCentral, BorderLayout.CENTER);
			add(pInferior, BorderLayout.SOUTH);

			pSuperior.add(lTitulo);
			pCentral.add(lNick);
			pCentral.add(lPasword);
			pCentral.add(lSueldo);
			pCentral.add(lPuesto);
			pInferior.add(bCancelar);

			this.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosed(WindowEvent e) {
					System.out.println(dni);
					System.out.println(contrasenia);
					System.out.println(sueldo);
					System.out.println(puesto);
				}

			});

			bCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			setVisible(true);

		}

	}

	// Es un hilo para hacer un reloj en la sesion de inicio
	public void relojDeLaSesionDeInicio() {
		DateTimeFormatter tiempo = DateTimeFormatter.ofPattern("HH:mm:ss");
		JLabel tiempReloj = new JLabel("Reloj");
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
						tiempReloj.setText(tiempo.format(LocalDateTime.now()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread hilo = new Thread(runnable);
		hilo.start();
	}
}
