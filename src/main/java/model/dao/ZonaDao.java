package model.dao;

import java.io.IOException;
import java.util.List;

import controller.Nave;
import controller.TipoZona;
import controller.Zona;

public class ZonaDao {

	static private List<String[]> csvContents;
	static final private String nombreArchivo = "Zona.csv";
	
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

	static public Boolean eliminar(Integer zonaId) throws IOException {
		refreshContents();
		List<String[]> newContent = CsvUtils.eliminarTupla(zonaId, csvContents);
		Boolean rewriteSuccessFlag = CsvUtils.reescribir(nombreArchivo, newContent);
		Boolean successFlag = false;
		if (rewriteSuccessFlag) {
			for (Zona zona : Zona.getZonas()) {
				if (zona.getId() == zonaId) {
					Zona.popZona(zona);
					successFlag = true;
				}
			}
		}
		return successFlag;
	}
	
	static public Zona ensamblar(String id) throws IllegalArgumentException, IOException {
		refreshContents();
		
		String[] items = CsvUtils.obtenerTupla(id, csvContents);
		
		Integer idZona = Integer.valueOf(id);
		Integer idTipoZona = Integer.valueOf(items[2]);
		TipoZona tipo = TipoZona.getById(idTipoZona);
		
		if (tipo == null) {
			throw new IllegalArgumentException("Ocurrió un error al asociar el tipo de zona");
		}
		
		Zona zona = new Zona(idZona, tipo);
	
		Integer idNave = Integer.valueOf(items[1]);
		Nave nave = Nave.getById(idNave);
		if (nave == null) {
			nave = NaveDao.ensamblar(items[1]);
		}
		nave.addZona(zona);
		
		return zona;
	}
	
	static public Boolean almacenar(Zona zona) throws IOException {
		refreshContents();
		String id = String.valueOf(zona.getId());
		String idNave = "";
		for (Nave nave : Nave.getNaves()) {
			if (nave.getZonas().contains(zona)) {
				idNave = String.valueOf(nave.getId());
			}
		}
		if (idNave == "") {
			throw new IOException("No se encontró el objeto al que este hace referencia");
		}
		String idTipoZona = String.valueOf(zona.getTipo().getId());
		String[] atributos = new String[3];
		atributos[0] = id;
		atributos[1] = idNave;
		atributos[2] = idTipoZona;
		csvContents.add(atributos);
		Boolean successFlag = CsvUtils.reescribir(nombreArchivo, csvContents);
		return successFlag;
	}
	
}
