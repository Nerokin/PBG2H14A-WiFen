package wifen.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Put description here
 *
 * @author unknown
 *
 */
public class SpielfeldModel implements Serializable {

	// Class Constants
	private static final long serialVersionUID = -8355739346048731596L;

	private GridType typ;
	private ArrayList<MediumModel> mediums;
	private ArrayList<MarkerModel> markers;
	private double sizeX;
	private double sizeY;
	private BackgroundImage backgroundImage;

	// Constructors

	/**
	 * Put description here
	 *
	 * @param t
	 */
	public SpielfeldModel(GridType t, double x, double y, BackgroundImage backgroundImage) {
		this.backgroundImage = backgroundImage;
		this.typ = t;
		this.mediums = new ArrayList<MediumModel>();
		this.markers = new ArrayList<MarkerModel>();
		this.sizeX = x;
		this.sizeY = y;
	}

	/**
	 * Put description here
	 *
	 * @param sm
	 */
	public SpielfeldModel(SpielfeldModel sm) {
		this.typ = sm.getTyp();
		this.mediums = new ArrayList<MediumModel>(sm.getMediums());
		this.markers = new ArrayList<MarkerModel>(sm.getMarkers());
		this.sizeX = sm.getSizeX();
		this.sizeY = sm.getSizeY();
	}

	// Methods

	/**
	 * Put description here
	 *
	 * @param m
	 */
	public void placeMarker(MarkerModel m) {
		getMarkers().remove(m);
		getMarkers().add(m);
	}

	/**
	 * Put description here
	 *
	 * @param m
	 */
	public void removeMarker(UUID id) {
		MarkerModel mm = null;
		for(MarkerModel n : getMarkers()){
			if(n.getId().equals(id)){
				mm = n;
			}
		}
		this.getMarkers().remove(mm);
	}

	/**
	 * Put description here
	 *
	 * @param mm
	 */
	public void placeMedium(MediumModel mm) {
		getMediums().remove(mm);
		getMediums().add(mm);
	}

	/**
	 * Put description here
	 *
	 * @param mm
	 */
	public void removeMedium(UUID id) {
		MediumModel mm = null;
		for(MediumModel m : getMediums()) {
			if(m.getId().equals(id)) {
				mm = m;
			}
		}
		getMediums().remove(mm);
	}

	// Getters & Setters

	public GridType getTyp() {
		return typ;
	}
	public ArrayList<MediumModel> getMediums() {
		return mediums;
	}
	public ArrayList<MarkerModel> getMarkers() {
		return markers;
	}

	public double getSizeY() {
		return this.sizeY;
	}

	public double getSizeX() {
		return this.sizeX;
	}
	public void setSizeX(double x) {
		this.sizeX=x;
	}

	public BackgroundImage getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(BackgroundImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

}
