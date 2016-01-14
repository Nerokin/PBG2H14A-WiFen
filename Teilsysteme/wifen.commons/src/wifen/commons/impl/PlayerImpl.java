package wifen.commons.impl;

import wifen.commons.Player;
import wifen.commons.SpielerRolle;


public class PlayerImpl implements Player {

	private String name;
	private SpielerRolle rolle;
	
	public PlayerImpl(String name){
		this.name = name;
	}
	
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
}
