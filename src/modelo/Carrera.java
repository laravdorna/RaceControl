package modelo;

import java.util.ArrayList;
import modelo.TipoCarrera;

public class Carrera {

	private int id;
	private String nombreCarrera;
	private ArrayList<Integer> cochesParticipando = new ArrayList<Integer>();
	private ArrayList<Integer> podio = new ArrayList<Integer>();
	private TipoCarrera tipoCarrera;

	public Carrera() {

	}

	public Carrera(int id, String nombreCarrera, TipoCarrera tipoCarrera) {
		super();
		this.id = id;
		this.nombreCarrera = nombreCarrera;
		this.tipoCarrera = tipoCarrera;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	public ArrayList<Integer> getCochesParticipando() {
		return cochesParticipando;
	}

	public void setCochesParticipando(ArrayList<Integer> cochesParticipando) {
		this.cochesParticipando = cochesParticipando;
	}

	public ArrayList<Integer> getPodio() {
		return podio;
	}

	public void setPodio(ArrayList<Integer> podio) {
		this.podio = podio;
	}

	public TipoCarrera getTipoCarrera() {
		return tipoCarrera;
	}

	public void setTipoCarrera(TipoCarrera tipoCarrera) {
		this.tipoCarrera = tipoCarrera;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Carrera [id=" + id + ", nombreCarrera=" + nombreCarrera + ", cochesParticipando=" + cochesParticipando
				+ ", podio=" + podio + ", tipoCarrera=" + tipoCarrera + "]";
	}

} //