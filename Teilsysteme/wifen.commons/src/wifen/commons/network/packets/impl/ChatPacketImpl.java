package wifen.commons.network.packets.impl;

import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.ChatPacket;

/**
 * Implementation of the {@linkplain ChatPacket} interface.
 * 
 * @author Konstantin Schaper
 *
 */
public class ChatPacketImpl extends PacketImpl implements ChatPacket {
	
	// Class Constants
	
	private static final long serialVersionUID = 5677973468806379785L;
	
	// Attributes
	
	private final String sourceName;
	private final String message;
	
	// Constructor(s)
	
	public ChatPacketImpl(String sourceName, String message) {
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
