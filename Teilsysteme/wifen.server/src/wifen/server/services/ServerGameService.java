package wifen.server.services;

import wifen.commons.SpielerRolle;
import wifen.commons.network.Connection;

public interface ServerGameService {

	public void addPlayer(String playerName, SpielerRolle role, Connection out);
	public void addGameEvent(String eventMessage);
	
}
