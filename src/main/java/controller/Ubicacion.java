package controller;

import java.util.ArrayList;
import java.util.List;

public class Ubicacion {

	private Integer id;
	private List<ProductoEnUbicacion> productos = new ArrayList<>();
	static final private Integer PESO_MAXIMO = 1250;
	static private List<Ubicacion> ubicaciones = new ArrayList<>();
	
	public Ubicacion(Integer id) {
		super();
		ubicaciones.add(this);
		this.id = id;
	}
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<ProductoEnUbicacion> getProductos() {
		return List.copyOf(productos);
	}
	public void popProducto(ProductoEnUbicacion productoEnUbic) {
		productos.remove(productoEnUbic);
	}
	public void addProducto(ProductoEnUbicacion productoEnUbic) {
		this.productos.add(productoEnUbic);
	}
	public static Integer getPesoMaximo() {
		return PESO_MAXIMO;
	}
	static public List<Ubicacion> getUbicaciones() {
		return List.copyOf(ubicaciones);
	}
	static public void popUbicacion(Ubicacion ubicacion) {
		ubicaciones.remove(ubicacion);
	}
	static public void addUbicacion(Ubicacion ubicacion) {
		ubicaciones.add(ubicacion);
	}
	
	static public Ubicacion getById(Integer id) {
		for (Ubicacion ubicacion : Ubicacion.getUbicaciones()) {
			if (ubicacion.getId().equals(id)) {
				return ubicacion;
			}
		}
		return null;
	}
	
}
