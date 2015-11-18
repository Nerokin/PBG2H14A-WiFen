package wifen.server.network.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.impl.ConnectionImpl;
import wifen.server.network.Server;
import wifen.server.network.ServerEvent;
import wifen.server.network.ServerListener;

public class ServerImpl implements Server, ConnectionListener {
	
	// Attributes
	private final ServerSocket serverSocket;
	private final List<ServerListener> listeners = new ArrayList<ServerListener>();
	private final List<ConnectionListener> connectionListeners = new ArrayList<ConnectionListener>();
	private final List<Connection> connectionList = new ArrayList<Connection>();
	// TODO
	
	// Constructor(s)
	public ServerImpl(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}
	
	// Methods

	@Override
	public void acceptConnections() throws IOException {
		Socket clientSocket;
		while ((clientSocket = serverSocket.accept()) != null) {
			Connection conn = new ConnectionImpl(clientSocket);
			new Thread(conn::readPackets);
			conn.addListener(this);
			connectionList.add(conn);
		}
	}

	@Override
	public void broadcastPacket(Packet packet) {
		for (Connection connection : connectionList) {
			connection.sendPacket(packet);
		}
	}
	
	@Override
	public boolean addListener(ServerListener listener) {
		return listeners.add(listener);
	}
	public boolean addListener(ConnectionListener listener){
		return connectionListeners.add(listener);
	}
	
	@Override
	public boolean shutdown() {
		try {
			serverSocket.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Getters & Setters
	
	// TODO
	
	@Override
	public List<Connection> getConnections() {
		return connectionList;
	}

	@Override
	public void handle(ConnectionEvent event) {
		fireEvent(event);
	}
	protected final void fireEvent(ServerEvent event){
		for (ServerListener serverListener : listeners) {
			serverListener.handle(event);
			if (event.isConsumed()) break;
		}
	}
	protected final void fireEvent(ConnectionEvent event){
		for (ConnectionListener connectionListener : connectionListeners) {
			connectionListener.handle(event);
			if (event.isConsumed()) break;
		}
	}
}
