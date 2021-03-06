package wifen.client.services.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.spi.ServiceRegistry;

import javafx.application.Platform;
import javafx.stage.Stage;
import wifen.client.application.ClientApplication;
import wifen.client.services.ClientChatService;
import wifen.client.services.ClientMediaService;
import wifen.client.services.GameService;
import wifen.client.ui.controllers.Hauptmenu;
import wifen.client.ui.controllers.SpielbrettController;
import wifen.commons.GameStateModel;
import wifen.commons.MarkerModel;
import wifen.commons.MediumModel;
import wifen.commons.Player;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.MarkerPacket;
import wifen.commons.network.packets.MarkerRemovedPacket;
import wifen.commons.network.packets.MediumPacket;
import wifen.commons.network.packets.MediumRemovedPacket;
import wifen.commons.network.packets.impl.MarkerPacketImpl;
import wifen.commons.network.packets.impl.MarkerRemovedPacketImpl;
import wifen.commons.network.packets.impl.MediumPacketImpl;
import wifen.commons.network.packets.impl.MediumRemovedPacketImpl;

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
	private GameStateModel initialModel;
	private final Set<GameServiceListener> listeners = new HashSet<>();

	// Constructor(s)

	public GameProvider(GameStateModel initialModel, Player player) throws IOException {
		this.activePlayer = player;
		setInitialModel(initialModel);
		setGameView(new SpielbrettController(initialModel));
		addListener(getGameView());
		getListeners().forEach((listener) -> listener.onRolleChanged(this, getActivePlayer().getRolle()));
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
			else if(packetEvent.getPacket() instanceof MarkerRemovedPacket){
				MarkerRemovedPacket packet = (MarkerRemovedPacket) packetEvent.getPacket();
				getGameView().getPlayfield().RemoveMarker(packet.getMarkerId());
			}
			else if(packetEvent.getPacket() instanceof MediumPacket) {
				MediumPacket packet = (MediumPacket) packetEvent.getPacket();
				getGameView().getPlayfield().AddMedium(packet.getMediumModel());
			}
			else if(packetEvent.getPacket() instanceof MediumRemovedPacket) {
				MediumRemovedPacket packet = (MediumRemovedPacket) packetEvent.getPacket();
				getGameView().getPlayfield().RemoveMedium(packet.getMediumId());
			}
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

			// Load ChatLog
			getRegistry().getServiceProviders(ClientChatService.class, true).next().loadChatlog(getInitialModel().getChatLog());

			// Load Media
			getRegistry().getServiceProviders(ClientMediaService.class, true).next().loadMedia(getInitialModel().getMedia());

			// Load GameEventLog
			//getRegistry().getServiceProviders(ClientGameeventService.class, true).next().loadGameeventlog(getInitialModel().getEreignisLog());
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

	public void sendMediumPlaced(MediumModel m) {
		try {
			ClientApplication.instance().getServiceRegistry().getServiceProviders(Connection.class, true).next()
			.sendPacket(new MediumPacketImpl(m));
		} catch (Exception e) {
			logger.log(Level.WARNING, "Medium konnte nicht platziert werden", e);
		}
	}

	public void sendMediumRemoved(UUID id) {
		try{
			ClientApplication.instance().getServiceRegistry().getServiceProviders(Connection.class, true).next()
			.sendPacket(new MediumRemovedPacketImpl(id));
		} catch (Exception e) {
			logger.log(Level.WARNING, "Medium konnte nicht entfernt werden", e);
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

	public GameStateModel getInitialModel() {
		return initialModel;
	}

	public void setInitialModel(GameStateModel initialModel) {
		this.initialModel = initialModel;
	}

	@Override
	public void addListener(GameServiceListener listener) {
		getListeners().add(listener);
	}

	@Override
	public void removeListener(GameServiceListener listener) {
		getListeners().remove(listener);
	}

	public Set<GameServiceListener> getListeners() {
		return listeners;
	}

}
