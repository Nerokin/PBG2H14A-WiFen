package wifen.server.network.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.events.ConnectionClosedEvent;
import wifen.commons.network.impl.ConnectionImpl;
import wifen.server.network.Server;
import wifen.server.network.ServerEvent;
import wifen.server.network.ServerListener;
import wifen.server.network.events.impl.ServerShutdownEventImpl;
import wifen.server.network.events.impl.ServerStartedEventImpl;
/**
 * Put description here
 * 
 * @author Marius Vogelsang
 * @author David Joachim
 * @author Konstantin Schaper
 */
public class ServerImpl implements Server, ConnectionListener {
	
	//Constants	
	private static final Logger logger = Logger.getLogger(ServerImpl.class.getName());
	
	// Attributes
	private final ServerSocket serverSocket;
	private final Set<ServerListener> listeners = new HashSet<ServerListener>();
	private final Set<ConnectionListener> connectionListeners = new HashSet<ConnectionListener>();
	private final List<Connection> connectionList = new ArrayList<Connection>();
	
	// Constructor(s)
	/**
	 * Put description here
	 * 
	 * @param port
	 * @throws IOException
	 */
	public ServerImpl(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		fireEvent(new ServerStartedEventImpl(this));
		logger.info("Server has been successfully started.");
	}
	
	/**
	 * Put description here
	 * 
	 * @param port
	 * @param listeners
	 * @throws IOException
	 */
	public ServerImpl(int port, ServerListener... listeners) throws IOException {
		serverSocket = new ServerSocket(port);
		Arrays.asList(listeners).forEach((listener) -> addListener(listener));
		fireEvent(new ServerStartedEventImpl(this));
		logger.info("Server has been successfully started.");
	}
	
	// Methods
	@Override
	public void acceptConnections() throws IOException {
		Socket clientSocket;
		while ((clientSocket = getServerSocket().accept()) != null) {
			Connection conn = new ConnectionImpl(clientSocket, this);
			new Thread(conn::readPackets).start();
			getConnectionList().add(conn);
			logger.info("A connection has been accepted");
		}
	}

	@Override
	public void broadcastPacket(Packet packet) {
		for (Connection connection : getConnectionList()) {
			connection.sendPacket(packet);
		}
	}
	
	@Override
	public boolean shutdown() {
		try {
			getServerSocket().close();
			fireEvent(new ServerShutdownEventImpl(this));
			return true;
		} catch (IOException e) {
			logger.log(Level.SEVERE, "An exception occurred while closing the server socket", e);
			return false;
		}
	}
	@Override
	public void handle(ConnectionEvent event) {
		logger.info("A connection event occured: " + event);
		if (event instanceof ConnectionClosedEvent) {
			getConnectionList().remove(event.getSource());
		}
		fireEvent(event);
	}
	
	// Event Handling	
	/**
	 * Put description here 
	 * 
	 * @param event
	 */
	protected final void fireEvent(ServerEvent event){
		logger.info("A server event is being fired on " + getListeners().size() + " listeners.");
		for (ServerListener serverListener : new HashSet<>(getListeners())) {
			serverListener.handle(event);
			if (event.isConsumed()) break;
		}
	}
	
	/**
	 * Put description here
	 * 
	 * @param event
	 */
	protected final void fireEvent(ConnectionEvent event){
		logger.info("A connection event is being fired on " + getConnectionListeners().size() + " listeners.");
		for (ConnectionListener connectionListener : new HashSet<>(getConnectionListeners())) {
			connectionListener.handle(event);
			if (event.isConsumed()) break;
		}
	}	
	
	@Override
	public boolean addListener(ServerListener listener) {
		logger.info("ServerListener added");
		return getListeners().add(listener);
	}
	
	@Override
	public boolean addListener(ConnectionListener listener){
		logger.info("ConnectionListener added");
		return getConnectionListeners().add(listener);
	}
	
	@Override
	public boolean removeListener(ServerListener listener) {
		logger.info("ServerListener removed");
		return getListeners().remove(listener);
	}

	@Override
	public boolean removeListener(ConnectionListener listener) {
		logger.info("ConnectionListener removed");
		return getConnectionListeners().remove(listener);
	}
	
	// Getter & Setter
	public List<Connection> getConnectionList() {
		return connectionList;
	}
	
	/**
	 * @return read only list containing all connections
	 */
	@Override
	public List<Connection> getConnections() {
		return Collections.unmodifiableList(getConnectionList());
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public Set<ServerListener> getListeners() {
		return listeners;
	}

	public Set<ConnectionListener> getConnectionListeners() {
		return connectionListeners;
	}
	
}
