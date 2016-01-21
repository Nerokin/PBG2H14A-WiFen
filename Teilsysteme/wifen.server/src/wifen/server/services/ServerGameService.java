package wifen.server.services;

import wifen.commons.MarkerModel;
import wifen.commons.Medium;
import wifen.commons.SpielerRolle;
import wifen.commons.network.Connection;

public interface ServerGameService {

	public void addPlayer(String playerName, SpielerRolle role, Connection out);
	public void addGameEvent(String eventMessage);
	public void addChatMessage(String chatMessage);
	public void addMarker(MarkerModel marker);
	public void addMedia(Medium media);
	
}
