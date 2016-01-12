/**
 *
 */
package wifen.client.services;

import wifen.commons.RoundDataListener;
import wifen.commons.network.Connection;

/**
 * Put description here
 * 
 * @author Oliver Bardong
 *
 */
public interface IAdminMessage {
	/**
	 * Put description here
	 * 
	 * @return
	 */
	public boolean OpenDialog();

	/**
	 * Put description here
	 * 
	 * @param connection
	 */
	public void setConnection(Connection connection);

	/**
	 * Put description here
	 * 
	 * @return
	 */
	public boolean isAvailable();
}
