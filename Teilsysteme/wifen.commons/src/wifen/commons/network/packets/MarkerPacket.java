package wifen.commons.network.packets;

import wifen.commons.MarkerModel;
import wifen.commons.network.Packet;

public interface MarkerPacket extends Packet {
	
	public MarkerModel getMarkerModel();

}
