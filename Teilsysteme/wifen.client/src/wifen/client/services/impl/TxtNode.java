package wifen.client.services.impl;
/*
 * Node für Rohtext-Dateien (.txt)
 * Gibt den Inhalt als String-Array zurück, zeilenweise getrennt
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import wifen.client.services.FileNode;

public class TxtNode implements FileNode<String[]>{
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
		Scanner sc;
		try {
			sc = new Scanner(f);

			ArrayList<String> l = new ArrayList<String>();
			while(sc.hasNextLine()) {
				String s = sc.nextLine();
				if(!s.isEmpty()) {
					l.add(s);
				}
			}
			sc.close();
			return (String[]) l.toArray(new String[l.size()]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}