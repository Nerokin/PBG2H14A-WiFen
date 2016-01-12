package wifen.commons.services.impl;

import javafx.scene.image.Image;
import wifen.commons.services.FileNode;

public class ImageNode implements FileNode<Image>{
	String path;
	double posx;
	double posy;

	public ImageNode(String p) {
		this.path = p;
	}

	public void setPath(String p) {
		this.path = p;
	}

	public String getPath() {
		return this.path;
	}

	public Image getFileContent() {
		return new Image(path, true);
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