package model.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

	public static Boolean reescribir(String nombreArchivo, List<String[]> content) throws FileNotFoundException {
		Boolean successFlag = false;
		Path path = Paths.get("src", "main", "java", "model", nombreArchivo);
		try (PrintWriter writer = new PrintWriter(path.toFile())) {
			for (String[] splitLine : content) {
				String line = String.join(",", splitLine);
				writer.println(line);
			}
			successFlag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileNotFoundException("Error al acceder al archivo de guardado");
		}
		return successFlag;
	}
	
	public static List<String[]> leerArchivo(String nombreArchivo) throws IOException {
		List<String[]> contents = new ArrayList<>();
		try {
			List<String> lines = Files.readAllLines(Paths.get("src", "main", "java", "model", nombreArchivo));
			for (String line : lines) {
				 String[] result = line.split(",");
				 contents.add(result);
			}
			return contents;
		} catch (NoSuchFileException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidPathException e) {
			e.printStackTrace();
		}
		if (contents.isEmpty()) {
			throw new IOException("Error al leer el archivo de guardado");
		}
		return null;
	}
	
	public static List<String[]> eliminarTupla(Integer id, List<String[]> csvContents) {
		String idStr = String.valueOf(id);
		for (String[] item : csvContents) {
			if (item[0] == idStr) {
				csvContents.remove(item);
			}
		}
		return csvContents;
	}

	public static String[] obtenerTupla(String id, List<String[]> csvContents) throws IllegalArgumentException {
		for (String[] item : csvContents) {
			if (item[0] == id) {
				return item;
			}
		}
		throw new IllegalArgumentException("La información especificada no fue encontrada");
	}
	
	public static Integer getNextId(String nombreArchivo) throws IOException {
		List<String[]> contents = leerArchivo(nombreArchivo);
		Integer nextId = 1;
		for (String[] tuple : contents) {
			Integer id = Integer.valueOf(tuple[0]);
			if (id >= nextId) {
				nextId = ++id;
			}
		}
		return nextId;
	}
}
