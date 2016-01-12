package wifen.commons.network.packets.impl;

import wifen.commons.network.impl.PacketImpl;

/**
 * Implementation of Round Data packages
 * @author Oliver Bardong
 * @requirement LF300
 */
public class RoundDataPacketImpl extends PacketImpl {
	
	// Class Constants

	private static final long serialVersionUID = 6776060734464805312L;
	
	private Object[] roundData;

	public RoundDataPacketImpl(Object[] data)
	{
		roundData = data;
	}

	public Object[] getRoundData()
	{
		return roundData;
	}

	public void setRoundData(Object[] roundData)
	{
		this.roundData = roundData;
	}
	
	@Override
	public String toString() {
		return "RoundDataPacketImpl(" + roundData + ")";
	}
}