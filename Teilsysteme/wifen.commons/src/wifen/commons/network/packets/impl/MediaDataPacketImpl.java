package wifen.commons.network.packets.impl;

import wifen.commons.Medium;
import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.MediaDataPacket;

public class MediaDataPacketImpl extends PacketImpl implements MediaDataPacket
{
	private static final long serialVersionUID = -8597096663167790919L;

	private String sourceName;
	private Medium medium;
	
	public MediaDataPacketImpl(String sourceName, Medium medium)
	{
		this.sourceName = sourceName;
		this.medium = medium;
	}
	
	@Override
	public String getSourceName()
	{
		return sourceName;
	}

	@Override
	public Medium getMedium()
	{
		return medium;
	}

	@Override
	public String toString()
	{
		return "{" +  getSourceName() + ": " + medium + "}";
	}
}
