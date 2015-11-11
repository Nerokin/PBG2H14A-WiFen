package wifen.server.network;

import wifen.commons.network.Packet;

public interface Server {
	
	public void acceptConnections();
	public void broadcastPacket(Packet packet);

}
