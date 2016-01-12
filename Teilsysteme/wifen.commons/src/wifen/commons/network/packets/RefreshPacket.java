package wifen.commons.network.packets;

import wifen.commons.GameStateModel;
import wifen.commons.network.Packet;

public interface RefreshPacket extends Packet{
	public GameStateModel getGameStateModel();
}
