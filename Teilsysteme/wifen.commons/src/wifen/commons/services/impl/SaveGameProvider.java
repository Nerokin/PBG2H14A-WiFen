/**
 *
 */
package wifen.commons.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import wifen.client.application.ClientApplication;
import wifen.client.services.GameService;
import wifen.commons.MarkerModel;
import wifen.commons.Medium;
import wifen.commons.SaveGameData;
import wifen.commons.SpielfeldModel;
import wifen.commons.services.SaveGameService;

/**
 * @author Oliver Bardong
 *
 */
public class SaveGameProvider implements SaveGameService {


	/* (non-Javadoc)
	 * @see wifen.commons.services.SaveGameService#SaveAllData()
	 */
	@Override
	public File SaveAllData(String path) throws IOException {
		// collect all Data
		GameService gameservice = ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class,false).next();
		List<String> chatlog = gameservice.getCurrentModel().getChatLog();
		List<String> ereignislog = gameservice.getCurrentModel().getEreignisLog();
		List<MarkerModel> marker = gameservice.getGameView().getPlayfield().getModel().getMarkers();
		List<Medium> media = gameservice.getGameView().getMediaLibrary().getMedienListe();
		SpielfeldModel spModel = gameservice.getGameView().getPlayfield().getModel();
		// settings sammeln

		SaveGameData data = new SaveGameData();
		chatlog.toArray(data.chatLog);
		ereignislog.toArray(data.eventLog);

		data.saveMarker(marker);
		media.toArray(data.medien);

		data.saveSpielfeldModel(spModel);

		File output = new File(path);
		FileOutputStream dest = new FileOutputStream(output);
		ZipOutputStream zipOut = new ZipOutputStream(dest);
		ObjectOutputStream serOut = new ObjectOutputStream(zipOut);

        ZipEntry entry = new ZipEntry("SaveData");
        zipOut.putNextEntry(entry);
        serOut.writeObject(data);
        serOut.close();
        zipOut.close();
        dest.close();

		return output;
	}

	/* (non-Javadoc)
	 * @see wifen.commons.services.SaveGameService#LoadAllData(java.io.File)
	 */
	@Override
	public void LoadAllData(File saveFile) throws IOException, ClassNotFoundException {
		GameService gameservice = ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class,false).next();

		SaveGameData data;
		FileInputStream fi = new FileInputStream(saveFile);
		ZipInputStream zi = new ZipInputStream(fi);
		ZipEntry zentry = zi.getNextEntry();
		ObjectInputStream serIn = new ObjectInputStream(zi);
		data = (SaveGameData) serIn.readObject();

		//TODO: Push data arround
	}

}
