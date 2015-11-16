package wifen.commons.network;

import wifen.commons.CustomEvent;

/**
 * Super interface for every event being fired by {@linkplain Connection}s.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface ConnectionEvent extends CustomEvent {
	
	/**
	 * @return The connection this event has been fired on
	 */
	public Connection getSource();

}
