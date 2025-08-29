package controller;

import java.util.ArrayList;
import java.util.List;

public class Producto {

	private Integer id;
	private Integer codigo;
	private String descripcion;
	private Double peso;
	private UnidadMedida unidadMedida;
	static private List<Producto> productos = new ArrayList<>();
	
	public Producto(Integer id, Integer codigo, String descripcion, Double peso, UnidadMedida unidadMedida) {
		super();
		productos.add(this);
		this.id = id;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.peso = peso;
		this.unidadMedida = unidadMedida;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	static public List<Producto> getProductos() {
		return List.copyOf(productos);
	}
	static public void popProducto(Producto producto) {
		productos.remove(producto);
	}
	static public void addProducto(Producto producto) {
		productos.add(producto);
	}
	
	static public Producto getById(Integer id) {
		for (Producto producto : Producto.getProductos()) {
			if (producto.getId().equals(id)) {
				return producto;
			}
		}
		return null;
	}
	
}
