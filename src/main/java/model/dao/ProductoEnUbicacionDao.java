package model.dao;

import java.io.IOException;
import java.util.List;

import controller.Producto;
import controller.ProductoEnUbicacion;
import controller.Ubicacion;

public class ProductoEnUbicacionDao {

	static private List<String[]> csvContents;
	static final private String nombreArchivo = "ProductoEnUbicacion.csv";
	
	static private void refreshContents() throws IOException {
		try {
			csvContents = CsvUtils.leerArchivo(nombreArchivo);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Error al acceder al archivo de guardado");
		}
	}
	
	public static List<String[]> getCsvContents() throws IOException {
		refreshContents();
		return csvContents;
	}

	static public Boolean eliminar(Integer productoEnUbicId) throws IOException {
		refreshContents();
		List<String[]> newContent = CsvUtils.eliminarTupla(productoEnUbicId, csvContents);
		Boolean rewriteSuccessFlag = CsvUtils.reescribir(nombreArchivo, newContent);
		Boolean successFlag = false;
		if (rewriteSuccessFlag) {
			for (ProductoEnUbicacion productoEnUbic : ProductoEnUbicacion.getProductosEnUbic()) {
				if (productoEnUbic.getId() == productoEnUbicId) {
					ProductoEnUbicacion.popProductoEnUbic(productoEnUbic);;
					successFlag = true;
				}
			}
		}
		return successFlag;
	}
	
	static public ProductoEnUbicacion ensamblar(String id) throws IllegalArgumentException, IOException {
		refreshContents();
		
		String[] items = CsvUtils.obtenerTupla(id, csvContents);
		
		Integer idProductoEnUbic = Integer.valueOf(id);
		
		Producto producto = Producto.getById(Integer.valueOf(items[1]));
		if (producto == null) {
			producto = ProductoDao.ensamblar(items[1]);
		}
		
		Double cantidad = Double.valueOf(items[3]);
		
		ProductoEnUbicacion productoEnUbic = new ProductoEnUbicacion(idProductoEnUbic, producto, cantidad);
	
		Integer idUbicacion = Integer.valueOf(items[2]);
		Ubicacion ubicacion = Ubicacion.getById(idUbicacion);
		if (ubicacion == null) {
			ubicacion = UbicacionDao.ensamblar(items[2]);
		}
		ubicacion.addProducto(productoEnUbic);
		
		return productoEnUbic;
	}
	
	static public Boolean almacenar(ProductoEnUbicacion productoEnUbic) throws IOException {
		refreshContents();
		String id = String.valueOf(productoEnUbic.getId());
		String idProducto = String.valueOf(productoEnUbic.getProducto().getId());
		String idUbicacion = "";
		for (Ubicacion ubicacion : Ubicacion.getUbicaciones()) {
			if (ubicacion.getProductos().contains(productoEnUbic)) {
				idUbicacion = String.valueOf(ubicacion.getId());
			}
		}
		if (idUbicacion == "") {
			throw new IOException("No se encontró el objeto al que este hace referencia");
		}
		String cantidad = String.valueOf(productoEnUbic.getCantidad());
		String[] atributos = new String[4];
		atributos[0] = id;
		atributos[1] = idProducto;
		atributos[2] = idUbicacion;
		atributos[3] = cantidad;
		csvContents.add(atributos);
		Boolean successFlag = CsvUtils.reescribir(nombreArchivo, csvContents);
		return successFlag;
	}
	
}
