package wifen.commons.network.events.impl;

import wifen.commons.network.Connection;
import wifen.commons.network.Packet;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.impl.ConnectionEventImpl;

public class PacketReceivedEventImpl extends ConnectionEventImpl implements PacketReceivedEvent{

	private Packet packet;
	
	public PacketReceivedEventImpl(Packet packet, Connection connection) {
		super(connection);
		this.packet = packet;
	}

	@Override
	public Packet getPacket() {
		return packet;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + getPacket().toString() + "}";
	}

}
