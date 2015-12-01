package wifen.commons.network.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;
import wifen.commons.network.events.impl.ConnectionClosedEventImpl;
import wifen.commons.network.events.impl.PacketReceivedEventImpl;

/**
 * @author Marius Vogelsang
 * @author David Joachim
 * @author Konstantin Schaper
 */
public class ConnectionImpl implements Connection {
	
	private static final Logger logger = Logger.getLogger(ConnectionImpl.class.getName());

	
	private final Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private final List<ConnectionListener> listeners = new ArrayList<ConnectionListener>();
	
	public ConnectionImpl(Socket s) throws IOException
	{
		this.socket = s;
		this.oos = new ObjectOutputStream(getSocket().getOutputStream());
		logger.info("Connection has been established.");
		
	}
	public ConnectionImpl(InetAddress address, int port) throws IOException {
		this(new Socket(address, port));
	}
	
	@Override
	public boolean sendPacket(Packet packet) {
		try {
			oos.writeObject(packet);
			oos.flush();
			logger.info("A Packet has been successfully sent (" + packet + ")");
			return true;
		} catch (IOException e) {
			logger.log(Level.WARNING, "Packet could not be sent", e);
			return false;
		}
	}

	@Override
	public boolean addListener(ConnectionListener listener) {
		return getListeners().add(listener);
	}
	
	@Override
	public void readPackets() {
			try {
				ois = new ObjectInputStream(getSocket().getInputStream());
				Object obj = null;
				while ((obj = ois.readObject()) != null) { 
					if(obj instanceof Packet)
						fireEvent(new PacketReceivedEventImpl((Packet) obj, this));
				}
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, "Input type could not be found", e);
			} catch (IOException e) {
				fireEvent(new ConnectionClosedEventImpl(this));
				logger.log(Level.WARNING, "Connection has been closed while reading packets", e);
			}

	}

	@Override
	public boolean close() {
		try {
			if (!getSocket().isClosed()) {
				getSocket().close();
				fireEvent(new ConnectionClosedEventImpl(this));
			}
			return true;
		} catch (IOException e) {
			logger.log(Level.WARNING, "An exception occurred when closing the connection", e);
			return false;
		}
	}
	protected final void fireEvent(ConnectionEvent event){
		for (ConnectionListener connectionListener : getListeners()) {
			connectionListener.handle(event);
			if (event.isConsumed()) break;
		}
	}
	
	// Getter & Setter
	
	public List<ConnectionListener> getListeners() {
		return listeners;
	}
	public Socket getSocket() {
		return socket;
	}
}
