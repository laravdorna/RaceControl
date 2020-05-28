package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Fichero {

	public static ArrayList<ArrayList<String>> leer(String nombreFichero) {
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		
		BufferedReader csvReader;
		try {
			csvReader = new BufferedReader(new FileReader(nombreFichero));
			String row;
			while ((row = csvReader.readLine()) != null) {
				ArrayList<String> data = new ArrayList<String>(Arrays.asList(row.split(",")));
				rows.add(data);	
			}
			csvReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rows;
	}

	public static void escribir(ArrayList<ArrayList<String>>rows, String nombreFichero) {
		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(nombreFichero);

			for (ArrayList<String> rowData : rows) {
				System.out.println(rowData);
				csvWriter.append(String.join(",", rowData));
				csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
