package ventanas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaCliente extends JFrame {
public VentanaCliente(String titulo) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600,400);
		setLocation(200,10);
		setTitle(titulo);
		
		JPanel pSuperior = new JPanel();
		JPanel pCentral = new JPanel();
		JPanel pInferior = new JPanel();
		
		
		JLabel lTitulo = new JLabel ("Login");
		JButton bAceptar = new JButton ("Aceptar");
		JButton bCancelar = new JButton ("Cancelar");
		JLabel lNick = new JLabel ("Nombre");
		JLabel lPasword = new JLabel ("Contraseņa");
		JLabel lRegistro = new JLabel ("Todavia no tienes cuenta");
		JLabel lMensaje = new JLabel ("ERROR. No se ha encontrado ningun usuario");
		JButton bRegistrarse = new JButton ("Registrarse");
		JTextField tfNick = new JTextField(15);
		JTextField tfPass = new JTextField(10);
		
		
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
		pCentral.add(bAceptar);
		pCentral.add(bCancelar);
		pInferior.add(lRegistro);
		pInferior.add(bRegistrarse);
		
		
		setVisible(true);
		
		//Cerrar la ventana
		bCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();	
			}
		});
		bRegistrarse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VentanaRegistro vr =new VentanaRegistro("Registro");
				vr.setVisible(true);
				dispose();
			}
		});
		
		
		// Cuenta de registro
		bAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
