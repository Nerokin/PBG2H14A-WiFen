package wifen.commons.network.events.impl;

import wifen.commons.network.Connection;
import wifen.commons.network.events.ConnectionEstablishedEvent;
import wifen.commons.network.impl.ConnectionEventImpl;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class ConnectionEstablishedEventImpl extends ConnectionEventImpl implements ConnectionEstablishedEvent {

	/**
	 * Put description here
	 * 
	 * @param connection
	 */
	public ConnectionEstablishedEventImpl(Connection connection) {
		super(connection);
	}

}
