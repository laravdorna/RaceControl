package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import modelo.Carrera;
import modelo.Coche;
import modelo.Garaje;
import modelo.TipoCarrera;
import main.main;

public class CarrerasControl {

	private ArrayList<Carrera> carreras = new ArrayList<>();

	public CarrerasControl() {
		super();
	}

	public ArrayList<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(ArrayList<Carrera> carreras) {
		this.carreras = carreras;
	}

	public int getSiguienteNumero() {
		return ultimoId() + 1;
	}

	public Carrera addCarrera(String nombrePremio, TipoCarrera tipoCarrera) {
		Carrera nuevaCarrera = new Carrera(getSiguienteNumero(), nombrePremio, tipoCarrera);
		carreras.add(nuevaCarrera);
		return nuevaCarrera;
	}

	public void borrarCarrera(Carrera carrera) {
		carreras.remove(carrera);
	}

	
	public int buscarCarrera(int buscada) {
		int posicion = -1;
		for (int i = 0; i < carreras.size(); i++) {
			Carrera carrera = carreras.get(i);
			if (carrera.getId() == buscada)
				posicion = carreras.indexOf(carrera);
			return posicion;
		}

		return posicion;
	}

	public Carrera obtenerCarrera(int idBuscado) {
		Carrera carrera = null;
		for (int i = 0; i < carreras.size(); i++) {
			carrera = carreras.get(i);
			if (carrera.getId() == idBuscado) {
				carrera = carreras.get(i);
				return carrera;
			}
		}
		return carrera;
	}

	public void inscribirParticipantesGaraje(int idGaraje, Carrera carrera) {
		Coche coche;
		int tamañoCoches = main.cochesC.getCoches().size();

		for (int j = 0; j < tamañoCoches; j++) {
			coche = main.cochesC.getCoches().get(j);
			int idGarajeCoche = coche.getIdGaraje();
			if (idGaraje == idGarajeCoche) {
				int idCoche = coche.getId();
				carrera.getCochesParticipando().add(coche.getId());
			}
		}

	}

	public void inscribirParticipantesMixto(Carrera carrera) {

		int tamañoGarajes = main.garajesC.getGarajes().size();
		int tamañoCoches = main.cochesC.getCoches().size();

		for (int i = 0; i < tamañoGarajes; i++) {
			Garaje garaje = main.garajesC.getGarajes().get(i);
			int idGaraje = garaje.getId();
			ArrayList<Coche> cochesGaraje = new ArrayList<>();

			for (int j = 0; j < tamañoCoches; j++) {
				Coche coche = main.cochesC.getCoches().get(j);
				int idGarajeCoche = coche.getIdGaraje();
				if (idGaraje == idGarajeCoche) {
					cochesGaraje.add(coche);
				}
			}

			if (cochesGaraje.size() > 0) {
				int aleatorio = new Random().nextInt(cochesGaraje.size() - 1);
				Coche cocheAleatorio = cochesGaraje.get(aleatorio);
				carrera.getCochesParticipando().add(cocheAleatorio.getId());
			}
		}
	}

	public int ultimoId() {
		int max = 0;
		for (int i = 0; i < carreras.size(); i++) {
			if (carreras.get(i).getId() > max)
				max = carreras.get(i).getId();
		}
		return max;
	}

	public void simularCarreraEstandar(Carrera carrera) {
		int turnos = 180;// min 3 horas
		ArrayList<Integer> distancias = new ArrayList<Integer>();
		ArrayList<Integer> velocidades = new ArrayList<Integer>();

		for (int i = 0; i < carrera.getCochesParticipando().size(); i++) {// prepara participantes para la carrera
			distancias.add(0);
			velocidades.add(0);
		}

		for (int i = 0; i < turnos; i++) {// la carrera
			for (int j = 0; j < carrera.getCochesParticipando().size(); j++) {// participantes
				int velocidadActual = velocidades.get(j);
				int distanciaActual = distancias.get(j);
				distancias.set(j, distanciaActual + velocidadActual);

				int aleatorio = new Random().nextInt(3);

				if (aleatorio == 2 && velocidadActual < 200) {// acelera
					velocidadActual += 10;
				} else if (aleatorio == 0 && velocidadActual > 0) {// decelera
					velocidadActual -= 10;
				}
				velocidades.set(j, velocidadActual);
			}
		}

		calcularPodio(carrera, distancias);

	}

	public void simularCarreraEliminacion(Carrera carrera) {
		int turnos = carrera.getCochesParticipando().size() - 1;
		ArrayList<Integer> distancias = new ArrayList<Integer>();
		ArrayList<Integer> velocidades = new ArrayList<Integer>();
		ArrayList<Integer> eliminados = new ArrayList<Integer>();

		for (int i = 0; i < carrera.getCochesParticipando().size(); i++) {// prepara participantes para la carrera
			distancias.add(0);
			velocidades.add(0);
		}

		for (int i = 0; i < turnos; i++) {// la carrera
			for (int j = 0; j < carrera.getCochesParticipando().size(); j++) {// participantes
				if (!eliminados.contains(j)) {

					int velocidadActual = velocidades.get(j);
					int distanciaActual = distancias.get(j);
					distancias.set(j, distanciaActual + velocidadActual);

					int aleatorio = new Random().nextInt(3);

					if (aleatorio == 2 && velocidadActual < 200) {// acelera
						velocidadActual += 10;
					} else if (aleatorio == 0 && velocidadActual > 0) {// decelera
						velocidadActual -= 10;
					}
					velocidades.set(j, velocidadActual);
				}
			}

			if (carrera.getCochesParticipando().size() - 3 > eliminados.size()) {
				int posUltimo = Collections.min(distancias);
				eliminados.add(posUltimo);
			}

		}

		calcularPodio(carrera, distancias);

	}

	private void calcularPodio(Carrera carrera, ArrayList<Integer> distancias) {
		int posPrimero = 0;
		int posSegundo = 0;
		int posTercero = 0;

		int distanciaPrimero = 0;
		int distanciaSegundo = 0;
		int distanciaTercero = 0;

		for (int i = 0; i < distancias.size(); i++) {

			if (distancias.get(i) > distanciaTercero) {

				if (distancias.get(i) > distanciaSegundo) {

					if (distancias.get(i) > distanciaPrimero) {

						distanciaSegundo = distanciaPrimero;
						posSegundo = posPrimero;

						distanciaPrimero = distancias.get(i);
						posPrimero = i;

						continue;
					}

					distanciaTercero = distanciaSegundo;
					posTercero = posSegundo;

					distanciaSegundo = distancias.get(i);
					posSegundo = i;

					continue;
				}

				distanciaTercero = distancias.get(i);
				posTercero = i;

			}

		}

		int idCochePrimero = carrera.getCochesParticipando().get(posPrimero);
		int idCocheSegundo = carrera.getCochesParticipando().get(posSegundo);
		int idCocheTercero = carrera.getCochesParticipando().get(posTercero);

		carrera.setPodio(new ArrayList<Integer>());
		carrera.getPodio().add(idCochePrimero);
		carrera.getPodio().add(idCocheSegundo);
		carrera.getPodio().add(idCocheTercero);
	}

	// ficheros JSON
	public void leerFicheroJSON() {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("carreras.json");
			carreras = objectMapper.readValue(file, new TypeReference<ArrayList<Carrera>>() {
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void escribirFicheroJSON() {

		try {
			ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			objectMapper.writeValue(new File("carreras.json"), carreras);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
