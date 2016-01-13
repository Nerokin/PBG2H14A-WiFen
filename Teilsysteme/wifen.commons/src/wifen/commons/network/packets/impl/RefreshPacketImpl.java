package wifen.commons.network.packets.impl;

import wifen.commons.GameStateModel;
import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.RefreshPacket;

public class RefreshPacketImpl extends PacketImpl implements RefreshPacket{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2122498255546757610L;
	
	private GameStateModel model;
	
	//Constructor
	
	public RefreshPacketImpl(GameStateModel model){
		super();
		this.model = model;
	}

	//Setter u. Getter
	
	@Override
	public GameStateModel getGameStateModel() {
		return model;
	} 
}
