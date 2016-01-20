package wifen.commons.network.packets;

import wifen.commons.Medium;
import wifen.commons.network.Packet;

public interface MediaDataPacket extends Packet 
{
	public String getSourceName();
	public Medium getMedium();
}
