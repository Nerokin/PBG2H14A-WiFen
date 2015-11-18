package wifen.commons.network;

import wifen.commons.RoundDataListener;

/**
 * Round Data Updater Interface
 * @author Oliver Bardong
 * @requirement LF300
 *
 */
public interface RoundDataUpdater {
	public void setRoundData(Object[] data);
	public void updateData();


	public void setConnection(Connection connection);

	public void setOnDataUpdateRecived(RoundDataListener event);
}
