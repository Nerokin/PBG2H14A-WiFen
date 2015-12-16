package wifen.server.network.events.impl;

import wifen.server.network.Server;
import wifen.server.network.events.ServerStartedEvent;
import wifen.server.network.impl.ServerEventImpl;

/**
 * Implementation of the {@linkplain ServerStartedEvent} interface.
 * 
 * @author Konstantin Schaper
 */
public class ServerStartedEventImpl extends ServerEventImpl implements ServerStartedEvent {

	public ServerStartedEventImpl(Server server) {
		super(server);
	}

}
