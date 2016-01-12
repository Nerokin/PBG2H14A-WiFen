package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import wifen.commons.Player;
import wifen.commons.SpielerRolle;


/**
 * Anzeige vom Ereignisfenster
 * 
 * @author Kevin Curtis
 *
 */
public class AdminFensterController extends VBox {

	// Constants

	public static final String CSS_PATH = "/wifen/client/ui/css/AdminFenster.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/AdminFenster.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes

	@FXML private Button speichern_btn;
	@FXML private Button edit_btn;
	@FXML private Button neu_btn;
	@FXML private Button zuweisen_btn;
	@FXML private ListView<SpielerRolle> rollenList;
	@FXML private ListView<Player> spielerList;

	// Constructor

	public AdminFensterController() throws IOException {
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
		edit_btn.setOnAction(this::edit);
		neu_btn.setOnAction(this::neu);
		zuweisen_btn.setOnAction(this::zuweisen);
	}

	// Event Handler

	public void speichern(ActionEvent event)
	{
		
	}

	public void edit(ActionEvent event)
	{
		
	}
	
	public void neu(ActionEvent event)
	{
		
	}
	
	public void zuweisen(ActionEvent event)
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
