/* 
 * Diese Klasse bietet Methoden zum Zugriff auf Dateiinhalte mit einheitlichen Funktionsparametern.
 */


public interface FileLoaderService {
	
	/* 
	 * Lädt ein Bild aus Dateien der folgenden Formate: .png, .bmp, .jpg, .gif (nicht animiert).
	 * Gibt ein Objekt des Typs Image (javafx.scene.image.Image) zurück.
	 */
	public Image loadImage(String path);
	
	public String[] loadText(String path);
	
	public String[][] loadCsv(String path);
	
	public String[][] loadCsv(String path, String delimiter);
	
	// public ? loadDoc(String path);
	
	// public ? loadXls(String path);
	
	// public ? loadPdf(String path);
}