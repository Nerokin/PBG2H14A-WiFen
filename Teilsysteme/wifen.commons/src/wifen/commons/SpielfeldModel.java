package wifen.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import javafx.scene.layout.Pane;
import wifen.commons.services.FileNode;

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
	private ArrayList<FileNode<?>> files;
	private ArrayList<MarkerModel> markers;
	private double sizeX;
	private double sizeY;

	// Constructors

	/**
	 * Put description here
	 *
	 * @param t
	 */
	public SpielfeldModel(GridType t, double x, double y) {
		this.typ = t;
		this.files = new ArrayList<FileNode<?>>();
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
		this.files = new ArrayList<FileNode<?>>(sm.getFiles());
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
	 * @param fn
	 */
	public void placeFileNode(FileNode<?> fn) {
		this.getFiles().add(fn);
	}

	/**
	 * Put description here
	 *
	 * @param fn
	 */
	public void removeFileNode(FileNode<?> fn) {
		this.getFiles().add(fn);
	}

	// Getters & Setters

	public GridType getTyp() {
		return typ;
	}
	public ArrayList<FileNode<?>> getFiles() {
		return files;
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
}
