package wifen.commons;

import javafx.scene.image.Image;


/**
 * Put description here
 *
 * @author unknown
 *
 */
public class MarkerType implements java.io.Serializable {
	private transient Image img;
	private String name;

	/**
	 * Put description here
	 *
	 * @param n
	 * @param img
	 */
	public MarkerType(String n, Image img) {
		this.name = n;
		this.img = img;
	}

	/**
	 * Put description here
	 *
	 * @param img
	 * @param n
	 */
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
