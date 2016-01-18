package wifen.commons.network.packets.impl;

import java.util.UUID;

import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.MarkerRemovedPacket;

public class MarkerRemovedPacketImpl extends PacketImpl implements MarkerRemovedPacket{


	private static final long serialVersionUID = 633000645626562439L;
	private UUID markderId;
	
	public MarkerRemovedPacketImpl(UUID mid){
		this.setMarkderId(mid);
	}

	public UUID getMarkderId() {
		return markderId;
	}

	public void setMarkderId(UUID markderId) {
		this.markderId = markderId;
	}
}
