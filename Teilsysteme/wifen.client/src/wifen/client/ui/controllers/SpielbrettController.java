package wifen.client.ui.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.CheckComboBox;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import wifen.client.application.ClientApplication;
import wifen.client.services.ClientChatService;
import wifen.commons.GameStateModel;
import wifen.commons.network.Connection;

public class SpielbrettController extends BorderPane {
	
	private static final Logger logger = Logger.getLogger(SpielbrettController.class.getName());

	//constants
	public static final String CSS_PATH = "/wifen/client/ui/css/Spielbrett.css";
	public static final String FXML_PATH ="/wifen/client/ui/views/Spielbrett.fxml";
	
	//Properties
	
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	private final ObjectProperty<GameStateModel> currentModel = new SimpleObjectProperty<>();

	@FXML StackPane PlayField;
	@FXML CheckComboBox choiceID;
	@FXML Button optionID;
	@FXML Button refreshID;
	@FXML Button dcID;
	@FXML public ChatController chatBox;
	
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
		setPlayfield(new SpielfeldView(20, 20, initialModel.getViewModel(), getMarkerWindow()));
		PlayField.getChildren().add(getPlayfield());
		Polyline scale = new Polyline();
		/* Draw the Scale */
		scale = new Polyline();
		scale.setStroke(Color.RED);
		scale.getPoints().addAll(new Double[]{
				50.0,40.0,
				50.0,50.0,
				106.7,50.0,
				106.7,40.0});
		StackPane.setAlignment(scale, Pos.TOP_LEFT);
		StackPane.setMargin(scale, new Insets(15, 0, 0, 15));
		PlayField.getChildren().add(scale);
		
		layout();
	}
	
	//Initialization
	
	@FXML
	private void initialize() {
		try {
			chatBox.setChatService(ClientApplication.instance().getServiceRegistry().getServiceProviders(ClientChatService.class, false).next());
		} catch (Exception e) {
			logger.log(Level.WARNING, "Es ist kein ChatService registriert", e);
		}
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
