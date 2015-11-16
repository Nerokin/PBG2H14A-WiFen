package wifen.commons;

/**
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface CustomEvent {
	
	/**
	 * @return Whether this event has been marked as consumed or not
	 */
	public boolean isConsumed();
	
	/**
	 * Marks this event as consumed.<br>
	 * This does not actually perform any
	 * functionality itself but informs the
	 * source of the event that it should not 
	 * forward the event to any more listeners.
	 */
	public void consume();

}
