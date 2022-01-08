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
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class VentanaTrabajador extends JFrame {
	private static Thread hilo;
	public VentanaTrabajador (String titulo) {
		addWindowListener(new WindowAdapter() {
			
			
			@Override
			public void windowClosed(WindowEvent e) {
				hilo.interrupt();
			}
			
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600,400);
		setLocation(200,10);
		setTitle(titulo);
		
		JPanel pSuperior = new JPanel();
		JPanel pCentral = new JPanel();
		JPanel pInferior = new JPanel();
		
		
		JLabel lTitulo = new JLabel ("Inicia tu sesion");
		JButton bCancelar = new JButton ("Cancelar");
		JLabel lNick = new JLabel ("DNI");
		JLabel lPasword = new JLabel ("Contraseña");
		JButton bIniciosesion = new JButton ("Inicio de sesion");
		JTextField tfNick = new JTextField(8);
		
		JTextField tfPass = new JTextField(10);
		hilo= new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!Thread.interrupted()) {
					try {
						char[] s = tfNick.getText().toCharArray();
						if (s.length == new char[8].length) {
							tfNick.setEditable(false);
						}
					} catch (NullPointerException e) {
						// TODO: handle exception
					}
					
				}
			}
		});
		hilo.start();
	
		
		pSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));
		pCentral.setLayout(new GridLayout(10, 17));
		
		
		pSuperior.setBackground(Color.WHITE);
		pCentral.setBackground(Color.WHITE);
		pInferior.setBackground(Color.WHITE);
		lTitulo.setFont(new Font("Arial", Font.ITALIC, 26));
		lTitulo.setBackground(Color.GRAY);
		lTitulo.setOpaque(true);
		
		
		add (pSuperior, BorderLayout.NORTH);
		add (pCentral, BorderLayout.CENTER);
		add (pInferior, BorderLayout.SOUTH);
		pSuperior.add(lTitulo);
		pCentral.add(lNick);
		pCentral.add(tfNick);
		pCentral.add(lPasword);
		pCentral.add(tfPass);
		pCentral.add(bIniciosesion);
		pInferior.add(bCancelar);
		
		
		
		setVisible(true);
		
		//Cerrar la ventana
		bCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();	
			}
		});
		bIniciosesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			VentanaIniciosesion vr =new VentanaIniciosesion("Inicio de sesion", null, tfNick.getText(), tfPass.getText()); 
				vr.setVisible(true);
				dispose();
			}
		});

}

}
