package wifen.client.services.impl;

import java.util.List;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wifen.client.services.ClientChatService;
import wifen.client.ui.controllers.ChatController;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.ChatPacket;
import wifen.commons.network.packets.impl.ChatPacketImpl;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class ClientChatProvider implements ClientChatService, ConnectionListener {
	
	// Class Constants
	
	private final Logger logger = Logger.getLogger(ClientChatProvider.class.getName());
	
	// Properties
	
	private final ObjectProperty<Connection> connection = new SimpleObjectProperty<>();
	private final ObservableList<String> chatHistory = FXCollections.observableArrayList();
	private final ObjectProperty<ChatController> chatDisplay = new SimpleObjectProperty<>();
	
	// Attributes
	
	private final ChangeListener<Connection> onConnectionChangeListener = this::onConnectionChanged;
	
	// Constructor(s)
	
	public ClientChatProvider() {
		// Update the chat controller when the network connection changes
		connectionProperty().addListener(getOnConnectionChangeListener());
	}
	
	/**
	 * Put description here
	 * 
	 * @param connection
	 */
	public ClientChatProvider(Connection connection) {
		this();
		setConnection(connection);
	}
	
	// <--- ClientChatService --->

	@Override
	public void showRole() {
		throw new UnsupportedOperationException("Not yet implemented!"); // TODO: Implement
	}

	@Override
	public void loadChatlog(List<String> chatLog) {
		logger.info("Loading ChatLog: " + chatLog);
		getChatHistory().clear();
		getChatHistory().addAll(chatLog);
	}
	
	@Override
	public void sendMessage(String playerName, String message) {
		if (getConnection() != null && getConnection().isConnected()) {
			ChatPacket chatPacket = new ChatPacketImpl(playerName, message);
			logger.info("Sending ChatPacket: " + chatPacket);
			getConnection().sendPacket(chatPacket);
		} else {
			logger.warning("Chat-Nachricht konnte nicht gesendet werden, da keine aktive Netzwerkverbindung besteht.");
		}
	}
	
	@Override
	public final ChatController getChatDisplay() {
		return this.chatDisplayProperty().get();
	}
	
	@Override
	public final void setChatDisplay(final ChatController chatDisplay) {
		this.chatDisplayProperty().set(chatDisplay);
	}
	
	@Override
	public ObservableList<String> getChatHistory() {
		return chatHistory;
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
			if (packet instanceof ChatPacket) {
				ChatPacket chatPacket = (ChatPacket) packet;
				logger.info("Incoming ChatPacket: " + chatPacket);
				getChatHistory().add(chatPacket.getSourceName() + ": " + chatPacket.getMessage());
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

	public final ObjectProperty<ChatController> chatDisplayProperty() {
		return this.chatDisplay;
	}

}
