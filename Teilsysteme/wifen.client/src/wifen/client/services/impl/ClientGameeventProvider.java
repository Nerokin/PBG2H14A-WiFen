package wifen.client.services.impl;

import java.util.List;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wifen.client.services.ClientGameeventService;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.GameeventPacket;
import wifen.commons.network.packets.impl.GameeventPacketImpl;

public class ClientGameeventProvider implements ClientGameeventService, ConnectionListener {

	// Class Constants
	
		private final Logger logger = Logger.getLogger(ClientGameeventProvider.class.getName());
		
		// Properties
		
		private final ObjectProperty<Connection> connection = new SimpleObjectProperty<>();
		private final ObservableList<String> gameeventHistory = FXCollections.observableArrayList();
		
		// Attributes
		
		private final ChangeListener<Connection> onConnectionChangeListener = this::onConnectionChanged;
		
		// Constructor(s)
		
		public ClientGameeventProvider() {
			// Update the chat controller when the network connection changes
			connectionProperty().addListener(getOnConnectionChangeListener());
		}
		
		/**
		 * Put description here
		 * 
		 * @param connection
		 */
		public ClientGameeventProvider(Connection connection) {
			this();
			setConnection(connection);
		}
		
		// <--- ClientGameeventService --->
		
		@Override
		public void loadGameeventlog(List<String> gelog) {
			logger.info("Loading Gameevent-Log: " + gelog);
			getGameeventHistory().clear();
			getGameeventHistory().addAll(gelog);
		}
		
		@Override
		public void sendGameevent(String playername, String message) {
			if (getConnection() != null && getConnection().isConnected()) {
				GameeventPacket gepacket = new GameeventPacketImpl(message);
				logger.info("Sending ChatPacket: " + gepacket);
				getConnection().sendPacket(gepacket);
			} else {
				logger.warning("Ereignislog-Nachricht konnte nicht gesendet werden, da keine aktive Netzwerkverbindung besteht.");
			}
		}
		
		@Override
		public ObservableList<String> getGameeventHistory() {
			return gameeventHistory;
		}
		
		@Override
		public final Connection getConnection() {
			return this.connectionProperty().get();
		}
		
		@Override
		public final void setConnection(final Connection connection) {
			this.connectionProperty().set(connection);
		}
		
		// <--- ConnectionListener --->
		
		@Override
		public void handle(ConnectionEvent connectionEvent) {
			if (connectionEvent instanceof PacketReceivedEvent) {
				PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
				Packet packet = packetEvent.getPacket();
				if (packet instanceof GameeventPacket) {
					GameeventPacket gepacket = (GameeventPacket) packet;
					logger.info("Incoming GameEventPacket: " + gepacket);
					
					// Perform change on JavaFX Application Thread as this service is most likely being used by UI Components
					Platform.runLater(() -> getGameeventHistory().add(gepacket.getMessage()));
				}
			}
		}
		
		// Event Handlers
		
		/**
		 * Put description here
		 * 
		 * @param observable
		 * @param oldValue
		 * @param newValue
		 */
		public void onConnectionChanged(ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue) {
			
			logger.info("Connection changed from " + oldValue + " to " + newValue);
			
			if (oldValue != null) {
				oldValue.removeListener(this);
			}
			
			if (newValue != null) {
				newValue.addListener(this);
			}
			
		}
		
		// Getter & Setter
		
		public final ObjectProperty<Connection> connectionProperty() {
			return this.connection;
		}
		
		public ChangeListener<Connection> getOnConnectionChangeListener() {
			return onConnectionChangeListener;
		}

}
