package wifen.commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import wifen.commons.network.impl.ConnectionImpl;


/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class MarkerType implements Serializable{
	private static final Logger logger = Logger.getLogger(MarkerType.class.getName());

	private static final long serialVersionUID = -8137288255259091500L;
	private byte[] img;
	private String name;
	private int width;
	private int heigth;
	
	/**
	 * Put description here
	 * 
	 * @param n
	 * @param img
	 * @throws IOException 
	 */
	public MarkerType(String n, Image img) throws IOException {
		this.name = n;
		this.img = imageToByte(img);
	}
	
	/**
	 * Put description here
	 * 
	 * @param img
	 * @param n
	 * @throws IOException 
	 */
	public MarkerType(Image img, String n) throws IOException{
		this.img = imageToByte(img);
		this.name = n;
	}
	
	private byte[] imageToByte(Image i) throws IOException{
		this.width = (int) i.getWidth();
		this.heigth = (int) i.getHeight();
		BufferedImage bImage = SwingFXUtils.fromFXImage(i, null);
		ByteArrayOutputStream s = new ByteArrayOutputStream();
		ImageIO.write(bImage, "png", s);
		byte[] res  = s.toByteArray();
		return res;
	}
	
	private Image byteToImage(byte[] raw, final int width, final int height) {
		WritableImage image = new WritableImage(width, height);
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(raw);
			BufferedImage read = ImageIO.read(bis);
			image = SwingFXUtils.toFXImage(read, null);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Marker konnte nicht platziert werden", e);
		}
		return image;
	}
	
	public String makePathFromName() {
		String path = "file:src/resources/"+name+".png";
		return path;
	}

	public Image getImg() {
		return byteToImage(this.img,this.width,this.heigth);
	}

	public void setImg(Image img) throws IOException {
		this.img = imageToByte(img);
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
