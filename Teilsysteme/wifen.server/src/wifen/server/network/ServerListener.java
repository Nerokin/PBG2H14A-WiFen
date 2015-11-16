package wifen.server.network;

/**
 * Super interface for all entities interested in
 * events fired from {@linkplain Server}s.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface ServerListener<T extends ServerEvent> {
	
	/**
	 * Called on the implementing class when the specified
	 * type of event is fired on the server to which
	 * this listener has been added.
	 * 
	 * @param event The event which has been fired
	 */
	public void handle(T event);

}
