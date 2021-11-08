package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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

                System.out.println("Recibido de cliente: [" + textoRecibido + "]");
                
                outputACliente.println("RESPUESTA AL CLIENTE");
            }
            System.out.println("El cliente se ha desconectado.");
            socket.close();
        } catch (IOException e) {
            System.err.println("Error en servidor: " + e.getMessage());
        }
    }
}
