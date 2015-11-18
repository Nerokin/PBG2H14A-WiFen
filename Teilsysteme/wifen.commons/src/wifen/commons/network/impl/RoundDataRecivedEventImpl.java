/**
 *
 */
package wifen.commons.network.impl;

import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.RoundDataRecivedEvent;

/**
 * @author Oliver Bardong
 * @requirement LF300
 *
 */
public class RoundDataRecivedEventImpl extends PacketImpl implements RoundDataRecivedEvent {

	RoundDataPacket packet;

	public RoundDataRecivedEventImpl(RoundDataPacket packet)
	{
		this.packet = packet;
	}

	@Override
	public boolean isConsumed() {
		// TODO Auto-generated method stub
		return isConsumed;
	}

	@Override
	public void consume() {
		// TODO Auto-generated method stub
		isConsumed = true;
	}

	@Override
	public RoundDataPacket getPacket() {
		// TODO Auto-generated method stub
		return packet;
	}


}
