package wifen.client.application;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.spi.ServiceRegistry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import wifen.client.services.ClientChatService;
import wifen.client.services.ClientGameeventService;
import wifen.client.services.ClientMediaService;
import wifen.client.services.ClientRefreshService;
import wifen.client.services.GameService;
import wifen.client.services.OptionService;
import wifen.client.services.SaveGameService;
import wifen.client.services.impl.ClientChatProvider;
import wifen.client.services.impl.ClientGameeventProvider;
import wifen.client.services.impl.ClientMediaProvider;
import wifen.client.services.impl.GameProvider;
import wifen.client.services.impl.OptionProvider;
import wifen.client.services.impl.SaveGameProvider;
import wifen.client.ui.controllers.Hauptmenu;
import wifen.commons.GameStateModel;
import wifen.commons.GridType;
import wifen.commons.SpielerRolle;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.events.ConnectionClosedEvent;
import wifen.commons.network.events.ConnectionEstablishedEvent;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.impl.ConnectionImpl;
import wifen.commons.network.packets.EnterGamePacket;
import wifen.server.network.Server;
import wifen.server.network.ServerEvent;
import wifen.server.network.ServerListener;
import wifen.server.network.events.ServerShutdownEvent;
import wifen.server.network.events.ServerStartedEvent;
import wifen.server.network.impl.ServerImpl;
import wifen.server.services.ServerChatService;
import wifen.server.services.ServerGameService;
import wifen.server.services.ServerGameeventService;
import wifen.server.services.ServerMediaService;
import wifen.server.services.ServerRefreshService;
import wifen.server.services.impl.ServerChatProvider;
import wifen.server.services.impl.ServerGameProvider;
import wifen.server.services.impl.ServerGameeventProvider;
import wifen.server.services.impl.ServerMediaProvider;

/**
 * Core of the Application.
 * Manages all services and is responsible for
 * starting the server or establishing network connections
 * to a server.
 *
 * @author Konstantin Schaper
 *
 */
public class ClientApplication extends Application implements ServerListener, ConnectionListener, EventHandler<WindowEvent> {

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

	/**
	 * Used to save the initial game state model while the server is starting.
	 */
	private final Map<String, Object> tmpModel = new HashMap<>();

	static {
		SERVICES = new HashSet<>();

		// Define all available types of services here

		SERVICES.add(Stage.class); // Main Window
		SERVICES.add(Server.class); // Active Server
		SERVICES.add(Connection.class); // Active Connection to Server (Network Client)
		SERVICES.add(ClientChatService.class); // Active Client Chat Service
		SERVICES.add(ServerChatService.class); // Active Server Chat Service
		SERVICES.add(ServerGameService.class); // Active Game (Serverside)
		SERVICES.add(GameService.class); // Active Game
		SERVICES.add(OptionService.class); // Game settings
		SERVICES.add(ClientGameeventService.class); // Ereignislog
		SERVICES.add(ServerGameeventService.class); // Ereignislog
		SERVICES.add(ServerRefreshService.class); // Refresh Service
		SERVICES.add(ClientRefreshService.class); // Refresh Service
		SERVICES.add(ClientMediaService.class); // Media Service
		SERVICES.add(ServerMediaService.class); // Media Service

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

		primaryStage.setOnCloseRequest(this);
	}

	@Override
	public void init() {
		// TODO What happens before the initialization of the GUI
		// Register new option service provider if none is present
		if(!getServiceRegistry().getServiceProviders(OptionService.class, false).hasNext()) {
			try {
				getServiceRegistry().registerServiceProvider(new OptionProvider(), OptionService.class);
				logger.info("A new OptionProvider has been registered");
			} catch (Exception e) {
				logger.log(Level.SEVERE, "OptionProvider could not be registered", e);
			}
		}
	}

	@Override
	public void stop() {
		// TODO What happens, when the application terminates
		// TODO Close open connections
		// TODO Shutdown active servers


	}

	// Methods

	/**
	 *
	 * @param <T> matches the parameter <i>serviceType</i>
	 * @param serviceType The class of which the caller expects an object to return.
	 * @return The first service of the given type, or null if there is none registered
	 */
	public <T> T getServiceProvider(Class<T> serviceType) {
		try {
			return getServiceRegistry().getServiceProviders(serviceType, true).next();
		} catch (Exception e) {
			return null;
		}
	}

	public void showLoadingScreen() {
		logger.info("Showing loading screen ...");
		BorderPane loadingScreen = new BorderPane();
		loadingScreen.setCenter(new Label("Loading ... :D"));
		ClientApplication.instance().getServiceRegistry().getServiceProviders(Stage.class, false).next().getScene().setRoot(loadingScreen);
	}

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
			ServerImpl server = new ServerImpl(ApplicationConstants.APPLICATION_PORT, this);

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

	public void cancelGameCreation(Exception e) {
		logger.log(Level.WARNING, "Game could not be created", e);
		Platform.runLater(() -> {
			new Alert(AlertType.ERROR, "Das Spiel konnte nicht erstellt werden (" + e.getMessage() + ")").showAndWait();
			try {
				getServiceRegistry().getServiceProviders(Stage.class, false).next().getScene().setRoot(new Hauptmenu());
			} catch (Exception e1) {
				new Alert(AlertType.ERROR, "Das Hauptmenü konnte nicht initialisiert werden, die Anwendung wird geschlossen").showAndWait();
			}
		});
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

				// Loading Screen
				logger.info("Showing loading screen ...");
				ClientApplication.instance().showLoadingScreen();

				// Creating tmpModel
				tmpModel.put("maximumPlayerCount", maximumPlayerCount);
				tmpModel.put("spectatorsAllowed", spectatorsAllowed);
				tmpModel.put("mediaInitiallyVisible", mediaInitiallyVisible);
				tmpModel.put("maxDiceFaceCount", maxDiceFaceCount);
				tmpModel.put("playerName", playerName);
				tmpModel.put("standardPlayerRole", standardPlayerRole);
				tmpModel.put("gridType", gridType);

				// Start the Server
				Thread t;
				t = new Thread(() -> {
					try {
						startServer();
					} catch (Exception e) {
						cancelGameCreation(e);
					}
				});
				t.setDaemon(true);
				t.setName("ServerStartThread");
				t.start();

			} else throw new IllegalStateException("There already is a game/server running");

		} catch (Exception e) {

			cancelGameCreation(e);

		}
	}

	// <--- ServerListener --->

	@Override
	public void handle(ServerEvent event) {
		if (event instanceof ServerStartedEvent) {

			try {

				// Register the server
				getServiceRegistry().registerServiceProvider(event.getSource(), Server.class);

				// Initialize the model
				GameStateModel model = new GameStateModel((int) tmpModel.get("maximumPlayerCount"),
						(boolean) tmpModel.get("spectatorsAllowed"), (boolean) tmpModel.get("mediaInitiallyVisible"),
						(int) tmpModel.get("maxDiceFaceCount"), (SpielerRolle) tmpModel.get("standardPlayerRole"),
						(GridType) tmpModel.get("gridType"), new ArrayList<>(), new ArrayList<>());

				// Register a new chat service provider if the is none present
				if (!getServiceRegistry().getServiceProviders(ServerChatService.class, false).hasNext()) {
					try {
						getServiceRegistry().registerServiceProvider(new ServerChatProvider(event.getSource()), ServerChatService.class);
						logger.info("A new ServerChatProvider has been registered");
					} catch (Exception e) {
						logger.log(Level.SEVERE, "ServerChatProvider could not be registered", e);
					}

				}

				// Register new gameevent service provider if none is present
				if(!getServiceRegistry().getServiceProviders(ServerGameeventService.class, false).hasNext()) {
					try {
						getServiceRegistry().registerServiceProvider(new ServerGameeventProvider(event.getSource()), ServerGameeventService.class);
						logger.info("A new ServerGameeventProvider has been registered");
					} catch (Exception e) {
						logger.log(Level.SEVERE, "ServerGameeventProvider could not be registered", e);
					}
				}

				// Register new media service provider if none is present
				if(!getServiceRegistry().getServiceProviders(ServerMediaService.class, false).hasNext()) {
					try {
						getServiceRegistry().registerServiceProvider(new ServerMediaProvider(event.getSource()), ServerMediaService.class);
						logger.info("A new ServerMediaProvider has been registered");
					} catch (Exception e) {
						logger.log(Level.SEVERE, "ServerMediaProvider could not be registered", e);
					}
				}

				// Initialize the server game service
				ServerGameService serverGameService = null;;
				try {
					serverGameService = new ServerGameProvider(getServiceRegistry().getServiceProviders(Server.class, true).next(), model,
							getServiceRegistry().getServiceProviders(ServerGameeventService.class, true).next());
					getServiceRegistry().registerServiceProvider(serverGameService, ServerGameService.class);
				} catch (Exception e) {
					logger.log(Level.SEVERE, "ServerGameService could not be created", e);
				}

				// Connect to local Server
				startConnection(InetAddress.getLocalHost());

				// Send yourself an invite. How charming!
				final ServerGameService finalGameService = serverGameService;
				final ConnectionListener listener = new ConnectionListener() {

					@Override
					public void handle(ConnectionEvent connectionEvent) {
						if (connectionEvent instanceof ConnectionEstablishedEvent) {
							finalGameService.addPlayer((String) tmpModel.get("playerName"), SpielerRolle.ADMIN, connectionEvent.getSource());
							event.getSource().removeListener(this);
						}
					}

				};
				event.getSource().addListener(listener);

			} catch (Exception e) {
				// TODO Shutdown Server
				// TODO ChatService(s) deregistrieren
				// TODO GameEventService(s) deregistrieren
				// TODO GameService deregistrieren

				cancelGameCreation(e);
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

		logger.log(Level.INFO, "Client handles the event " + connectionEvent);

		if (connectionEvent instanceof ConnectionClosedEvent) {

			logger.info("Attempting to shutdown ClientChatService ...");

			// Fetch the current chat service
			ClientChatService chatService;
			try {
				chatService = getServiceRegistry().getServiceProviders(ClientChatService.class, false).next();
			} catch (NoSuchElementException e) {
				chatService = null;
			}


			if (chatService != null) {

				// Remove the invalidated connection from the chat service
				chatService.setConnection(null);

				// Remove the chat service from the service registry
				getServiceRegistry().deregisterServiceProvider(chatService, ClientChatService.class);

				logger.info("The ClientChatService has been successfully shut down");

			} else logger.info("There is no ClientChatService");

			// Fetch the current gameevent service

			logger.info("Attempting to shutdown ClientGameEventService ...");

			ClientGameeventService geservice;
			try {
				geservice = getServiceRegistry().getServiceProviders(ClientGameeventService.class, false).next();
			} catch (NoSuchElementException e) {
				geservice = null;
			}
			if(geservice != null) {
				// Remove invalidated connection from service
				geservice.setConnection(null);

				// Remove service from registry
				getServiceRegistry().deregisterServiceProvider(geservice, ClientGameeventService.class);

				logger.info("The ClientGameEventService has been successfully shut down");
			} else logger.info("There is no ClientGameEventService");

			// Fetch current game service

			logger.info("Attempting to shutdown ClientGameService ...");

			GameService gameService;
			try {
				gameService = getServiceRegistry().getServiceProviders(GameService.class, true).next();
			} catch (NoSuchElementException e) {
				gameService = null;
			}
			if (gameService != null) {
				getServiceRegistry().deregisterServiceProvider(gameService, GameService.class);
				logger.info("The ClientGameService has been successfully shut down");
			} else logger.info("There is no GameService");

			// Deregister the Connection
			getServiceRegistry().deregisterServiceProvider(connectionEvent.getSource(), Connection.class);

		} else if (connectionEvent instanceof PacketReceivedEvent) {

			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			if (packetEvent.getPacket() instanceof EnterGamePacket) {
				EnterGamePacket packet = (EnterGamePacket) packetEvent.getPacket();

				logger.info("Checking incoming EnterGamePacket ...");

				// Falls der Name zurückkommt, kommt das Modell mit ( falls kein Modell kommt -> Fehler)
				if(packet.getPlayer() != null && packet.getInitialModel() != null) {

					logger.info("Attempting to register Client-Side ChatService ...");

					// Whenever a player connects to a game, create a new chat service for that connection and register it if there is none present yet
					if (!getServiceRegistry().getServiceProviders(ClientChatService.class, false).hasNext()) {
						try {
							getServiceRegistry().registerServiceProvider(new ClientChatProvider(connectionEvent.getSource()), ClientChatService.class);
							logger.info("A new ClientChatProvider has been registered");
						} catch (Exception e) {
							logger.log(Level.SEVERE, "ClientChatProvider could not be registered", e);
						}
					}


					logger.info("Attempting to register Client-Side MediaService ...");

					// Whenever a player connects to a game, create a new media service for that connection and register it if there is none present yet
					if (!getServiceRegistry().getServiceProviders(ClientMediaService.class, false).hasNext()) {
						try {
							getServiceRegistry().registerServiceProvider(new ClientMediaProvider(connectionEvent.getSource()), ClientMediaService.class);
							logger.info("A new ClientMediaProvider has been registered");
						} catch (Exception e) {
							logger.log(Level.SEVERE, "ClientMediaProvider could not be registered", e);
						}
					}

					logger.info("Attempting to register Client-Side GameEventService ...");

					// Create a new Gameevent-Service for the new connection and register it if there is none yet present
					if(!getServiceRegistry().getServiceProviders(ClientGameeventService.class, false).hasNext()) {
						try {
							getServiceRegistry().registerServiceProvider(new ClientGameeventProvider(connectionEvent.getSource()));
							logger.info("A new ClientGameeventProvider has been registered");
						} catch (Exception e) {
							logger.log(Level.SEVERE, "ClientGameeventProvider could not be registered", e);
						}
					}

					logger.info("Attempting to register Client-Side GameService ...");

					// Create the client-side game service
					GameService gameService = null;
					try {
						gameService = new GameProvider(packet.getInitialModel(), packet.getPlayer());
					} catch (Exception e) {
						logger.log(Level.SEVERE, "GameProvider could not be created", e);
					}

					// Register the game service
					try {
						getServiceRegistry().registerServiceProvider(gameService, GameService.class);
						logger.info("A new GameProvider has been registered");
					} catch (Exception e) {
						logger.log(Level.SEVERE, "GameProvider could not be registered", e);
					}

					// Define the Game's Chat Player Name
					gameService.getGameView().chatBox.setPlayerName(gameService.getActivePlayerName());

					// Display the game's playfield
					getServiceRegistry().getServiceProviders(Stage.class, false).next().getScene().setRoot(gameService.getGameView());

					// Register any other relevant client-side services ....

					// TODO

				}

			}

		}

	}

	// Getter & Setter

	public static ClientApplication instance() {
		return INSTANCE;
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	@Override
	public void handle(WindowEvent event) {
		if(getServiceProvider(GameService.class)!=null)
		{
			SaveGameService saveService = new SaveGameProvider();
			// Ask if the user wants to save
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Save the game?");
			alert.setHeaderText("Do you want to save the game before closing?");
			alert.setContentText("Pressing cancel will not save and then close the game.");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get()==ButtonType.OK)
			{
				// Save game here!
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save game");
				GameService gameservice = ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class,false).next();
				File file = fileChooser.showSaveDialog(gameservice.getGameView().getScene().getWindow());
				if(file != null)
				{
					try {
						saveService.SaveAllData(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.log(Level.SEVERE, "Game could not be saved!", e);
					}
				}
			}
		}
	}

}
