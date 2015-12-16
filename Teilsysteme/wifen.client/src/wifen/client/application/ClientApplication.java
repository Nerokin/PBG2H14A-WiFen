package wifen.client.application;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.spi.ServiceRegistry;

import javafx.application.Application;
import javafx.stage.Stage;
import wifen.client.services.ClientChatService;
import wifen.client.services.impl.ClientChatProvider;
import wifen.client.ui.controllers.Spielfeld;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.events.ConnectionClosedEvent;
import wifen.commons.network.events.ConnectionEstablishedEvent;
import wifen.commons.network.impl.ConnectionImpl;
import wifen.server.network.Server;
import wifen.server.network.ServerEvent;
import wifen.server.network.ServerListener;
import wifen.server.network.events.ServerShutdownEvent;
import wifen.server.network.events.ServerStartedEvent;
import wifen.server.network.impl.ServerImpl;
import wifen.server.services.ServerChatService;
import wifen.server.services.impl.ServerChatProvider;


/**
 * Core of the Application. 
 * Manages all services and is responsible for 
 * starting the server or establishing network connections
 * to a server.
 * 
 * @author Konstantin Schaper
 *
 */
public class ClientApplication extends Application implements ServerListener, ConnectionListener {

	// Class Constants

	private static ClientApplication INSTANCE;
	private static final Iterator<Class<?>> SERVICES;
	private static final Logger logger = Logger.getLogger(ClientApplication.class.getName());

	// Attributes

	private final ServiceRegistry serviceRegistry;

	static {
		Set<Class<?>> services = new HashSet<>();
		// Define all available services here

		services.add(Server.class);
		services.add(Connection.class);
		services.add(ClientChatService.class);
		services.add(ServerChatService.class);
		services.add(Spielfeld.class);
		services.add(Stage.class);

		// ...
		SERVICES = services.iterator();
	}

	// Constructor(s)

	public ClientApplication() {
		if (INSTANCE == null) INSTANCE = this;
		serviceRegistry = new ServiceRegistry(SERVICES);
	}

	// Overridden Methods

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Initialize the GUI
		
		// Simple test for the playfield user interface
		//getServiceRegistry().registerServiceProvider(new Spielfeld(1920, 1080, 800, 600, 10, 10, null), Spielfeld.class);
		//getServiceRegistry().getServiceProviderByClass(Spielfeld.class).draw();
	}

	@Override
	public void init() {
		// TODO What happens before the initialization of the GUI
		
		// Initialize a simple test which instantiates a server with a single connection on localhost
		try {
			startServer();
			try {
				startConnection(InetAddress.getLocalHost());
				try {
					getServiceRegistry().getServiceProviders(ClientChatService.class, false).next().sendMessage("ME", "Ich bin soooo klasse!");
				} catch (Exception e) {
					logger.log(Level.WARNING, "ChatMessage could not be sent", e);
				}
			} catch (IOException e) {
				logger.severe("Connection could not be established!");
			}
		} catch (IOException e) {
			logger.severe("Server could not be started!");
		}
	}

	@Override
	public void stop() {
		// TODO What happens, when the application terminates
	}

	// Methods

	public Connection startConnection(InetAddress address) throws IOException {
		// Return null if there already is a connection registered
		if (getServiceRegistry().getServiceProviders(Connection.class, false).hasNext()) return null;
		else {
			Connection conn = new ConnectionImpl(address, ApplicationConstants.APPLICATION_PORT, this);
			new Thread(conn::readPackets).start();
			getServiceRegistry().registerServiceProvider(conn, Connection.class);
			return conn;
		}
	}

	public Server startServer() throws IOException {

		// Return null if there already is a server registered
		if (getServiceRegistry().getServiceProviders(Server.class, false).hasNext()) return null;
		else {
		
			// Instantiate the server
			Server server = new ServerImpl(ApplicationConstants.APPLICATION_PORT, this);
			
			// Actually start the server
			Thread t = new Thread(() -> {
					try {
						server.acceptConnections();
					} catch (Exception e) {
						logger.log(Level.WARNING, "The server crashed ...", e);
						server.shutdown();
						getServiceRegistry().deregisterServiceProvider(server);
					}
				});
			t.setDaemon(true);
			t.start();
			
			// Register the server
			getServiceRegistry().registerServiceProvider(server, Server.class);
			
			// Return the instantiated server instance
			return server;
			
		}
	}
	
	// <--- ServerListener --->
	
	@Override
	public void handle(ServerEvent event) {
		if (event instanceof ServerStartedEvent) {
			
			// Register a new chat service provider if the is none present
			if (!getServiceRegistry().getServiceProviders(ServerChatService.class, false).hasNext()) {
				try {
					getServiceRegistry().registerServiceProvider(new ServerChatProvider(event.getSource()), ServerChatService.class);
					logger.info("A new ServerChatProvider has been registered");
				} catch (Exception e) {
					logger.log(Level.SEVERE, "ServerChatProvider could not be registered", e);
				}
				
			}
			
		} else if (event instanceof ServerShutdownEvent) {
			
			// Fetch the current chat service
			ServerChatService chatService = getServiceRegistry().getServiceProviders(ServerChatService.class, false).next();
			
			if (chatService != null) {
			
				// Remove the invalidated server from the chat service
				chatService.setServer(null);
				
				// Remove the chat service from the service registry
				getServiceRegistry().deregisterServiceProvider(chatService, ServerChatService.class);
			
			}
			
			// Deregister the Server
			getServiceRegistry().deregisterServiceProvider(event.getSource(), Server.class);
		}
	}
	
	// <--- ConnectionListener --->
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		if (connectionEvent instanceof ConnectionEstablishedEvent) {
			
			
			// Whenever a player connects to a server, create a new chat service for that connection and register it if there is none present yet
			if (!getServiceRegistry().getServiceProviders(ClientChatService.class, false).hasNext()) {
				try {
					getServiceRegistry().registerServiceProvider(new ClientChatProvider(connectionEvent.getSource()), ClientChatService.class);
					logger.info("A new ClientChatProvider has been registered");
				} catch (Exception e) {
					logger.log(Level.SEVERE, "ClientChatProvider could not be registered", e);
				}
			}
			
		} else if (connectionEvent instanceof ConnectionClosedEvent) {
			
			// Fetch the current chat service
			ClientChatService chatService = getServiceRegistry().getServiceProviders(ClientChatService.class, false).next();
			
			if (chatService != null) {
			
				// Remove the invalidated connection from the chat service
				chatService.setConnection(null);
				
				// Remove the chat service from the service registry
				getServiceRegistry().deregisterServiceProvider(chatService, ClientChatService.class);
			
			}
			
			// Deregister the Connection
			getServiceRegistry().deregisterServiceProvider(connectionEvent.getSource(), Connection.class);
		}

	}

	// Getter & Setter

	public static ClientApplication instance() {
		return INSTANCE;
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

}
