package wifen.client.ui.controllers;

import java.util.ArrayList;

public class W�rfelanimationListe {
	private ArrayList<String> alleWuerfelEinzeln = new ArrayList<String>();
	public W�rfelanimationListe(){

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
