import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Reserva {
    protected double importe;
    protected String fecha;
    protected HashMap<Producto, Integer> mapaProducto = new HashMap<Producto, Integer>();
    protected int numeroPersonas;
    protected EnumZona zona;
    
    public Reserva() {
    	this.importe = importe;
    	this.mapaProducto = mapaProducto;
    	this.numeroPersonas = numeroPersonas;
    	this.zona = zona; 
    	
    }
    public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public HashMap<Producto, Integer> getMapaProducto() {
		return mapaProducto;
	}

	public void setMapaProducto(HashMap<Producto, Integer> mapaProducto) {
		this.mapaProducto = mapaProducto;
	}

	public int getNumeroPersonas() {
		return numeroPersonas;
	}

	public void setNumeroPersonas(int numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}

	public EnumZona getZona() {
		return zona;
	}

	public void setZona(EnumZona zona) {
		zona = zona;
	}

	public double calcImporte() {
    	importe = 0;
    	for (Map.Entry<Producto, Integer> entry: mapaProducto.entrySet()) {
    		Producto key = entry.getKey();
    		Integer value = entry.getValue();
    		importe = importe + (key.getPrecio() * value) ;
    	
    	}
    	return importe;
    }
    	
   public void anadirAlMapa(Producto pNuevo) {
	   if (mapaProducto.size() == 0) {
		   mapaProducto.put(pNuevo, 1);
	   }else {
		   try {
			   for(Map.Entry<Producto, Integer> entry : mapaProducto.entrySet()) {
				   Producto key = entry.getKey();
				   Integer value = entry.getValue();
				   if (pNuevo.getNombre().equals(key.getNombre())) {
					   mapaProducto.replace(key,  value +1);
					   return;
				   }
			   }
			   
		   }catch(Exception e) {
			   
		   }
	   }
   }
   
   public void eliminarDelMapa(Producto producto) {
	   try {
		   for (Map.Entry<Producto, Integer > entry : mapaProducto.entrySet() ) {
			   Producto key =entry.getKey();
			   Integer value = entry.getValue();
			   if (producto.getNombre().equals(key.getNombre())) {
				   if (value == 1) {
					   mapaProducto.remove(producto);
					   return;
				   }else {
					   mapaProducto.replace(key,  value -1);
					   return;
				   }
		   }
		   }
	   }catch (Exception e) {
		   
	   }
   }
	   

    
    	
    
}
