package wifen.commons.network.events;

import wifen.commons.network.packets.impl.RoundDataPacketImpl;

/**
 * Round Data Updated Event interface
 * @author Oliver Bardong
 * @requirement LF300
 *
 */
public interface RoundDataRecivedEvent extends PacketReceivedEvent {

	/**
	 * Returns the Packet this event contains
	 * @return
	 */
	public RoundDataPacketImpl getPacket();
}
