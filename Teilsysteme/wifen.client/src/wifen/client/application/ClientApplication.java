package wifen.client.application;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.spi.ServiceRegistry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import wifen.client.services.ClientChatService;
import wifen.client.services.GameService;
import wifen.client.services.impl.ClientChatProvider;
import wifen.client.services.impl.GameProvider;
import wifen.client.ui.controllers.Hauptmenu;
import wifen.commons.GameStateModel;
import wifen.commons.GridType;
import wifen.commons.SpielerRolle;
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

	/**
	 * Represents this class' singleton instance 
	 */
	private static ClientApplication INSTANCE;
	
	/**
	 * Contains all types of services, which could be registered
	 * in this application's {@linkplain #serviceRegistry}.<br>
	 * Defined in static context.
	 */
	private static final Set<Class<?>> SERVICES;
	
	/**
	 * Main Application Logger.
	 */
	private static final Logger logger = Logger.getLogger(ClientApplication.class.getName());

	// Attributes

	/**
	 * Manages all available services and provides
	 * methods to either register or deregister a certain
	 * service provider.
	 */
	private final ServiceRegistry serviceRegistry;

	static {
		SERVICES = new HashSet<>();
		
		// Define all available types of services here

		SERVICES.add(Stage.class); // Main Window
		SERVICES.add(Server.class); // Active Server
		SERVICES.add(Connection.class); // Active Connection to Server (Network Client)
		SERVICES.add(ClientChatService.class); // Active Client Chat Service
		SERVICES.add(ServerChatService.class); // Active Server Chat Service
		SERVICES.add(GameService.class); // Active Game
	}

	// Constructor(s)

	/**
	 * Default constructor used by the {@linkplain Main} class
	 * to instantiate the application.
	 */
	public ClientApplication() {
		if (INSTANCE == null) INSTANCE = this;
		serviceRegistry = new ServiceRegistry(SERVICES.iterator());
	}

	// Overridden Methods

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Initialize the GUI
		
		primaryStage.setTitle("WiFeN");
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		primaryStage.setScene(new Scene(new Hauptmenu()));
		primaryStage.setMaximized(true);
		primaryStage.show();
		getServiceRegistry().registerServiceProvider(primaryStage, Stage.class);
	}

	@Override
	public void init() {
		// TODO What happens before the initialization of the GUI
	}

	@Override
	public void stop() {
		// TODO What happens, when the application terminates
		// TODO Close open connections
		// TODO Shutdown active servers
	}

	// Methods

	/**
	 * Connects to a remote or local host (server) and registers it with this
	 * application's service registry. 
	 * Does not refresh the connection if there already is
	 * an active connection registered within this application's
	 * {@linkplain ServiceRegistry}, even if the addresses do not match.
	 * 
	 * @param address The internet address to connect to
	 * @return Either the newly established connection or the currently active connection, if there is any
	 * @throws IOException If a network error occurred
	 */
	public Connection startConnection(InetAddress address) throws IOException {
		// Return active connection if there already is one registered
		try {
			return getServiceRegistry().getServiceProviders(Connection.class, false).next();
		} catch (NoSuchElementException e) {
			Connection conn = new ConnectionImpl(address, ApplicationConstants.APPLICATION_PORT, this);
			Thread t = new Thread(conn::readPackets);
			t.setDaemon(true);
			t.start();
			getServiceRegistry().registerServiceProvider(conn, Connection.class);
			return conn;
		}
	}

	/**
	 * Creates a new server on the default port ({@linkplain ApplicationConstants#APPLICATION_PORT})
	 * and registers it with this application's service registry.
	 * If there already is an active server, it is returned and no new server is created.
	 * 
	 * @return The freshly created server or the active one, if there is any
	 * @throws IOException If a network error occurred
	 */
	public Server startServer() throws IOException {
		// Return active server if there already is one registered
		try {
			return getServiceRegistry().getServiceProviders(Server.class, false).next();
		} catch (NoSuchElementException e) {
			// Instantiate the server
			Server server = new ServerImpl(ApplicationConstants.APPLICATION_PORT, this);
			
			// Actually start the server
			Thread t = new Thread(() -> {
					try {
						server.acceptConnections();
					} catch (Exception e2) {
						logger.log(Level.WARNING, "The server crashed ...", e2);
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
	
	/**
	 * Instantiates a new {@linkplain GameProvider} and registers it with this
	 * application's service registry, if there is none yet.
	 * 
	 * @param maximumPlayerCount Maximum number of players allowed to join the game
	 * @param spectatorsAllowed Whether passive players are allowed to watch the game
	 * @param mediaInitiallyVisible Whether media data is initially visible or not
	 * @param maxDiceFaceCount The largest kind of dice the players can roll
	 * @param playerName The host's player name
	 * @param standardPlayerRole The default role for new players
	 * @param gridType The grid type for the playfield to display
	 */
	public void hostGame(int maximumPlayerCount, boolean spectatorsAllowed, boolean mediaInitiallyVisible, int maxDiceFaceCount, String playerName, SpielerRolle standardPlayerRole, GridType gridType) {
		try {
			
			// Check if a game is already running
			if (!(getServiceRegistry().getServiceProviders(GameService.class, false).hasNext()
				 || getServiceRegistry().getServiceProviders(Server.class, false).hasNext())) {
				// Start the Server
				startServer();
				
				// Connect to local Server
				startConnection(InetAddress.getLocalHost());
				
				// Initialize the model
				GameStateModel model = new GameStateModel(maximumPlayerCount, spectatorsAllowed, mediaInitiallyVisible, maxDiceFaceCount, standardPlayerRole, gridType);
				
				// Initialize the game service which ties everything together
				GameService gameService = new GameProvider(model, playerName);
				getServiceRegistry().registerServiceProvider(gameService, GameService.class);
			
				// Define the Game's Chat Player Name
				gameService.getGameView().chatBox.setPlayerName(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getPlayerName());
				
				// Display the game's playfield
				getServiceRegistry().getServiceProviders(Stage.class, false).next().getScene().setRoot(gameService.getGameView());
				
			} else throw new IllegalStateException("There already is a game/server running");
			
		} catch (Exception e) {
			//e.printStackTrace();
			new Alert(AlertType.ERROR, "Das Spiel konnte nicht erstellt werden (" + e.getMessage() + ")").showAndWait();
			try {
				getServiceRegistry().getServiceProviders(Stage.class, false).next().getScene().setRoot(new Hauptmenu());
			} catch (IOException e1) {
				new Alert(AlertType.ERROR, "Das Hauptmen� konnte nicht initialisiert werden, die Anwendung wird geschlossen").showAndWait();
				Platform.exit();
			}
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
