package wifen.commons.network.packets;

import wifen.commons.network.Packet;

/**
 * Describes a network packet wrapping a simple
 * chat message.
 * 
 * @author Konstantin Schaper
 * 
 */
public interface ChatPacket extends Packet {
	
	public String getSourceName();
	public String getMessage();

}
