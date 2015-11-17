package wifen.commons.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.image.Image;
import wifen.commons.services.FileLoaderService;

/*
 * Implementiert die Methoden des FileLoader-Interfaces zur Verwendung.
 */

public class FileLoaderProvider implements FileLoaderService {
	
	public Image loadImage(String path) {
		return new Image(path, true);
	}
	
	public String[] loadText(String path) {
		File f = new File(path);
		Scanner sc = new Scanner(f);
		ArrayList<String> l = new ArrayList<String>();
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			if(!s.isEmpty()) {
				l.add(s);
			}
		}
		return (String[]) l.toArray();
	}
	
	/*
	 * Liest CSV-Dateien ein. Eine Zeile entspricht dabei einem Datensatz.
	 */
	
	public String[][] loadCsv(String path, String delimiter) {
		String[] lines = loadText(path);
		ArrayList<String[]> ls = new ArrayList<String[]>();
		for(String l : lines) {
			ls.add(l.split(delimiter));
		}
		return (String[][]) ls.toArray();
	}
	
	public String[][] loadCsv(String path) {
		return loadCsv(path, ",");
	}
}