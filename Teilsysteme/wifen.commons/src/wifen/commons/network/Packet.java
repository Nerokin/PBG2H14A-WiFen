package wifen.commons.network;

/**
 * Super interface for all information containers sent 
 * and received by {@linkplain Connection}s.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface Packet {
	
	/**
	 * The source is defined by the receiving connection
	 * and does not change.
	 * 
	 * @return The connection this packet originated from
	 */
	public Connection getSource();

}
