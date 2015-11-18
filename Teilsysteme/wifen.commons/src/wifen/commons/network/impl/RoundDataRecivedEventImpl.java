/**
 *
 */
package wifen.commons.network.impl;

import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.RoundDataRecivedEvent;
import wifen.commons.impl.CustomEventImpl;

/**
 * @author Oliver Bardong
 * @requirement LF300
 *
 */
public class RoundDataRecivedEventImpl extends CustomEventImpl implements RoundDataRecivedEvent {

	RoundDataPacket packet;
	Connection source;
	public RoundDataRecivedEventImpl(RoundDataPacket packet, Connection source)
	{
		this.packet = packet;
		this.source = source;
	}

	@Override
	public RoundDataPacket getPacket() {
		// TODO Auto-generated method stub
		return packet;
	}

	@Override
	public Connection getSource() {
		// TODO Auto-generated method stub
		return source;
	}


}
