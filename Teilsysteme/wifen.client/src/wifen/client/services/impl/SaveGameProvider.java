/**
 *
 */
package wifen.client.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import wifen.client.application.ClientApplication;
import wifen.client.services.GameService;
import wifen.client.services.SaveGameService;
import wifen.client.services.impl.SaveGameData.MarkerSave;
import wifen.commons.MarkerModel;
import wifen.commons.Medium;
import wifen.commons.SpielfeldModel;

/**
 * @author Oliver Bardong
 *
 */
public class SaveGameProvider implements SaveGameService {


	/* (non-Javadoc)
	 * @see wifen.commons.services.SaveGameService#SaveAllData()
	 */
	@Override
	public File SaveAllData(File file) throws IOException {
		// collect all Data
		GameService gameservice = ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class,false).next();
		List<String> chatlog = gameservice.getCurrentModel().getChatLog();
		List<String> ereignislog = gameservice.getCurrentModel().getEreignisLog();
		List<MarkerModel> marker = gameservice.getGameView().getPlayfield().getModel().getMarkers();
		Medium[] media = gameservice.getGameView().getMediaLibrary().getMedienListe();
		SpielfeldModel spModel = gameservice.getGameView().getPlayfield().getModel();
		// settings sammeln

		SaveGameData data = new SaveGameData();
		chatlog.toArray(data.chatLog);
		ereignislog.toArray(data.eventLog);

		data.saveMarker(marker);
		data.medien = media;

		data.spielfeldModel=spModel;

		File output = file;
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
	public SaveGameData LoadSave(File saveFile) throws IOException, ClassNotFoundException {
		GameService gameservice = ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class,false).next();

		SaveGameData data;
		FileInputStream fi = new FileInputStream(saveFile);
		ZipInputStream zi = new ZipInputStream(fi);
		ZipEntry zentry = zi.getNextEntry();
		ObjectInputStream serIn = new ObjectInputStream(zi);
		data = (SaveGameData) serIn.readObject();
		serIn.close();
		zi.close();
		fi.close();
		return data;
		//TODO: Push data arround
	}

	@Override
	public void LoadGame(SaveGameData saveData) {
		// TODO Auto-generated method stub
		saveData.deserializeAll();
		GameService gameservice = ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class,false).next();
		gameservice.getGameView().getPlayfield().setModel(saveData.spielfeldModel);
		gameservice.getCurrentModel().getChatLog().addAll(Arrays.asList(saveData.chatLog));
		gameservice.getCurrentModel().getEreignisLog().addAll(Arrays.asList(saveData.eventLog));

		for (MarkerSave markerSave : saveData.marker) {
			gameservice.getGameView().getPlayfield().getModel().placeMarker(markerSave.model);
		}
		for (Medium medium : saveData.medien) {
			gameservice.getGameView().getMediaLibrary().addMedium(medium);
		}


	}
}
