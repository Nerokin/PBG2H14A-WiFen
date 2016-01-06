package wifen.commons.services;
/*
 * Generischer Knoten zum Verweis auf eine im Client verwendete Externe Datei.
 * <T> = Rückgabetyp für den Dateiinhalt
 * Speichert nicht den Inhalt der Datei
 */

public interface FileNode<T> {
	
	public double getX();
	public double getY();
	
	public void setX(double x);
	public void setY(double y);

	public void setPath(String p);

	public String getPath();

	public T getFileContent();
}