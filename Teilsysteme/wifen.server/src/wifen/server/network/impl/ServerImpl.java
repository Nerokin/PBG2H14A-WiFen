package wifen.server.network.impl;

import java.util.List;

import wifen.commons.network.Connection;
import wifen.commons.network.Packet;
import wifen.server.network.Server;
import wifen.server.network.ServerListener;

public class ServerImpl implements Server {
	
	// Attributes
	
	// TODO
	
	// Constructor(s)
	
	public ServerImpl(int port) {
		// TODO Auto-generated constructor stub
	}
	
	// Methods

	@Override
	public void acceptConnections() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broadcastPacket(Packet packet) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean addListener(ServerListener<?> listener) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean shutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	// Getters & Setters
	
	// TODO
	
	@Override
	public List<Connection> getConnections() {
		// TODO Auto-generated method stub
		return null;
	}

}
