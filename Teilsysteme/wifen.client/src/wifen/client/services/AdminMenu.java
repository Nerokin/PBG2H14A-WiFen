package wifen.client.services;

import wifen.commons.SpielerRolle;

public class AdminMenu {
	boolean isAdmin = false;

	public boolean isAdmin(SpielerRolle spieler) {
		if (spieler == SpielerRolle.ADMIN) {
			isAdmin = true;
		} else {
			// Im Spielfeld Medienbibliothek(laut zettel) ausgrauen/unbenutzbar
			// machen aber Spielfeld/Ansichtsfenster mit der checkbox gibt es
			// noch nicht.
			// sies macht man mit
			// fx:id.setDisable(true); setzen. dann sollte es disabled sein
		}
		return isAdmin;
	}
}
