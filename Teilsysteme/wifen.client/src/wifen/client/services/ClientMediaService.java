package wifen.client.services;

import wifen.commons.network.Connection;

public interface ClientMediaService 
{
	public Connection getConnection();
	public void setConnection(Connection newConnection);
}
