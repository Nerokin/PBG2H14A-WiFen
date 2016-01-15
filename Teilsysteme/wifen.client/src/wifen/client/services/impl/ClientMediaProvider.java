package wifen.client.services.impl;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import wifen.client.services.ClientMediaService;
import wifen.commons.Medium;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.MediaDataPacket;
import wifen.commons.network.packets.MediaRequestPacket;
import wifen.commons.network.packets.impl.MediaRequestPacketImpl;

public class ClientMediaProvider implements ClientMediaService, ConnectionListener
{
	private final Logger logger = Logger.getLogger(ClientChatProvider.class.getName());
	private final ObjectProperty<Connection> connection = new SimpleObjectProperty<>();

	private final ChangeListener<Connection> onConnectionChangeListener = this::onConnectionChanged;
	
	private String tempPlayerName = "";
	private String tempFileName = "";
	private String tempFileType = "";
	private byte[] tempFileData = null;
	
	public ClientMediaProvider()
	{
		connectionProperty().addListener(getOnConnectionChangeListener());
	}
	
	public ClientMediaProvider(Connection connection)
	{
		this();
		setConnection(connection);
	}
	
	@Override
	public final Connection getConnection()
	{
		return this.connectionProperty().get();
	}

	@Override
	public final void setConnection(final Connection connection)
	{
		this.connectionProperty().set(connection);
	}

	public void onConnectionChanged(ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue)
	{
		logger.info("Connection changed from " + oldValue + " to " + newValue);
		
		if (oldValue != null)
		{
			oldValue.removeListener(this);
		}
		if (newValue != null) 
		{
			newValue.addListener(this);
		}
		
	}

	@Override
	public void handle(ConnectionEvent connectionEvent)
	{
		if (connectionEvent instanceof PacketReceivedEvent)
		{
			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			Packet packet = packetEvent.getPacket();
			if(packet instanceof MediaRequestPacket)
			{
				MediaRequestPacket requestPacket = (MediaRequestPacket) packet;
				
				logger.info("Incoming MediaRequestPacket: " + packet);
				
				// Check host answer and send on accept
				if(requestPacket.getRequestType() == 1)
				{					
					// Create and send data packet with id
					if (getConnection() != null && getConnection().isConnected())
					{						
						MediaDataPacket dataPacket = new MediaDataPacketImpl(tempPlayerName, tempFileName + tempFileType + ":" + tempFileData);
						logger.info("Sending DataPacket: " + dataPacket);
						getConnection().sendPacket(requestPacket);
					}
					else
					{
						logger.warning("Data-Nachricht konnte nicht gesendet werden, da keine aktive Netzwerkverbindung besteht.");
					}
				}
			}
			else if(packet instanceof MediaDataPacket)
			{
				MediaDataPacket dataPacket = (MediaDataPacket) packet;
				
				logger.info("Incoming MediaDataPacket: " + packet);
				
				// Add to library
				Platform.runLater(() -> getMediaLibrary().add(dataPacket.getMessage()));
			}
		}
	}
	
	public final void trySendFile(String playerName, Medium medium)
	{
		if (getConnection() != null && getConnection().isConnected())
		{
			tempFileData = medium.getRawData();
			tempFileName = medium.getName();
			tempFileType = medium.getType();
			
			MediaRequestPacket requestPacket = new MediaRequestPacketImpl(playerName, medium.getName() + "." + medium.getType(), 2);
			logger.info("Sending RequestPacket: " + requestPacket);
			getConnection().sendPacket(requestPacket);
		}
		else
		{
			logger.warning("Request-Nachricht konnte nicht gesendet werden, da keine aktive Netzwerkverbindung besteht.");
		}
	}
	
	public final ObjectProperty<Connection> connectionProperty()
	{
		return this.connection;
	}

	public ChangeListener<Connection> getOnConnectionChangeListener()
	{
		return onConnectionChangeListener;
	}

}
