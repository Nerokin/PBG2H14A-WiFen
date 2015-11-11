package wifen.commons.network;

/**
 * Super interface for all information containers sent 
 * and received by {@linkplain Connection}s.
 * 
 * @author Konstantin Schaper (pbg2h14ash)
 * @requirement LF190
 */
public interface Packet {
	
	Connection getSource();

}
