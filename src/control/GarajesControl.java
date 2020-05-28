package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import modelo.Coche;
import modelo.Garaje;

public class GarajesControl {

	ArrayList<Garaje> garajes = new ArrayList<>();

	public GarajesControl() {
		super();
	}

	public ArrayList<Garaje> getGarajes() {
		return garajes;
	}

	public void setGarajess(ArrayList<Garaje> garajes) {
		this.garajes = garajes;
	}

	public int getSiguienteNumero() {
		return ultimoId() + 1;
	}

	public Garaje addGaraje(String nombre) {
		Garaje nuevoGaraje = new Garaje(getSiguienteNumero(), nombre);
		garajes.add(nuevoGaraje);
		return nuevoGaraje;
	}

	public void borrarGaraje(Garaje garaje) {
		garajes.remove(garaje);

	}

	public int buscarGaraje(String buscado) {
		int posicion = -1;
		for (int i = 0; i < garajes.size(); i++) {
			Garaje garaje = garajes.get(i);
			if (garaje.getNombre().contentEquals(buscado)) {
				posicion = garajes.indexOf(garaje);
				return posicion;
			}
		}
		return posicion;
	}

	public Garaje obtenerGaraje(String buscado) {
		Garaje garaje = null;
		for (int i = 0; i < garajes.size(); i++) {
			garaje = garajes.get(i);
			if (garaje.getNombre().contentEquals(buscado)) {
				garaje = garajes.get(i);
				return garaje;
			}
		}
		return garaje;
	}

	public int ultimoId() {
		int max = 0;
		for (int i = 0; i < garajes.size(); i++) {
			if (garajes.get(i).getId() > max)
				max = garajes.get(i).getId();
		}
		return max;
	}

	// ficheros JSON
	public void leerFicheroJSON() {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("garajes.json");
			garajes = objectMapper.readValue(file, new TypeReference<ArrayList<Garaje>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void escribirFicheroJSON() {

		try {
			ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			objectMapper.writeValue(new File("garajes.json"), garajes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
