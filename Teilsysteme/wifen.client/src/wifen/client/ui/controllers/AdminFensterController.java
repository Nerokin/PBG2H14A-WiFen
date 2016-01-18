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
	@FXML private Button btn_sitzungSpeichern;
	@FXML private Button btn_editieren;
	@FXML private Button btn_neu;
	@FXML private Button btn_zuweisen;
	@FXML private ListView<SpielerRolle> lv_rollen;
	@FXML private ListView<PlayerImpl> lv_spieler;
	@FXML private TitledPane tp_admin;

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
		
		btn_sitzungSpeichern.setOnAction(this::speichern);
		btn_editieren.setOnAction(this::edit);
		btn_neu.setOnAction(this::neu);
		btn_zuweisen.setOnAction(this::zuweisen);

		spielerRollen.addAll(SpielerRolle.values());
		lv_rollen.setItems(spielerRollen);
	}

	// Event Handler

	public void speichern(ActionEvent event) {

	}

	public void edit(ActionEvent event) {
		if (lv_rollen.getSelectionModel().getSelectedItem() == null)
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
		if (lv_spieler.getSelectionModel().getSelectedItem() == null)
			System.out.println("Bitte eine rolle auswählen");
		else if (lv_rollen.getSelectionModel().getSelectedItem() == null)
			System.out.println("Bitte ein Spieler auswählen");
		else {
			SpielerRolle rolle = lv_rollen.getSelectionModel().getSelectedItem();
			lv_spieler.getSelectionModel().getSelectedItem().setRolle(rolle);
		}
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
