package wifen.commons;

public class MediumInstance
{
	Medium medium;
	private String caption;
	
	public MediumInstance(Medium medium)
	{
		this.medium = medium;
		
		caption = "";
	}

	// Getter / Setter
	public String getCaption()
	{
		return caption;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}
}
