import java.time.format.TextStyle;

import jdk.jfr.Timestamp;

public class Junit {
    @Test
    public void TestDivision(){
        Operaciones op = new Operaciones();
        double res = op.division(4,2);
        assertEqulals(2, res);
    }
    
    public class LeerFichero {
    
        public void muestraFichero(String archivo) throws FileNotFoundException, IOException {
            String cadena;
            FileReader f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine())!=null) {
                System.out.println(cadena);
            }
            b.close();
        }
    
        public void main(String[] args) throws IOException {
            muestraContenido("/home/mario/archivo.txt");
        }
        public double division(double a, double b) throws Exception{
            if(b==0){
                throw new Exception("Cero no funciona");
            }
            double res = a/b;
            return res;
            }
        }
        
       
    }

}
