/**
 *
 */
package wifen.commons.network.events;

import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.Packet;

/**
 * @author Oliver Bardong
 * @requirements LF190 LF300
 * Interface event fired when a Packet has been received
 */
public interface PacketReceivedEvent extends ConnectionEvent {

	/**
	 * Returns the Packet this event contains
	 * @return
	 */
	public Packet getPacket();
	
}
