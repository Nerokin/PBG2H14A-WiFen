package wifen.server.application;

import wifen.commons.network.ConnectionEvent;
import wifen.server.network.Server;
import wifen.server.network.impl.ServerImpl;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Server server = new ServerImpl(1234);
			new Thread(()->{
				try {
					server.acceptConnections();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
			server.addListener((ConnectionEvent event)->{
				System.out.println(event);
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
