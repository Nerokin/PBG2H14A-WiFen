package wifen.commons;

import javafx.scene.image.Image;


public class MarkerType {
	private Image img;
	private String name;
	
	public MarkerType(String n, Image img) {
		this.name = n;
		this.img = img;
	}
	public MarkerType(Image img, String n){
		this.img = img;
		this.name = n;
	}
	
	public String makePathFromName() {
		String path = "file:src/resources/"+name+".png";
		return path;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
