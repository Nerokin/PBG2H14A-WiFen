package wifen.server.services.impl;

import java.util.HashMap;
import java.util.Map;

import wifen.commons.GameStateModel;
import wifen.commons.Player;
import wifen.commons.SpielerRolle;
import wifen.commons.impl.PlayerImpl;
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

	// Attributes
	
	private Server server;
	private GameStateModel gameState;
	private Map<Player, Connection> playerConns = new HashMap<>();
	private ServerGameeventService gameEventService;
	
	// Constructor(s)
	
	public ServerGameProvider(Server s, GameStateModel initialModel, ServerGameeventService eventService) {
		this.setServer(s);
		s.addListener(this);
		this.gameState = initialModel;
		this.gameEventService = eventService;
		
	}
	
	// <--- ServerGameService --->
	
	@Override
	public void addPlayer(String playerName, SpielerRolle role, Connection associatedConnection) {
		Player player = new PlayerImpl(playerName, role);
		
		// Wenn ja: Wird der ConnectionList hinzugefügt
		playerConns.put(player, associatedConnection);
		// Schicken ein EnterGamePacket an den beitretenden Spieler
		associatedConnection.sendPacket(new EnterGamePacketImpl(player, getGameState()));
		// EnterGameMessage auslösen bei allen Spielern
		gameEventService.fireEvent(playerName + " ist dem Spiel beigetreten.");
	}
	
	// <--- ConnectionListener --->
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		if (connectionEvent instanceof PacketReceivedEvent) {
			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			if (packetEvent.getPacket() instanceof EnterGamePacket) {
				EnterGamePacket packet = (EnterGamePacket) packetEvent.getPacket();
				
				// Nachschauen ob noch Platz im Aktiven Game ist
				if (playerConns.size() < getGameState().getMaxPlayerCount()){
					addPlayer(packet.getName(), getGameState().getStandardPlayerRole(), connectionEvent.getSource());
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


	public Map<Player, Connection> getPlayerConns() {
		return playerConns;
	}


	public void setPlayerConns(Map<Player, Connection> playerConns) {
		this.playerConns = playerConns;
	}
	
}
