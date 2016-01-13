package wifen.server.services.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.Packet;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.RefreshPacket;
import wifen.server.network.Server;
import wifen.server.services.ServerRefreshService;

public class ServerRefreshProvider implements ServerRefreshService{
	
	private final ObjectProperty<Server> server = new SimpleObjectProperty<>();
	
	private final ChangeListener<Server> onServerChangedListener = this::onServerChanged;

	public ServerRefreshProvider(Server server){
		serverProperty().addListener(getOnServerChangedListener());
		setServer(server);
	}
	
	public void handle(ConnectionEvent connectionEvent) {
		if(connectionEvent instanceof PacketReceivedEvent){
			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			Packet packet = packetEvent.getPacket();
			if(packet instanceof RefreshPacket){
				getServer().broadcastPacket(packet);
			}
		}
	}

	public Server getServer() {
		return this.serverProperty().get();
	}
	public final void setServer(final Server server){
		this.serverProperty().set(server);
	}
	
	public void onServerChanged(ObservableValue<? extends Server> observable, Server oldValue, Server newValue) {
		if (oldValue != null) {
			oldValue.removeListener(this);
		}
		
		if (newValue != null) {
			newValue.addListener(this);
		}
	}
	
	public final ObjectProperty<Server> serverProperty(){
		return this.server;
	}
	
	public ChangeListener<Server> getOnServerChangedListener(){
		return onServerChangedListener;
	}
}
