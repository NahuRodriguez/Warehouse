package controller;

import java.util.ArrayList;
import java.util.List;

public class ProductoEnUbicacion {

	private Integer id;
	private Producto producto;
	private Double cantidad;
	static private List<ProductoEnUbicacion> productosEnUbic = new ArrayList<>();
	
	public ProductoEnUbicacion(Integer id, Producto producto, Double cantidad) {
		this.id = id;
		productosEnUbic.add(this);
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Double getWeight() {
		return producto.getUnidadMedida().getFactorConversion() * producto.getPeso() * cantidad;
	}
	static public List<ProductoEnUbicacion> getProductosEnUbic() {
		return List.copyOf(productosEnUbic);
	}
	static public void popProductoEnUbic(ProductoEnUbicacion productoEnUbic) {
		productosEnUbic.remove(productoEnUbic);
	}
	static public void addProductoEnUbic(ProductoEnUbicacion productoEnUbic) {
		productosEnUbic.add(productoEnUbic);
	}
	
	static public ProductoEnUbicacion getById(Integer id) {
		for (ProductoEnUbicacion productoEnUbic : ProductoEnUbicacion.getProductosEnUbic()) {
			if (productoEnUbic.getId().equals(id)) {
				return productoEnUbic;
			}
		}
		return null;
	}
	
}
