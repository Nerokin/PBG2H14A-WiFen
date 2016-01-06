package wifen.client.ui.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Put description here
 * 
 * @author Kevin Curtis
 * @author Konstantin Schaper (Logik)
 */
public class Hauptmenu extends AnchorPane {

	// Constants

	public static final String CSS_PATH = "/wifen/client/ui/css/Hauptmenu.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/hauptmenu.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes

	@FXML private AnchorPane AnchorPaneHM;
	@FXML private Label versionHmLabel;
	@FXML private Label ipHmLabel;
	@FXML private Button beitretenHmBtn;
	@FXML private Button erstellenHmBtn;
	@FXML private Button ladenHmBtn;
	@FXML private Button optionenHmBtn;
	@FXML private Button verlassenHmBtn;
	
	// Constructor

	/**
	 * Put description here
	 * 
	 * @throws IOException
	 */
	public Hauptmenu() throws IOException {
		super();

		// Apply CSS
		getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());

		// Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource(FXML_PATH));
		getFXMLLoader().setController(this);

		// Load the View
		getFXMLLoader().load();
	}

	// Initialization
	/**
	 * Put description here
	 */
	@FXML
	private void initialize() {
		// TODO: Data Binding and Setup of Event Handling
		AnchorPaneHM.setMinSize(800, 600);
		
		// Look up IP-address and display it
		try {
			ipHmLabel.setText(InetAddress.getLocalHost().getHostAddress() + " (Lokal)");
		} catch (UnknownHostException e) {
			ipHmLabel.setText("Konnte nicht gefunden werden");
		}
		
		// Events
		erstellenHmBtn.setOnAction(this::erstellenHmBtnOnAction);
	}
	
	// Event Handlers

	/**
	 * Put description here
	 * 
	 * @param event
	 */
	private final void erstellenHmBtnOnAction(ActionEvent event) {
		Parent p = null;
		try {
			p = new SpielErstellenController();
			getScene().setRoot(p);
		} catch (IOException e) {
			new Alert(AlertType.ERROR, "Spielerstellung konnte nicht geladen werden").showAndWait();
		}
		
	}

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
