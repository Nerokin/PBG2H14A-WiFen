package wifen.server.services;

import wifen.commons.network.ConnectionListener;
import wifen.server.network.Server;

public interface ServerMediaService extends ConnectionListener
{
	public Server getServer();
	public void setServer(Server newServer);
}
	