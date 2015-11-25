package wifen.client.services.impl;
/*
 * Node für .doc- und .docx-Dateien
 * Öffnet die Datei in der Standardanwendung des Betriebsystems
 */ 

class DocNode implements FileNode<void>{
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
	
	public void getFileContent() {
		ClientApplication.getInstance().getHostServices().showDocument(this.path);
	}
}