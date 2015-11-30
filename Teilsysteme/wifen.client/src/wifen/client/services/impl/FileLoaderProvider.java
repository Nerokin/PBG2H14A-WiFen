package wifen.client.services.impl;

import wifen.client.services.FileLoaderService;

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
		return new CsvNode(path, delimiter);
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