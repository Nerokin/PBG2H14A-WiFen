package wifen.client.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import wifen.commons.GameStateModel;
import wifen.commons.GridType;
import wifen.commons.MarkerModel;
import wifen.commons.Medium;
import wifen.commons.SpielfeldModel;

public class SaveGameData implements java.io.Serializable{
	public String[] chatLog;
	public String[] eventLog;
	public MarkerSave[] marker;
	public Medium[] medien;
	public GameStateModel model;
	public SpielfeldModel spielfeldModel;

	private void prepareLoad()
	{
		for (MarkerSave markerItem : marker) {
			markerItem.loadImage();
		}
	}

	public void saveMarker(Collection<MarkerModel> markers)
	{
		List<MarkerSave> saveData = new ArrayList<MarkerSave>();
		for (MarkerModel markerModel : markers) {
			saveData.add(new MarkerSave(markerModel, markerModel.getType().makePathFromName()));
		}
	}

	/*public void saveSpielfeldModel(SpielfeldModel spModel)
	{
		this.spielfeldModel = new SpielfeldModelSave();
		this.spielfeldModel.type = spModel.getTyp();
		this.spielfeldModel.sizeX = spModel.getSizeX();
		this.spielfeldModel.sizeY = spModel.getSizeY();
	}*/

	public void deserializeAll()
	{
		/*for (MediumSave medium : medien) {
			medium.writeFile();
		}*/
		prepareLoad();
	}

	public class MarkerSave implements java.io.Serializable{
		public MarkerModel model;
		public String imagePath;

		public MarkerSave(MarkerModel model, String path)
		{
			this.model = model;
			this.imagePath = path;
		}

		public void loadImage()
		{
			try {
				model.getType().setImg(new Image(imagePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public class MediumSave implements java.io.Serializable{
		public Medium baseMedium;

		/*public void writeFile() {
			try {
				File f = new File(baseMedium.);
				FileOutputStream fo = new FileOutputStream(f);
				fo.write(baseMedium.getRawData());
				fo.flush();
				fo.close();

			} catch (Exception e) {
				Logger.getGlobal().log(Level.WARNING, "File could not be saved!");
			}
		}*/
	}
	class SpielfeldModelSave implements java.io.Serializable{
		public GridType type;
		public double sizeX;
		public double sizeY;
	}
}
