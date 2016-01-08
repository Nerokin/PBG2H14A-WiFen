package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class EreignisFenster extends VBox {

	// Constants

	public static final String CSS_PATH = "/wifen/client/ui/css/Ereignisfenster.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/Ereignisfenster.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes

	// OH GOTT bitte einen Listview verwenden
	@FXML private TextArea ereignislogTF;

	// Constructor

	/**
	 * Put description here
	 * 
	 * @throws IOException
	 */
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
	}

	// Event Handler

	/**
	 * Put description here
	 * 
	 * @param text
	 */
	public void log(String text) {
		if (text.charAt(text.length() - 1) == '\n')
			ereignislogTF.appendText(text);
		else
			ereignislogTF.appendText(text + "\n");
	}

	/**
	 * Put description here
	 * 
	 * @return
	 */
	public String getLog() {

		// Bitte eine Liste zur�ckgeben Observable List
		return ereignislogTF.getText();
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
