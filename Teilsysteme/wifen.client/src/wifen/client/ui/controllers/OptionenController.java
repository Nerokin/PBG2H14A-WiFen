package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
//import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class OptionenController extends AnchorPane {

	// constants
	public static final String CSS_PATH = "/wifen/client/ui/css/Optionen.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/Optionen.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes
	@FXML Slider sliderVolumen;
	@FXML CheckBox cbMuteMusik;
	@FXML CheckBox cbMuteSound;
	@FXML Slider sliderMaxDateigroesse;
	@FXML Button btnSpeichern;
	@FXML Button btnAbbrechen;
	@FXML Label ipAdress;
	@FXML Label versionNumber;

	// @FXML private FormationDisplay formatDisplay;
	// TODO

	// Constructor

	public OptionenController() throws IOException {
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

	@FXML
	private void initialize() {
		// formatDisplay.setOnMouseClicked(this::click);
		// TODO: Data Binding and Setup of Event Handling
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
