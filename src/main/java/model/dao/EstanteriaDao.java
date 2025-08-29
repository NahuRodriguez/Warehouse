package model.dao;

import java.io.IOException;
import java.util.List;

import controller.Estanteria;
import controller.Zona;

public class EstanteriaDao {
	
	static private List<String[]> csvContents;
	static final private String nombreArchivo = "Estanteria.csv";
	
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

	static public Boolean eliminar(Integer estanteriaId) throws IOException {
		refreshContents();
		List<String[]> newContent = CsvUtils.eliminarTupla(estanteriaId, csvContents);
		Boolean rewriteSuccessFlag = CsvUtils.reescribir(nombreArchivo, newContent);
		Boolean successFlag = false;
		if (rewriteSuccessFlag) {
			for (Estanteria estanteria : Estanteria.getEstanterias()) {
				if (estanteria.getId() == estanteriaId) {
					Estanteria.popEstanteria(estanteria);;
					successFlag = true;
				}
			}
		}
		return successFlag;
	}
	
	static public Estanteria ensamblar(String id) throws IllegalArgumentException, IOException {
		refreshContents();
		
		String[] items = CsvUtils.obtenerTupla(id, csvContents);
		
		Integer idEstanteria = Integer.valueOf(id);
		
		Estanteria estanteria = new Estanteria(idEstanteria);
	
		Integer idZona = Integer.valueOf(items[1]);
		Zona zona = Zona.getById(idZona);
		if (zona == null) {
			zona = ZonaDao.ensamblar(items[1]);
		}
		zona.addEstanteria(estanteria);
		
		return estanteria;
	}
	
	static public Boolean almacenar(Estanteria estanteria) throws IOException {
		refreshContents();
		String id = String.valueOf(estanteria.getId());
		String idZona = "";
		for (Zona zona : Zona.getZonas()) {
			if (zona.getEstanterias().contains(estanteria)) {
				idZona = String.valueOf(zona.getId());
			}
		}
		if (idZona == "") {
			throw new IOException("No se encontró el objeto al que este hace referencia");
		}
		String[] atributos = new String[2];
		atributos[0] = id;
		atributos[1] = idZona;
		csvContents.add(atributos);
		Boolean successFlag = CsvUtils.reescribir(nombreArchivo, csvContents);
		return successFlag;
	}
	
}
