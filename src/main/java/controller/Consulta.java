package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.dao.EstanteriaDao;
import model.dao.ProductoDao;
import model.dao.ProductoEnUbicacionDao;
import model.dao.ZonaDao;

public class Consulta {

	static public List<String[]> porNave(Nave nave) throws IOException {
		List<String[]> results = new ArrayList<>();
		String id = String.valueOf(nave.getId());
		List<String[]> csvContents = ZonaDao.getCsvContents();
		for (String[] tuple : csvContents) {
			if (tuple[1].equals(id)) {
				results.add(tuple);
			}
		}
		return results;
	}
	
	static public List<String[]> porZona(Zona zona) throws IOException {
		List<String[]> results = new ArrayList<>();
		String id = String.valueOf(zona.getId());
		List<String[]> csvContents = EstanteriaDao.getCsvContents();
		for (String[] tuple : csvContents) {
			if (tuple[1].equals(id)) {
				results.add(tuple);
			}
		}
		return results;
	}
	
	static public List<String[]> porUbicacion(Ubicacion ubicacion) throws IOException {
		List<String[]> results = new ArrayList<>();
		String id = String.valueOf(ubicacion.getId());
		List<String[]> csvContents = ProductoEnUbicacionDao.getCsvContents();
		for (String[] tuple : csvContents) {
			if (tuple[2].equals(id)) {
				results.add(tuple);
			}
		}
		return results;
	}
	
	static public List<String[]> porProducto(int id) throws IOException {
		List<String[]> results = new ArrayList<>();
		List<String[]> csvContents = ProductoEnUbicacionDao.getCsvContents();
		for (String[] tuple : csvContents) {
			if (Integer.valueOf(tuple[1]).equals(id)) {
				results.add(tuple);
			}
		}
		return results;
	}
	
	static public List<String[]> total() throws IOException {
		List<String[]> productoCsv = ProductoDao.getCsvContents();
		List<String[]> productoEnUbicCsv = ProductoEnUbicacionDao.getCsvContents();
		List<String[]> results = new ArrayList<>();
		for (String[] productoTupla : productoCsv) {
			String[] result = new String[6];
			for (int i=0; i<5; i++) {
				result[i] = productoTupla[i];
			}
			Double cantidadSuma = 0d;
			for (String[] productoEnUbicTupla : productoEnUbicCsv) {
				if (productoEnUbicTupla[1].equals(productoTupla[0])) {
					cantidadSuma += Double.valueOf(productoEnUbicTupla[3]);
				}
			}
			result[5] = String.valueOf(cantidadSuma);
		}
		return results;
	}
	
}
