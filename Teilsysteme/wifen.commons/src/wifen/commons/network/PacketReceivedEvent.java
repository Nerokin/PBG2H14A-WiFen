package wifen.commons.network;

public interface PacketReceivedEvent extends ConnectionEvent{
	Packet getPacket();
}
