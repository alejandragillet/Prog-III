package ventanas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import basedatos.BaseDeDatos;
import logica.Cliente;
import logica.GestionDiscoteca;
import logica.Persona;
import logica.Producto;

public class VentanaRegistro extends JFrame {
public VentanaRegistro(String titulo, GestionDiscoteca gs){
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600,400);
		setTitle(titulo);
		
		JPanel pSuperior = new JPanel();
		JPanel pCentral = new JPanel();
		JPanel pInferior = new JPanel();
		
		JLabel lTitulo = new JLabel ("Registrate aqui!");
		JButton bAceptar = new JButton ("Aceptar");
		JButton bLogIn = new JButton ("Log In");
		JLabel lNick = new JLabel ("Nombre");
		JLabel lApellido = new JLabel ("Apellido");
		JLabel lDNI = new JLabel ("DNI");
		JLabel lPasword = new JLabel ("Contrasena");
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
				VentanaCliente v1= new VentanaCliente("DeustoDisco", gs);
				v1.setVisible(true);
				dispose();	
			}
		});
		
	
		bAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gs.cargarFicheroBinarioCliente(gs.getlCLientes(), "cliente.dat");
				//System.out.println("Cagados;;" + gs.getlCLientes());
				for (Cliente cliente : gs.getlCLientes()) {
					if(tfNick.getText().equals(cliente.getNombre()) && tfApellido.getText().equals(cliente.getApellido())) { //Nombre y Apellido de usuario ha sido usado
						JOptionPane.showMessageDialog(VentanaRegistro.this, "Ese nombre y apellido de cliente ya existen. Por favor vuelva a intentarlo");
						tfNick.setText("");
						tfApellido.setText("");
						tfDNI.setText("");
						tfPass.setText("");
						return;
					}
				}
				Cliente cl =new Cliente(tfNick.getText(),tfPass.getText(), tfApellido.getText(),tfDNI.getText()); //Crear nuevo cliente
				gs.getlCLientes().add(cl);
				System.out.println("Lista clientes: " + gs.getlCLientes());
				//BaseDeDatos.clienteInsert(null, tfNick.getText(), tfDNI.getText(), tfPass.getText());
				JOptionPane.showMessageDialog(VentanaRegistro.this, "Se ha registrado correctamente. Ahora registrate");
			}
		});
		
	}
///HILO PARA INICIAR CON UNOS SEGUNDOS////////////////////
		private void esperarXsegundos(int segundos) {
			try {
				Thread.sleep(segundos * 1000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
		}


}
