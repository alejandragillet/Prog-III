package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import logica.GestionDiscoteca;
import logica.Puestoaleatorio;

public class VentanaIniciosesion extends JFrame {
	private String horas= JOptionPane.showInputDialog("Horas trabajadas");
	
	
	public VentanaIniciosesion (String titulo, GestionDiscoteca gs, String dni, String contrasenia) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); {
			setSize(600,400);
			setLocation(200,10);
			setTitle(titulo);
			
			JPanel pSuperior = new JPanel();		
			JLabel lTitulo = new JLabel ("Bienvenido");
			JPanel pCentral = new JPanel();
			JPanel pInferior = new JPanel();
			JButton bCancelar = new JButton ("Cancelar");
			
			int hora= 0;
			try {
				hora = Integer.parseInt(horas);
			} catch (NumberFormatException e) {
			}
			
			String puesto = Puestoaleatorio.imprimir(Puestoaleatorio.Puestoaleatorio(1));
			int sueldo = 0;
			if (puesto.equals("Segurata")) {
				sueldo = 15;
			}else if (puesto.equals("Camarero")) {
				sueldo = 10;
			}
			
			JLabel lNick = new JLabel ("DNI: " + dni);

			JLabel lPasword = new JLabel ("Contrase�a: " + contrasenia);
			JLabel lSueldo = new JLabel("Sueldo por noche: " + hora * sueldo);
			JLabel lPuesto = new JLabel ("Puesto de trabajo: "+ puesto);

			pSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));
			pCentral.setLayout(new GridLayout(10, 17));
			
			pSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));

			lTitulo.setFont(new Font("Arial", Font.ITALIC, 26));
			lTitulo.setBackground(Color.GRAY);
			lTitulo.setOpaque(true);
			
			add (pSuperior, BorderLayout.NORTH);
			add (pCentral, BorderLayout.CENTER);
			add (pInferior, BorderLayout.SOUTH);


			
			pSuperior.add(lTitulo);
			pCentral.add(lNick);
			pCentral.add(lPasword);
			pCentral.add(lSueldo);
			pCentral.add(lPuesto);
			pInferior.add(bCancelar);


			bCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();	
				}
			});
			
			setVisible(true);
		

//		bLogIn.addActionListener(new ActionListener() {
//			@Override
//				public void actionPerformed(ActionEvent e) {
//					VentanaTrabajador v1= new VentanaTrabajador("DeustoDisco");
//				v1.setVisible(true);
//				dispose();	
//				}
//			});			

		}
		
	}
	//Es un hilo para hacer un reloj en la sesion de inicio
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

