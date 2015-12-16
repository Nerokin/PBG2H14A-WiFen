package wifen.client.ui.controllers;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import wifen.client.resources.Marker;
import wifen.client.resources.MarkerType;
import wifen.client.services.MarkerService;
import wifen.client.ui.controllers.MarkerView;

public class Spielfeld implements MarkerService {
	private Stage stage;
	private ScrollPane scrollPane;
	private Pane pane;
	private double sizeFieldX;
	private double sizeFieldY;
	private double sizeSceneX;
	private double sizeSceneY;
	private int tilesPerRow;
	private int tilesPerCol;
	private int typ;
	private double lastX=0;
	private double lastY=0;
	private boolean hasDragged = false;
	private MarkerView markerWindow;
	
	private Spielfeld(double sizeFieldX, double sizeFieldY, double sizeSceneX, double sizeSceneY, int tilesPerRow, int tilePerCol, int type, MarkerView markerWindow) {
		this.stage = new Stage();
		this.pane = new Pane();
		this.scrollPane = new ScrollPane(pane);
		this.sizeFieldX = sizeFieldX;
		this.sizeFieldY = sizeFieldY;
		this.sizeSceneX = sizeSceneX;
		this.sizeSceneY = sizeSceneY;
		scrollPane.setMaxHeight(sizeFieldY);
		scrollPane.setMaxWidth(sizeFieldX);
		scrollPane.setMinHeight(sizeFieldY);
		scrollPane.setMinWidth(sizeFieldX);
		stage.setHeight(sizeSceneY);
		stage.setWidth(sizeSceneX);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setPannable(true);
		this.tilesPerCol = tilePerCol;
		this.tilesPerRow = tilesPerRow;
		this.typ = type;
		this.markerWindow = markerWindow; 
	}
	
	/**initialize a field with a rectangle grid
	 * sizeFieldX => Stack width
	 * sizeFieldY => Stack height
	 * sizeSceneX => Scene width
	 * sizeSceneY => Scene height
	 * tilesPerRow => rectangle per row
	 * tilesPerCol => rectangle per column*/
	public Spielfeld(double sizeFieldX, double sizeFieldY, double sizeSceneX, double sizeSceneY, int tilesPerRow, int tilesPerCol, MarkerView markerWindow){
		this(sizeFieldX, sizeFieldY, sizeSceneX, sizeSceneY, tilesPerRow, tilesPerCol, 1, markerWindow);
	}
	/**initialize a field with a hexagon grid
	 * sizeFieldX => Stack width
	 * sizeFieldY => Stack height
	 * sizeSceneX => Scene width
	 * sizeSceneY => Scene height
	 * tilesPerCol => hexagons per column*/
	public Spielfeld(double sizeFieldX, double sizeFieldY, double sizeSceneX, double sizeSceneY, int tilesPerCol, MarkerView markerWindow){
		this(sizeFieldX, sizeFieldY, sizeSceneX, sizeSceneY, 0, tilesPerCol, 2, markerWindow);
	}
	/**initialize a field without a grid
	 * sizeFieldX => Stack width
	 * sizeFieldY => Stack height
	 * sizeSceneX => Scene width
	 * sizeSceneY => Scene height*/
	public Spielfeld(double sizeFieldX, double sizeFieldY, double sizeSceneX, double sizeSceneY, MarkerView markerWindow){
		this(sizeFieldX, sizeFieldY, sizeSceneX, sizeSceneY, 0, 0, 0, markerWindow);
	}
	/**draws the field in a new scene*/
	public void draw(){
		Scene scene = new Scene(scrollPane,sizeFieldX,sizeFieldY);
		stage.setScene(scene);
		scrollPane.setStyle("-fx-background-color: #FFFFFF;");
		if(this.typ == 1){
			Polyline line = new Polyline();
			double a = scrollPane.getWidth()/tilesPerRow;
			double b = scrollPane.getHeight()/tilesPerCol;
			line.autosize();
			line.getPoints().addAll(new Double[]{
			        0.00, 0.00,
			        0.00, scrollPane.getHeight()});
			for(int i=0; i<tilesPerCol;i++){
				for(int j=0; j<tilesPerRow;j++){
					line.getPoints().addAll(new Double[]{
					        j*a, i*b,
					        (j+1)*a, i*b,
					        (j+1)*a, (i+1)*b,
					        j*a, (i+1)*b,
					        j*a, i*b});				
				}
				line.getPoints().addAll(new Double[]{
				        (tilesPerRow-1)*a, (i+1)*b});	
			}
			this.add(line);
		}else if(this.typ == 2){
			boolean toggle = true;
			double height = scrollPane.getHeight()/tilesPerCol;
			double b = height/2;
			double c = b/Math.sin(Math.toRadians(60));
			double a = 0.5*c;
			int tilesPerRow = (int) (scrollPane.getWidth()/(2*a+2*c));
			Polyline line = new Polyline();
			for(int i=0; i<tilesPerCol;i++){
				if(toggle){
					for(int j=0; j<tilesPerRow;j++){
						line.getPoints().addAll(new Double[]{
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
						line.getPoints().addAll(new Double[]{
								(a+c+((2*(c+a))*(tilesPerRow-1))), (i*(2*b))+(2*b),
								((2*a+2*c)*(tilesPerRow-1))+(2*a+c), ((i+1)*(2*b))+b,});
						toggle=false;
					}
				}else{
					for(int j=tilesPerRow-1; j>=0;j--){
						line.getPoints().addAll(new Double[]{
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
						line.getPoints().addAll(new Double[]{
								(a+((2*(c+a))*0)), ((i)*(2*b))+(2*b),
								(a+((2*(c+a))*0)), (i+1)*(2*b)});
						toggle=true;
					}
				}
			}
			this.add(line);
		}else if(this.typ==0){
			
		}
		scrollPane.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				hasDragged = false;
			}	
		});
		scrollPane.setOnDragDetected(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				hasDragged = true;				
			}
			
		});
		pane.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(!hasDragged&&event.getButton() == MouseButton.PRIMARY) {
					if(event.getX() > pane.getWidth() || event.getY() > pane.getHeight()) {
						System.out.println("Invalid coordinates for placing Marker: Out of field!");
					} else {
						System.out.println("Event X: "+event.getX()+" Event Y: "+event.getY());
						placeMarker(getSelectedType(), event.getX(), event.getY());
						// beinhaltet Neurendern der ScrollPane und aller Children, damit Marker korrekt nach dem Erstellen angezeigt werden
						scrollPane.snapshot(null,null);
					}
				}
			}
		});
		stage.setResizable(true);
		stage.show();
	}
	/**resize the grid. 
	 * sizeFieldX for the new X Value.
	 * sizeFieldY for the new Y Value.*/
	public void setGrid(double sizeFieldX,double sizeFieldY){
		this.sizeFieldX = sizeFieldX;
		this.sizeFieldY = sizeFieldY;
		draw();
	}
	/**resize the window.
	 * sizeSceneX for the new X Value.
	 * sizeSceneY for the new Y Value.*/
	public void setStage(double sizeSceneX, double sizeSceneY){
		this.sizeSceneX = sizeSceneX;
		this.sizeSceneY = sizeSceneY;
		draw();
	}
	/**Get all Children of the Pane*/
	public ObservableList<Node> getChildren(){
		return pane.getChildren();
	}
	/**Get ScrollPane*/
	public ScrollPane getScrollPane(){
		return scrollPane;
	}
	
	/**Add Node to stack*/
	public void add(Node value){
		pane.getChildren().add(value);
	}
	/**Delete Node from stack*/
	public void remove(Node value){
		pane.getChildren().remove(value);
	}
	
	/**
	 * Return the type of marker currently selected in the marker-selection window.
	 */
	@Override
	public MarkerType getSelectedType() {
		ImageView iv = markerWindow.getSelectedMarkerType();
		return new MarkerType(iv.getImage(), iv.getId());
	}
	
	/**
	 * Places one marker of the currently selected type at the mouse's current position.
	 */
	@Override
	public void placeMarker(MarkerType mt, double x, double y) {		
		add(new Marker(x, y, mt, this));
	}
	
	@Override
	public void removeMarker(Marker m) {
		pane.getChildren().remove(m);
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

	public double getSizeFieldX() {
		return sizeFieldX;
	}

	public double getSizeFieldY() {
		return sizeFieldY;
	}

	public double getSizeSceneX() {
		return sizeSceneX;
	}

	public double getSizeSceneY() {
		return sizeSceneY;
	}

	public int getTilesPerRow() {
		return tilesPerRow;
	}

	public int getTilesPerCol() {
		return tilesPerCol;
	}

	public int getTyp() {
		return typ;
	}
}
