package wifen.client.services;

import java.util.UUID;

import javax.imageio.spi.RegisterableService;

import wifen.client.ui.controllers.SpielbrettController;
import wifen.commons.GameStateModel;
import wifen.commons.MarkerModel;
import wifen.commons.Player;
import wifen.commons.SpielerRolle;

/**
 * Manages a single game session.
 * 
 * @author Konstantin Schaper
 *
 */
public interface GameService extends RegisterableService {
	
	public interface GameServiceListener {
		// TODO Add methods
		public void onRolleChanged(GameService service, SpielerRolle rolle);
	}
	
	public String getActivePlayerName();
	public Player getActivePlayer();
	public GameStateModel getCurrentModel();
	public void overrideModel(GameStateModel newModel);
	public SpielbrettController getGameView();
	public void sendMarkerPlaced(MarkerModel marker);
	public void sendMarkerRemoved(UUID id);
	public void addListener(GameServiceListener listener);
	public void removeListener(GameServiceListener listener);

}
