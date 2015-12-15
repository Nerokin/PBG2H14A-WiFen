package wifen.commons;

import java.io.File;

import wifen.client.services.FileNode;
import wifen.client.services.impl.FileLoaderProvider;

public class Medium
{
	private String name;
	private FileNode<?> file;
	
	public Medium(File file)
	{
		name = file.getName();
		
		// Create filenode
		FileLoaderProvider loader = new FileLoaderProvider();
		
		String ext = name.substring(name.lastIndexOf('.'));
		if(ext.equalsIgnoreCase("txt"))
		{
			this.file = loader.loadText(file.getPath()); 
		}
		else if(ext.equalsIgnoreCase("csv"))
		{
			this.file = loader.loadCsv(file.getPath(), ";"); 
		}
		else if(ext.equalsIgnoreCase("pdf"))
		{
			this.file = loader.loadPdf(file.getPath()); 
		}
		else if(ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("bmp") || ext.equalsIgnoreCase("gif"))
		{
			this.file = loader.loadImage(file.getPath()); 
		}
		else if(ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx"))
		{
			this.file = loader.loadDoc(file.getPath()); 
		}
		else if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx"))
		{
			this.file = loader.loadXls(file.getPath()); 
		}
		else
		{
			throw new IllegalArgumentException("Unrecognized file extension");
		}
	}
	
	// Getter & Setter	
	public String getName()
	{
		return name;
	}
	
	public FileNode<?> getFile()
	{
		return file;
	}

	@Override
	public String toString()
	{
		return name;
	}
	
	// Create instance
	public MediumInstance createInstance()
	{
		return new MediumInstance(this);
	}
}
