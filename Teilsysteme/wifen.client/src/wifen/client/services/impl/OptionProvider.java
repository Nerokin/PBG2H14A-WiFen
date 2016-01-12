package wifen.client.services.impl;

import java.util.prefs.Preferences;
import wifen.client.services.OptionService;

/**
 * Implementation of the {@linkplain OptionService} interface.
 * 
 * @author Marc Brinkmann
 *
 */
public class OptionProvider implements OptionService {
	
	// Attributes
	Preferences prefs = Preferences.userRoot().node(this.getClass().getName());	

	@Override
	public double getVolume() {
		return prefs.getDouble("Volume", 100);
	}

	@Override
	public boolean getSoundMuted() {
		return prefs.getBoolean("SoundMuted", false);
	}

	@Override
	public boolean getMusicMuted() {
		return prefs.getBoolean("MusicMuted", false);
	}

	@Override
	public double getMaxFileSize() {
		return prefs.getDouble("MaxFileSize", 10000);
	}

	@Override
	public void setVolume(double volume) {
		prefs.putDouble("Volume", volume);
	}

	@Override
	public void setSoundMuted(boolean soundMuted) {
		prefs.putBoolean("SoundMuted", soundMuted);
	}

	@Override
	public void setMusicMuted(boolean musicMuted) {
		prefs.putBoolean("MusicMuted", musicMuted);		
	}

	@Override
	public void setMaxFileSize(double maxFileSize) {
		prefs.putDouble("MaxFileSize", maxFileSize);
	}
}
