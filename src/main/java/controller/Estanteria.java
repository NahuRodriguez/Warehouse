package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Estanteria {

	private Integer id;
	private Ubicacion[][] ubicaciones = new Ubicacion[3][3];
	static private List<Estanteria> estanterias = new ArrayList<>();
	
	public Estanteria(Integer id) {
		super();
		estanterias.add(this);
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Ubicacion[][] getUbicaciones() {
		return Arrays.copyOf(ubicaciones, ubicaciones.length);
	}
	public Ubicacion getUbicacion(int posicion) throws IndexOutOfBoundsException {
		int y = (posicion - 1) / 3;
		int x = (posicion - 1) % 3;
		try {
			return ubicaciones[y][x];
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			throw new IndexOutOfBoundsException("Problema de acceso a la matriz de ubicaciones por sus índices");
		}
	}
	public void addUbicacion(Ubicacion ubicacion, int posicion) {
		int y = (posicion - 1) / 3;
		int x = (posicion - 1) % 3;
		try {
			ubicaciones[y][x] = ubicacion;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	static public List<Estanteria> getEstanterias() {
		return List.copyOf(estanterias);
	}
	static public void popEstanteria(Estanteria estanteria) {
		estanterias.remove(estanteria);
	}
	static public void addEstanteria(Estanteria estanteria) {
		estanterias.add(estanteria);
	}
	
	public int[] getPosicion(Ubicacion ubicacion) {
		int[] posicion = {3, 3};
		for (int y = 0; y<3; y++) {
			for (int x = 0; x<3; x++) {
				if (this.ubicaciones[y][x].equals(ubicacion)) {
					posicion[0] = y;
					posicion[1] = x;
					return posicion;
				}
			}
		}
		return posicion;
	}
	
	static public Estanteria getById(Integer id) {
		for (Estanteria estanteria : Estanteria.getEstanterias()) {
			if (estanteria.getId().equals(id)) {
				return estanteria;
			}
		}
		return null;
	}
	
}
