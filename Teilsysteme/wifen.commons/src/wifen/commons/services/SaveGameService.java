/**
 *
 */
package wifen.commons.services;

import java.io.File;
import java.io.IOException;

/**
 * @author Oliver Bardong
 *
 */
public interface SaveGameService {

	public File SaveAllData(String path)throws IOException;
	public void LoadAllData(File saveFile)throws IOException, ClassNotFoundException;
}
