package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// en realidad debería llamarse Cliente, sin embargo, ya existe una clase Cliente.
// TODO en proceso de creacion
public class Comunicador {
    private static final String HOST = "localhost";
    private static final int PORT = 4000;
    PrintWriter outputAServer;
    boolean finComunicacion = false;

    public Comunicador() {

    }

    public void lanzaCliente() {
        try (Socket socket = new Socket(HOST, PORT)) {
            BufferedReader inputDesdeServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputAServer = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Despues de outputAServer");
            do { // Ciclo de lectura desde el servidor hasta que acabe la comunicación
                String feedback = inputDesdeServer.readLine(); // Devuelve mensaje de servidor o null cuando se cierra
                                                               // la comunicación (BLOQUEANTE - Se queda esperando)
                if (feedback != null) {
                    System.out.println(feedback);
                } else { // Comunicación cortada por el servidor o por error en comunicación
                    finComunicacion = true;
                }
            } while (!finComunicacion);
        } catch (IOException e) {
            System.err.println("Error en cliente: " + e.getMessage() + "\n");
        }
        System.out.println("Fin del proceso del cliente");
    }

    public void emitirMensaje(String mensaje) {
        System.out.println("Emitiendo mensaje: " + mensaje);
        outputAServer.println(mensaje);
        // TODO return "de la respuesta del servidor"
    }

    public void esperarConexion() {
        while (true) {
            System.out.println(outputAServer != null);
            if (outputAServer != null) {
                return;
            }
        } 
    }

    public static void main(String[] args) {
        Servidor ss = new Servidor();
        Comunicador cc = new Comunicador();

        (new Thread() {
            @Override
            public void run() {
                ss.lanzaServidor();
            }
        }).start();

        (new Thread() {
            @Override
            public void run() {
                cc.lanzaCliente();
                System.out.println("");
            }
        }).start();

        cc.esperarConexion();
        cc.emitirMensaje("holaaaaa");
        
    }
}
