package wifen.server.services.impl;

import java.util.Map;

import wifen.commons.GameStateModel;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.EnterGamePacket;
import wifen.commons.network.packets.impl.EnterGamePacketImpl;
import wifen.server.network.Server;
import wifen.server.services.ServerGameService;
import wifen.server.services.ServerGameeventService;

public class ServerGameProvider implements ServerGameService, ConnectionListener {

	private Server server;
	private GameStateModel gameState;
	private Map<String, Connection> playerConns;
	private ServerGameeventService gameEventService;
	
	public ServerGameProvider(Server s, GameStateModel initialModel, ServerGameeventService eventService) {
		// TODO Auto-generated constructor stub
		this.setServer(s);
		this.gameState = initialModel;
		this.gameEventService = eventService;
		
	}
	
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		if (connectionEvent instanceof PacketReceivedEvent) {
			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			if (packetEvent.getPacket() instanceof EnterGamePacket) {
				EnterGamePacket packet = (EnterGamePacket) packetEvent.getPacket();
				
				// Nachschauen ob noch Platz im Aktiven Game ist
				if (playerConns.size() < getGameState().getMaxPlayerCount()){
					// Wenn ja: Wird der ConnectionList hinzugefügt
					playerConns.put(packet.getName(), packet.getSource());
					// Schicken ein EnterGamePacket an den beitretenden Spieler
					packet.getSource().sendPacket(new EnterGamePacketImpl(packet.getName(), getGameState()));
					// EnterGameMessage auslösen bei allen Spielern
					gameEventService.fireEvent(packet.getName() + " ist dem Spiel beigetreten.");
				}
				else
				{
					// Wenn nein: Spieler bekommt eine Fehlermeldung das das Spiel voll ist
					packet.getSource().sendPacket(new EnterGamePacketImpl(null, null));
				}
				
			}
		}
	}


	public Server getServer() {
		return server;
	}


	public void setServer(Server server) {
		this.server = server;
	}


	public GameStateModel getGameState() {
		return gameState;
	}


	public void setGameState(GameStateModel gameState) {
		this.gameState = gameState;
	}


	public Map<String, Connection> getPlayerConns() {
		return playerConns;
	}


	public void setPlayerConns(Map<String, Connection> playerConns) {
		this.playerConns = playerConns;
	}
	
}
