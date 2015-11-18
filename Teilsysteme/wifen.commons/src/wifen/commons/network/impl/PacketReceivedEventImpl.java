package wifen.commons.network.impl;

import wifen.commons.impl.CustomEventImpl;
import wifen.commons.network.Connection;
import wifen.commons.network.Packet;
import wifen.commons.network.PacketReceivedEvent;

public class PacketReceivedEventImpl extends ConnectionEventImpl implements PacketReceivedEvent{

	private Packet packet;
	
	public PacketReceivedEventImpl(Packet packet, Connection connection) {
		this.packet = packet;
		this.connection = connection;
	}

	@Override
	public Packet getPacket() {
		return packet;
	}

}
