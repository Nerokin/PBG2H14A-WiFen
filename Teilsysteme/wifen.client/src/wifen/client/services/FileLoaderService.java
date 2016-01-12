package wifen.client.services;

import wifen.commons.services.impl.CsvNode;
import wifen.commons.services.impl.DocNode;
import wifen.commons.services.impl.ImageNode;
import wifen.commons.services.impl.PdfNode;
import wifen.commons.services.impl.TxtNode;
import wifen.commons.services.impl.XlsNode;

/*
 * Diese Klasse bietet Methoden zum Zugriff auf Dateiinhalte mit einheitlichen Funktionsparametern.
 */


/**
 * Put description here
 * 
 * @author unknown
 *
 */
public interface FileLoaderService {

	/*
	 * Methoden geben Nodes auf die jeweiligen Dateitypen aus. Nodes sind nach Auslesemethode und sinnhaft getrennt.
	 */
	/**
	 * Put description here
	 * 
	 * @param path
	 * @return
	 */
	public ImageNode loadImage(String path);

	/**
	 * Put description here
	 * 
	 * @param path
	 * @return
	 */
	public TxtNode loadText(String path);

	/**
	 * Put description here
	 * 
	 * @param path
	 * @return
	 */
	public CsvNode loadCsv(String path);

	/**
	 * Put description here
	 * 
	 * @param path
	 * @param delimiter
	 * @return
	 */
	public CsvNode loadCsv(String path, String delimiter);

	/**
	 * Put description here
	 * 
	 * @param path
	 * @return
	 */
	public DocNode loadDoc(String path);

	/**
	 * Put description here 
	 * 
	 * @param path
	 * @return
	 */
	public XlsNode loadXls(String path);

	/**
	 * Put description here
	 * 
	 * @param path
	 * @return
	 */
	public PdfNode loadPdf(String path);
	
	/**
	 * Put description here
	 * 
	 * @param path
	 * @return
	 */
	public boolean controlSize(String path);
	
	/**
	 * Put description here
	 * 
	 * @return
	 */
	public long getMaxSize();
	
	/**
	 * Put description here
	 * 
	 * @param maxSize
	 */
	public void setMaxSize(long maxSize);

	
}