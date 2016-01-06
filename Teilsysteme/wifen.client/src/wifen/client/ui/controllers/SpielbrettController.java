package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import wifen.commons.GameStateModel;

public class SpielbrettController extends AnchorPane {

	//constants
	public static final String CSS_PATH = "/wifen/client/ui/css/Spielbrett.css";
	public static final String FXML_PATH ="/wifen/client/ui/views/Spielbrett.fxml";
	
	//Properties
	
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	private final ObjectProperty<GameStateModel> currentModel = new SimpleObjectProperty<>();

	@FXML ChoiceBox choiceID;
	@FXML Button optionID;
	@FXML Button refreshID;
	@FXML Button dcID;
	@FXML TextArea ereignis1ID;
	@FXML TextArea ereignis2ID;
	@FXML TextArea ereignis3ID;
	@FXML Pane SpielbrettID;
	
	private ObservableList<String> ereignisse = FXCollections.observableArrayList();
	private SpielfeldView playfield;
	private MarkerWindow markerWindow;
	
	//@FXML private FormationDisplay formatDisplay;
	//TODO
	
	//Constructor
	
	public SpielbrettController() throws IOException {
		super();
		
		//Apply CSS
		//getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
		
		//Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource(FXML_PATH));
		getFXMLLoader().setController(this);
		
		//Load the View
		getFXMLLoader().load();
		
		
	}
	
	public SpielbrettController(GameStateModel initialModel) throws IOException {
		this();
		// Initialize Playfield
		setMarkerWindow(new MarkerWindow());
		setPlayfield(new SpielfeldView(1980, 1080, 10, 10, initialModel.getViewModel(), getMarkerWindow()));
		SpielbrettID.getChildren().add(getPlayfield());
	}
	
	//Initialization
	
	@FXML
	private void initialize() {
	//	formatDisplay.setOnMouseClicked(this::click);
		//TODO: Data Binding and Setup of Event Handling
	}
	
	// Event Handlers
	/*
	 * Kp ob das so richtig ist :D
	 * 
	@FXML
	public void choices(ActionEvent event)
	{
	
		String ereignis1 = "Ereignis";
		String ereignis2 = "Würfel";
		String ereignis3 = "Chat";
		String ereignis4 = "Marker";
		String ereignis5 = "Medienbibliothek";
		String ereignis6 = "Admin";
		
		ereignisse.addAll(ereignis1, ereignis2, ereignis3, ereignis4, ereignis5, ereignis6);
		
		choiceID.setItems(ereignisse);
	}*/
	//TODO
	
	//Getter & Setter
	
	public FXMLLoader getFXMLLoader() {
		return fxmlLoader.get();
	}
	
	public void setFXMLLoader(FXMLLoader value) {
		fxmlLoader.set(value);
	}
	
	public ReadOnlyObjectProperty<FXMLLoader> fxmlLoaderProperty() {
		return fxmlLoader;
	}

	public final ObjectProperty<GameStateModel> currentModelProperty() {
		return this.currentModel;
	}
	

	public final wifen.commons.GameStateModel getCurrentModel() {
		return this.currentModelProperty().get();
	}
	

	public final void setCurrentModel(final wifen.commons.GameStateModel currentModel) {
		this.currentModelProperty().set(currentModel);
	}

	public MarkerWindow getMarkerWindow() {
		return markerWindow;
	}

	public void setMarkerWindow(MarkerWindow markerWindow) {
		this.markerWindow = markerWindow;
	}

	public SpielfeldView getPlayfield() {
		return playfield;
	}

	public void setPlayfield(SpielfeldView playfield) {
		this.playfield = playfield;
	}
	

}
