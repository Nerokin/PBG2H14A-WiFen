/**
 *
 */
package wifen.commons.network.events.impl;

import wifen.commons.impl.CustomEventImpl;
import wifen.commons.network.Connection;
import wifen.commons.network.events.RoundDataRecivedEvent;
import wifen.commons.network.packets.impl.RoundDataPacketImpl;

/**
 * @author Oliver Bardong
 * @requirement LF300
 *
 */
public class RoundDataRecivedEventImpl extends CustomEventImpl implements RoundDataRecivedEvent {

	RoundDataPacketImpl packet;
	Connection source;
	/**
	 * Put description here
	 * 
	 * @param packet
	 * @param source
	 */
	public RoundDataRecivedEventImpl(RoundDataPacketImpl packet, Connection source)
	{
		this.packet = packet;
		this.source = source;
	}

	@Override
	public RoundDataPacketImpl getPacket() {
		// TODO Auto-generated method stub
		return packet;
	}

	@Override
	public Connection getSource() {
		// TODO Auto-generated method stub
		return source;
	}


}
