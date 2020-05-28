package modelo;

import java.util.ArrayList;

public class Garaje {
	
	int id;
	String nombre;
	public Garaje(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	public Garaje() {
		super();
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
	@Override
	public String toString() {
		return "Garaje [id=" + id + ", nombre=" + nombre + "]";
	}


}
