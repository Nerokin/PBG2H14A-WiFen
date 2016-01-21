package wifen.commons;

import javafx.scene.image.Image;

public enum FileType {
	PDF("./resources/pdf.png"),
	DOC("./resources/doc.png"),
	XLS("./resources/xls.png"),
	TXT("./resources/txt.png"),
	CSV("./resources/csv.png"),
	IMG("./resources/img.png");
	
	private Image img;
	
	private FileType(String path) {
		img = new Image(path);
	}
	
	public Image getImg() {
		return img;
	}
}
