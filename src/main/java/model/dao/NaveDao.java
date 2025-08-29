package model.dao;

import java.io.IOException;
import java.util.List;

import controller.Nave;

public class NaveDao {

	static private List<String[]> csvContents;
	static final private String nombreArchivo = "Nave.csv";
	
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
	
	static public Boolean eliminar(Integer naveId) throws IOException {
		refreshContents();
		List<String[]> newContent = CsvUtils.eliminarTupla(naveId, csvContents);
		Boolean rewriteSuccessFlag = CsvUtils.reescribir(nombreArchivo, newContent);
		Boolean successFlag = false;
		if (rewriteSuccessFlag) {
			for (Nave nave : Nave.getNaves()) {
				if (nave.getId() == naveId) {
					Nave.popNave(nave);;
					successFlag = true;
				}
			}
		}
		return successFlag;
	}
	
	static public Nave ensamblar(String id) throws IllegalArgumentException, IOException {
		refreshContents();
		
		String[] items = CsvUtils.obtenerTupla(id, csvContents);
		
		Integer idNave = Integer.valueOf(id);
		String nombre = items[1];
		
		Nave nave = new Nave(idNave, nombre);
		return nave;
	}
	
	static public void ensamblarTodas(String nombreArchivo) throws IllegalArgumentException, IOException {
		refreshContents();
		
		List<String[]> items = CsvUtils.leerArchivo(nombreArchivo);
		if (items == null) {
			throw new IllegalArgumentException("El archivo no pudo ser leído");
		}
		for (String[] item : items) {
			Integer idNave = Integer.valueOf(item[0]);
			String nombre = item[1];
			new Nave(idNave, nombre);
		}
	}
	
	static public Boolean almacenar(Nave nave) throws IOException {
		refreshContents();
		String id = String.valueOf(nave.getId());
		String nombre = nave.getNombre();
		String[] atributos = new String[2];
		atributos[0] = id;
		atributos[1] = nombre;
		csvContents.add(atributos);
		Boolean successFlag = CsvUtils.reescribir(nombreArchivo, csvContents);
		return successFlag;
	}
	
}
