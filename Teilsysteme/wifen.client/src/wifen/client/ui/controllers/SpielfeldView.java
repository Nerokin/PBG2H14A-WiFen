
package wifen.client.ui.controllers;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import wifen.client.application.ClientApplication;
import wifen.client.resources.MarkerView;
import wifen.client.resources.MediumView;
import wifen.client.services.ClientGameeventService;
import wifen.client.services.GameService;
import wifen.client.services.MarkerService;
import wifen.commons.GridType;
import wifen.commons.MarkerModel;
import wifen.commons.MarkerType;
import wifen.commons.Medium;
import wifen.commons.MediumModel;
import wifen.commons.SpielfeldModel;

/**
 * View for displaying a given Playfield Model
 *
 * @author Steffen M�ller
 * @author Nicolas Braun
 *
 */
public class SpielfeldView extends ScrollPane implements MarkerService {
	private int tilesPerRow;
	private int tilesPerCol;
//	private double lastX;
//	private double lastY;
//	private Timer timer;
//	private long lastTime;
//	private Polyline drawing;
//	private boolean drawn = false;
	private boolean hasPressed = false;
//	private boolean hasDragged = false;
//	private Double[] points;
	private double values[] = new double[4];
	private SpielfeldModel model;
//	private MarkerWindow markerWindow;
	private Polyline grid;
	private SpielfeldView self;

	/**initialize a field of given size for given model
	 * tilesPerRow => rectangle per row, may be null for model.getType() == GridType.NONE
	 * tilesPerCol => rectangle per column, may be null for model.getType() == GridType.NONE || GridType.Hex
	 * sm => the data model of the field to display
	 * */
	public SpielfeldView(int tilesPerRow, int tilesPerCol, SpielfeldModel sm, MarkerWindow markerWindow) {
		super(new Pane());
		self=this;
		this.model = sm;
		((Pane) this.getContent()).setMaxSize(this.model.getSizeX(), this.model.getSizeY());
		((Pane) this.getContent()).setMinSize(this.model.getSizeX(), this.model.getSizeY());
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.setPannable(true);
		this.tilesPerCol = tilesPerCol;
		this.tilesPerRow = tilesPerRow;
//		this.markerWindow = markerWindow;
		if (sm.getBackgroundImage() != null)
			((Pane)this.getContent()).setBackground(
					new Background(
							new BackgroundImage(
									sm.getBackgroundImage().getImage(),
									BackgroundRepeat.NO_REPEAT,
									BackgroundRepeat.NO_REPEAT,
									BackgroundPosition.CENTER,
									new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true))));
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
			double a = model.getSizeX()/tilesPerRow;
			double b = model.getSizeY()/tilesPerCol;
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
			double height = model.getSizeY()/tilesPerCol;
			double b = height/2;
			double c = b/Math.sin(Math.toRadians(60));
			double a = 0.5*c;
			this.tilesPerRow = (int) (model.getSizeX()/(2*a+2*c));
			((Pane) this.getContent()).setMaxSize((this.tilesPerRow*(2*a+2*c)), this.model.getSizeY());
//			this.setMaxWidth((this.tilesPerRow*(2*a+2*c)));
//			this.model.setSizeX((this.tilesPerRow*(2*a+2*c)));
			grid  = new Polyline();
			System.out.println(tilesPerRow);
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
			this.addToView(grid);
		}

		/* Register Listeners */
		this.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
//				hasDragged = false;
//				lastTime=0;
//				if(event.getButton() == MouseButton.PRIMARY && event.isAltDown()){
//					System.out.println("Beginn Drawing!");
//					((ScrollPane) event.getSource()).setPannable(false);
//					lastX = event.getX();
//					lastY = event.getY();
//					points = new Double[2];
//					points[0] = lastX;
//					points[1] = lastY;
//					drawing = new Polyline();
//				}
				if(event.getButton() == MouseButton.SECONDARY && event.isControlDown()){
					hasPressed = true;
					((ScrollPane) event.getSource()).setPannable(false);
					values[0]=event.getX();
					values[1]=event.getY();
				}
			}
		});
//		this.setOnMouseMoved(new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent event) {
//				if(event.isAltDown() && MouseButton.PRIMARY == event.getButton()){
//					System.out.println("Dragging");
//					drawn = true;
//					timer = new Timer();
//				    timer.scheduleAtFixedRate(new TimerTask() {
//				        @Override
//				        public void run() {
//				        	//double x = MouseInfo.getPointerInfo().getLocation().getX();
//				        	//double y = MouseInfo.getPointerInfo().getLocation().getY();
//				            if(distance(lastX,lastY,event.getX(),event.getY())>2){
//				            	for(Double p : points){
//				            		System.out.println(p);
//				            	}
//				            	Double[] help = points;
//				            	points = new Double[help.length+2];
//				            	points = help;
//				            	lastX = event.getX();
//				            	lastY = event.getY();
//				            	points[points.length-2]= lastX;
//				            	points[points.length-1]= lastY;
//				            }
//				        }
//				    }, 0, 500);
//				}
//			}
//
//		});
//		this.setOnDragDetected(new EventHandler<MouseEvent>(){
//
//			@Override
//			public void handle(MouseEvent event) {
//				hasDragged = true;
//			}
//
//		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(hasPressed && event.isControlDown()){
					((ScrollPane) event.getSource()).setPannable(true);
					values[2]=event.getX();
					values[3]=event.getY();
					DecimalFormat df = new DecimalFormat("#.##");
					System.out.println("Mouse Released");
					ClientApplication.instance().getServiceRegistry().getServiceProviders(ClientGameeventService.class, false).next().makeLocalGameevent("Lokal:", (df.format(distance(values[0],values[1],values[2],values[3])))+" Units");
				}
//				if(drawn){
//					((ScrollPane) event.getSource()).setPannable(true);
//					System.out.println("Drawn!");
//					drawn = false;
//					timer.cancel();
//					drawing.getPoints().addAll(points);
//					((SpielfeldView) event.getSource()).addToView(drawing);
//				}
				hasPressed = false;
			}

		});
		/*((Pane) this.getContent()).setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(!hasDragged&&event.getButton() == MouseButton.PRIMARY) {
					if(event.getX() > ((Pane) event.getSource()).getWidth() || event.getY() > ((Pane) event.getSource()).getHeight()) {
						System.out.println("Invalid coordinates for placing Marker: Out of field!");
					} else {
						if(event.isControlDown()){
							File file = new File(getClass().getResource("../../resources/note.png").getFile());
							Image i = new Image(file.toURI().toString());
							MarkerModel m = null;
							try {
								m = new MarkerModel(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer(), event.getX(), event.getY(), new MarkerType("note", i), "");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							model.placeMarker(m);
							((Pane) event.getSource()).getChildren().add(new MarkerView(m, self));
							// snapshot renders all children again so the added element is displayed properly
							self.snapshot(null,null);
						}
						else{
							MarkerModel m = new MarkerModel(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer(), event.getX(), event.getY(), getSelectedType(), "");
							ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMarkerPlaced(m);
							// snapshot renders all children again so the added element is displayed properly
							self.snapshot(null,null);
					}
					}
				}
			}
		});*/

		getContent().setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				if(e.getDragboard().hasContent(MedienbibliothekController.MEDIUM_FORMAT) && e.getDragboard().getContent(MedienbibliothekController.MEDIUM_FORMAT) instanceof Medium) {
					e.acceptTransferModes(TransferMode.COPY);
				}
				else if(e.getDragboard().hasContent(MarkerWindow.MARKER_FORMAT) && e.getDragboard().getContent(MarkerWindow.MARKER_FORMAT) instanceof MarkerType) {
					e.acceptTransferModes(TransferMode.COPY);
				}
				e.consume();
			}
		});

		getContent().setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent e) {
				Dragboard db = e.getDragboard();
				if(db.hasContent(MedienbibliothekController.MEDIUM_FORMAT) && db.getContent(MedienbibliothekController.MEDIUM_FORMAT) instanceof Medium) {
					ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next()
					.sendMediumPlaced(new MediumModel(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer(), (Medium) db.getContent(MedienbibliothekController.MEDIUM_FORMAT), e.getX(), e.getY()));
					e.setDropCompleted(true);
				} else if(db.hasContent(MarkerWindow.MARKER_FORMAT) && db.getContent(MarkerWindow.MARKER_FORMAT) instanceof MarkerType) {
					MarkerType mt = (MarkerType) db.getContent(MarkerWindow.MARKER_FORMAT);
					MarkerModel m = new MarkerModel(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer(), e.getX(), e.getY(), mt, "");
					ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMarkerPlaced(m);
					e.setDropCompleted(true);
				}
				else e.setDropCompleted(false);
			}
		});
	}

	// Methods
//	private void getImageResolution(String url) {
//		// TODO
//	}

	private void addMarkersFromModel() {
		for(MarkerModel m : this.model.getMarkers()) {
			this.addToView(new MarkerView(m, this));
		}
	}

	private void addFilesFromModel() {
		for(MediumModel m : this.model.getMediums()) {
			this.addToView(new MediumView(m, this));
		}
	}

	private void removeAllMarkerViews() {
		for(Node n : ((Pane)this.getContent()).getChildren()) {
			if(n instanceof MarkerView) {
				this.removeFromView(n);
			}
		}
	}

	private void removeAllFileViews() {
		for(Node n : ((Pane)this.getContent()).getChildren()) {
			if(n instanceof MediumView) {
				this.removeFromView(n);
			}
		}
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
	public double distance(double x1,double y1, double x2, double y2){
		double dis = 0;
		dis = (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
		dis = Math.sqrt(dis);
		return dis;
	}

	// Getters & Setters

	public SpielfeldView getView() {
		return this;
	}

	public void setModel(SpielfeldModel sm) {
		removeAllViews();
		boolean switchtype = sm.getTyp() != this.model.getTyp();
		this.model = sm;
		if(switchtype) {
			removeFromView(this.grid);
			draw(this.model.getTyp());
		}
		addAllFromModel();
		// snapshot renders all children again so the added element is displayed properly
		this.snapshot(null,null);
	}

	public SpielfeldModel getModel(){
		return model;
	}

	public void AddMedium(MediumModel mediumModel) {
		boolean neu = true;
		for(Node n : ((Pane)this.getContent()).getChildren()){
			if(n instanceof MediumView){
				System.out.println(n);
				if(((MediumView)n).getMedium().equals(mediumModel)){
					((MediumView)n).setTranslateX(mediumModel.getPosx());
					((MediumView)n).setTranslateY(mediumModel.getPosy());
					((MediumView)n).setMedium(mediumModel);
					((CheckMenuItem)((MediumView)n).getContextMenu().getItems().get(1)).setSelected(((MediumView)n).getMedium().getIsStatic());

					neu = false;
					break;
				}
			}
		}
		if(neu){
			Platform.runLater(() -> addToView(new MediumView(mediumModel, self)));
		}
		Platform.runLater(() -> model.placeMedium(mediumModel));
	}

	public void RemoveMedium(UUID mediumId) {
		MediumView mv = null;
		for(Node n : ((Pane)this.getContent()).getChildren()){
			if(n instanceof MediumView){
				if(((MediumView)n).getMedium().getId().equals(mediumId)){
					mv=(MediumView) n;
				}
			}
		}
		final MediumView mvReturn = mv;
		Platform.runLater(() -> removeFromView(mvReturn));
		Platform.runLater(() -> model.removeMarker(mediumId));
	}


	public void AddMarker(MarkerModel m){
		boolean neu = true;
		for(Node n : ((Pane)this.getContent()).getChildren()){
			if(n instanceof MarkerView){
				if(((MarkerView)n).getMarkerModel().equals(m)){
					((MarkerView)n).setTranslateX(m.getPosx());
					((MarkerView)n).setTranslateY(m.getPosy());
					((MarkerView)n).setMarker(m);
					((CheckMenuItem)((MarkerView)n).getContextMenu().getItems().get(1)).setSelected(((MarkerView)n).getMarkerModel().getIsStatic());

					neu = false;
					break;
				}
			}
		}
		if(neu){
			Platform.runLater(() -> addToView(new MarkerView(m, self)));
		}
		Platform.runLater(() -> model.placeMarker(m));
	}

	public void RemoveMarker(UUID ID){
		MarkerView mv = null;
		for(Node n : ((Pane)this.getContent()).getChildren()){
			if(n instanceof MarkerView){
				if(((MarkerView)n).getMarkerModel().getId().equals(ID)){
					mv=(MarkerView) n;
				}
			}
		}
		final MarkerView mvReturn = mv;
		Platform.runLater(() -> removeFromView(mvReturn));
		Platform.runLater(() -> model.removeMarker(ID));
	}

	/**Add Node to Pane*/
	public void addToView(Node value){
		((Pane) this.getContent()).getChildren().add(value);
	}
	/**Delete Node from Pane*/
	public void removeFromView(Node value){
		((Pane) this.getContent()).getChildren().remove(value);
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
