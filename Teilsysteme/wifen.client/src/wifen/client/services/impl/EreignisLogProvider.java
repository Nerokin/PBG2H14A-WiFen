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

/**
 * @author Fabian Hitziger
 *
 */
public class EreignisLogProvider implements EreignisLogService, ConnectionListener{

	private final Logger logger =  Logger.getLogger(EreignisLogProvider.class.getName());
	
	private final ObjectProperty<Connection> connection = new SimpleObjectProperty<>();
	private final ObservableList<String> logHistory = FXCollections.observableArrayList();
	
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showRole() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadChatlog(List<String> chatLog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ObservableList<String> getChatHistory() {
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
