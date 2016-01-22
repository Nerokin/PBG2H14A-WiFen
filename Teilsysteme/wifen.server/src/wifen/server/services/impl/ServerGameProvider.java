package wifen.server.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import wifen.commons.GameStateModel;
import wifen.commons.MarkerModel;
import wifen.commons.Medium;
import wifen.commons.MediumModel;
import wifen.commons.Player;
import wifen.commons.SpielerRolle;
import wifen.commons.impl.PlayerImpl;
import wifen.commons.network.Connection;
import wifen.commons.network.ConnectionEvent;
import wifen.commons.network.ConnectionListener;
import wifen.commons.network.events.ConnectionClosedEvent;
import wifen.commons.network.events.PacketReceivedEvent;
import wifen.commons.network.packets.EnterGamePacket;
import wifen.commons.network.packets.MarkerPacket;
import wifen.commons.network.packets.MarkerRemovedPacket;
import wifen.commons.network.packets.MediumPacket;
import wifen.commons.network.packets.MediumRemovedPacket;
import wifen.commons.network.packets.PlayerListPacket;
import wifen.commons.network.packets.impl.EnterGamePacketImpl;
import wifen.commons.network.packets.impl.MarkerPacketImpl;
import wifen.commons.network.packets.impl.MarkerRemovedPacketImpl;
import wifen.commons.network.packets.impl.MediumPacketImpl;
import wifen.commons.network.packets.impl.MediumRemovedPacketImpl;
import wifen.commons.network.packets.impl.PlayerListPacketImpl;
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
		gameEventService.fireEvent(playerName + " ist dem Spiel als " +"\""+role+"\""+ " beigetreten.");
	}
	
	@Override
	public void addGameEvent(String eventMessage) {
		getGameState().getEreignisLog().add(eventMessage);
	}
	
	@Override
	public void addChatMessage(String chatMessage) {
		getGameState().getChatLog().add(chatMessage);
	}
	
	@Override
	public void addMarker(MarkerModel marker) {
		getGameState().getViewModel().placeMarker(marker);
	}

	@Override
	public void addMedia(Medium media) {
		getGameState().getMedia().add(media);
	}
	
	public void addMediumView(MediumModel medium) {
		getGameState().getViewModel().placeMedium(medium);
	}
	
	// <--- ConnectionListener --->
	
	@Override
	public void handle(ConnectionEvent connectionEvent) {
		if (connectionEvent instanceof PacketReceivedEvent) {
			PacketReceivedEvent packetEvent = (PacketReceivedEvent) connectionEvent;
			if (packetEvent.getPacket() instanceof EnterGamePacket) {
				EnterGamePacket packet = (EnterGamePacket) packetEvent.getPacket();
				
				// Nachschauen ob noch Platz im Aktiven Game ist
				if (!packet.getName().trim().equals("") 
						&& getPlayerConns().size() < getGameState().getMaxPlayerCount()){
					addPlayer(packet.getName(), getGameState().getStandardPlayerRole(), connectionEvent.getSource());
				}
				else
				{
					// Wenn nein: Spieler bekommt eine Fehlermeldung das das Spiel voll ist
					connectionEvent.getSource().sendPacket(new EnterGamePacketImpl(null, null));
				}
				
			} else if (packetEvent.getPacket() instanceof MarkerPacket) {
				MarkerPacket packet = (MarkerPacket) packetEvent.getPacket();
				
				addMarker(packet.getMarkerModel());
				getPlayerConns().values()
				.forEach((connection) -> connection.sendPacket(new MarkerPacketImpl(packet.getMarkerModel())));
			} 
			else if(packetEvent.getPacket() instanceof MarkerRemovedPacket){
				MarkerRemovedPacket packet = (MarkerRemovedPacket) packetEvent.getPacket();

				getGameState().getViewModel().removeMarker(packet.getMarkerId());
				getPlayerConns().values()
				.forEach((connection) -> connection.sendPacket(new MarkerRemovedPacketImpl(packet.getMarkerId())));
			}
			
			else if(packetEvent.getPacket() instanceof PlayerListPacket){
				ArrayList<Player> players = new ArrayList<>(getPlayerConns().keySet());
				connectionEvent.getSource().sendPacket(new PlayerListPacketImpl(players));
			}
			else if(packetEvent.getPacket() instanceof MediumPacket) {
				MediumPacket packet = (MediumPacket) packetEvent.getPacket();
				addMediumView(packet.getMediumModel());
				getPlayerConns().values().forEach((connection) -> connection.sendPacket(new MediumPacketImpl(packet.getMediumModel())));
			}
			else if(packetEvent.getPacket() instanceof MediumRemovedPacket) {
				MediumRemovedPacket packet = (MediumRemovedPacket) packetEvent.getPacket();
				getGameState().getViewModel().removeMedium(packet.getMediumId());
				getPlayerConns().values().forEach((connection) -> connection.sendPacket(new MediumRemovedPacketImpl(packet.getMediumId())));
			}
			
		} else if (connectionEvent instanceof ConnectionClosedEvent) {
			Player p = getConnectionPlayer(connectionEvent.getSource());
			getPlayerConns().remove(p);
			gameEventService.fireEvent(p.getName() + " hat das Spiel verlassen.");
		}
	}

	public Player getConnectionPlayer(Connection conn) {
		for (Entry<Player, Connection> entry : getPlayerConns().entrySet()) {
			if (entry.getValue().equals(conn)) return entry.getKey();
		}
		return null;
	}
	
	// Getter & Setter

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
