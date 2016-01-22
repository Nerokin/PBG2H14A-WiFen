package wifen.client.services;

import java.util.List;

import wifen.commons.Medium;
import wifen.commons.network.Connection;

public interface ClientMediaService
{
	public Connection getConnection();
	public void loadMedia(List<Medium> media);
	public void setConnection(Connection newConnection);
	public void trySendFile(String playerName, Medium medium);
}
