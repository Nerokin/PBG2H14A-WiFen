package wifen.commons;

import java.io.Serializable;
import java.util.UUID;

public class MediumModel implements Serializable {
	
	// Class Constants

	private static final long serialVersionUID = 1L;
	
	// Attributes
	
	private final UUID id = UUID.randomUUID();
	private Medium medium;
	private double posx;
	private double posy;
	private String desc;
	private Player owner;
	private boolean isStatic;
	
	// Constructor(s)
	
	public MediumModel(Medium medium, double x, double y) {
		this(medium, x, y, "");
	}
	
	public MediumModel(Medium med, double x, double y, String description) {
		setMedium(med);
		setPosx(x);
		setPosy(y);
		setDesc(description);
	}
	
	// Methods
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" 
				+ "owner: " + getOwner()
				+ ", x: " + getPosx()
				+ ", y: " + getPosy()
				+ ", desc: " + getDesc()
				+ ", isStatic: " + getIsStatic()
				+ "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null
				&& obj instanceof MediumModel
				&& getId().equals(((MediumModel) obj).getId());
	}
	
	// Getters & Setters

	public Medium getMedium() {
		return medium;
	}

	public void setMedium(Medium medium) {
		this.medium = medium;
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

	public boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	
}
