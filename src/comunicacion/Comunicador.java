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
    Socket socket;
    PrintWriter outputAServer;
    BufferedReader inputDesdeServer;
    boolean finComunicacion = false;

    public Comunicador() {

    }

    public void lanzaCliente() {
        try (Socket socket = new Socket(HOST, PORT)) {
            this.socket = socket;
            inputDesdeServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputAServer = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Despues de outputAServer");
            do {
                if (socket.isClosed()) finComunicacion = true;
            } while (!finComunicacion);
        } catch (IOException e) {
            System.err.println("Error en cliente: " + e.getMessage() + "\n");
        }
        System.out.println("Fin del proceso del cliente");
    }

    public String emitirMensaje(String mensaje) throws IOException {
        System.out.println("Emitiendo mensaje: " + mensaje);
        outputAServer.println(mensaje);
        String respuesta = inputDesdeServer.readLine();
        return respuesta;
    }

    public void esperarConexion() {
        while (true) {
            System.out.println(this.socket != null && this.socket.isConnected()); // preguntar

            if (this.socket != null && this.socket.isConnected()) {
                return; // dejar de esperar
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
        
        try {
            // para poder llamar a emitirMensaje, el outputAServer debe de esta definido (es decir, conexión establecida)
            String respueta = cc.emitirMensaje("holaaaaa");
            System.out.println("Respuesta: " + respueta);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
