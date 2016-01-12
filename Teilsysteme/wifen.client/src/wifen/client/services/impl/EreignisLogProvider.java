/**
 * 
 */
package wifen.client.services.impl;

import java.util.List;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wifen.client.services.EreignisLogService;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.packets.ChatPacket;
import wifen.commons.network.packets.impl.ChatPacketImpl;

/**
 * @author Fabian Hitziger
 *
 */
public class EreignisLogProvider implements EreignisLogService, ConnectionListener{

	private final Logger logger =  Logger.getLogger(EreignisLogProvider.class.getName());
	
	private final ObjectProperty<Connection> connection = new SimpleObjectProperty<>();
	private final ObservableList<String> logHistory = FXCollections.observableArrayList();
	// Attributes

	
	public EreignisLogProvider(Connection connection) {
		setConnection(connection);
	} 
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showRole() {
		throw new UnsupportedOperationException("Not yet implemented!"); // TODO: Implement
		
	}

	@Override
	public void loadEreignisLog(List<String> ereignisLog) {
		logger.info("Loading EreignisLog: " + ereignisLog);
		getHistory().clear();
		getHistory().addAll(ereignisLog);
		
	}

	@Override
	public void sendMessage(String message) {
		if (getConnection() != null && getConnection().isConnected()) {
			ChatPacket chatPacket = new ChatPacketImpl("" , message);
			logger.info("Sending ChatPacket: " + chatPacket);
			getConnection().sendPacket(chatPacket);
		} else {
			logger.warning("Chat-Nachricht konnte nicht gesendet werden, da keine aktive Netzwerkverbindung besteht.");
		}
		
	}

	@Override
	public ObservableList<String> getHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnection(Connection newConnection) {
		// TODO Auto-generated method stub
		
	}

}
