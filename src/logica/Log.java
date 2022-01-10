package logica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log {
	private BufferedWriter buffered;
	private String ruta;// Tambien se puede meter un fichero
	private Object close;

	public Log(String ruta) {
		super();
		this.ruta = ruta;
	}

	public Log(String ruta, boolean reset) throws IOException {// El reset se utiliza por si se quiere reiniciar
		super();
		this.ruta = ruta;
		this.abrirPrograma(!reset);
	}

	private void abrirPrograma(boolean append) throws IOException {// En el momento de enceder el programa se debe de
																	// lanzar hacia arriba
		this.buffered = new BufferedWriter(new FileWriter(this.ruta, append));
	}

	// se a�aden las lineas donde se van mostrando los clientes que entran en el
	// programa
	public void anadeLinea(String tot) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
		String formatoFecha = sdf.format(new Date());

		if (this.buffered == null) {
			// si no se ha abierto el archivo => se abre
			this.abrirPrograma(true);
		}

		this.buffered.write("[" + formatoFecha + "]" + tot + "\n");
		this.close();
	}

	public String[] getLines() throws IOException {
		ArrayList<String> linesFile = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(this.ruta));
		// se van a�adiendo las lineas
		String line;
		while ((line = br.readLine()) != null) {
			linesFile.add(line);
		}

		// se pasa el array y se recorre
		br.close();
		String[] lines = new String[(linesFile.size())];

		for (int i = 0; i < linesFile.size(); i++) {
			lines[i] = linesFile.get(i);

		}

		return lines;

	}

	public void resetLog() throws IOException {
		this.abrirPrograma(false);
		this.close();
	}

	public void close() throws IOException {

		this.buffered.close();
	}

	// Prueba del log
	public static void main(String[] args) throws IOException {
		Log miLog = new Log("milog.log");

		miLog.anadeLinea("Ale");

		// miLog.resetLog();

		// miLog.anadeLinea("Pedro");
	}

}
