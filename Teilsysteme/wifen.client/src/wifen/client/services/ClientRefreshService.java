package wifen.client.services;

import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;

public interface ClientRefreshService{
	public Connection getConnection();
	public void setConnection (Connection newConnection);;
	public void refresh();
	public void handle(ConnectionEvent connectionEvent);
}
