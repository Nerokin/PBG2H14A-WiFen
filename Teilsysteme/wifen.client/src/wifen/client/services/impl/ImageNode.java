package wifen.client.services.impl;

import javafx.scene.image.Image;
import wifen.client.services.FileNode;
/*
 * Node für Bilddateien (.png, .jpg, .bmp, .gif (unanimiert))
 * Gibt den Dateiinhalt als Image-Objekt zurück
 */

public class ImageNode implements FileNode<Image>{
	String path;

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
		return new Image("file:\\" + path, true);
	}
}