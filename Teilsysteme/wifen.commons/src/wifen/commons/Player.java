package wifen.commons;

import java.io.Serializable;
import java.util.UUID;

public interface Player extends Serializable {
	
	public UUID getId();
	public String getName();
	public void setName(String newName);
	public SpielerRolle getRolle();
	public void setRolle(SpielerRolle newRolle);
}
