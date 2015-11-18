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
	
	@Override
	public Connection getSource() {
		// TODO Auto-generated method stub
		return connection;
	}

}
