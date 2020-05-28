package vista;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import main.main;
import modelo.Carrera;
import modelo.Coche;
import modelo.Garaje;
import modelo.Torneo;

public class TorneoMenu {

	static Scanner teclado = new Scanner(System.in);
	
	private static HashMap<Integer, HashMap<String, Object>> menuMap = new HashMap<Integer, HashMap<String, Object>>() {
		{
			put(1, new HashMap<String, Object>() {
				{
					put("texto", "Listar torneo:");
					put("clase", "vista.TorneoMenu");
					put("metodo", "listaTorneo");
				}
			});

			put(2, new HashMap<String, Object>() {
				{
					put("texto", "Nuevo torneo:");
					put("clase", "vista.TorneoMenu");
					put("metodo", "nuevoTorneo");
				}
			});

			put(3, new HashMap<String, Object>() {
				{
					put("texto", "Eliminar torneo:");
					put("clase", "vista.TorneoMenu");
					put("metodo", "eliminarTorneo");
				}
			});

			put(4, new HashMap<String, Object>() {
				{
					put("texto", "Simular torneo:");
					put("clase", "vista.TorneoMenu");
					put("metodo", "simularTorneo");
				}
			});
		}
	};

	public static void selectorTorneo() {

		menuTorneo();
		int opcion = 0;
		do {
			opcion = menuTorneo();

			Class<?> MenuClase;
			try {
				if (menuMap.get(opcion)!= null) {
					String className = (String) menuMap.get(opcion).get("clase");
					MenuClase = Class.forName(className);
					Method[] metodos = MenuClase.getMethods();
					String methodName = (String) menuMap.get(opcion).get("metodo");
					Method method = MenuClase.getMethod(methodName);
					method.invoke(null);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (opcion != 0);
	}

	public static int menuTorneo() {
		int op = -1;
		int max = Collections.max(menuMap.keySet());

		do {
			System.out.println("TORNEOS:");
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


	public static void nuevoTorneo() {
		// TODO Auto-generated method stub

		System.out.println("Introduzca el nombre del torneo:");
		String nombre = teclado.nextLine();
		teclado.reset();

		System.out.println("Introduzca el numero de carreras: (< 10)");
		int numCarreras = teclado.nextInt();
		if (numCarreras > 10)
			numCarreras = 10;
		teclado.reset();

		Torneo torneo = main.torneoC.addTorneo(nombre, numCarreras);

		System.out.println("El torneo es de un garaje o mixto (Introduce g/m)");
		String respuesta = teclado.nextLine();
		respuesta = teclado.nextLine();
		teclado.reset();
		
		if (respuesta.equalsIgnoreCase("g")) {
			inscribirCarreras("GARAJE", torneo);
		} else {
			inscribirCarreras("MIXTA", torneo);
		}

	}

	public static void inscribirCarreras(String eleccion, Torneo torneo) {

		switch (eleccion) {
		case "MIXTA":
			main.torneoC.inscribirCarrerasMixta(torneo);
			break;
		case "GARAJE":
			System.out.println("Introduzca el nombre del garaje:");
			String nombreGaraje = teclado.nextLine();
			Garaje garaje = main.garajesC.obtenerGaraje(nombreGaraje);

			if (garaje != null) {
				int idGaraje = garaje.getId();
				main.torneoC.inscribirCarrerasGaraje(torneo, idGaraje);
			} else {
				System.out.println("El garaje no existe");

				// crear bucle
			}

			break;
		default:
			break;
		}
	}

	public static void simularTorneo() {

		int idBuscado;
		System.out.println("Introduzca el id del torneo que desea simular:");
		idBuscado = teclado.nextInt();

		Torneo torneo = main.torneoC.obtenerTorneo(idBuscado);

		if (torneo != null) {
			Map<Integer, Integer> clasificacion = main.torneoC.simularTorneo(torneo);
			System.out.println("CLASIFICACIÓN:/n" + clasificacion.toString());

		} else {
			System.out.println("El torneo no existe");
		}

	}

	public static void listaTorneo() {
		ArrayList<Torneo> torneos = main.torneoC.getTorneos();

		if (torneos.isEmpty()) {
			System.out.println("No hay ningun torneo añadido");
		} else {
			System.out.println("Listado de torneos:");
			for (int i = 0; i < torneos.size(); i++) {
				System.out.println(torneos.get(i).toString());
			}
		}

	}

	public static void eliminarTorneo() {

		System.out.println("Introduzca el id del torneo que desea eliminar:");
		int idBuscado = teclado.nextInt();
		teclado.reset();

		Torneo torneo = main.torneoC.obtenerTorneo(idBuscado);

		if (torneo != null) {
			main.torneoC.borrarTorneo(torneo);

		} else {
			System.out.println("El torneo no existe");
		}
	}

}
