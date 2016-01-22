package wifen.commons;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

/**
 * Put description here
 *
 * @author unknown
 *
 */
public class Medium implements Serializable
{
	private static final long serialVersionUID = 5665637985008900238L;
	
	private String name;
	private FileType type;
	private byte[] rawrData;

	/**
	 * Put description here
	 *
	 * @param file
	 */
	public Medium(File file) throws IOException
	{
		name = file.getName();
		
		type = getTypeFromName(name);

		rawrData = Files.readAllBytes(file.toPath());
	}

	// Getter & Setter
	public String getName()
	{
		return name;
	}

	public FileType getType() {
		return type;
	}

	public byte[] getRawData()
	{
		return rawrData;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "{"
				+ "name :" + getName()
				+ ", type: " + getType()
				+ ", file: " + getRawData()
				+ "}";
	}

	// Create instance
	/**
	 * Put description here
	 *
	 * @return
	 */
	public MediumInstance createInstance()
	{
		return new MediumInstance(this);
	}
	
	private FileType getTypeFromName(String name) {
		String x = name.substring(name.lastIndexOf('.')).toLowerCase(); 
		switch(x) {
		case ".bmp":
		case ".jpg":
		case ".png":
		case ".gif":
			return FileType.IMG;
		case ".txt":
			return FileType.TXT;
		case ".csv":
			return FileType.CSV;
		case ".doc":
		case ".docx":
			return FileType.DOC;
		case ".xls":
		case ".xlsx":
			return FileType.XLS;
		case ".pdf":
			return FileType.PDF;
		default:
			throw new UnsupportedOperationException("Filetype " + x + " not supported");
		}
	}
}
