package wifen.commons.network.packets;
import wifen.commons.GameStateModel;
import wifen.commons.Player;
import wifen.commons.network.Packet;

public interface EnterGamePacket extends Packet{
	
	// Request from client
	public String getName();
	
	// Send back to client
	public Player getPlayer();
	public GameStateModel getInitialModel();

}
