package wifen.commons.network;

/**
 * Represents a network connection.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface Connection {
	
	/**
	 * Sends a packet to the destination affiliated with this
	 * connection.
	 * 
	 * @param packet The packet to be sent.
	 * @return Whether the packet has been successfully sent.
	 */
	public boolean sendPacket(Packet packet);
	
	/**
	 * Registers a listener for all upcoming {@linkplain ConnectionEvent}s 
	 * fired by this Connection.
	 * 
	 * @param listener The listener to add.
	 * @return Whether the listener has been added.
	 */
	public boolean addListener(ConnectionListener listener);

}