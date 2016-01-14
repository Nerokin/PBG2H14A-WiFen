package wifen.client.services;

import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import wifen.client.ui.controllers.ChatController;
import wifen.commons.network.Connection;

/**
 * Manages a single {@linkplain ChatController}.
 * 
 * @author Konstantin Schaper
 *
 */
public interface ClientChatService {
	
	
	public ObservableList<String> showRole(StringProperty playerRole);
	public void loadChatlog(List<String> chatLog);
	public void sendMessage(String playerName, String message);
	public void showOtherPlayer();
	
	public ObservableList<String> getChatHistory();
	public Connection getConnection();
	public void setConnection(Connection newConnection);
}


