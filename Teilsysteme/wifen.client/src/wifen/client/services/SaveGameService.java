/**
 *
 */
package wifen.client.services;

import java.io.File;
import java.io.IOException;

import wifen.client.services.impl.SaveGameData;

/**
 * @author Oliver Bardong
 *
 */
public interface SaveGameService {

	public File SaveAllData(File file)throws IOException;
	public SaveGameData LoadSave(File saveFile)throws IOException, ClassNotFoundException;
	public void LoadGame(SaveGameData saveData);
}
