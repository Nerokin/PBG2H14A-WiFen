package wifen.commons.network.packets;

import wifen.commons.MediumModel;
import wifen.commons.network.Packet;

public interface MediumPacket extends Packet {
	
	public MediumModel getMediumModel();

}