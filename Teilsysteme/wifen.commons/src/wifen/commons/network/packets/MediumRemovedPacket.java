package wifen.commons.network.packets;

import java.util.UUID;

import wifen.commons.network.Packet;

public interface MediumRemovedPacket extends Packet {
	
	public UUID getMediumId();

}
