package wifen.commons.network.impl;

import wifen.commons.impl.CustomEventImpl;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
/**
 * @author Marius Vogelsang, David Joachim
 *
 */
public class ConnectionEventImpl extends CustomEventImpl implements ConnectionEvent {

	protected Connection connection;
	
	public ConnectionEventImpl(Connection connection)
	{
		this.connection = connection;
	}
	
	@Override
	public Connection getSource() {
		return connection;
	}

}
