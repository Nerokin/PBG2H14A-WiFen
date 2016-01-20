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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import wifen.commons.SpielerRolle;



/**
 * Anzeige vom AdminFenster
 * 
 * @author Kevin Curtis
 *
 */
public class AdminRollenNeu extends VBox {

	// Constants

	public static final String CSS_PATH = "/wifen/client/ui/css/AdminRollenNeu.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/AdminRollenNeu.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes

	@FXML private Button btn_erstellen;
	@FXML private CheckTreeView<String> ctv_spielElemente;
	@FXML private CheckBox cbx_verdecktWuerfeln;
	@FXML private CheckBox cbx_alleElementeSichtbar;
	@FXML private TextField tf_name;

	private SpielerRolle spielerRolle;

	// Constructor

	public AdminRollenNeu(SpielerRolle spielerRolle) throws IOException {
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
		
		this.spielerRolle = spielerRolle;
	}

	// Initialization
	@FXML
	private void initialize() {
		// TODO: Data Binding and Setup of Event Handling
		btn_erstellen.setOnAction(this::erstellen);
	}

	// Event Handler

	private void erstellen(ActionEvent event)
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
