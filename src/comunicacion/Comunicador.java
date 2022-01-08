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
    static Socket socket;
    static PrintWriter outputAServer;
    static BufferedReader inputDesdeServer;
    static boolean finComunicacion = false;

    public static void lanzaCliente() {
        try (Socket s = new Socket(HOST, PORT)) {
            socket = s;
            inputDesdeServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputAServer = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Despues de outputAServer");
            do {
                if (socket.isClosed())
                    finComunicacion = true;
            } while (!finComunicacion);
        } catch (IOException e) {
            System.err.println("Error en cliente: " + e.getMessage() + "\n");
        }
        System.out.println("Fin del proceso del cliente");
    }

    /**
     * Envia y mensaje al servidor. Y devuelve su respuesta
     * 
     * @param mensaje a enviar al servidor
     * @return la respuesta del servidor
     * @throws IOException
     */
    private static String emitirMensaje(String mensaje) throws IOException { // te devuelve el mensaje del servidor
        System.out.println("Emitiendo mensaje: " + mensaje);
        outputAServer.println(mensaje);
        String respuesta = inputDesdeServer.readLine();
        return respuesta;
    }

    public static boolean login(String nombre, String pass) throws IOException {
        String r = emitirMensaje("login:" + nombre + "-" + pass);
        String[] splitted = r.split("-");
        String respuestaLogin = splitted[1];

        if (respuestaLogin == "true") {
            return true;
        } else {
            return false;
        }
    }

    public static void esperarConexion() {
        while (true) {
            try {
                Thread.sleep(1); // necesario
                if (socket != null && socket.isConnected()) {
                    System.out.println("Conexion establecida");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error en esperarConexion: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        (new Thread() {
            @Override
            public void run() {
                Servidor.lanzaServidor();
            }
        }).start();

        (new Thread() {
            @Override
            public void run() {
                Comunicador.lanzaCliente();
                System.out.println("");
            }
        }).start();

        Comunicador.esperarConexion();

        try {
            // para poder llamar a emitirMensaje, el outputAServer debe de esta definido (es decir, conexión establecida)
            String respueta = Comunicador.emitirMensaje("holaaaaa");
            System.out.println("Respuesta: " + respueta);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
