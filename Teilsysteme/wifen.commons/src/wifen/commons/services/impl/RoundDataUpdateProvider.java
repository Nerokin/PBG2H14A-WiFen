/**
 *
 */
package wifen.commons.services.impl;

import java.util.ArrayList;

import wifen.commons.RoundDataListener;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.events.impl.PacketReceivedEventImpl;
import wifen.commons.network.events.impl.RoundDataRecivedEventImpl;
import wifen.commons.network.packets.impl.RoundDataPacketImpl;
import wifen.commons.services.RoundDataUpdateService;

/**
 * Round Data Updater implementation
 * @author Oliver Bardong
 * @requirement LF300
 *
 */
public class RoundDataUpdateProvider implements RoundDataUpdateService {

	private Connection connection;
	private Packet dataPacket;


	ArrayList<RoundDataListener> eventList = new ArrayList<RoundDataListener>();

	@Override
	public void setRoundData(Object[] data) {
		// TODO Auto-generated method stub
		dataPacket = new RoundDataPacketImpl(data);
		connection.sendPacket(dataPacket);
	}

	@Override
	public void updateData() {
		if(dataPacket!=null)
			connection.sendPacket(dataPacket);
	}


	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
		connection.addListener(new ConnectionListener() {
			@Override
			public void handle(ConnectionEvent event) {
				PacketReceivedEventImpl packetEvent = null;
				if (event instanceof PacketReceivedEventImpl) {
					packetEvent = (PacketReceivedEventImpl) event;
				}
				Packet tmp = packetEvent.getPacket();

				if(tmp instanceof RoundDataPacketImpl)
				{
					event.consume();
					RoundDataRecivedEventImpl tmpEvent = new RoundDataRecivedEventImpl((RoundDataPacketImpl)tmp, event.getSource());
					for (RoundDataListener roundDataListener : eventList) {
						roundDataListener.handle(tmpEvent);
						if(tmpEvent.isConsumed())
							break;
					}
				}
			}

		});
	}

	@Override
	public void setOnDataUpdateRecived(RoundDataListener event) {
		eventList.add(event);
	}

}
