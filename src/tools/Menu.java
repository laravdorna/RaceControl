package tools;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
	static Scanner teclado = new Scanner(System.in);
	
	private static HashMap<Integer, HashMap<String, Object>> menuMap = new HashMap<Integer, HashMap<String, Object>>();
	private static String nombreMenu = "";
	
	public static void selector() {

		menu();
		int opcion = 0;
		do {
			opcion = menu();

			Class<?> MenuClase;
			try {
				String className = (String) menuMap.get(opcion).get("clase");
				MenuClase = Class.forName(className);
				String methodName = (String) menuMap.get(opcion).get("metodo");
				Method method = MenuClase.getMethod(methodName);
				System.out.println(method.toString());
				method.invoke(null);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (opcion != 0);
	}

	private static int menu() {
		int op = -1;
		int max = Collections.max(menuMap.keySet());

		do {
			System.out.println(nombreMenu);
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
