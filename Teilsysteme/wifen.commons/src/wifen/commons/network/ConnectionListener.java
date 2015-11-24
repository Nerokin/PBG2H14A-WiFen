package wifen.commons.network;

import java.lang.reflect.Type;

/**
 * Super interface for all entities interested in
 * events fired from {@linkplain Connection}s.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface ConnectionListener {
	
	/**
	 * Called on the implementing class when the specified
	 * type of event is fired on the connection to which
	 * this listener has been added.
	 * 
	 * @param connectionEvent The event which has been fired
	 */
	public void handle(ConnectionEvent connectionEvent);


}
