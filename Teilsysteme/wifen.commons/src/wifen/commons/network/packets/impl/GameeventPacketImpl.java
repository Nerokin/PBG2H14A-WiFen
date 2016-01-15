package wifen.commons.network.packets.impl;

import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.GameeventPacket;

public class GameeventPacketImpl extends PacketImpl implements GameeventPacket {

	// Class Attributes
	
	private static final long serialVersionUID = 1L;
	
	// Attributes
	
	private final String message;
	
	// Constructor(s)
	
	public GameeventPacketImpl(String message) {
		super();
		this.message = message;
	}
	
	// Methods
	
	@Override
	public String toString() {
		return "{" + getMessage() + "}";
	}
	
	// Getter & Setter

	@Override
	public String getMessage() {
		return message;
	}

}
