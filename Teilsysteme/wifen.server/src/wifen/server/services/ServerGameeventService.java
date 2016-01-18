package wifen.server.services;

import javax.imageio.spi.RegisterableService;

import wifen.commons.network.ConnectionListener;
import wifen.server.network.Server;

/**
 * Manages Gameevents incoming to the specified server and broadcasts them to all associated connections.
 * 
 * @author Steffen Müller
 */
public interface ServerGameeventService extends ConnectionListener, RegisterableService {
	
	public Server getServer();
	public void setServer(Server newServer);
	public void fireEvent(String eventMessage);
}
