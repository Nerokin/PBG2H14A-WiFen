package wifen.commons;

import javafx.scene.image.Image;

public enum FileType {
	PDF("/wifen/commons/resources/pdf.png"),
	DOC("/wifen/commons/resources/doc.png"),
	XLS("/wifen/commons/resources/xls.png"),
	TXT("/wifen/commons/resources/txt.png"),
	CSV("/wifen/commons/resources/csv.png"),
	IMG("/wifen/commons/resources/img.png");
	
	private Image img;
	
	private FileType(String path) {
		img = new Image(path);
	}
	
	public Image getImg() {
		return img;
	}
}
