package wifen.server.network;

import java.io.IOException;
import java.util.List;

import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;

/**
 * Network service interface for handling incoming connections
 * and forwarding events to registered listeners.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface Server {
	
	/**
	 * Starts waiting for incoming connection attempts.<br>
	 * Does block the calling thread until the server is
	 * shutdown or any exception occurs. 
	 * 
	 */
	public void acceptConnections() throws IOException;
	
	/**
	 * Attempts to close all registered {@linkplain Connection}s and stop
	 * accepting connections.<br>
	 * The {@linkplain #acceptConnections()} method can still be
	 * called to start the server functionality again.
	 * 
	 * @return Whether or not the server has been shut down correctly.
	 */
	public boolean shutdown();
	
	/**
	 * Sends the given packet through all connections.
	 * 
	 * @param packet The packet to be sent
	 * @throws IllegalArgumentException When packet is null
	 */
	public void broadcastPacket(Packet packet) throws IllegalArgumentException;
	
	/**
	 * @return All currently registered connections
	 */
	public List<Connection> getConnections();
	
	/**
	 * Registers a listener for all upcoming {@linkplain ServerEvent}s 
	 * fired by this Server.
	 * 
	 * @param listener The listener to add
	 * @return Whether the listener has been added
	 */
	public boolean addListener(ServerListener listener);
	
	/**
	 * Registers a listener for all upcoming {@linkplain ConnectionEvent}s
	 * that occur on any connection maintained by this server instance
	 * 
	 * @param listener to add
	 * @return whether the listener has been successfully added
	 */
	public boolean addListener(ConnectionListener listener);
	
	/**
	 * Unregisters a previously registered listener.
	 * 
	 * @param listener The listener to remove
	 * @return Whether the listener was registered and has been successfully unregistered
	 */
	public boolean removeListener(ServerListener listener);
	
	/**
	 * Unregisters a previously registered listener.
	 * 
	 * @param listener The listener to remove
	 * @return Whether the listener was registered and has been successfully unregistered
	 */
	public boolean removeListener(ConnectionListener listener);
}
