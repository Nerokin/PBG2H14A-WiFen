package wifen.client.services;

import java.util.List;

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
	
	
	public void showRole();
	public void loadChatlog(List<String> chatLog);
	public void sendMessage(String playerName, String message);
	
	public ObservableList<String> getChatHistory();
	public Connection getConnection();
	public void setConnection(Connection newConnection);
}
