package wifen.client.services;

import wifen.client.ui.controllers.SpielbrettController;
import wifen.commons.GameStateModel;
import wifen.commons.Player;

/**
 * Manages a single game session.
 * 
 * @author Konstantin Schaper
 *
 */
public interface GameService {
	
	public String getPlayerName();
	public Player getActivePlayer();
	public GameStateModel getCurrentModel();
	public void overrideModel(GameStateModel newModel);
	public SpielbrettController getGameView();

}
