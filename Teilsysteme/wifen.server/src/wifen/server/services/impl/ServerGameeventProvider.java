package wifen.server.services.impl;

import java.util.NoSuchElementException;

import javax.imageio.spi.ServiceRegistry;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.GameeventPacket;
import wifen.commons.network.packets.impl.GameeventPacketImpl;
import wifen.server.network.Server;
import wifen.server.services.ServerGameService;
import wifen.server.services.ServerGameeventService;

/**
 * Implementation of the {@linkplain ServerGameeventService} interface.
 * 
 * @author Steffen M�ller
 */

public class ServerGameeventProvider implements ServerGameeventService {

	// Properties	
	
	private final ObjectProperty<Server> server = new SimpleObjectProperty<>();
	
	// Attributes	
	
	private final ChangeListener<Server> onServerChangedListener = this::onServerChanged;
	private ServiceRegistry registry;
	
	// Constructor(s)	
	
	public ServerGameeventProvider(Server server) {
		serverProperty().addListener(getOnServerChangedListener());
		setServer(server);
	}
	
	// <--- ServerListener --->	
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		if (connectionEvent instanceof PacketReceivedEvent) {
			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			if (packetEvent.getPacket() instanceof GameeventPacket) {
				GameeventPacket packet = (GameeventPacket) packetEvent.getPacket();
				getServer().broadcastPacket(new GameeventPacketImpl(packet.getMessage()));
			}
		}
	}
	
	// <--- ServerGameeventService --->	
	
	@Override
	public final Server getServer() {
		return this.serverProperty().get();
	}

	public final void setServer(final Server server) {
		this.serverProperty().set(server);
	}
	
	@Override
	public void fireEvent(String eventMessage) {
		try {
			getRegistry()
			.getServiceProviders(ServerGameService.class, true)
			.next()
			.addGameEvent(eventMessage);
		} catch (NoSuchElementException e) {
			// TODO ERROR
		}
		getServer().broadcastPacket(new GameeventPacketImpl(eventMessage));
	}
	
	// <--- RegisterableService --->
	
	@Override
	public void onRegistration(ServiceRegistry registry, Class<?> category) {
		if (getRegistry() != null && !getRegistry().equals(registry))
			registry.deregisterServiceProvider(this);
		else
			setRegistry(registry);
	}

	@Override
	public void onDeregistration(ServiceRegistry registry, Class<?> category) {
		if (getRegistry() != null && getRegistry().equals(registry))
			setRegistry(null);
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

	public void setRegistry(ServiceRegistry registry) {
		this.registry = registry;
	}



}
