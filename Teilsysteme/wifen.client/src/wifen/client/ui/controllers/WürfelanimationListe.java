package wifen.client.ui.controllers;

import java.util.ArrayList;

public class WürfelanimationListe {
	private ArrayList<String> alleWuerfelEinzeln = new ArrayList<String>();
	public WürfelanimationListe(){

	}
	public void addWelcherWuerfel(String thisWurfel){

		alleWuerfelEinzeln.add(thisWurfel);
	}
	public ArrayList<String> getAlleWurfel(){
		return alleWuerfelEinzeln;
	}
	public void resetWuerfelList(){
		alleWuerfelEinzeln.clear();
	}



}
