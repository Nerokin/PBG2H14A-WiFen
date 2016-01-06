package wifen.client.services;

import wifen.commons.MarkerType;

public interface MarkerService {
	
	public MarkerType getSelectedType();
	
	/*
	 * Optional functionalities
	public void changeMarkerType(Marker m, MarkerType mt);
	
	public void changeMarkerDescription(Marker m, String newd);
	 */
}
