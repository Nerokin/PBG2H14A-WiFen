package wifen.client.services;

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
}