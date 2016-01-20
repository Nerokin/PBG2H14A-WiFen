package wifen.commons.network.packets;

import wifen.commons.network.Packet;

public interface GameeventPacket extends Packet {
	
	public String getMessage();
	public String getSourceName();

}
