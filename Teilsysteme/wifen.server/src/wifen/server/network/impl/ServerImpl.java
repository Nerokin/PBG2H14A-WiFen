package wifen.server.network.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
/**
 * @author Marius Vogelsang, David Joachim
 *
 */
public class ServerImpl implements Server, ConnectionListener {
	
	//Constants
	
	private static final Logger logger = Logger.getLogger(ServerImpl.class.getName());
	
	// Attributes
	private final ServerSocket serverSocket;
	private final List<ServerListener> listeners = new ArrayList<ServerListener>();
	private final List<ConnectionListener> connectionListeners = new ArrayList<ConnectionListener>();
	private final List<Connection> connectionList = new ArrayList<Connection>();
	// TODO
	
	// Constructor(s)
	public ServerImpl(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		logger.info("Server has been successfully started.");
	}
	
	// Methods

	@Override
	public void acceptConnections() throws IOException {
		Socket clientSocket;
		while ((clientSocket = getServerSocket().accept()) != null) {
			Connection conn = new ConnectionImpl(clientSocket);
			new Thread(conn::readPackets).start();
			conn.addListener(this);
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
	
	protected final void fireEvent(ServerEvent event){
		for (ServerListener serverListener : getListeners()) {
			serverListener.handle(event);
			if (event.isConsumed()) break;
		}
	}
	
	protected final void fireEvent(ConnectionEvent event){
		logger.info("A connection event is being fired on " + getConnectionListeners().size() + " listeners.");
		for (ConnectionListener connectionListener : getConnectionListeners()) {
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
		return getConnectionListeners().add(listener);
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

	public List<ServerListener> getListeners() {
		return listeners;
	}

	public List<ConnectionListener> getConnectionListeners() {
		return connectionListeners;
	}
}
