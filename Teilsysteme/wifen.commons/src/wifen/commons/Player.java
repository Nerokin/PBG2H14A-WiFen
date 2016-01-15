package wifen.commons;

import java.io.Serializable;

public interface Player extends Serializable {
	
	public String getName();
	public void setName(String newName);
	public SpielerRolle getRolle();
	public void setRolle(SpielerRolle newRolle);
}
