package model.dao;

import java.io.IOException;
import java.util.List;

import controller.Estanteria;
import controller.Ubicacion;

public class UbicacionDao {

	static private List<String[]> csvContents;
	static final private String nombreArchivo = "Ubicacion.csv";
	
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

	static public Boolean eliminar(Integer ubicacionId) throws IOException {
		refreshContents();
		List<String[]> newContent = CsvUtils.eliminarTupla(ubicacionId, csvContents);
		Boolean rewriteSuccessFlag = CsvUtils.reescribir(nombreArchivo, newContent);
		Boolean successFlag = false;
		if (rewriteSuccessFlag) {
			for (Ubicacion ubicacion : Ubicacion.getUbicaciones()) {
				if (ubicacion.getId() == ubicacionId) {
					Ubicacion.popUbicacion(ubicacion);
					successFlag = true;
				}
			}
		}
		return successFlag;
	}
	
	static public Ubicacion ensamblar(String id) throws IllegalArgumentException, IOException {
		refreshContents();
		
		String[] items = CsvUtils.obtenerTupla(id, csvContents);
		
		Integer idUbicacion = Integer.valueOf(id);
		
		Ubicacion ubicacion = new Ubicacion(idUbicacion);
	
		Integer idEstanteria = Integer.valueOf(items[1]);
		Integer posicion = Integer.valueOf(items[2]);
		Estanteria estanteria = Estanteria.getById(idEstanteria);
		if (estanteria == null) {
			estanteria = EstanteriaDao.ensamblar(items[1]);
		}
		estanteria.addUbicacion(ubicacion, posicion);
		
		return ubicacion;
	}
	
	static public Boolean almacenar(Ubicacion ubicacion) throws IOException {
		refreshContents();
		String id = String.valueOf(ubicacion.getId());
		String idEstanteria = "";
		String posicion = "";
		for (Estanteria estanteria : Estanteria.getEstanterias()) {
			int[] resultadoPredeterminado = {3, 3};
			if (estanteria.getPosicion(ubicacion) != resultadoPredeterminado) {
				idEstanteria = String.valueOf(estanteria.getId());
				int yPosicion = estanteria.getPosicion(ubicacion)[0];
				int xPosicion = estanteria.getPosicion(ubicacion)[1];
				posicion = String.valueOf((yPosicion * 3) + xPosicion + 1);
				break;
			}
		}
		if (idEstanteria == "" || posicion == "") {
			throw new IOException("No se encontró el objeto al que este hace referencia");
		}
		String[] atributos = new String[3];
		atributos[0] = id;
		atributos[1] = idEstanteria;
		atributos[2] = posicion;
		csvContents.add(atributos);
		Boolean successFlag = CsvUtils.reescribir(nombreArchivo, csvContents);
		return successFlag;
	}
	
}
