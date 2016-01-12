package wifen.server.network;

import wifen.commons.CustomEvent;

/**
 * Super interface for every event being fired by {@linkplain Server}s.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface ServerEvent extends CustomEvent {
	
	/**
	 * @return The server this event has been fired on
	 */
	public Server getSource();

}
