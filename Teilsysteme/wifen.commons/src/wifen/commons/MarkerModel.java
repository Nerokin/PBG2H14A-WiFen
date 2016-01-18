package wifen.commons;

import java.io.Serializable;
import java.util.UUID;

/**
 * Put description here
 * 
 * @author unkown
 *
 */
public class MarkerModel implements Serializable {
	
	private static final long serialVersionUID = 9190467360671950047L;
	
	private final UUID id = UUID.randomUUID();
	private Player owner;
	private MarkerType type;
	private double posx;
	private double posy;
	private String desc;
	
	// Constructors	
	/**
	 * Put description here
	 * 
	 * @param x
	 * @param y
	 * @param mt
	 * @param d
	 */
	public MarkerModel(Player owner, double x, double y, MarkerType mt, String d) {
		this.owner = owner;
		this.type = mt;
		this.posx = x;
		this.posy = y;
		this.desc = d;
	}
	
	// Methods
	
	@Override
	public boolean equals(Object obj) {
		return obj != null
				&& obj instanceof MarkerModel
				&& getId().equals(((MarkerModel) obj).getId());
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	// Getters & Setters	
	
	public MarkerType getType() {
		return type;
	}

	public void setType(MarkerType type) {
		this.type = type;
	}

	public double getPosx() {
		return posx;
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public double getPosy() {
		return posy;
	}

	public void setPosy(double posy) {
		this.posy = posy;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public UUID getId() {
		return id;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	
}
