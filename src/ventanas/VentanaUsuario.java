package ventanas;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaUsuario extends JFrame {
	private JButton crearCliente;
	private JButton crearTrabajador;
	
	
	
	public VentanaUsuario() {
		
		
		
		Container cp=this.getContentPane();
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new GridLayout(6, 2));
		
		crearCliente = new JButton("Crear usuario");
		crearTrabajador = new JButton("Crear Trabajador");
		
		panelCentral.add(crearCliente);
		panelCentral.add(crearTrabajador);
		
		cp.add(panelCentral);
		
		setTitle("Eleccion usuario/trabajador");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
	}
		
		//Posible hilo en el momento de clicar boton para cliente
		/*crearCliente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Thread hilo = new Thread(new Runnable(){

					public void run(){
						
					
				VentanaCliente vent= new VentanaCliente(); //Meter los datos de la base de datos
				vent.setLocation( 620, 300 );
				vent.setVisible( true );
				}
				});
				hilo.start();
			}
			
		});
		*/
	
		
		
		//Main
		public static void main(String[] args) {
			VentanaUsuario ventana = new VentanaUsuario();
			ventana.setLocation( 620, 300 );
			ventana.setVisible( true );
		

	}
}
