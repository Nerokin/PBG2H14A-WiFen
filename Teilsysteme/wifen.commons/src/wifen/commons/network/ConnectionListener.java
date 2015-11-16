package wifen.commons.network;

/**
 * Super interface for all entities interested in
 * events fired from {@linkplain Connection}s.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface ConnectionListener<T extends ConnectionEvent> {
	
	/**
	 * Called on the implementing class when the specified
	 * type of event is fired on the connection to which
	 * this listener has been added.
	 * 
	 * @param event The event which has been fired
	 */
	public void handle(T event);

}
