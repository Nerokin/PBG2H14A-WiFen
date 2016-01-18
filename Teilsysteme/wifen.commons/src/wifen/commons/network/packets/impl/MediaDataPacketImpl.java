package wifen.commons.network.packets.impl;

import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.MediaDataPacket;

public class MediaDataPacketImpl extends PacketImpl implements MediaDataPacket
{
	private static final long serialVersionUID = -8597096663167790919L;

	private String sourceName;
	private String dataBlock;
	
	public MediaDataPacketImpl(String sourceName, String dataBlock)
	{
		this.sourceName = sourceName;
		this.dataBlock = dataBlock;
	}
	
	@Override
	public String getSourceName()
	{
		return sourceName;
	}

	@Override
	public String getDataBlock()
	{
		return dataBlock;
	}

	@Override
	public String getFileType()
	{
		return dataBlock.split("^.*[.]{1}*[:]{1}*$")[1];
	}

	@Override
	public String getFileName()
	{
		return dataBlock.split("^.*[.]{1}*[:]{1}*$")[0];
	}

	@Override
	public String getFileData()
	{
		return dataBlock.split("^.*[.]{1}*[:]{1}*$")[2];
	}

	@Override
	public String toString()
	{
		return "{" +  getSourceName() + ": " + getDataBlock() + "}";
	}
}
