package wifen.commons.network.packets.impl;

import wifen.commons.MarkerModel;
import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.MarkerPacket;

public class MarkerPacketImpl extends PacketImpl implements MarkerPacket {

	// Class Constants
	
	private static final long serialVersionUID = 5725279320770502512L;
	
	// Attributes
	
	private final MarkerModel markerModel;
	
	// Constructor(s)
	
	public MarkerPacketImpl(MarkerModel markerModel) {
		super();
		this.markerModel = markerModel;
	}
	
	// Methods
	
	public String toString() {
		return getClass().getSimpleName() + "{"
				+ "markerModel: " + getMarkerModel()
				+ "}";
	}
	
	// Getter & Setter

	@Override
	public MarkerModel getMarkerModel() {
		return markerModel;
	}

}
