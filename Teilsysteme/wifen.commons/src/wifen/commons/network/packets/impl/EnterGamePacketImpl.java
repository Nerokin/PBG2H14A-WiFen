package wifen.commons.network.packets.impl;

import wifen.commons.GameStateModel;
import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.EnterGamePacket;

public class EnterGamePacketImpl extends PacketImpl implements EnterGamePacket{

	private static final long serialVersionUID = 1L;
	private final String name;
	private final GameStateModel gameState;

	
	public EnterGamePacketImpl(String name, GameStateModel state){
		this.name = name;
		this.gameState = state;
	}
	
	@Override
	public String getName() {		
		return name;
	}

	@Override
	public GameStateModel getInitialModel() {
		return gameState;
	}

}
