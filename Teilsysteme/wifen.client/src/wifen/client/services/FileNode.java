package wifen.client.services;
/*
 * Generischer Knoten zum Verweis auf eine im Client verwendete Externe Datei. 
 * <T> = Rückgabetyp für den Dateiinhalt
 * Speichert nicht den Inhalt der Datei
 */

interface FileNode<T> {
	
	public void setPath(String p);
	
	public String getPath();
	
	public T getFileContent();
}