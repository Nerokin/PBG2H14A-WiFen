package wifen.commons.services.impl;
/*
 * Node für Rohtext-Dateien (.txt)
 * Gibt den Inhalt als String-Array zurück, zeilenweise getrennt
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import wifen.commons.services.FileNode;

public class TxtNode implements FileNode<String[]>{
	String path;
	double posx;
	double posy;

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
			return (String[]) l.toArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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