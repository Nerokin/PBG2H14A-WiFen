package wifen.commons.network.packets.impl;

import java.util.UUID;

import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.MediumRemovedPacket;

public class MediumRemovedPacketImpl extends PacketImpl implements MediumRemovedPacket {

	private static final long serialVersionUID = 633000645626562439L;
	private UUID mediumId;
	
	public MediumRemovedPacketImpl(UUID mid) {
		this.setMediumId(mid);
	}

	public UUID getMarkderId() {
		return mediumId;
	}

	public void setMediumId(UUID mId) {
		this.mediumId = mId;
	}

	@Override
	public UUID getMediumId() {
		// TODO Auto-generated method stub
		return mediumId;
	}

}
