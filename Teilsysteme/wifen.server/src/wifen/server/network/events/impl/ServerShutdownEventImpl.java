package wifen.server.network.events.impl;

import wifen.server.network.Server;
import wifen.server.network.events.ServerShutdownEvent;
import wifen.server.network.events.ServerStartedEvent;
import wifen.server.network.impl.ServerEventImpl;

/**
 * Implementation of the {@linkplain ServerShutdownEvent} interface.
 * 
 * @author Konstantin Schaper
 */
public class ServerShutdownEventImpl extends ServerEventImpl implements ServerStartedEvent {

	public ServerShutdownEventImpl(Server server) {
		super(server);
	}

}
