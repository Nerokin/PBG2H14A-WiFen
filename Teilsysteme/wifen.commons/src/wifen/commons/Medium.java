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
	private String type;
	private byte[] rawrData;

	/**
	 * Put description here
	 *
	 * @param file
	 */
	public Medium(File file) throws IOException
	{
		name = file.getName();
		
		type = name.substring(name.lastIndexOf('.')).toLowerCase();

		rawrData = Files.readAllBytes(file.toPath());
	}

	// Getter & Setter
	public String getName()
	{
		return name;
	}

	public String getType() {
		return type;
	}

	public byte[] getRawData()
	{
		return rawrData;
	}

	@Override
	public String toString()
	{
		return name;
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
}
