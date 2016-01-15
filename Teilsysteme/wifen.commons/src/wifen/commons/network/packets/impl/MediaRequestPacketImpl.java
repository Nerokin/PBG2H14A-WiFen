package wifen.commons.network.packets.impl;

import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.MediaRequestPacket;

public class MediaRequestPacketImpl extends PacketImpl implements MediaRequestPacket
{
	private static final long serialVersionUID = 7422903790705502261L;

	private final String sourceName;
	private final String fileName;
	private final int requestType; // 0=Deny; 1=Accept; 2=Request; -1=Unknown
	
	public MediaRequestPacketImpl(String sourceName, String fileName, int requesttype)
	{
		super();
		this.sourceName = sourceName;
		this.fileName = fileName;
		this.requestType = requesttype;
	}

	
	@Override
	public String toString()
	{
		return "{" +  getSourceName() + ": " + getFileName() + "(" + getRequestType() + ")}";
	}
	
	@Override
	public String getSourceName()
	{
		return sourceName;
	}

	@Override
	public String getFileName()
	{
		return fileName;
	}
	
	@Override
	public int getRequestType()
	{
		return requestType;
	}

}
