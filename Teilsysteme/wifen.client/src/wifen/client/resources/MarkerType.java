package wifen.client.resources;

import javafx.scene.image.Image;
import wifen.client.services.impl.FileLoaderProvider;

public class MarkerType {
	Image img;
	String name;

	public MarkerType(String n) {
		name = n;
		img = new FileLoaderProvider().loadImage(makePathFromName()).getFileContent();
	}

	public MarkerType(Image img, String n) {
		this.img = img;
		this.name = n;
	}

	private String makePathFromName() {
		String path = "file:src/resources/" + name + ".png";
		return path;
	}
	public String getName() {
		return name;
	}
}
