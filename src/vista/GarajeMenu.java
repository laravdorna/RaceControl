package vista;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import main.main;
import modelo.Garaje;

public class GarajeMenu {
	static Scanner teclado = new Scanner(System.in);
	
	private static HashMap<Integer, HashMap<String, Object>> menuMap = new HashMap<Integer, HashMap<String, Object>>() {
		{
			put(1, new HashMap<String, Object>() {
				{
					put("texto", "Listar garaje:");
					put("clase", "vista.GarajeMenu");
					put("metodo", "listaGaraje");
				}
			});

			put(2, new HashMap<String, Object>() {
				{
					put("texto", "Nuevo garaje:");
					put("clase", "vista.GarajeMenu");
					put("metodo", "nuevoGaraje");
				}
			});

			put(3, new HashMap<String, Object>() {
				{
					put("texto", "Eliminar garaje:");
					put("clase", "vista.GarajeMenu");
					put("metodo", "eliminarGaraje");
				}
			});
		}
	};
	
	public static void selectorGaraje() {

		menuGaraje();
		int opcion = 0;
		do {
			opcion = menuGaraje();

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

	private static int menuGaraje() {
		int op = -1;
		int max = Collections.max(menuMap.keySet());

		do {
			System.out.println("GARAJES:");
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

	// Metodos

	public static void nuevoGaraje() {
		String escuderia;
		System.out.println("nombre de la escuderia:");
		escuderia = teclado.nextLine();
		teclado.reset();

		// comprobar q no existe
		if (main.garajesC.buscarGaraje(escuderia) == -1) {
			main.garajesC.addGaraje(escuderia);
		} else {
			System.out.println("El garaje ya existe");
		}
	}

	public static void eliminarGaraje() {
		String escuderia;
		System.out.println("escuderia del garaje  que desea eliminar:");
		escuderia = teclado.nextLine();
		teclado.reset();
		
		Garaje garaje = main.garajesC.obtenerGaraje(escuderia);
		
		if (garaje != null) {
			main.garajesC.borrarGaraje(garaje);
			System.out.println("El garaje ha sido eliminado.");
			//si elimino un garaje sus coches deberían ponerse null??
		} else {
			System.out.println("El garaje no existe");
		}
	}

	public static void listaGaraje() {

		ArrayList<Garaje> garajes = main.garajesC.getGarajes();

		if (garajes.isEmpty()) {
			System.out.println("No hay ningun garaje añadido");
		} else {
			System.out.println("Listado de Garajes: ");
			for (int i = 0; i < garajes.size(); i++) {
				System.out.println(garajes.get(i).getNombre());
			}
		}
	}

}// finalclass
