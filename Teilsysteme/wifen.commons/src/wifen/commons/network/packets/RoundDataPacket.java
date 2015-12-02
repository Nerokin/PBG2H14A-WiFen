package wifen.commons.network.packets;

/**
 * Round Data packages interface
 * @author Oliver Bardong
 * @requirement LF300
 */
public interface RoundDataPacket {

	public Object[] getRoundData();

	public void setRoundData(Object[] roundData);
}
