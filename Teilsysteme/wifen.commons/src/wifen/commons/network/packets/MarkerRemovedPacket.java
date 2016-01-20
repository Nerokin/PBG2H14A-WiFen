package wifen.commons.network.packets;

import java.util.UUID;

import wifen.commons.network.Packet;

public interface MarkerRemovedPacket extends Packet{

	public UUID getMarkerId();
}
