package wifen.client.services;

import java.util.List;

import javafx.collections.ObservableList;
import wifen.commons.network.Connection;

public interface EreignisLogService {
	
	public void showRole();
	public void loadEreignisLog(List<String> ereignisLog);
	public void sendMessage(String message);
	
	public ObservableList<String> getHistory();
	public Connection getConnection();
	public void setConnection(Connection newConnection);
}
