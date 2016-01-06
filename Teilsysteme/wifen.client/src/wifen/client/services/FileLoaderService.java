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


public interface FileLoaderService {

	/*
	 * Methoden geben Nodes auf die jeweiligen Dateitypen aus. Nodes sind nach Auslesemethode und sinnhaft getrennt.
	 */
	public ImageNode loadImage(String path);

	public TxtNode loadText(String path);

	public CsvNode loadCsv(String path);

	public CsvNode loadCsv(String path, String delimiter);

	public DocNode loadDoc(String path);

	public XlsNode loadXls(String path);

	public PdfNode loadPdf(String path);
	
	public boolean controlSize(String path);
	
	public long getMaxSize();
	
	public void setMaxSize(long maxSize);

	
}