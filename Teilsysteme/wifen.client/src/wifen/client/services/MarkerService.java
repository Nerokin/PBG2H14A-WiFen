package wifen.client.services;

import wifen.client.resources.Marker;
import wifen.client.resources.MarkerType;
import wifen.client.ui.controllers.Spielfeld;

public interface MarkerService {

	public MarkerType getSelectedType();

	public void placeMarker(MarkerType mt, double x, double y);

	public void removeMarker(Marker m);

	/*
	 * Optional functionalities public void changeMarkerType(Marker m,
	 * MarkerType mt);
	 * 
	 * public void changeMarkerDescription(Marker m, String newd);
	 */
}
