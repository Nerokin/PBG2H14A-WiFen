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
import wifen.commons.network.Connection;
import wifen.commons.network.impl.ConnectionImpl;
import wifen.server.network.Server;
import wifen.server.network.impl.ServerImpl;

public class ClientApplication extends Application {

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

		// ...
		SERVICES = services.iterator();
	}

	// Constructor(s)

	public ClientApplication() {
		if (INSTANCE == null)
			INSTANCE = this;
		serviceRegistry = new ServiceRegistry(SERVICES);
	}

	// Overridden Methods

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Initialize the GUI
	}

	@Override
	public void init() {
		// TOOD What happens before the initialization of the GUI
	}

	@Override
	public void stop() {
		// TODO What happens, when the application terminates
	}

	// Methods

	public Connection startConnection(InetAddress address) throws IOException {
		Connection conn = new ConnectionImpl(address, ApplicationConstants.APPLICATION_PORT);
		getServiceRegistry().registerServiceProvider(conn, Connection.class);
		return conn;
	}

	public Server startServer() throws IOException {
		// Instantiate the server
		Server server = new ServerImpl(ApplicationConstants.APPLICATION_PORT);

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

	// Getter & Setter

	public static ClientApplication instance() {
		return INSTANCE;
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

}
