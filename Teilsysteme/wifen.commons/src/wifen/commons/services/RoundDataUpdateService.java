package wifen.commons.services;

import wifen.commons.RoundDataListener;
import wifen.commons.network.Connection;

/**
 * Round Data Updater Interface
 * @author Oliver Bardong
 * @requirement LF300
 *
 */
public interface RoundDataUpdateService {
	public void setRoundData(Object[] data);
	public void updateData();


	public void setConnection(Connection connection);

	public void setOnDataUpdateRecived(RoundDataListener event);
}
