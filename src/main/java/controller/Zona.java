package controller;

import java.util.ArrayList;
import java.util.List;

public class Zona {

	private Integer id;
	private List<Estanteria> estanterias = new ArrayList<>();
	private TipoZona tipo;
	static private List<Zona> zonas = new ArrayList<>();
	
	public Zona(Integer id, TipoZona tipo) {
		super();
		zonas.add(this);
		this.id = id;
		this.tipo = tipo;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Estanteria> getEstanterias() {
		return List.copyOf(estanterias);
	}
	public void popEstanteria(Estanteria estanteria) {
		estanterias.remove(estanteria);
	}
	public void addEstanteria(Estanteria estanteria) {
		this.estanterias.add(estanteria);
	}
	public TipoZona getTipo() {
		return tipo;
	}
	public void setTipo(TipoZona tipo) {
		this.tipo = tipo;
	}
	static public List<Zona> getZonas() {
		return List.copyOf(zonas);
	}
	static public void popZona(Zona zona) {
		zonas.remove(zona);
	}
	static public void addZona(Zona zona) {
		zonas.add(zona);
	}
	
	static public Zona getById(Integer id) {
		for (Zona zona : Zona.getZonas()) {
			if (zona.getId().equals(id)) {
				return zona;
			}
		}
		return null;
	}
	
}
