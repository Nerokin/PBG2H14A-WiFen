package wifen.commons.network.events.impl;

import wifen.commons.network.Connection;
import wifen.commons.network.events.ConnectionClosedEvent;
import wifen.commons.network.impl.ConnectionEventImpl;

public class ConnectionClosedEventImpl extends ConnectionEventImpl implements ConnectionClosedEvent {

	public ConnectionClosedEventImpl(Connection connection) {
		super(connection);
	}

}
