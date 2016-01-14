package wifen.commons.services.impl;
/*
 * Node für .pdf-Dateien
 * Öffnet die Datei in der Standardanwendung des Betriebsystems
 */

import wifen.commons.services.FileNode;

public class PdfNode implements FileNode<Void>{
	String path;
	double posx;
	double posy;

	public PdfNode(String p) {
		this.path = p;
	}

	public void setPath(String p) {
		this.path = p;
	}

	public String getPath() {
		return this.path;
	}

	public Void getFileContent() {
		//ClientApplication.getInstance().getHostServices().showDocument(this.path);
		return null;
	}
	
	@Override
	public double getX() {
		return posx;
	}

	@Override
	public double getY() {
		return posy;
	}

	@Override
	public void setX(double x) {
		this.posx = x;
	}

	@Override
	public void setY(double y) {
		this.posy = y;
	}
}