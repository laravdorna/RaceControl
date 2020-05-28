package main;

import modelo.Coche;
import tools.FicheroJSON;

import java.util.ArrayList;
import java.util.Scanner;

import control.CarrerasControl;
import control.CochesControl;
import control.GarajesControl;
import control.TorneoControl;
import vista.MenuPrincipal;

public class main {

	public static CochesControl cochesC = new CochesControl();
	public static GarajesControl garajesC = new GarajesControl();
	public static CarrerasControl carrerasC = new CarrerasControl();
	public static TorneoControl torneoC = new TorneoControl();
	public static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {


		System.out.println("Bienvenido al RACE CONTROL.");

		System.out.println("Carga de ficheros");
		garajesC.leerFicheroJSON();
		cochesC.leerFicheroJSON();
		carrerasC.leerFicheroJSON();
		torneoC.leerFicheroJSON();

		MenuPrincipal.selectorPrincipal();

		System.out.println("Guardado de  ficheros");
		garajesC.escribirFicheroJSON();
		cochesC.escribirFicheroJSON();
		carrerasC.escribirFicheroJSON();
		torneoC.escribirFicheroJSON();
		System.out.println("FIN");
	}

}
