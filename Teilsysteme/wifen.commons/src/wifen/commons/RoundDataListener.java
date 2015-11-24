/**
 * 
 */
package wifen.commons;

import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.RoundDataRecivedEvent;

/**
 * @author Oliver Bardong
 *
 */
public interface RoundDataListener <T extends RoundDataRecivedEvent> {
	
	/**
	 * Called on the implementing class when the specified
	 * type of event is fired on the connection to which
	 * this listener has been added.
	 * 
	 * @param event The event which has been fired
	 */
	public void handle(T event);

}
