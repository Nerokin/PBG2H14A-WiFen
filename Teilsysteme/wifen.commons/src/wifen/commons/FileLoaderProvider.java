/*
 * Implementiert die Methoden des FileLoader-Interfaces zur Verwendung.
 */

public class FileLoaderProvider implements FileLoader {
	
	public Image loadImage(String path) {
		return new Image(path, true);
	}
	
	public String[] loadTxt(String path) {
		File f = new File(path);
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
	
	/*
	 * Liest CSV-Dateien ein. Eine Zeile entspricht dabei einem Datensatz.
	 */
	
	public String[][] loadCsv(String path, String delimiter) {
		String[] lines = loadTxt(path);
		ArrayList<String[]> ls = new ArrayList<String[]>();
		for(String l : lines) {
			ls.add(l.split(delimiter));
		}
		return ls.toArray();
	}
	
	public String[][] loadCsv(String path) {
		return loadCsv(path, ",");
	}
}