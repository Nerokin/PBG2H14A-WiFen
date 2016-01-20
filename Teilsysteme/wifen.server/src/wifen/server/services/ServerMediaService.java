package wifen.server.services;

import javax.imageio.spi.RegisterableService;

import wifen.server.network.Server;

public interface ServerMediaService extends RegisterableService
{
	public Server getServer();
	public void setServer(Server newServer);
}
	