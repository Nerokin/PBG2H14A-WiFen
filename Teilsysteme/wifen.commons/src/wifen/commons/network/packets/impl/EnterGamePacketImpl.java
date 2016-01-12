package wifen.commons.network.packets.impl;

import java.net.InetAddress;

import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.EnterGamePacket;

public class EnterGamePacketImpl extends PacketImpl implements EnterGamePacket{

	private static final long serialVersionUID = 1L;
	private final String name;

	
	public EnterGamePacketImpl(String name){
		this.name = name;

	}
	
	@Override
	public String getName() {		
		return name;
	}

}
