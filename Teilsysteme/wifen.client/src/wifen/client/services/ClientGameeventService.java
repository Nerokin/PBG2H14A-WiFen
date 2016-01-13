package wifen.client.services;

import java.util.List;

import javafx.collections.ObservableList;
import wifen.commons.network.Connection;

/**
 * Manages a single (GameeventController?).
 * 
 * @author Steffen Müller
 *
 */

public interface ClientGameeventService {
	
	public void loadGameeventlog(List<String> gameeventlog);
	public void sendGameevent(String playername, String message);
	
	public ObservableList<String> getGameeventHistory();
	public Connection getConnection();
	public void setConnection(Connection newConnection);
}
