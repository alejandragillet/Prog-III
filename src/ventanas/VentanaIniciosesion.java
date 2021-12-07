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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import logica.GestionDiscoteca;

public class VentanaIniciosesion extends JFrame {
	public VentanaIniciosesion (String titulo, GestionDiscoteca gs) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); {
			setSize(600,400);
			setLocation(200,10);
			setTitle(titulo);
			
			JPanel pSuperior = new JPanel();		
			JLabel lTitulo = new JLabel ("Bienvenido");
			

			
			pSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));

			lTitulo.setFont(new Font("Arial", Font.ITALIC, 26));
			lTitulo.setBackground(Color.GRAY);
			lTitulo.setOpaque(true);
			
			add (pSuperior, BorderLayout.NORTH);

			pSuperior.add(lTitulo);

		
			setVisible(true);
//			
////			bLogIn.addActionListener(new ActionListener() {
////				@Override
////				public void actionPerformed(ActionEvent e) {
////					VentanaTrabajador v1= new VentanaTrabajador("DeustoDisco");
////					v1.setVisible(true);
////					dispose();	
////				}
////			});			
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

