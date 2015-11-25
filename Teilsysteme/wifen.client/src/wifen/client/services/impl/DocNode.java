package wifen.client.services.impl;

import wifen.client.services.FileNode;

/*
 * Node für .doc- und .docx-Dateien
 * Öffnet die Datei in der Standardanwendung des Betriebsystems
 */

public class DocNode implements FileNode<Void>{
	String path;

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
		ClientApplication.getInstance().getHostServices().showDocument(this.path);
	}
}