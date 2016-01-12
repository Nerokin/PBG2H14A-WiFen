package wifen.client.services.impl;

import java.io.IOException;
import java.util.logging.Logger;

import wifen.client.services.GameService;
import wifen.client.ui.controllers.SpielbrettController;
import wifen.commons.GameStateModel;

/**
 * Implementation of the {@linkplain GameService} interface.
 * 
 * @author Konstantin Schaper
 *
 */
public class GameProvider implements GameService {
	
	// Class Constants
	
	private final Logger logger = Logger.getLogger(GameProvider.class.getName());
	
	// Attributes
	
	private String playerName;
	private SpielbrettController gameView;
	
	// Constructor(s)
	
	public GameProvider(GameStateModel initialModel, String playerName) throws IOException {
		setPlayerName(playerName);
		setGameView(new SpielbrettController(initialModel));
	}

	// Getter & Setter
	
	@Override
	public GameStateModel getCurrentModel() {
		return getGameView().getCurrentModel();
	}

	@Override
	public void overrideModel(GameStateModel newModel) {
		getGameView().setCurrentModel(newModel);
	}
	
	@Override
	public SpielbrettController getGameView() {
		return gameView;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setGameView(SpielbrettController gameView) {
		this.gameView = gameView;
	}

}
