package wifen.client.services.impl;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.spi.ServiceRegistry;

import javafx.application.Platform;
import javafx.stage.Stage;
import wifen.client.application.ClientApplication;
import wifen.client.resources.MarkerView;
import wifen.client.services.GameService;
import wifen.client.ui.controllers.Hauptmenu;
import wifen.client.ui.controllers.SpielbrettController;
import wifen.commons.GameStateModel;
import wifen.commons.MarkerModel;
import wifen.commons.Player;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.events.ConnectionClosedEvent;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.EnterGamePacket;
import wifen.commons.network.packets.MarkerPacket;
import wifen.commons.network.packets.impl.EnterGamePacketImpl;
import wifen.commons.network.packets.impl.MarkerPacketImpl;
import wifen.commons.network.packets.impl.MarkerRemovedPacketImpl;

/**
 * Implementation of the {@linkplain GameService} interface.
 * 
 * @author Konstantin Schaper
 *
 */
public class GameProvider implements GameService, ConnectionListener {
	
	// Class Constants
	
	private final Logger logger = Logger.getLogger(GameProvider.class.getName());
	
	// Attributes
	
	private Player activePlayer;
	private SpielbrettController gameView;
	private ServiceRegistry registry;
	
	// Constructor(s)
	
	public GameProvider(GameStateModel initialModel, Player player) throws IOException {
		this.activePlayer = player;
		setGameView(new SpielbrettController(initialModel));
	}
	
	// <--- ConnectionListener --->
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		if (connectionEvent instanceof PacketReceivedEvent) {
			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			if (packetEvent.getPacket() instanceof MarkerPacket) {
				MarkerPacket packet = (MarkerPacket) packetEvent.getPacket();
				getGameView().getPlayfield().AddMarker(packet.getMarkerModel());
			}
		} else if (connectionEvent instanceof ConnectionClosedEvent) {
			
		}
	}
	
	// <--- RegisterableService --->
	
	@Override
	public void onRegistration(ServiceRegistry registry, Class<?> category) {
		if (getRegistry() != null && !getRegistry().equals(registry))
			registry.deregisterServiceProvider(this); 
		else {
			setRegistry(registry);
			getRegistry().getServiceProviders(Connection.class, true).next().addListener(this);
		}
	}

	@Override
	public void onDeregistration(ServiceRegistry registry, Class<?> category) {
		if (getRegistry() != null && getRegistry().equals(registry)) {
			try {
				Stage window = ClientApplication.instance().getServiceRegistry().getServiceProviders(Stage.class, true).next();
				window.getScene().setRoot(new Hauptmenu());
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Oberfläche konnte nicht ins hauptmenü wechseln, die Anwendung wird beendet", e);
				Platform.exit();
			}
			setRegistry(null);
		}
	}
	
	// <--- GameService --->
	
	@Override
	public void sendMarkerRemoved(UUID id) {
		try {
			ClientApplication.instance()
			.getServiceRegistry()
			.getServiceProviders(Connection.class, true).next()
			.sendPacket(new MarkerRemovedPacketImpl(id));
		} catch (Exception e) {
			logger.log(Level.WARNING, "Marker konnte nicht entfernt werden", e);
		}
	}
	
	@Override
	public void sendMarkerPlaced(MarkerModel marker) {
		try {
			ClientApplication.instance()
			.getServiceRegistry()
			.getServiceProviders(Connection.class, true).next()
			.sendPacket(new MarkerPacketImpl(marker));
		} catch (Exception e) {
			logger.log(Level.WARNING, "Marker konnte nicht platziert werden", e);
			// TODO Marker konnte nicht platziert werden
		}
	}
	
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
	public String getActivePlayerName() {
		return getActivePlayer().getName();
	}
	
	@Override
	public Player getActivePlayer() {
		return activePlayer;
	}

	// Getter & Setter
	
	public void setGameView(SpielbrettController gameView) {
		this.gameView = gameView;
	}

	public ServiceRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(ServiceRegistry registry) {
		this.registry = registry;
	}

}
