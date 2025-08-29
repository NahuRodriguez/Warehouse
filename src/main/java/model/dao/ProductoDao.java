package model.dao;

import java.io.IOException;
import java.util.List;

import controller.Producto;
import controller.UnidadMedida;

public class ProductoDao {

	static private List<String[]> csvContents;
	static final private String nombreArchivo = "Producto.csv";
	
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

	static public Boolean eliminar(Integer productoId) throws IOException {
		refreshContents();
		List<String[]> newContent = CsvUtils.eliminarTupla(productoId, csvContents);
		Boolean rewriteSuccessFlag = CsvUtils.reescribir(nombreArchivo, newContent);
		Boolean successFlag = false;
		if (rewriteSuccessFlag) {
			for (Producto producto : Producto.getProductos()) {
				if (producto.getId() == productoId) {
					Producto.popProducto(producto);;
					successFlag = true;
				}
			}
		}
		return successFlag;
	}
	
	static public Producto ensamblar(String id) throws IllegalArgumentException, IOException {
		refreshContents();
		
		String[] items = CsvUtils.obtenerTupla(id, csvContents);
		
		Integer idProducto = Integer.valueOf(id);
		Integer codigo = Integer.valueOf(items[1]);
		String descripcion = items[2];
		Double peso = Double.valueOf(items[3]);
		Integer idUnidad = Integer.valueOf(items[4]);
		UnidadMedida unidad = UnidadMedida.getById(idUnidad);
		
		if (unidad == null) {
			throw new IllegalArgumentException("Ocurrió un error al asociar la unidad de medida");
		}
		
		Producto producto = new Producto(idProducto, codigo, descripcion, peso, unidad);
		return producto;
	}
	
	static public Boolean almacenar(Producto producto) throws IOException {
		refreshContents();
		String id = String.valueOf(producto.getId());
		String codigo = String.valueOf(producto.getCodigo());
		String descripcion = producto.getDescripcion();
		String peso = String.valueOf(producto.getPeso());
		String idUnidadMedida = String.valueOf(producto.getUnidadMedida().getId());
		String[] atributos = new String[5];
		atributos[0] = id;
		atributos[1] = codigo;
		atributos[2] = descripcion;
		atributos[3] = peso;
		atributos[4] = idUnidadMedida;
		csvContents.add(atributos);
		Boolean successFlag = CsvUtils.reescribir(nombreArchivo, csvContents);
		return successFlag;
	}
	
}
