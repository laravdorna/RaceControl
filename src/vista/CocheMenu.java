package vista;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import main.main;
import modelo.Coche;
import modelo.Garaje;
import tools.Fichero;
import tools.Menu;

public class CocheMenu{
	static Scanner teclado = main.teclado;
	
	private static HashMap<Integer, HashMap<String, Object>> menuMap = new HashMap<Integer, HashMap<String, Object>>() {
		{
			put(1, new HashMap<String, Object>() {
				{
					put("texto", "Listar coche:");
					put("clase", "vista.CocheMenu");
					put("metodo", "listaCoches");
				}
			});

			put(2, new HashMap<String, Object>() {
				{
					put("texto", "Nuevo coche:");
					put("clase", "vista.CocheMenu");
					put("metodo", "nuevoCoche");
				}
			});

			put(3, new HashMap<String, Object>() {
				{
					put("texto", "Eliminar coche:");
					put("clase", "vista.CocheMenu");
					put("metodo", "eliminarCoche");
				}
			});
		}
	};
	private static String nombreMenu = "COCHES";

	public static void selectorCoche() {

		menuCoche();
		int opcion = 0;
		do {
			opcion = menuCoche();

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

	private static int menuCoche() {
		int op = -1;
		int max = Collections.max(menuMap.keySet());

		do {
			System.out.println(nombreMenu);
			menuMap.forEach((k, v) -> System.out.println(k + " : " + v.get("texto")));
			System.out.println("0 : SALIR.");

			try {
				teclado.reset();
				op = Integer.parseInt(teclado.nextLine());
				teclado.reset();
			} catch (NumberFormatException ex) {
				System.out.println("Debes escoger un valor entre 0 y " + max);
				op = -1;
			}

		} while (op < 0 || op > max);
		return op;
	}

	// Metodos

	public static void nuevoCoche() {
		String marca;
		String modelo;
		Garaje garaje = null;
		int idGaraje = 0;
		System.out.println("Introduzca la marca:");
		marca = teclado.nextLine();
		teclado.reset();

		System.out.println("Introduzca el modelo:");
		modelo = teclado.nextLine();
		teclado.reset();
		System.out.println("Desea añadir el coche al garaje:s/n");
		String respuesta = teclado.nextLine();
		if (respuesta.equalsIgnoreCase("s")) {

			garaje = main.garajesC.obtenerGaraje(marca);
			if (garaje == null) {
				garaje = main.garajesC.addGaraje(marca);
				idGaraje = garaje.getId();

			} else {
				idGaraje = garaje.getId();
			}
			System.out.println("Coche añadido al garaje");
		}
		main.cochesC.addCoche(marca, modelo, idGaraje);
	}

	public static void eliminarCoche() {
		int idBuscado;
		System.out.println("Introduzca el id del coche que desea eliminar:");
		idBuscado = teclado.nextInt();
		teclado.reset();
		Coche coche = main.cochesC.obtenerCoche(idBuscado);

		if (coche != null) {
			main.cochesC.borrarCoche(coche);
			System.out.println("Coche eliminado");
		} else {
			System.out.println("El coche no  exite");
		}
	}

	public static void listaCoches() {

		ArrayList<Coche> coches = main.cochesC.getCoches();

		if (coches.isEmpty()) {
			System.out.println("No hay ningun coche añadido");
		} else {
			System.out.println("Listado de coches:");
			for (int i = 0; i < coches.size(); i++) {
				System.out.println(coches.get(i).toString());
			}
		}
	}

}// finalclass
