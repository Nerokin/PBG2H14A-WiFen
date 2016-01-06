package wifen.client.services;

import wifen.client.ui.controllers.SpielbrettController;
import wifen.client.ui.controllers.SpielfeldView;
import wifen.commons.GameStateModel;

/**
 * Manages a single game session.
 * 
 * @author Konstantin Schaper
 *
 */
public interface GameService {
	
	public GameStateModel getCurrentModel();
	public void overrideModel(GameStateModel newModel);
	public SpielbrettController getGameView();

}
