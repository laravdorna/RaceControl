package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import main.main;
import modelo.Carrera;
import modelo.Coche;
import modelo.TipoCarrera;
import modelo.Torneo;

public class TorneoControl {

	private ArrayList<Torneo> torneos = new ArrayList<>();

	public TorneoControl() {
		super();
	}

	public ArrayList<Torneo> getTorneos() {
		return torneos;
	}

	public void setTorneos(ArrayList<Torneo> torneos) {
		this.torneos = torneos;
	}

	public int getSiguienteNumero() {
		return ultimoId() + 1;
	}

	public int ultimoId() {
		int max = 0;
		for (int i = 0; i < torneos.size(); i++) {
			if (torneos.get(i).getId() > max)
				max = torneos.get(i).getId();
		}
		return max;
	}

	public Torneo addTorneo(String nombreTorneo, int numCarreras) {
		Torneo nuevoTorneo = new Torneo(getSiguienteNumero(), nombreTorneo, numCarreras);
		torneos.add(nuevoTorneo);
		return nuevoTorneo;
	}

	public void borrarTorneo(Torneo torneo) {
		torneos.remove(torneo);
	}

	public int buscarTorneo(int buscada) {
		int posicion = -1;
		for (int i = 0; i < torneos.size(); i++) {
			Torneo torneo = torneos.get(i);
			if (torneo.getId() == buscada)
				posicion = torneos.indexOf(torneo);
			return posicion;
		}

		return posicion;
	}

	public Torneo obtenerTorneo(int idBuscado) {
		Torneo torneo = null;
		for (int i = 0; i < torneos.size(); i++) {
			torneo = torneos.get(i);
			if (torneo.getId() == idBuscado) {
				torneo = torneos.get(i);
				return torneo;
			}
		}
		return torneo;
	}

	public void inscribirCarrerasMixta(Torneo torneo) {
		ArrayList<Integer> participantes = null;

		for (int i = 0; i < torneo.getNumCarreras(); i++) {

			int aleatorio = new Random().nextInt(2);
			TipoCarrera tipoCarrera;
			if (aleatorio == 0) {
				tipoCarrera = TipoCarrera.ESTANDAR;
			} else {
				tipoCarrera = TipoCarrera.ELIMINACION;
			}
			Carrera carrera = main.carrerasC.addCarrera(torneo.getNombre() + "_" + i, tipoCarrera);

			if (participantes == null) {
				main.carrerasC.inscribirParticipantesMixto(carrera);
				participantes = carrera.getCochesParticipando();
			} else {
				carrera.setCochesParticipando(participantes);
			}
			torneo.getCarreras().add(carrera.getId());
		}
	}

	public void inscribirCarrerasGaraje(Torneo torneo, int idGaraje) {
		ArrayList<Integer> participantes = null;

		for (int i = 0; i < torneo.getNumCarreras(); i++) {
			int aleatorio = new Random().nextInt(2);
			TipoCarrera tipoCarrera;
			if (aleatorio == 0) {
				tipoCarrera = TipoCarrera.ESTANDAR;
			} else {
				tipoCarrera = TipoCarrera.ELIMINACION;
			}
			Carrera carrera = main.carrerasC.addCarrera(torneo.getNombre() + "_" + i, tipoCarrera);

			if (participantes == null) {
				main.carrerasC.inscribirParticipantesGaraje(idGaraje, carrera);
				participantes = carrera.getCochesParticipando();
			} else {
				carrera.setCochesParticipando(participantes);
			}
			torneo.getCarreras().add(carrera.getId());
		}
	}

	public Map<Integer, Integer> simularTorneo(Torneo torneo) {

		Map<Integer, Integer> resultadosTorneo = new HashMap<Integer, Integer>();
		Carrera primeraCarrera = main.carrerasC.obtenerCarrera(torneo.getCarreras().get(0));

		for (int i = 0; i < primeraCarrera.getCochesParticipando().size(); i++) {
			resultadosTorneo.put(primeraCarrera.getCochesParticipando().get(i), 0);
		}

		for (int i = 0; i < torneo.getCarreras().size(); i++) {

			Carrera carrera = main.carrerasC.obtenerCarrera(torneo.getCarreras().get(i));
			TipoCarrera tipoCarrera = carrera.getTipoCarrera();

			if (tipoCarrera == TipoCarrera.ELIMINACION) {
				main.carrerasC.simularCarreraEliminacion(carrera);
			} else {
				main.carrerasC.simularCarreraEstandar(carrera);
			}
			int puntos = 15;
			for (int j = 0; j < carrera.getPodio().size(); j++) {

				int puntosActuales = resultadosTorneo.get(carrera.getPodio().get(j));
				resultadosTorneo.put(carrera.getPodio().get(j), puntosActuales + puntos);
				puntos -= 5;
			}
		}
		Map<Integer, Integer> podioTorneo = resultadosTorneo.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return podioTorneo;
	}

	// ficheros JSON
	public void leerFicheroJSON() {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("torneos.json");
			torneos = objectMapper.readValue(file, new TypeReference<ArrayList<Torneo>>() {
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void escribirFicheroJSON() {

		try {
			ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			objectMapper.writeValue(new File("torneos.json"), torneos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
