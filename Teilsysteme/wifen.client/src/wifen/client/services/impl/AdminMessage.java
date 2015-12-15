/**
 *
 */
package wifen.client.services.impl;

import java.util.Optional;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

import javafx.scene.control.TextInputDialog;
import wifen.client.services.IAdminMessage;
import wifen.client.ui.controllers.EreignisFenster;
import wifen.commons.RoundDataListener;
import wifen.commons.SpielerRolle;
import wifen.commons.impl.Player;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.events.impl.PacketReceivedEventImpl;
import wifen.commons.network.events.impl.RoundDataRecivedEventImpl;
import wifen.commons.network.packets.AdminMessageDataPacket;
import wifen.commons.network.packets.impl.AdminMessageDataPacketImpl;
import wifen.commons.network.packets.impl.RoundDataPacketImpl;

/**
 * @author Oliver Bardong
 *
 */
public class AdminMessage implements IAdminMessage {

	private EreignisFenster logWindow;
	private Connection connection;
	private Player currentPlayer;

	public AdminMessage(EreignisFenster logWindow, Player currentPlayer)
	{
		this.logWindow = logWindow;
	}

	/* (non-Javadoc)
	 * @see wifen.client.services.IAdminMessage#OpenDialog()
	 */
	@Override
	public boolean OpenDialog() {
		if(!isAvailable())
		{
			logWindow.log("Cannot send Admin message with current player privileges!");
			return false;
		}
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Send to all");
		dialog.setHeaderText("Send a message to all players");
		dialog.setContentText("Message:");
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent())
		{
			logWindow.log("Admin says: " + result.get());
			AdminMessageDataPacketImpl packet = new AdminMessageDataPacketImpl();
			packet.setAdminMessage(result.get());
			connection.sendPacket((Packet) packet);
			return true;
		}

		return false;
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

				if(tmp instanceof AdminMessageDataPacket)
				{
					event.consume();
					AdminMessageDataPacket packet = (AdminMessageDataPacket)tmp;
					logWindow.log("Admin says: " + packet.getAdminMessage());
				}
			}

		});
	}

	@Override
	public boolean isAvailable() {
		return currentPlayer.getRolle() == SpielerRolle.ADMIN;
	}

}
