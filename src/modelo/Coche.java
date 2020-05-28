package modelo;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Coche {

	
	private int id;
	private String marca;
	private String modelo;
	private final int vmax = 200;
	private int idGaraje;

	
	public Coche() {
	}

	
	public Coche(int id, String marca, String modelo, int garaje) {
		super();
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.idGaraje = garaje;
	}


	public Coche(int n, String marca, String modelo) {
		this.id = n;
		this.marca = marca;
		this.modelo = modelo;

	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public int getIdGaraje() {
		return idGaraje;
	}


	public void setIdGaraje(int idGaraje) {
		this.idGaraje = idGaraje;
	}


	@Override
	public String toString() {
		return "Coche [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", vmax=" + vmax + ", idGaraje="
				+ idGaraje + "]";
	}


	

	
}
