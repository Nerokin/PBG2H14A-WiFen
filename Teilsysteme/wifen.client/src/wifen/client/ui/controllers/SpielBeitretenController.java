package wifen.client.ui.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import wifen.client.application.ApplicationConstants;
import wifen.client.application.ClientApplication;
import wifen.client.services.impl.GameProvider;
import wifen.commons.network.Connection;
import wifen.commons.network.impl.ConnectionImpl;
import wifen.commons.network.packets.EnterGamePacket;
import wifen.commons.network.packets.impl.EnterGamePacketImpl;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class SpielBeitretenController extends BorderPane {

	private final Logger logger = Logger.getLogger(SpielBeitretenController.class.getName());

	
	// constants
	public static final String CSS_PATH = "/wifen/client/ui/css/SpielBeitreten.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/SpielBeitreten.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes
	@FXML TextField tfPort;
	@FXML TextField tfname;
	@FXML TextField tfIp;
	@FXML Label ipAdress;
	@FXML Label versionNumber;
	@FXML Button connectButton;
	@FXML Button backToMenuBtn;

	// @FXML private FormationDisplay formatDisplay;
	// TODO

	// Constructor

	/**
	 * Put description here
	 * 
	 * @throws IOException
	 */
	public SpielBeitretenController() throws IOException {
		super();

		// Apply CSS
		// getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());

		// Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource(FXML_PATH));
		getFXMLLoader().setController(this);

		// Load the View
		getFXMLLoader().load();
	}

	// Initialization

	@FXML
	private void initialize() {
		// formatDisplay.setOnMouseClicked(this::click);
		// TODO: Data Binding and Setup of Event Handling
		
	
		backToMenuBtn.setOnAction(new EventHandler<ActionEvent>(){
			 public void handle(ActionEvent e) {
			Parent p = null;
			try {
				p = new Hauptmenu();
				getScene().setRoot(p);
			} catch (IOException e2) {
				new Alert(AlertType.ERROR, "Hauptmen� konnte nicht geladen werden").showAndWait();
			}
			
		}
		});
		
		connectButton.setOnAction(new EventHandler<ActionEvent>(){
			 public void handle(ActionEvent e) {
				 try{
					Connection tmpConn = ClientApplication.instance().startConnection(InetAddress.getByName(tfIp.getText()));
						 
					String requestedName = "Peter";
					logger.log(Level.INFO, requestedName);
					EnterGamePacket packet = new EnterGamePacketImpl(requestedName);
					tmpConn.sendPacket(packet);

				 }
				 catch(Exception ex){
					 // Warnung ausgeben
					 logger.log(Level.WARNING, "Spiel konnte nicht beigetreten werden", ex);
					 new Alert(AlertType.WARNING, "Spiel konnte nicht beigetreten werden").showAndWait();
				 }
				 }
				 
		});
	}

	// Event Handlers
	/*
	 * private void click(MouseEvent event){
	 * 
	 * } //TODO
	 */
	
	
	
	
	// Getter & Setter

	public FXMLLoader getFXMLLoader() {
		return fxmlLoader.get();
	}

	public void setFXMLLoader(FXMLLoader value) {
		fxmlLoader.set(value);
	}

	public ReadOnlyObjectProperty<FXMLLoader> fxmlLoaderProperty() {
		return fxmlLoader;
	}

}
