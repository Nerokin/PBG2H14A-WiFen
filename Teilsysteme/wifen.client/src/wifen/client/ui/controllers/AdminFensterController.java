package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import wifen.commons.Player;
import wifen.commons.SpielerRolle;
import wifen.commons.impl.PlayerImpl;

/**
 * Anzeige vom AdminFenster
 * 
 * @author Kevin Curtis
 *
 */
public class AdminFensterController extends TitledPane {

	// Constants

	public static final String CSS_PATH = "/wifen/client/ui/css/AdminFensterController.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/AdminFenster.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes

	@FXML
	private Button speichern_btn;
	@FXML
	private Button edit_btn;
	@FXML
	private Button neu_btn;
	@FXML
	private Button zuweisen_btn;
	@FXML
	private ListView<SpielerRolle> rollenList;
	@FXML
	private ListView<PlayerImpl> spielerList;

	private PlayerImpl peter;

	private ObservableList<SpielerRolle> spielerRollen = FXCollections.observableArrayList();
	private ObservableList<PlayerImpl> spielerListen = FXCollections.observableArrayList();
	// private ObservableList<SpielerListe>spieler =
	// FXCollections.observableArrayList();

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
		
		setText("Admin");
		
		speichern_btn.setOnAction(this::speichern);
		edit_btn.setOnAction(this::edit);
		neu_btn.setOnAction(this::neu);
		zuweisen_btn.setOnAction(this::zuweisen);

		spielerRollen.addAll(SpielerRolle.values());
		rollenList.setItems(spielerRollen);

		peter = new PlayerImpl("Peter");
		spielerListen.add(peter);
		spielerList.setItems(spielerListen);

		System.out.println(peter.getRolle());
	}

	// Event Handler

	public void speichern(ActionEvent event) {

	}

	public void edit(ActionEvent event) {
		if (rollenList.getSelectionModel().getSelectedItem() == null)
			System.out.println("Bitte wähle eine Rolle aus");
		else {
			try {
				Stage editStage = new Stage();
				editStage.setScene(new Scene(new AdminRollenEdit()));
				editStage.centerOnScreen();
				editStage.setTitle("Edit");
				editStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void neu(ActionEvent event) {
		try {
			Stage newStage = new Stage();
			newStage.setScene(new Scene(new AdminRollenNeu()));
			newStage.centerOnScreen();
			newStage.setTitle("Neu");
			newStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void zuweisen(ActionEvent event) {
		if (spielerList.getSelectionModel().getSelectedItem() == null)
			System.out.println("Bitte eine rolle auswählen");
		else if (rollenList.getSelectionModel().getSelectedItem() == null)
			System.out.println("Bitte ein Spieler auswählen");
		else {
			SpielerRolle rolle = rollenList.getSelectionModel().getSelectedItem();
			spielerList.getSelectionModel().getSelectedItem().setRolle(rolle);
		}

		System.out.println(peter.getRolle());
	}
	
	@Override
	public String toString() {
		return "Admin";
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
