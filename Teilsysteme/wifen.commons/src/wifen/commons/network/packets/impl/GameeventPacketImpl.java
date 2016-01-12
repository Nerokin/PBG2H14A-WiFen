package wifen.commons.network.packets.impl;

import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.GameeventPacket;

public class GameeventPacketImpl extends PacketImpl implements GameeventPacket {

	// Class Attributes
	
	private static final long serialVersionUID = 1L;
	
	// Attributes
	
	private final String sourceName;
	private final String message;
	
	// Constructor(s)
	
	public GameeventPacketImpl(String sourceName, String message) {
		super();
		this.sourceName = sourceName;
		this.message = message;
	}
	
	// Methods
	
	@Override
	public String toString() {
		return "{" +  getSourceName() + ": " + getMessage() + "}";
	}
	
	// Getter & Setter

	@Override
	public String getSourceName() {
		return sourceName;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
