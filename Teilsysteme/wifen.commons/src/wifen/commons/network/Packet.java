package wifen.commons.network;

import java.io.Serializable;

/**
 * Super interface for all information containers sent 
 * and received by {@linkplain Connection}s.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface Packet extends Serializable {
	
	/**
	 * The source is defined by the receiving connection
	 * and does not change.
	 * 
	 * @return The connection this packet originated from
	 */
	public Connection getSource();

}
