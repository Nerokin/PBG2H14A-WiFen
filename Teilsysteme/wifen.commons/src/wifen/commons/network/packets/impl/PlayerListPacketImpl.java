package wifen.commons.network.packets.impl;



import java.util.ArrayList;

import wifen.commons.Player;
import wifen.commons.network.impl.PacketImpl;
import wifen.commons.network.packets.PlayerListPacket;

public class PlayerListPacketImpl extends PacketImpl implements PlayerListPacket{


	private static final long serialVersionUID = 1132211500198519533L;
	private ArrayList<Player> currentPlayerList = new ArrayList<Player>();
	
	
	public PlayerListPacketImpl(ArrayList<Player> playerList){
		this.currentPlayerList = playerList;
	}
	
	// Getter & Setter

	public ArrayList<Player> getCurrentPlayerList() {
		return currentPlayerList;
	}


	
}
