package wifen.client.services.impl;

import java.io.IOException;

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
	
	// Attributes
	
	private SpielbrettController gameView;
	private GameStateModel currentModel;
	
	// Constructor(s)
	
	public GameProvider(GameStateModel initialModel) {
		try {
			setCurrentModel(initialModel);
			gameView = new SpielbrettController(getCurrentModel());
		} catch (IOException e) {
			// TODO
		}
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

	private void setCurrentModel(GameStateModel currentModel) {
		this.currentModel = currentModel;
	}

}
