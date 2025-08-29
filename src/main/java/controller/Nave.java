package controller;

import java.util.ArrayList;
import java.util.List;

public class Nave {
	
	private Integer id;
	private String nombre;
	private List<Zona> zonas = new ArrayList<>();
	static private List<Nave> naves = new ArrayList<>();
	
	public Nave(Integer id, String nombre) {
		naves.add(this);
		this.id = id;
		this.nombre = nombre;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public List<Zona> getZonas() {
		return List.copyOf(zonas);
	}
	public void popZona(Zona zona) {
		zonas.remove(zona);
	}
	public void addZona(Zona zona) {
		this.zonas.add(zona);
	}
	static public List<Nave> getNaves() {
		return List.copyOf(naves);
	}
	static public void popNave(Nave nave) {
		naves.remove(nave);
	}
	static public void addNave(Nave nave) {
		naves.add(nave);
	}
	
	static public Nave getById(Integer id) {
		for (Nave nave : Nave.getNaves()) {
			if (nave.getId().equals(id)) {
				return nave;
			}
		}
		return null;
	}
	
}
