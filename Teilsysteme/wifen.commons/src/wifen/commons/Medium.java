package wifen.commons;

import java.io.File;

import wifen.client.services.FileNode;
import wifen.client.services.impl.FileLoaderProvider;

public class Medium
{
	private String name;
	FileNode<?> file;
	
	public Medium(File file)
	{
		name = file.getName();
		
		// Create filenode
		FileLoaderProvider loader = new FileLoaderProvider();
		
		String ext = name.substring(name.lastIndexOf('.'));
		if(ext.equals("txt"))
		{
			this.file = loader.loadText(file.getPath()); 
		}
		else if(ext.equals("csv"))
		{
			this.file = loader.loadCsv(file.getPath()); 
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
	
	@Override
	public String toString()
	{
		return name;
	}
}
