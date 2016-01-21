package wifen.commons.network.packets.impl;

import wifen.commons.MediumModel;
import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.MediumPacket;

public class MediumPacketImpl extends PacketImpl implements MediumPacket {
	
	// Class Constants
	
	private static final long serialVersionUID = 1L;
	
	// Attributes
	
	MediumModel medium;
	
	// Constructor(s)
	
	public MediumPacketImpl(MediumModel m) {
		medium = m;
	}
	
	// Methods
	
	@Override
	public MediumModel getMediumModel() {
		return medium;
	}
}
