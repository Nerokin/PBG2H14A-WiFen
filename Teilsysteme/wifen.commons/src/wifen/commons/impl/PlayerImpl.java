package wifen.commons.impl;

import java.util.UUID;

import wifen.commons.Player;
import wifen.commons.SpielerRolle;


public class PlayerImpl implements Player {

	// Class Constants
	
	private static final long serialVersionUID = -4227720921093737707L;
	
	// Attribute
	
	private final UUID id = UUID.randomUUID();
	private String name;
	private SpielerRolle rolle;
	
	// Constructor(s)
	
	public PlayerImpl(String name, SpielerRolle role){
		this.name = name;
		this.rolle = role;
	}
	
	// Methods
	
	@Override
	public boolean equals(Object obj) {
		return obj != null
				&& obj instanceof Player
				&& getId().equals(((Player) obj).getId());
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	// Getter & Setter
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String newName) {
		this.name = newName;
		
	}

	@Override
	public SpielerRolle getRolle() {
		return this.rolle;
	}

	@Override
	public void setRolle(SpielerRolle newRolle) {
		this.rolle = newRolle;
	}

	public UUID getId() {
		return id;
	}
}
