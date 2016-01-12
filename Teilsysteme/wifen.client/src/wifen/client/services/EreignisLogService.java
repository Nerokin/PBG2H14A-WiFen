package wifen.client.services;

import java.util.List;

import javafx.collections.ObservableList;
import wifen.commons.network.Connection;

public interface EreignisLogService {
	
	public void showRole();
	public void loadChatlog(List<String> chatLog);
	public void sendMessage(String message);
	
	public ObservableList<String> getChatHistory();
	public Connection getConnection();
	public void setConnection(Connection newConnection);
}
