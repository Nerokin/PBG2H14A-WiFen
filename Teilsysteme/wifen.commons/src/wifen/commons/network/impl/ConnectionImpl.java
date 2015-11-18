package wifen.commons.network.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.Packet;

public class ConnectionImpl implements Connection {

	
	private final Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private final List<ConnectionListener> listeners = new ArrayList<ConnectionListener>();
	
	public ConnectionImpl(Socket s) throws IOException
	{
		this.socket = s;
		this.ois = new ObjectInputStream(socket.getInputStream());
	}
	public ConnectionImpl(InetAddress address, int port) throws IOException{
		this(new Socket(address, port));
	}
	
	@Override
	public boolean sendPacket(Packet packet) {
		try {
			oos.writeObject(packet);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				Object obj = ois.readObject();
				if(obj instanceof Packet){
					fireEvent(new PacketReceivedEventImpl((Packet) obj, this));
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
