package wifen.commons.network.packets;
import wifen.commons.GameStateModel;
import wifen.commons.network.Packet;

public interface EnterGamePacket extends Packet{
	
	public String getName();
	public GameStateModel getInitialModel();

}
