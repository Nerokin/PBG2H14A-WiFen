/**
 *
 */
package wifen.commons.network;

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
