package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import modelo.Coche;
import modelo.Garaje;
import main.main;

public class CochesControl {

	private ArrayList<Coche> coches = new ArrayList<>();

	public CochesControl() {
	}

	public ArrayList<Coche> getCoches() {
		return coches;
	}

	public void setCoches(ArrayList<Coche> coches) {
		this.coches = coches;
	}

	public int getSiguienteNumero() {
		return ultimoId() + 1;
	}

	public Coche addCoche(String marca, String modelo, int g) {
		Coche nuevoCoche = new Coche(getSiguienteNumero(), marca, modelo, g);
		coches.add(nuevoCoche);
		return nuevoCoche;
	}

	public void borrarCoche(Coche coche) {
		coches.remove(coche);

	}

	public int buscarCoche(int buscado) {
		int posicion = -1;
		for (int i = 0; i < coches.size(); i++) {
			Coche coche = coches.get(i);
			;
			if (coche.getId() == buscado) {
				posicion = coches.indexOf(coche);
			}
		}
		return posicion;
	}

	public Coche obtenerCoche(int idBuscado) {
		Coche coche = null;
		for (int i = 0; i < coches.size(); i++) {
			coche = coches.get(i);
			if (coche.getId() == idBuscado) {
				coche = coches.get(i);
				return coche;
			}
		}
		return coche;
	}

	public int ultimoId() {
		int max = 0;
		for (int i = 0; i < coches.size(); i++) {
			if (coches.get(i).getId() > max)
				max = coches.get(i).getId();
		}
		return max;
	}

	// ficheros JSON
	public void leerFicheroJSON() {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("coches.json");
			coches = objectMapper.readValue(file, new TypeReference<ArrayList<Coche>>() {
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void escribirFicheroJSON() {

		try {
			ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			objectMapper.writeValue(new File("coches.json"), coches);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
