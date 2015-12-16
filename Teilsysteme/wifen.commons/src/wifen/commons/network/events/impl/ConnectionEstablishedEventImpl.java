package wifen.commons.network.events.impl;

import wifen.commons.network.Connection;
import wifen.commons.network.events.ConnectionEstablishedEvent;
import wifen.commons.network.impl.ConnectionEventImpl;

public class ConnectionEstablishedEventImpl extends ConnectionEventImpl implements ConnectionEstablishedEvent {

	public ConnectionEstablishedEventImpl(Connection connection) {
		super(connection);
	}

}
