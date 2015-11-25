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
	
	public ImageNode loadImage(String path) {
		return new ImageNode(path);
	}
	
	public TxtNode loadText(String path) {
		return new TxtNode(path);
	}
	
	public CsvNode loadCsv(String path, String delimiter) {
		return new CsvNode(String path, String delimiter);
	}
	
	public CsvNode loadCsv(String path) {
		return loadCsv(path, ",");
	}
	
	public DocNode loadDoc(String path) {
		return new DocNode(path);
	}
	
	public XlsNode loadXls(String path) {
		return new XlsNode(path);
	}
	
	public PdfNode loadPdf(String path) {
		return new PdfNode(path);
	}
}