package wifen.commons.network.packets;

import wifen.commons.network.Packet;

public interface MediaRequestPacket extends Packet 
{	
	public String getSourceName();
	public String getFileName();
	
	public int getRequestType();
}
