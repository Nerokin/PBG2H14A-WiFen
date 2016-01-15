package wifen.commons.network.packets.impl;

import wifen.commons.GameStateModel;
import wifen.commons.Player;
import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.EnterGamePacket;

public class EnterGamePacketImpl extends PacketImpl implements EnterGamePacket{

	private static final long serialVersionUID = 1L;
	private final String name;
	private final GameStateModel gameState;
	private final Player player;

	
	public EnterGamePacketImpl(String name){
		this.name = name;
		this.gameState = null;
		this.player = null;
	}
	
	public EnterGamePacketImpl(Player player, GameStateModel state){
		this.name = null;
		this.gameState = state;
		this.player = player;
	}
	
	@Override
	public String getName() {		
		return name;
	}

	@Override
	public GameStateModel getInitialModel() {
		return gameState;
	}

	public Player getPlayer() {
		return player;
	}

}
