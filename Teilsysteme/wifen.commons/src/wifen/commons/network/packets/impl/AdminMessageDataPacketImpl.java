/**
 *
 */
package wifen.commons.network.packets.impl;

import wifen.commons.network.packets.AdminMessageDataPacket;

/**
 * @author Oliver Bardong
 *
 */
public class AdminMessageDataPacketImpl implements AdminMessageDataPacket {

	private String message;
	/* (non-Javadoc)
	 * @see wifen.commons.network.packets.AdminMessageDataPacket#getAdminMessage()
	 */
	@Override
	public String getAdminMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see wifen.commons.network.packets.AdminMessageDataPacket#setAdminMessage(java.lang.String)
	 */
	@Override
	public void setAdminMessage(String message) {
		this.message = message;
	}

}
