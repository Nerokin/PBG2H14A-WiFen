package wifen.commons.network;

import wifen.commons.network.impl.RoundDataPacket;

/**
 * Round Data Updated Event interface
 * @author Oliver Bardong
 * @requirement LF300
 *
 */
public interface RoundDataRecivedEvent extends PacketRecivedEvent {

	/**
	 * Returns the Packet this event contains
	 * @return
	 */
	public RoundDataPacket getPacket();
}
