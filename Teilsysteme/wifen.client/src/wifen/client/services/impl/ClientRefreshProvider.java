package wifen.client.services.impl;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import wifen.client.application.ClientApplication;
import wifen.client.services.ClientRefreshService;
import wifen.client.services.GameService;
import wifen.commons.GameStateModel;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.RefreshPacket;
import wifen.commons.network.packets.impl.RefreshPacketImpl;

public class ClientRefreshProvider implements ClientRefreshService, ConnectionListener {
	
	//private final Logger logger = Logger.getLogger(ClientRefreshProvider.class.getName(), null);
	
	private final ObjectProperty<Connection> connection = new SimpleObjectProperty<>();
	
	private final ChangeListener<Connection> onConnectionChangeListener = this::onConnectionChanged;
	
	public ClientRefreshProvider(){
		connectionProperty().addListener(getOnConnectionChangeListener());
	}
	
	public ClientRefreshProvider(Connection connection){
		this();
		setConnection(connection);
	}
	
	@Override
	public Connection getConnection() {
		return this.connectionProperty().get();
	}

	@Override
	public void setConnection(Connection newConnection) {
		this.connectionProperty().set(newConnection);
	}

	@Override
	public void refresh() {
		ClientApplication client = new ClientApplication();
		RefreshPacketImpl refreshPacket = new RefreshPacketImpl(client.getServiceRegistry().getServiceProviderByClass(GameService.class).getCurrentModel());
		this.getConnection().sendPacket(refreshPacket);
		}
	
	public final ObjectProperty<Connection> connectionProperty(){
		return this.connection;
	}
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		if(connectionEvent instanceof PacketReceivedEvent){
			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			Packet packet = packetEvent.getPacket();
			if(packet instanceof RefreshPacket){
				RefreshPacket refreshPacket = (RefreshPacket) packet;
				GameStateModel state = refreshPacket.getGameStateModel();
				ClientApplication client = new ClientApplication();
				client.getServiceRegistry().getServiceProviderByClass(GameService.class).overrideModel(state);
			}
		}
	}
	
	public void onConnectionChanged(ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue) {
				
		if (oldValue != null) {
			oldValue.removeListener(this);
		}
		
		if (newValue != null) {
			newValue.addListener(this);
		}
	}
	
	public ChangeListener<Connection> getOnConnectionChangeListener(){
		return onConnectionChangeListener;
	}

}
