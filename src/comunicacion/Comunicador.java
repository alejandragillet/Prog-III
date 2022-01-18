package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;

import logica.Cliente;
import logica.GestionDiscoteca;

// en realidad debería llamarse Cliente, sin embargo, ya existe una clase Cliente.
public class Comunicador {
    private static final String HOST = "localhost";
    private static final int PORT = 4000;
    static Socket socket;
    static PrintWriter outputAServer;
    static BufferedReader inputDesdeServer;
    static boolean finComunicacion = false;

    public static void lanzaCliente() {
        try (Socket s = new Socket(HOST, PORT)) { // se crea un socket y se intenta conecctar al HOST:PORT
            socket = s; // lo guardo como atributo
            inputDesdeServer = new BufferedReader(new InputStreamReader(socket.getInputStream())); // se crea un objeto que nos permite recibir mensajes del servidor 
            outputAServer = new PrintWriter(socket.getOutputStream(), true); // se crea un objeto que nos permite enviar mensajes al servidor
            System.out.println("Despues de outputAServer");
            do {
                if (socket.isClosed()) // comprobar si la conexión se ha cerrado
                    finComunicacion = true; // hago terminar el bucle
            } while (!finComunicacion);
        } catch (IOException e) {
            GestionDiscoteca.LOG_RAIZ.log(Level.SEVERE, "Programa cerrandose debido a un error en la conexión con el servidor", e);
            System.exit(-1); // cierro el programa para evitar problemas mayores (login, registro)
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
        outputAServer.println(mensaje); // emite mensaje a servidor
        String respuesta = inputDesdeServer.readLine(); // espera una respuesta del servidor
        return respuesta;
    }

    public static Cliente login(String nombre, String pass) throws IOException {
        String r = emitirMensaje("login:" + nombre + "-" + pass);
        String[] splitted = r.split("-");
        String respuestaLogin = splitted[1];

        if (respuestaLogin.equals("true")) {
            String apellido = splitted[2];
            String DNI = splitted[3];
            return new Cliente(nombre, pass, apellido, DNI);
        }
        return null;
    }

    public static Cliente registrarse(String nombre, String pass, String DNI, String apellido) throws IOException {
        String r = emitirMensaje("register:" + nombre + "-" + pass + "-" + DNI + "-" + apellido);
        String[] splitted = r.split("-");
        String respuestaRegistro = splitted[1];
        System.out.println(respuestaRegistro);

        if (respuestaRegistro.equals("true")) {
            return new Cliente(nombre, pass, apellido, DNI);
        }
        return null;
    }
}
