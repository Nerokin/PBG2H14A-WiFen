package wifen.client.services.impl;
/*
 * Node für .pdf-Dateien
 * Öffnet die Datei in der Standardanwendung des Betriebsystems
 */

import wifen.client.application.ClientApplication;
import wifen.client.services.FileNode;

public class PdfNode implements FileNode<Void> {
	String path;

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
		ClientApplication.instance().getHostServices().showDocument(this.path);
		return null;
	}
}