package wifen.client.ui.controllers;

import java.io.IOException;

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
import javafx.scene.layout.GridPane;

/**
 * Bereitstellung des SpielLaden Fensters
 * 
 * @author Patrick Hasse
 *
 */
public class SpielLadenView extends GridPane {

	// Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes
	@FXML Button backToMenuBtn;

	// TODO

	// Constructor

	/**
	 * Constructor der spielLaden.fxml liest und l�d
	 * 
	 * @throws IOException
	 */
	public SpielLadenView() throws IOException {
		super();

		// Apply CSS
		// getStylesheets().add(getClass().getResource("/css/MainView.css").toExternalForm());

		// Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("../views/spielLaden.fxml"));
		getFXMLLoader().setController(this);

		// Load the View
		getFXMLLoader().load();

	}

	// Initialization

	@FXML
	private void initialize() {
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
	}

	// Event Handlers

	// TODO

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