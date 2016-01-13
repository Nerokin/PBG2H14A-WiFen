package wifen.client.services;

/**
 * Handles the options, which are defined by the user
 * 
 * @author Marc Brinkmann
 *
 */
public interface OptionService {
	
	public double getVolume();
	public boolean getSoundMuted();
	public boolean getMusicMuted();
	public double getMaxFileSize();
	
	public void setVolume(double volume);
	public void setSoundMuted(boolean soundMuted);
	public void setMusicMuted(boolean musicMuted);
	public void setMaxFileSize(double maxFileSize);

}
