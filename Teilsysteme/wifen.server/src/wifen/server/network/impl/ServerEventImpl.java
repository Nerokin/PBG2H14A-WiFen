package wifen.server.network.impl;

import wifen.commons.impl.CustomEventImpl;
import wifen.server.network.Server;
import wifen.server.network.ServerEvent;
/**
 * @author Marius Vogelsang, David Joachim
 *
 */
public class ServerEventImpl extends CustomEventImpl implements ServerEvent {

	protected Server server;
	
	public ServerEventImpl(Server server)
	{
		this.server = server;
	}
	
	@Override
	public Server getSource() {
		// TODO Auto-generated method stub
		return server;
	}

}