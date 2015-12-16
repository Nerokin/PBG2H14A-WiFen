package wifen.server.services.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.Packet;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.ChatPacket;
import wifen.server.network.Server;
import wifen.server.services.ServerChatService;

/**
 * Implementation of the {@linkplain ServerChatService} interface.
 * 
 * @author Konstantin Schaper
 */
public class ServerChatProvider implements ServerChatService {
	
	// Properties
	
	private final ObjectProperty<Server> server = new SimpleObjectProperty<>();
	
	// Attributes
	
	private final ChangeListener<Server> onServerChangedListener = this::onServerChanged;
	
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
			Packet packet = packetEvent.getPacket();
			if (packet instanceof ChatPacket) {
				getServer().broadcastPacket(packet);
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
	
	// Event Handling
	
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

}
