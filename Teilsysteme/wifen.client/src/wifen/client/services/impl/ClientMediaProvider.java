package wifen.client.services.impl;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import wifen.client.services.ClientMediaService;
import wifen.commons.network.Connection;

public class ClientMediaProvider implements ClientMediaService
{
	private final ObjectProperty<Connection> connection = new SimpleObjectProperty<>();
	

	public final ObjectProperty<Connection> connectionProperty()
	{
		return this.connection;
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

}
