package wifen.commons.network.impl;

import wifen.commons.network.Connection;
import wifen.commons.network.Packet;

/**
 * General implementation of the {@linkplain Packet} interface.<br>
 * Should be subclassed to specify the information sent.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public abstract class PacketImpl implements Packet {
	
	// Class Constants

	private static final long serialVersionUID = -8734483796691512001L;
	
	// Attributes
	
	private Connection source;
	
	// Constructor(s)
	
	public PacketImpl() {
		super();
	}
	
	// Getters & Setters

	@Override
	public final Connection getSource() {
		return source;
	}

	@Override
	public void setSource(Connection source) {
		this.source = source;
	}

}
