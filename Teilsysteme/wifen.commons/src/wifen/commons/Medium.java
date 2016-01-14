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
	private FileNode<?> file;
	private byte[] rawrData;

	/**
	 * Put description here
	 *
	 * @param file
	 */
	public Medium(File file) throws IOException
	{
		name = file.getName();
		// Create filenode
		FileLoaderProvider loader = new FileLoaderProvider(file.length());

		type = name.substring(name.lastIndexOf('.')).toLowerCase();
		if(type.equalsIgnoreCase("txt"))
		{
			this.file = loader.loadText(file.getPath());
		}
		else if(type.equalsIgnoreCase("csv"))
		{
			this.file = loader.loadCsv(file.getPath(), ";");
		}
		else if(type.equalsIgnoreCase("pdf"))
		{
			this.file = loader.loadPdf(file.getPath());
		}
		else if(type.equalsIgnoreCase("png") || type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("bmp") || type.equalsIgnoreCase("gif"))
		{
			this.file = loader.loadImage(file.getPath());
		}
		else if(type.equalsIgnoreCase("doc") || type.equalsIgnoreCase("docx"))
		{
			this.file = loader.loadDoc(file.getPath());
		}
		else if(type.equalsIgnoreCase("xls") || type.equalsIgnoreCase("xlsx"))
		{
			this.file = loader.loadXls(file.getPath());
		}
		else
		{
			throw new IllegalArgumentException("Unrecognized file extension");
		}

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

	public FileNode<?> getFile()
	{
		return file;
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
