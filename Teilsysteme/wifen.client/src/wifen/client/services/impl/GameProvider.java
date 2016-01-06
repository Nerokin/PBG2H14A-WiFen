package wifen.client.services.impl;

import wifen.client.services.GameService;
import wifen.client.ui.controllers.SpielfeldView;
import wifen.commons.GameStateModel;

/**
 * Implementation of the {@linkplain GameService} interface.
 * 
 * @author Konstantin Schaper
 *
 */
public class GameProvider implements GameService {
	
	// Attributes
	
	private final SpielbrettController gameView;
	private GameStateModel currentModel;
	
	// Constructor(s)
	
	public GameProvider(GameStateModel initialModel) {
		playfield = new SpielfeldView(sizeFieldX, sizeFieldY, 10, 10, initialModel, markerWindow);
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

}
