package wifen.client.services.impl;
/*
 * Node für .xls- und .xlsx-Dateien
 * Öffnet die Datei in der Standardanwendung des Betriebsystems
 */ 

class XlsNode implements FileNode<void> {
	String path;
	
	public XlsNode(String p) {
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