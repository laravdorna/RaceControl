package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FicheroJSON {
	static ObjectMapper objectMapper = new ObjectMapper();

	public static <objectClass> ArrayList<?> leer(String nombreFichero, Class<?> objectClass) {
		
		
		ArrayList<objectClass> objects = new ArrayList<objectClass>();
		try {
			File file = new File(nombreFichero);
			objects = objectMapper.readValue(file, new TypeReference<ArrayList<objectClass>>(){});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return objects;
	}

	public static void escribir(ArrayList<?> objects, String nombreFichero) {
		
		
		
		try {
			objectMapper.writeValue(new File(nombreFichero), objects);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
