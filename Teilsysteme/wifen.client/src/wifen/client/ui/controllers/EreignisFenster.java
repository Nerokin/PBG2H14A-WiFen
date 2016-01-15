package wifen.client.ui.controllers;

import java.io.IOException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;



/**
 * Anzeige vom Ereignisfenster
 * 
 * @author Kevin Curtis
 *
 */
public class EreignisFenster extends TitledPane {

	// Constants

	public static final String CSS_PATH = "/wifen/client/ui/css/Ereignisfenster.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/Ereignisfenster.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes

	private ObservableList<String> ereignislogList = FXCollections.observableArrayList();
	@FXML private ListView<String> ereignislogTF;

	// Constructor

	public EreignisFenster() throws IOException {
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
		setText("Ereignisse");
	}
	
	@Override
	public String toString() {
		return "Ereignisse";
	}

	// Event Handler

	/**
	 * Das eintragen von Texten in das Ereignisfenster
	 * 
	 * @param text
	 */
	public void log(String text) {
		ereignislogList.add(text);
		ereignislogTF.setItems(ereignislogList);
	}

	/**
	 * Die Methode gibt die eingetragenen Ereignisse zurück
	 * 
	 * @return
	 */
	public ObservableList<String> getLog() {
		return ereignislogList;
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
	/*
	public final void setChatService(final wifen.client.services.ClientChatService chatService) {
		this.chatServiceProperty().set(chatService);
	}	
	*/

}
