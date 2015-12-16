package wifen.client.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import wifen.client.services.FileNode;

/*
 * Node für .csv-Dateien (Rohtext-Tabelle)
 * Eine Zeile entspricht einem Datensatz, Felder getrennt durch den Delimiter
 * Dateiinhalt wird als String[][] ausgegeben, jedes Feld also als String interpretiert
 */

public class CsvNode implements FileNode<String[][]> {
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
		Scanner sc;
		try {
			sc = new Scanner(f);

			ArrayList<String[]> ls = new ArrayList<String[]>();
			while (sc.hasNextLine()) {
				String s = sc.nextLine();
				if (!s.isEmpty())
					ls.add(s.split(this.delimiter));
			}
			sc.close();
			return (String[][]) ls.toArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}