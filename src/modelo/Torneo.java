package modelo;

import java.util.ArrayList;
import java.util.Arrays;

public class Torneo {

	private int id;
	private String nombre;
	private ArrayList<Integer> carreras = new ArrayList<Integer>();
	private int numCarreras;
	
	public Torneo() {
		super();
	}

	public Torneo(int id, String nombre, int numCarreras) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.numCarreras= numCarreras;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Integer> getCarreras() {
		return carreras;
	}

	public void setCarreras(ArrayList<Integer> carreras) {
		this.carreras = carreras;
	}

	
	
	
	public int getNumCarreras() {
		return numCarreras;
	}

	public void setNumCarreras(int numCarreras) {
		this.numCarreras = numCarreras;
	}

	@Override
	public String toString() {
		return "Torneo [id=" + id + ", nombre=" + nombre + ", carreras=" + carreras + ", numCarreras=" + numCarreras
				+ "]";
	}

	
	
}
