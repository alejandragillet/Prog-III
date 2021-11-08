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

public class VentanaRegistro extends JFrame {
public VentanaRegistro(String titulo){
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600,400);
		setTitle(titulo);
		
		JPanel pSuperior = new JPanel();
		JPanel pCentral = new JPanel();
		JPanel pInferior = new JPanel();
		
		JLabel lTitulo = new JLabel ("Regístrate");
		JButton bAceptar = new JButton ("Aceptar");
		JButton bLogIn = new JButton ("Log In");
		JLabel lNick = new JLabel ("Nombre");
		JLabel lApellido = new JLabel ("Apellido");
		JLabel lDNI = new JLabel ("DNI");
		JLabel lPasword = new JLabel ("Contraseña");
		JTextField tfNick = new JTextField(15);
		JTextField tfApellido = new JTextField(15);
		JTextField tfDNI = new JTextField(10);
		JTextField tfPass = new JTextField(10);
		JLabel lRegistro = new JLabel("UNETE A NOSTROS");
		
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
		pCentral.add(lApellido);
		pCentral.add(tfApellido);
		pCentral.add(lDNI);
		pCentral.add(tfDNI);
		pCentral.add(lPasword);
		pCentral.add(tfPass);
		pCentral.add(bAceptar);
		pCentral.add(bLogIn);

		
		
		//Vuelve hacia atras
		bLogIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaCliente v1= new VentanaCliente("DeustoDisco");
				v1.setVisible(true);
				dispose();	
			}
		});
		
	
		
		
	}

}
