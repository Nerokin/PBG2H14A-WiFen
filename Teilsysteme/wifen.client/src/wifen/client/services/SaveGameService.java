/**
 *
 */
package wifen.client.services;

import java.io.File;
import java.io.IOException;

import wifen.client.services.impl.SaveGameData;
import wifen.commons.GameStateModel;

/**
 * @author Oliver Bardong
 *
 */
public interface SaveGameService {

	public File SaveAllData(File file)throws IOException;
	//public SaveGameData LoadSave(File saveFile)throws IOException, ClassNotFoundException;
	public GameStateModel LoadSave(File saveFile)throws IOException, ClassNotFoundException;
	public void LoadGame(SaveGameData saveData);
	public void LoadGame(GameStateModel loadedModel);
}
