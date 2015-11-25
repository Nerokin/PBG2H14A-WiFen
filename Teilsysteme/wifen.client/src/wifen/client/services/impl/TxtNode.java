package wifen.client.services.impl;
/*
 * Node für Rohtext-Dateien (.txt)
 * Gibt den Inhalt als String-Array zurück, zeilenweise getrennt
 */ 

class TxtNode implements FileNode<String[]>{
	String path;
	
	public TxtNode(String p) {
		this.path = p;
	}
	
	public void setPath(String p) {
		this.path = p;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String[] getFileContent() {
		File f = new File(this.path);
		Scanner sc = new Scanner(f);
		ArrayList<String> l = new ArrayList<String>();
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			if(!s.isEmpty()) {
				l.add(s);
			}
		}
		return l.toArray();
	}
}