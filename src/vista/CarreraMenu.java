package vista;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import control.CarrerasControl;
import control.GarajesControl;
import main.main;
import modelo.Carrera;
import modelo.Coche;
import modelo.Garaje;
import modelo.TipoCarrera;

public class CarreraMenu {

	static Scanner teclado = new Scanner(System.in);

	private static HashMap<Integer, HashMap<String, Object>> menuMap = new HashMap<Integer, HashMap<String, Object>>() {
		{
			put(1, new HashMap<String, Object>() {
				{
					put("texto", "Listar carrera:");
					put("clase", "vista.CarreraMenu");
					put("metodo", "listaCarrera");
				}
			});

			put(2, new HashMap<String, Object>() {
				{
					put("texto", "Nuevo carrera:");
					put("clase", "vista.CarreraMenu");
					put("metodo", "nuevaCarrera");
				}
			});

			put(3, new HashMap<String, Object>() {
				{
					put("texto", "Eliminar carrera:");
					put("clase", "vista.CarreraMenu");
					put("metodo", "eliminarCarrera");
				}
			});

			put(4, new HashMap<String, Object>() {
				{
					put("texto", "Simular carrera:");
					put("clase", "vista.CarreraMenu");
					put("metodo", "simularCarrera");
				}
			});
		}
	};

	public static void selectorCarrera() {

		menuCarrera();
		int opcion = 0;
		do {
			opcion = menuCarrera();

			Class<?> MenuClase;
			try {
				if (menuMap.get(opcion)!= null) {
					String className = (String) menuMap.get(opcion).get("clase");
					MenuClase = Class.forName(className);
					String methodName = (String) menuMap.get(opcion).get("metodo");
					Method method = MenuClase.getMethod(methodName);
					method.invoke(null);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (opcion != 0);
	}

	private static int menuCarrera() {
		int op = -1;
		int max = Collections.max(menuMap.keySet());

		do {
			System.out.println("CARRERAS:");
			menuMap.forEach((k, v) -> System.out.println(k + " : " + v.get("texto")));
			System.out.println("0 : SALIR.");

			try {
				op = Integer.parseInt(teclado.nextLine());
				teclado.reset();
			} catch (NumberFormatException ex) {
				System.out.println("Debes escoger un valor entre 0 y " + max);
				op = -1;
			}

		} while (op < 0 || op > max);
		return op;
	}

	public static void nuevaCarrera() {
		String nombre;
		TipoCarrera tipoCarrera = null;
		String respuesta;
		Carrera carrera = null;
		System.out.println("Introduzca el nombre de la carrera:");
		nombre = teclado.nextLine();
		teclado.reset();

		System.out.println("La carrera es estandar o de eliminacion (Introduce ESTANDAR/ELIMINACION):");
		respuesta = teclado.nextLine().toUpperCase();
		teclado.reset();

		if (respuesta.equalsIgnoreCase("ESTANDAR")) {
			tipoCarrera = tipoCarrera.ESTANDAR;
		} else {
			tipoCarrera = tipoCarrera.ELIMINACION;
		}

		carrera = main.carrerasC.addCarrera(nombre, tipoCarrera);

		System.out.println("La carrera es de un garaje o mixto (Introduce g/m)");
		respuesta = teclado.nextLine();
		if (respuesta.equalsIgnoreCase("g")) {
			inscribirParticipantes("GARAJE", carrera);
		} else {
			inscribirParticipantes("MIXTA", carrera);
		}

		System.out.println("Carrera creada");
	}

	public static void inscribirParticipantes(String eleccion, Carrera carrera) {

		switch (eleccion) {
		case "MIXTA":
			main.carrerasC.inscribirParticipantesMixto(carrera);

			break;
		case "GARAJE":
			System.out.println("Introduzca el nombre del garaje:");
			String nombreGaraje = teclado.nextLine();
			Garaje garaje = main.garajesC.obtenerGaraje(nombreGaraje);

			if (garaje != null) {
				int idGaraje = garaje.getId();
				main.carrerasC.inscribirParticipantesGaraje(idGaraje, carrera);
			} else {
				System.out.println("El garaje no existe");

				// crear bucle
			}

			break;
		default:
			break;
		}
	}

	public static void eliminarCarrera() {

		System.out.println("Introduzca el id de la carrera que desea eliminar:");
		int idBuscado = teclado.nextInt();
		teclado.reset();
		Carrera carrera = main.carrerasC.obtenerCarrera(idBuscado);

		if (carrera != null) {
			main.carrerasC.borrarCarrera(carrera);
			System.out.println("Carrera eliminada");
		} else {
			System.out.println("La carrera no  exite");
		}
	}

	public static void listaCarrera() {

		ArrayList<Carrera> carreras = main.carrerasC.getCarreras();

		if (carreras.isEmpty()) {
			System.out.println("No hay ninguna carrera añadido");
		} else {
			System.out.println("Listado de carreras:");
			for (int i = 0; i < carreras.size(); i++) {
				System.out.println(carreras.get(i).toString());
			}
		}
	}

	public static void simularCarrera() {
		int idBuscado;
		System.out.println("Introduzca el id de la carrera que desea simular:");
		idBuscado = teclado.nextInt();

		Carrera carrera = main.carrerasC.obtenerCarrera(idBuscado);
		if (carrera != null) {
			System.out.println("PARTICIPANTES:\n" + carrera.getCochesParticipando());

			TipoCarrera tipoCarrera = carrera.getTipoCarrera();
			if (tipoCarrera == TipoCarrera.ESTANDAR) {
				main.carrerasC.simularCarreraEstandar(carrera);
			} else {
				main.carrerasC.simularCarreraEliminacion(carrera);

			}
			System.out.println("PODIO:" + "\n PRIMERO:" + carrera.getPodio().get(0) + "\n SEGUNDO:"
					+ carrera.getPodio().get(1) + "\n TERCERO:" + carrera.getPodio().get(2));

			carrera.getPodio();
		} else {
			System.out.println("La carrera no existe");
		}

	}

}//
