package wifen.client.application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import wifen.commons.network.Connection;
import wifen.commons.network.impl.ConnectionImpl;
import wifen.commons.network.impl.PacketImpl;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection connection = new ConnectionImpl(InetAddress.getByName("localhost"),1234);
			connection.sendPacket(new PacketImpl(){
				@Override
				public String toString() {
					// TODO Auto-generated method stub
					return "hallo du geiler typ";
				}
			});
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
