package wifen.commons.network.impl;

/**
 * Implementation of Round Data packages
 * @author Oliver Bardong
 * @requirement LF300
 */
public class RoundDataPacketImpl extends PacketImpl {
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