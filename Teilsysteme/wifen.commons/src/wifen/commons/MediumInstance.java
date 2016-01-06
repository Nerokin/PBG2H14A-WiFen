package wifen.commons;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class MediumInstance
{
	private Medium medium;
	private String caption;
	
	/**
	 * Put description here
	 * 
	 * @param medium
	 */
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
