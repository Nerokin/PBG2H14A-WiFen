
package wifen.client.ui.controllers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import wifen.client.resources.MarkerView;
import wifen.client.services.MarkerService;
import wifen.commons.GridType;
import wifen.commons.MarkerModel;
import wifen.commons.MarkerType;
import wifen.commons.SpielfeldModel;

/**
 * View for displaying a given Playfield Model
 * 
 * @author Steffen Müller
 * @author Nicolas Braun
 *
 */
public class SpielfeldView extends ScrollPane implements MarkerService {
	private int tilesPerRow;
	private int tilesPerCol;
	private boolean hasDragged = false;
	private Polyline scale;
	private SpielfeldModel model;
	private MarkerWindow markerWindow;
	private Polyline grid;
	private SpielfeldView self;
	
	/**initialize a field of given size for given model
	 * sizeFieldX => Stack width
	 * sizeFieldY => Stack height
	 * tilesPerRow => rectangle per row, may be null for model.getType() == GridType.NONE
	 * tilesPerCol => rectangle per column, may be null for model.getType() == GridType.NONE || GridType.Hex
	 * model => the data model of the field to display*/
	public SpielfeldView(int tilesPerRow, int tilesPerCol, SpielfeldModel sm, MarkerWindow markerWindow) {
		super(new Pane());
		this.model = sm;
		((Pane) this.getContent()).setMaxSize(this.model.getSizeX(), this.model.getSizeY());
		((Pane) this.getContent()).setMinSize(this.model.getSizeX(), this.model.getSizeY());
		this.setMaxHeight(this.model.getSizeY());
		this.setMaxWidth(this.model.getSizeX());
		this.setMinHeight(this.model.getSizeY());
		this.setMinWidth(this.model.getSizeX());
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.setPannable(true);
		this.tilesPerCol = tilesPerCol;
		this.tilesPerRow = tilesPerRow;
		this.markerWindow = markerWindow;
		this.self = this;
		draw(this.model.getTyp());
		addMarkersFromModel();
		addFilesFromModel();
	}

	/**draws the field in a new scene*/
	public void draw(GridType typ){
		/* Draw the Grid */
		this.setStyle("-fx-background-color: #FFFFFF;");
		if(typ == GridType.SQUARE){
			grid = new Polyline();
			double a = this.getWidth()/tilesPerRow;
			double b = this.getHeight()/tilesPerCol;
			grid.getPoints().addAll(new Double[]{
			        0.00, 0.00,
			        0.00, this.getHeight()});
			for(int i=0; i<tilesPerCol;i++){
				for(int j=0; j<tilesPerRow;j++){
					grid.getPoints().addAll(new Double[]{
					        j*a, i*b,
					        (j+1)*a, i*b,
					        (j+1)*a, (i+1)*b,
					        j*a, (i+1)*b,
					        j*a, i*b});				
				}
				grid.getPoints().addAll(new Double[]{
				        (tilesPerRow-1)*a, (i+1)*b});	
			}
			this.addToView(grid);
		}else if(typ == GridType.HEX){
			boolean toggle = true;
			double height = this.getHeight()/tilesPerCol;
			double b = height/2;
			double c = b/Math.sin(Math.toRadians(60));
			double a = 0.5*c;
			this.tilesPerRow = (int) (this.getWidth()/(2*a+2*c));
			grid  = new Polyline();
			for(int i=0; i<tilesPerCol;i++){
				if(toggle){
					for(int j=0; j<tilesPerRow;j++){
						grid.getPoints().addAll(new Double[]{
								(((2*(c+a))*j)), (i*(2*b))+b,
						        (a+((2*(c+a))*j)), i*(2*b),
						        (a+c+((2*(c+a))*j)), i*(2*b),
						        ((2*a+2*c)*j)+(2*a+c), (i*(2*b))+b,
						        (a+c+((2*(c+a))*j)), (i*(2*b))+(2*b),
						        (a+((2*(c+a))*j)), (i*(2*b))+(2*b),
						        (((2*(c+a))*j)), (i*(2*b))+b,
						        (a+((2*(c+a))*j)), i*(2*b),
						        (a+c+((2*(c+a))*j)), i*(2*b),
						        ((2*a+2*c)*j)+(2*a+c), (i*(2*b))+b});		
					}
					if(i<tilesPerCol-1){
						grid.getPoints().addAll(new Double[]{
								(a+c+((2*(c+a))*(tilesPerRow-1))), (i*(2*b))+(2*b),
								((2*a+2*c)*(tilesPerRow-1))+(2*a+c), ((i+1)*(2*b))+b,});
						toggle=false;
					}
				}else{
					for(int j=tilesPerRow-1; j>=0;j--){
						grid.getPoints().addAll(new Double[]{
								((2*a+2*c)*j)+(2*a+c), (i*(2*b))+b,
								(a+c+((2*(c+a))*j)), (i*(2*b))+(2*b),
								(a+((2*(c+a))*j)), (i*(2*b))+(2*b),
								(((2*(c+a))*j)), (i*(2*b))+b,
						        (a+((2*(c+a))*j)), i*(2*b),
						        (a+c+((2*(c+a))*j)), i*(2*b),
						        ((2*a+2*c)*j)+(2*a+c), (i*(2*b))+b,
						        (a+c+((2*(c+a))*j)), i*(2*b),
						        (a+((2*(c+a))*j)), i*(2*b),
						        (((2*(c+a))*j)), (i*(2*b))+b});			
					}
					if(i<tilesPerCol-1){
						grid.getPoints().addAll(new Double[]{
								(a+((2*(c+a))*0)), ((i)*(2*b))+(2*b),
								(a+((2*(c+a))*0)), (i+1)*(2*b)});
						toggle=true;
					}
				}
			}
			addToView(grid);
		}
		
		/* Draw the Scale */
		scale = new Polyline();
		scale.getPoints().addAll(new Double[]{
				50.0,40.0,
				50.0,50.0,
				106.7,50.0,
				106.7,40.0});
		((Pane) this.getContent()).getChildren().add(scale);
		
		/* Register Listeners */
		this.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				hasDragged = false;
			}	
		});
		this.setOnDragDetected(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				hasDragged = true;
			}
			
		});
		((Pane) this.getContent()).setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(!hasDragged&&event.getButton() == MouseButton.PRIMARY) {
					if(event.getX() > ((Pane) event.getSource()).getWidth() || event.getY() > ((Pane) event.getSource()).getHeight()) {
						System.out.println("Invalid coordinates for placing Marker: Out of field!");
					} else {
						System.out.println("Event X: "+event.getX()+" Event Y: "+event.getY());
						MarkerModel m = new MarkerModel(event.getX(), event.getY(), getSelectedType(), "");
						model.placeMarker(m);
						((Pane) event.getSource()).getChildren().add(new MarkerView(m, self));
						// snapshot renders all children again so the added element is displayed properly
						self.snapshot(null,null);
					}
				}
				scale.setTranslateX(self.getHvalue()*(model.getSizeX()-self.getScene().getWidth()));
				scale.setTranslateY(self.getVvalue()*(model.getSizeY()-self.getScene().getHeight()+32));
			}
		});
	}
	
	// Methods
	
	private void addMarkersFromModel() {
		for(MarkerModel m : this.model.getMarkers()) {
			this.addToView(new MarkerView(m, this));
		}
	}
	
	private void addFilesFromModel() {
		// TODO: Add File Views to children
	}
	
	private void removeAllMarkerViews() {
		for(Node n : ((Pane)this.getContent()).getChildren()) {
			if(n instanceof MarkerView) {
				this.removeFromView(n);
			}
		}
	}
	
	private void removeAllFileViews() {
		// TODO: Remove File Views from Children
	}
	
	private void removeAllViews() {
		removeAllMarkerViews();
		removeAllFileViews();
		// Add additional types of views here
	}
	
	private void addAllFromModel() {
		addMarkersFromModel();
		addFilesFromModel();
		// Add additional types of views here
	}
	
	// Getters & Setters
	
	public SpielfeldModel getModel() {
		return this.model;
	}
	
	public void setModel(SpielfeldModel sm) {
		removeAllViews();
		boolean switchtype = sm.getTyp() != this.model.getTyp();
		this.model = sm;
		if(switchtype) {
			removeFromView(this.grid);
			removeFromView(this.scale);
			draw(this.model.getTyp());
		}
		addAllFromModel();
		// snapshot renders all children again so the added element is displayed properly
		this.snapshot(null,null);
	}
	
	/**Add Node to Pane*/
	public void addToView(Node value){
		((Pane) this.getContent()).getChildren().add(value);
	}
	/**Delete Node from Pane*/
	public void removeFromView(Node value){
		((Pane) this.getContent()).getChildren().remove(value);
	}
	
	/**
	 * Return the type of marker currently selected in the marker-selection window.
	 */
	@Override
	public MarkerType getSelectedType() {
		ImageView iv = markerWindow.getSelectedMarkerType();
		return new MarkerType(iv.getImage(), iv.getId());
	}
	
	/*
	 * Optional functionalities
	@Override
	public void changeMarkerType(Marker m, MarkerType mt) {
		m.changeType(mt);		
	}
	@Override
	public void changeMarkerDescription(Marker m, String newd) {
		m.changeDescription(newd);
	}
	*/
}
