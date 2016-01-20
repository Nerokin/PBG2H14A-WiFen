package wifen.client.services;

import wifen.commons.Medium;
import wifen.commons.network.Connection;

public interface ClientMediaService 
{
	public Connection getConnection();
	public void setConnection(Connection newConnection);
	public void trySendFile(String playerName, Medium medium);
}
