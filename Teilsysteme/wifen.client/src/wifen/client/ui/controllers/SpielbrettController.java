package wifen.client.ui.controllers;

 import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.CheckComboBox;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;
import wifen.client.application.ClientApplication;
import wifen.client.services.ClientChatService;
import wifen.client.services.ClientGameeventService;
import wifen.client.services.ClientMediaService;
import wifen.client.services.impl.ClientRefreshProvider;
import wifen.commons.GameStateModel;
import wifen.commons.network.Connection;

 /**
  * Put description here
  *
  * @author unknown
  * @author Marc Brinkmann
  *
  */
public class SpielbrettController extends BorderPane {

 	private static final Logger logger = Logger.getLogger(SpielbrettController.class.getName());

 	//constants
 	public static final String CSS_PATH = "/wifen/client/ui/css/Spielbrett.css";
 	public static final String FXML_PATH ="/wifen/client/ui/views/Spielbrett.fxml";

 	//Properties

 	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
 	private final ObjectProperty<GameStateModel> currentModel = new SimpleObjectProperty<>();

 	@FXML StackPane PlayField;
 	@FXML CheckComboBox<Node> choiceID;
 	@FXML Button optionID;
 	@FXML Button refreshID;
 	@FXML Button dcID;
 	@FXML public VBox ereignisVBoxID;
 	@FXML public ChatController chatBox;
 	@FXML public Wuerfelfenster diceBox;
 	@FXML public MarkerWindow markerBox;
 	@FXML public MedienbibliothekController mediaLibrary;
 	@FXML public AdminFensterController adminBox;
 	@FXML public EreignisFenster ereignisBox;

 	private SpielfeldView playfield;
 	private MarkerWindow markerWindow;

 	//@FXML private FormationDisplay formatDisplay;
 	//TODO

 	//Constructor

 	public SpielbrettController() throws IOException {
 		super();

 		//Apply CSS
 		getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());

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
 		setMarkerWindow(MarkerWindow.getInstance());
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
 			ereignisBox.lv_ereignislog.setItems(ClientApplication.instance().getServiceRegistry().getServiceProviders(ClientGameeventService.class, false).next().getGameeventHistory());
 			chatBox.setChatService(ClientApplication.instance().getServiceRegistry().getServiceProviders(ClientChatService.class, false).next());
 		} catch (Exception e) {
 			logger.log(Level.WARNING, "Es ist kein ChatService registriert", e);
 		}

 		try {
 			ereignisBox.lv_ereignislog.setItems(ClientApplication.instance().getServiceRegistry().getServiceProviders(ClientGameeventService.class, false).next().getGameeventHistory());
 			mediaLibrary.setMediaService(ClientApplication.instance().getServiceRegistry().getServiceProviders(ClientMediaService.class, false).next());
 		} catch (Exception e) {
 			logger.log(Level.WARNING, "Es ist kein MediaService registriert", e);
 		}

		 // and listen to the relevant events (e.g. when the selected indices or selected items change).
		 choiceID.getCheckModel().getCheckedItems().addListener((Change<? extends Node> c) -> {
			if (c.next()) {
			    c.getRemoved().forEach((node) -> {if (ereignisVBoxID.getChildren().contains(node)) ereignisVBoxID.getChildren().remove(node);});
			    c.getAddedSubList().forEach((node) -> {if (!ereignisVBoxID.getChildren().contains(node)) ereignisVBoxID.getChildren().add(node);});
			}
		 });

 		// create the data to  show in the CheckComboBox
 			final ObservableList<Node> choices = FXCollections.observableArrayList();

 			ereignisVBoxID.getChildren().clear();

			if (chatBox != null) choices.add(chatBox);
			if (diceBox != null) choices.add(diceBox);
			if (ereignisBox != null) choices.add(ereignisBox);
			if (markerBox != null) choices.add(markerBox);
			if (mediaLibrary != null) choices.add(mediaLibrary);
			if (adminBox != null) choices.add(adminBox);

 			// Create the CheckComboBox with the data
 			choiceID.getItems().addAll(choices);
 			if (ereignisBox != null) choiceID.getCheckModel().check(ereignisBox);
 			if (chatBox != null) choiceID.getCheckModel().check(chatBox);
 			if (diceBox != null) choiceID.getCheckModel().check(diceBox);

 			//Optionen aus Spielbrett aufrufen
 			optionID.setOnAction(new EventHandler<ActionEvent>() {
 				public void handle(ActionEvent ae) {
 					try {
 		                Parent root1 = new OptionenController();
 		                Stage stage = new Stage();
 		                stage.setScene(new Scene(root1));
 		                stage.show();
 		        } catch (IOException e) {
 						new Alert(AlertType.ERROR, "Optionsmenü konnte nicht geladen werden").showAndWait();
 					}
 				}
 			});

 		try{

 		} catch(Exception e){
 			logger.log(Level.WARNING, "Es ist kein EreignisService registriert", e);
 		}

 		//Fabian Hitziger
 		//Feuer ein ActionEvent sobald der Button gedrückt wird
 		//Trennt die Verbindung zum Server
 		//Schließt das Spielbrett und öffnet das Hauptmenü
 		dcID.setOnAction(new EventHandler<ActionEvent>(){

 			 @Override public void handle(ActionEvent e) {
 				 if(!ClientApplication.instance().getServiceRegistry().getServiceProviderByClass(Connection.class).close()){
 					 Parent p = null;
 					 try{
 						p = new Hauptmenu();
 					 	getScene().setRoot(p);
 					 } catch (IOException ex) {
 							new Alert(AlertType.ERROR, "Spielerstellung konnte nicht geladen werden").showAndWait();
 					 }

 				 }
 			 }
 		});

 		refreshID.setOnAction(new EventHandler<ActionEvent>(){

 			 @Override public void handle(ActionEvent e) {
 				 try{
 				 ClientApplication.instance().getServiceRegistry().getServiceProviders(ClientRefreshProvider.class, true).next().refresh();
 				 }
 				 catch(NoSuchElementException nex){
 					 new Alert(AlertType.ERROR, "Refresh nicht erfolgreich").showAndWait();
 				 }
 			 }
 		});
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

 	public MedienbibliothekController getMediaLibrary(){
 		return mediaLibrary;
 	}

 }