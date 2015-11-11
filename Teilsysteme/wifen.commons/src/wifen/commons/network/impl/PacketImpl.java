package wifen.commons.network.impl;

import wifen.commons.network.Connection;
import wifen.commons.network.Packet;

/**
 * 
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public abstract class PacketImpl implements Packet {
	
	// Attributes
	
	private final Connection source;
	
	// Constructor(s)
	
	public PacketImpl(Connection source) {
		this.source = source;
	}
	
	// Getters & Setters

	@Override
	public Connection getSource() {
		return source;
	}

}