package wifen.commons.network.events.impl;

import wifen.commons.network.Connection;
import wifen.commons.network.events.ConnectionClosedEvent;
import wifen.commons.network.impl.ConnectionEventImpl;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class ConnectionClosedEventImpl extends ConnectionEventImpl implements ConnectionClosedEvent {

	/**
	 * Put description here
	 * 
	 * @param connection
	 */
	public ConnectionClosedEventImpl(Connection connection) {
		super(connection);
	}

}
