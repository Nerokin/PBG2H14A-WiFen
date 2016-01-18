package wifen.client.ui.controllers;

import java.io.IOException;

import org.controlsfx.control.CheckTreeView;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;



/**
 * Anzeige vom AdminFenster
 * 
 * @author Kevin Curtis
 *
 */
public class AdminRollenEdit extends VBox {

	// Constants
	public static final String CSS_PATH = "/wifen/client/ui/css/AdminRollenEdit.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/AdminRollenEdit.fxml";

	// Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes
	@FXML private Button speichern_btn;
	@FXML private CheckTreeView<String> checkTreeView;
	@FXML private CheckBox verdeckt_cbx;
	@FXML private CheckBox alleElementeSichtbar_cbx;


	// Constructor
	public AdminRollenEdit() throws IOException {
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
		// TODO: Data Binding and Setup of Event Handling
		speichern_btn.setOnAction(this::speichern);
	}

	// Event Handler
	private void speichern(ActionEvent event)
	{
		
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
