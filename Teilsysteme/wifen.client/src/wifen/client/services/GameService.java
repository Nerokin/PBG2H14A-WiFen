package wifen.client.services;

import javax.imageio.spi.RegisterableService;

import wifen.client.ui.controllers.SpielbrettController;
import wifen.commons.GameStateModel;
import wifen.commons.MarkerModel;
import wifen.commons.Player;

/**
 * Manages a single game session.
 * 
 * @author Konstantin Schaper
 *
 */
public interface GameService extends RegisterableService {
	
	public String getActivePlayerName();
	public Player getActivePlayer();
	public GameStateModel getCurrentModel();
	public void overrideModel(GameStateModel newModel);
	public SpielbrettController getGameView();
	public void sendMarkerPlaced(MarkerModel marker);
	public void sendMarkerRemoved(int id);

}
