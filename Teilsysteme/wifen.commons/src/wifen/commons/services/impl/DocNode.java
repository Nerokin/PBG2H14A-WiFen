package wifen.commons.services.impl;

import wifen.commons.services.FileNode;

/*
 * Node für .doc- und .docx-Dateien
 * Öffnet die Datei in der Standardanwendung des Betriebsystems
 */

public class DocNode implements FileNode<Void>{
	String path;
	double posx;
	double posy;

	public DocNode(String p) {
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