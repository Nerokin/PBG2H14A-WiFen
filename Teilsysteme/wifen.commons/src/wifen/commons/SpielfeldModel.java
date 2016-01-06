package wifen.commons;

import java.util.ArrayList;

import wifen.commons.services.FileNode;

public class SpielfeldModel {
	
	private GridType typ;
	private ArrayList<FileNode<?>> files;
	private ArrayList<MarkerModel> markers;
	
	// Constructors
	
	public SpielfeldModel(GridType t) {
		this.typ = t;
		this.files = new ArrayList<FileNode<?>>();
		this.markers = new ArrayList<MarkerModel>();
	}
	
	public SpielfeldModel(SpielfeldModel sm) {
		this.typ = sm.getTyp();
		this.files = new ArrayList<FileNode<?>>(sm.getFiles());
		this.markers = new ArrayList<MarkerModel>(sm.getMarkers());
	}
	
	// Methods
	
	public void placeMarker(MarkerModel m) {
		this.getMarkers().add(m);
	}
	
	public void placeMarker(MarkerType mt, double x, double y, String d) {
		this.getMarkers().add(new MarkerModel(x, y, mt, d));
	}
	
	public void removeMarker(MarkerModel m) {
		this.getMarkers().remove(m);
	}
	
	public void placeFileNode(FileNode<?> fn) {
		this.getFiles().add(fn);
	}
	
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
}
