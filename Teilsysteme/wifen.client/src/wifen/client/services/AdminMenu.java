package wifen.client.services;

import wifen.commons.SpielerRolle;

/**
 * Put description here
 * 
 * @author Macel Ortmann
 *
 */
public class AdminMenu {
	boolean isAdmin = false;

	/**
	 * Im Spielfeld Medienbibliothek für nicht Admins ausgrauen/unbenutzbar machen
	 * 
	 * @param spieler
	 * @return
	 */
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
