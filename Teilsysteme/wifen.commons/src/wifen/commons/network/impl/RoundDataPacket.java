package wifen.commons.network.impl;

/**
 * Implementation of Round Data packages
 * @author Oliver Bardong
 * @requirement LF300
 */
public class RoundDataPacket extends PacketImpl {
	private Object[] roundData;

	public RoundDataPacket(Object[] data)
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
}