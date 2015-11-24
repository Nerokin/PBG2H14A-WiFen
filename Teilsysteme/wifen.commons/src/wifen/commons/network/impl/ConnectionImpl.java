package wifen.commons.network.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;

/**
 * @author Marius Vogelsang, David Joachim
 *
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
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		logger.info("Connection has been established.");
		
	}
	public ConnectionImpl(InetAddress address, int port) throws IOException{
		this(new Socket(address, port));
	}
	
	@Override
	public boolean sendPacket(Packet packet) {
		try {
			oos.writeObject(packet);
			logger.info("Packet successfully sent.");
			return true;
		} catch (IOException e) {
			logger.severe("Packet could not be sent.");
			return false;
		}
	}

	@Override
	public boolean addListener(ConnectionListener listener) {
		return listeners.add(listener);
	}
	
	@Override
	public void readPackets() {
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				Object obj = ois.readObject();
				if(obj instanceof Packet){
					fireEvent(new PacketReceivedEventImpl((Packet) obj, this));
				}
			} catch (ClassNotFoundException e) {
				logger.info("Input type could not be found");
			} catch (IOException e) {
				logger.info("Connection has been closed");
			}

	}

	@Override
	public boolean close() {
		try {
			socket.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	protected final void fireEvent(ConnectionEvent event){
		for (ConnectionListener connectionListener : listeners) {
			connectionListener.handle(event);
			if (event.isConsumed()) break;
		}
	}
}
