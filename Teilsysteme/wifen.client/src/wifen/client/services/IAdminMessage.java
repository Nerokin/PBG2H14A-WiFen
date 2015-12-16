/**
 *
 */
package wifen.client.services;

import wifen.commons.RoundDataListener;
import wifen.commons.network.Connection;

/**
 * @author Oliver Bardong
 *
 */
public interface IAdminMessage {
	public boolean OpenDialog();

	public void setConnection(Connection connection);

	public boolean isAvailable();
}
