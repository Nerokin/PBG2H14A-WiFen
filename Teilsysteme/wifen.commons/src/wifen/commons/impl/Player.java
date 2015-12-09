package wifen.commons.impl;

import wifen.commons.PlayerDataModel;
import wifen.commons.SpielerRolle;


public class Player implements PlayerDataModel{

	private String name;
	private SpielerRolle rolle;
	
	public Player(String name){
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
