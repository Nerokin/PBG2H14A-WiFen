package wifen.commons.services.impl;

import java.io.RandomAccessFile;
/*
 * Implementiert die Methoden des FileLoader-Interfaces zur Verwendung.
 * 
 * [Überarbeitet von Fabian Hitziger]
 * -> Überprüfung ob die Größe der zum Upload bereitgestellten Datei kleiner als die maximal zulässige
 * -> Datei größe [Falls angegeben] ist.
 */

import wifen.commons.services.FileLoaderService;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class FileLoaderProvider implements FileLoaderService {
	
	private long maxSize;
	
	public FileLoaderProvider(long maxSize){
		this.maxSize = maxSize;
	}
	
	public ImageNode loadImage(String path) {
		return controlSize(path) ? new ImageNode(path) : null;
	}
	
	public NoteNode loadNote(String path, String note) {
		return controlSize(path) ? new NoteNode(path,note) : null;
	}

	public TxtNode loadText(String path) {
		return controlSize(path) ? new TxtNode(path) : null;
	}

	public CsvNode loadCsv(String path, String delimiter) {
		return controlSize(path) ? new CsvNode(path, delimiter) : null;
	}

	public CsvNode loadCsv(String path) {
		return controlSize(path) ? loadCsv(path, ",") : null;
	}

	public DocNode loadDoc(String path) {
		return controlSize(path) ? new DocNode(path) : null;
	}

	public XlsNode loadXls(String path) {
		return controlSize(path) ? new XlsNode(path) : null;
	}

	public PdfNode loadPdf(String path) {
		return controlSize(path) ? new PdfNode(path) : null;
	}
	
	public boolean controlSize(String path){		
		if(maxSize > 0){
			return true;
		} else {
			long fileSize;
			try{
		        RandomAccessFile raf = new RandomAccessFile(path, "r");
		        fileSize = raf.length();
		        raf.close();
			} catch(Exception e){
				System.out.println(e);
				return false;
			}
			System.out.println(path + ": "+fileSize + " ["+maxSize+"]");
			return fileSize < this.maxSize;
		}
		
	}

	public long getMaxSize() {
		return this.maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}
	

}