package wifen.commons;

import javafx.scene.image.Image;

public enum FileType {
	PDF("./ressources/pdf.png"),
	DOC("./ressources/doc.png"),
	XLS("./ressources/xls.png"),
	TXT("./ressources/txt.png"),
	CSV("./ressources/csv.png"),
	IMG("./ressources/img.png");
	
	private Image img;
	
	private FileType(String path) {
		img = new Image(path);
	}
	
	public Image getImg() {
		return img;
	}
}
