package wifen.commons.services.impl;

import javafx.scene.image.Image;
import wifen.commons.services.FileNode;

public class NoteNode implements FileNode<String>{
	String path;
	String note;
	double posx;
	double posy;

	public NoteNode(String p, String n) {
		this.note = n;
		this.path = p;
	}

	public void setNote(String n) {
		this.note = n;
	}

	public String getNote() {
		return this.note;
	}
	
	public Image getFileImage(){
		return new Image(path, true);
	}
	
	public String getFileContent() {
		return note;
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

	@Override
	public void setPath(String p) {
		path=p;		
	}

	@Override
	public String getPath() {
		return path;
	}
}