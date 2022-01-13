package logica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class StaticVars {
    private static final String pathName = "config.properties";

    static Properties getPropertiesObject() {
        Properties properties = new Properties();
        try {
            InputStream input = new FileInputStream(new File(pathName));
            properties.load(input);
            input.close();
        } catch (Exception err) {
            System.err.println(err);
        }

        return properties;
    }

    static String get(String nombreVariable) {
        Properties propts = getPropertiesObject();
        return propts.getProperty(nombreVariable);
    }

    static void set(String nombreVariable, String valor) {
        Properties propts = getPropertiesObject();
        propts.setProperty(nombreVariable, valor);
        try {
            propts.store(new FileOutputStream(pathName), null);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
