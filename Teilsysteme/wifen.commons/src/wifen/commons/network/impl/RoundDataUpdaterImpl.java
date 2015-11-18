/**
 *
 */
package wifen.commons.network.impl;

import java.util.ArrayList;

import wifen.commons.RoundDataListener;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.PacketRecivedEvent;
import wifen.commons.network.Packet;
import wifen.commons.network.RoundDataRecivedEvent;
import wifen.commons.network.RoundDataUpdater;

/**
 * Round Data Updater implementation
 * @author Oliver Bardong
 * @requirement LF300
 *
 */
public class RoundDataUpdaterImpl implements RoundDataUpdater {

	private Connection connection;
	private Packet dataPacket;


	ArrayList<RoundDataListener> eventList = new ArrayList<RoundDataListener>();

	@Override
	public void setRoundData(Object[] data) {
		// TODO Auto-generated method stub
		dataPacket = new RoundDataPacket(data);
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
		connection.addListener(new ConnectionListener<PacketRecivedEvent>() {
			@Override
			public void handle(PacketRecivedEvent event) {
				Packet tmp = event.getPacket();

				if(tmp instanceof RoundDataPacket)
				{
					event.consume();
					RoundDataRecivedEventImpl tmpEvent = new RoundDataRecivedEventImpl((RoundDataPacket)tmp, event.getSource());
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
