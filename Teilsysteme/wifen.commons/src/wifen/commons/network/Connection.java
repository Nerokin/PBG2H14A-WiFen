package wifen.commons.network;

import java.io.IOException;

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
	 * @param packet The packet to be sent
	 * @return Whether the packet has been successfully sent 
	 */
	public boolean sendPacket(Packet packet);	
	
	/**
	 * Registers a listener for all upcoming {@linkplain ConnectionEvent}s 
	 * fired by this Connection.
	 * 
	 * @param listener The listener to add
	 * @return Whether the listener has been added
	 */
	public boolean addListener(ConnectionListener<?> listener);
	
	/**
	 * Starts reading packets from the sockets input stream.<br>
	 * Blocks until the Connection is closed or an exception occurs.
	 */
	public void readPackets();
	
	/**
	 * Tells the connection to close.
	 * 
	 * @return Whether the connection has been successfully closed
	 */
	public boolean close();

}
