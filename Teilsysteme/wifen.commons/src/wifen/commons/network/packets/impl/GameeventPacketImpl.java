package wifen.commons.network.packets.impl;

import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.GameeventPacket;

public class GameeventPacketImpl extends PacketImpl implements GameeventPacket {

	// Class Attributes
	
	private static final long serialVersionUID = 1L;
	
	// Attributes
	
	private final String message;
	private final String source;
	
	// Constructor(s)
	
	public GameeventPacketImpl(String source, String message) {
		super();
		this.message = message;
		this.source = source;
	}
	
	// Methods
	
	@Override
	public String toString() {
		return "" + getSourceName() + ": "+ getMessage() + "";
	}
	
	// Getter & Setter

	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public String getSourceName() {
		return source;
	}

}
