package wifen.commons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import wifen.commons.services.FileNode;
import wifen.commons.services.impl.FileLoaderProvider;

/**
 * Put description here
 *
 * @author unknown
 *
 */
public class Medium
{
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
