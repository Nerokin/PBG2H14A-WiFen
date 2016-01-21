package wifen.server.services.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.spi.ServiceRegistry;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.ChatPacket;
import wifen.commons.network.packets.impl.ChatPacketImpl;
import wifen.server.network.Server;
import wifen.server.services.ServerChatService;
import wifen.server.services.ServerGameService;

/**
 * Implementation of the {@linkplain ServerChatService} interface.
 * 
 * @author Konstantin Schaper
 */
public class ServerChatProvider implements ServerChatService, ConnectionListener {
	
	// Class Constants
	
	private final Logger logger = Logger.getLogger(ServerChatProvider.class.getName());
	
	// Properties	
	
	private final ObjectProperty<Server> server = new SimpleObjectProperty<>();
	
	// Attributes	
	
	private final ChangeListener<Server> onServerChangedListener = this::onServerChanged;
	private ServiceRegistry registry;
	
	// Constructor(s)	
	
	public ServerChatProvider(Server server) {
		serverProperty().addListener(getOnServerChangedListener());
		setServer(server);
	}
	
	// <--- ServerListener --->	
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		if (connectionEvent instanceof PacketReceivedEvent) {
			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			if (packetEvent.getPacket() instanceof ChatPacket) {
				ChatPacket packet = (ChatPacket) packetEvent.getPacket();
				try {
					// Save the message in the game state model
					ServerGameService gameService = getRegistry().getServiceProviders(ServerGameService.class, true).next();
					gameService.addChatMessage(packet.getSourceName() + ": " + packet.getMessage());
					
					// Broadcast the message
					getServer().broadcastPacket(new ChatPacketImpl(packet.getSourceName(), packet.getMessage()));
				} catch (Exception e) {
					logger.log(Level.WARNING, "Cannot save ChatMessage.", e);
				}
			}
		}
	}
	
	// <--- ServerChatService --->	
	
	@Override
	public final Server getServer() {
		return this.serverProperty().get();
	}

	public final void setServer(final Server server) {
		this.serverProperty().set(server);
	}
	
	// <--- Registerable --->
	
	@Override
	public void onRegistration(ServiceRegistry registry, Class<?> category) {
		if (getRegistry() != null && !getRegistry().equals(registry))
			registry.deregisterServiceProvider(this); 
		else {
			setRegistry(registry);
		}
	}

	@Override
	public void onDeregistration(ServiceRegistry registry, Class<?> category) {
		if (getRegistry() != null && getRegistry().equals(registry)) {
			setRegistry(null);
		}
	}
	
	// Event Handling	
	
	/**
	 * Automatically called whenever the associated server changes.<br>
	 * Stops listening the old server and starts listening the new server value.
	 */
	public void onServerChanged(ObservableValue<? extends Server> observable, Server oldValue, Server newValue) {
		if (oldValue != null) {
			oldValue.removeListener(this);
		}
		
		if (newValue != null) {
			newValue.addListener(this);
		}
	}
	
	// Getter & Setter
	
	public final ObjectProperty<Server> serverProperty() {
		return this.server;
	}

	public ChangeListener<Server> getOnServerChangedListener() {
		return onServerChangedListener;
	}

	public ServiceRegistry getRegistry() {
		return registry;
	}

	private void setRegistry(ServiceRegistry registry) {
		this.registry = registry;
	}

}
