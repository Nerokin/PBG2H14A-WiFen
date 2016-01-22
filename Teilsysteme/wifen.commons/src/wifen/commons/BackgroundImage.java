package wifen.commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import com.sun.istack.internal.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class BackgroundImage implements Serializable {

	// Class Constants

	private static final long serialVersionUID = -4530458633629448555L;
	private static final Logger logger = Logger.getLogger(BackgroundImage.class);

	// Attributes

	private final byte[] rawData;
	private final int width;
	private final int height;

	public BackgroundImage(Image image) throws IOException {
		this.width = (int) image.getWidth();
		this.height = (int) image.getHeight();
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		ByteArrayOutputStream s = new ByteArrayOutputStream();
		ImageIO.write(bImage, "png", s);
		rawData = s.toByteArray();
	}

	public BackgroundImage(byte[] rawData, int width, int height) {
		super();
		this.rawData = rawData;
		this.width = width;
		this.height = height;
	}

	// Getter & Setter

	public byte[] getRawData() {
		return rawData;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Image getImage(int width, int height) {
		WritableImage image = new WritableImage(width, height);
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(getRawData());
			BufferedImage read = ImageIO.read(bis);
			image = SwingFXUtils.toFXImage(read, null);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Image could not be converted", e);
		}
		return image;
	}

	public Image getImage() {
		return getImage(getWidth(), getHeight());
	}

}
