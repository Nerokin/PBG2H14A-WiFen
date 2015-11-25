package wifen.client.services.impl;

/*
 * Node für .csv-Dateien (Rohtext-Tabelle)
 * Eine Zeile entspricht einem Datensatz, Felder getrennt durch den Delimiter
 * Dateiinhalt wird als String[][] ausgegeben, jedes Feld also als String interpretiert
 */

class CsvNode implements FileNode<String[][]>{
	String path;
	String delimiter;
	
	public CsvNode(String p, String delimiter) {
		this.path = p;
	}
	
	public void setPath(String p) {
		this.path = p;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String[][] getFileContent() {
		File f = new File(this.path);
		Scanner sc = new Scanner(f);
		ArrayList<String[]> ls = new ArrayList<String[]>();
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			if(!s.isEmpty()) {
				ls.add(s.split(this.delimiter));
			}
		}
		return String[][] ls.toArray();
	}
}