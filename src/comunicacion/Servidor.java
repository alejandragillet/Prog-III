package comunicacion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import basedatos.*;
import logica.*;

public class Servidor {
    private static int PUERTO = 4000;

    boolean finComunicacion = false;
    Socket socket;

    public Servidor() {
       
    }

    public void lanzaServidor() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            socket = serverSocket.accept(); // Bloqueante
            System.out.println("Cliente conectado");
            BufferedReader inputDesdeCliente = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputACliente = new PrintWriter(socket.getOutputStream(), true);

            while (!finComunicacion) { // ciclo de lectura desde el cliente hasta que acabe la comunicación
                String textoRecibido = inputDesdeCliente.readLine(); // Ojo: bloqueante (este hilo se queda esperando)

                if (textoRecibido == null) {
                    finComunicacion = true;
                }

                // login:usuario-pass
                String [] split = textoRecibido.split(":");
                System.out.println(split[0]);


                if(split[0].equals("login")) {
                    String usuarioC = split[1].split("-")[0];
                    String passC = split[1].split("-")[1];
                    Cliente clienteC = new Cliente(usuarioC, passC, null);
                    Statement st = BaseDeDatos.conexion.createStatement();
                    ArrayList<String> resultadoR = BaseDeDatos.clienteSelect(st, clienteC, "");
                    
                outputACliente.println("RESPUESTA AL CLIENTE" + resultadoR);
                }

                System.out.println("Recibido de cliente: [" + textoRecibido + "]");
                
             
            }
            System.out.println("El cliente se ha desconectado.");
            socket.close();
        } catch (IOException e) {
            System.err.println("Error en servidor: " + e.getMessage());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
