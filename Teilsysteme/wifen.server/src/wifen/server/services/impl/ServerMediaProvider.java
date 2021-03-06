package wifen.server.services.impl;

import javax.imageio.spi.ServiceRegistry;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.MediaDataPacket;
import wifen.commons.network.packets.MediaRequestPacket;
import wifen.commons.network.packets.impl.MediaRequestPacketImpl;
import wifen.server.network.Server;
import wifen.server.services.ServerGameService;
import wifen.server.services.ServerMediaService;

public class ServerMediaProvider implements ServerMediaService, ConnectionListener
{
	// Properties	
	private final ObjectProperty<Server> server = new SimpleObjectProperty<>();
	
	// Attributes	
	private final ChangeListener<Server> onServerChangedListener = this::onServerChanged;
	private ServiceRegistry registry;
	
	// Constructor(s)	
	public ServerMediaProvider(Server server)
	{
		serverProperty().addListener(getOnServerChangedListener());
		setServer(server);
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
				
				if(requestPacket.getRequestType() == 2)
				{
					// TODO:
					// Check if Sender is Host then accept automatically
					// else ask Host for permission
					
					// Or just accept to make it easy :P
					connectionEvent.getSource().sendPacket(new MediaRequestPacketImpl(null, requestPacket.getFileName(), 1));
				}
				
			}
			else if(packet instanceof MediaDataPacket)
			{
				// Add media to game state model
				ServerGameService gameService = getRegistry().getServiceProviders(ServerGameService.class, true).next();
				gameService.addMedia(((MediaDataPacket) packet).getMedium());
				
				// Broadcast media
				getServer().broadcastPacket(packet);
			}
		}
	}
	
	// <--- Registerable --->
	@Override
	public void onRegistration(ServiceRegistry registry, Class<?> category)
	{
		if (getRegistry() != null && !getRegistry().equals(registry))
			registry.deregisterServiceProvider(this); 
		else {
			setRegistry(registry);
		}
	}

	@Override
	public void onDeregistration(ServiceRegistry registry, Class<?> category)
	{
		if (getRegistry() != null && getRegistry().equals(registry)) {
			setRegistry(null);
		}
	}
	
	// <--- ServerMediaService --->	
	@Override
	public final Server getServer()
	{
		return this.serverProperty().get();
	}

	public final void setServer(final Server server)
	{
		this.serverProperty().set(server);
	}

	
	// Event Handling	
	/**
	 * Automatically called whenever the associated server changes.<br>
	 * Stops listening the old server and starts listening the new server value.
	 */
	public void onServerChanged(ObservableValue<? extends Server> observable, Server oldValue, Server newValue)
	{
		if (oldValue != null)
		{
			oldValue.removeListener(this);
		}
		
		if (newValue != null)
		{
			newValue.addListener(this);
		}
	}
	
	// Getter & Setter
	public final ObjectProperty<Server> serverProperty()
	{
		return this.server;
	}

	public ChangeListener<Server> getOnServerChangedListener()
	{
		return onServerChangedListener;
	}


	public ServiceRegistry getRegistry()
	{
		return registry;
	}

	private void setRegistry(ServiceRegistry registry)
	{
		this.registry = registry;
	}

}
