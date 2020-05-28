package vista;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class MenuPrincipal {
	
	static Scanner teclado = main.main.teclado;

	private static HashMap<Integer, HashMap<String, Object>> menuMap = new HashMap<Integer, HashMap<String, Object>>() {
		{
			put(1, new HashMap<String, Object>() {
				{
					put("texto", "Opciones de coche:");
					put("clase", "vista.CocheMenu");
					put("metodo", "selectorCoche");
				}
			});

			put(2, new HashMap<String, Object>() {
				{
					put("texto", "Opciones de garaje:");
					put("clase", "vista.GarajeMenu");
					put("metodo", "selectorGaraje");
				}
			});

			put(3, new HashMap<String, Object>() {
				{
					put("texto", "Opciones de carrera:");
					put("clase", "vista.CarreraMenu");
					put("metodo", "selectorCarrera");
				}
			});

			put(4, new HashMap<String, Object>() {
				{
					put("texto", "Opciones de torneo:");
					put("clase", "vista.TorneoMenu");
					put("metodo", "selectorTorneo");
				}
			});
		}
	};

	public static void selectorPrincipal() {
		int opcion = 0;
		do {
			opcion = menu();

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

	public static int menu() {

		int op = -1;
		
		int max = Collections.max(menuMap.keySet());

		do {
			System.out.println("RACE CONTROL:");
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
}
