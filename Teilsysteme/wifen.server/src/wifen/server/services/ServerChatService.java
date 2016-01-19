package wifen.server.services;

import javax.imageio.spi.RegisterableService;

import wifen.server.network.Server;

/**
 * Manages all chat packets incoming at the specified server
 * and makes the same server broadcast the packets to all
 * established connections.
 * 
 * @author KonstantinSchaper
 *
 */
public interface ServerChatService extends RegisterableService {
	
	public Server getServer();
	public void setServer(Server newServer);

}
