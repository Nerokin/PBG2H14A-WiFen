package wifen.client.ui.controllers;

import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Interaction logic for the options view
 * 
 * @author unknown
 * @author Marc Brinkmann
 *
 */
public class OptionenController extends AnchorPane {
	
	// Attributes
	Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

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
	@FXML Label ipAdress;
	@FXML Label versionNumber;

	// @FXML private FormationDisplay formatDisplay;
	// TODO

	// Constructor.

	/**
	 * Put description here
	 * 
	 * @throws IOException
	 */
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
		btnSpeichern.setOnAction(this::speichernOnAction);
		sliderVolumen.setValue(prefs.getDouble("Volume", sliderVolumen.getMax()));
		cbMuteMusik.setSelected(prefs.getBoolean("MusicMuted", false));
		cbMuteSound.setSelected(prefs.getBoolean("SoundMuted", false));
		sliderMaxDateigroesse.setValue(prefs.getDouble("MaxFileSize", sliderMaxDateigroesse.getMax()/2));		
	}

	// Event Handlers	
	/**
	 * Saves the options in the user preferences
	 */
	private void speichernOnAction(ActionEvent event){
                prefs.putDouble("Volume", sliderVolumen.getValue());
        		prefs.putBoolean("MusicMuted", cbMuteMusik.isSelected());
        		prefs.putBoolean("SoundMuted", cbMuteSound.isSelected());
        		prefs.putDouble("MaxFileSize", sliderMaxDateigroesse.getValue());	
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
