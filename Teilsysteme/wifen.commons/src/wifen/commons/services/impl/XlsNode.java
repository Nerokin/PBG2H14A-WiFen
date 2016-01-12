package wifen.commons.services.impl;

import wifen.commons.services.FileNode;

/*
 * Node für .xls- und .xlsx-Dateien
 * Öffnet die Datei in der Standardanwendung des Betriebsystems
 */

public class XlsNode implements FileNode<Void> {
	String path;
	double posx;
	double posy;

	public XlsNode(String p) {
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