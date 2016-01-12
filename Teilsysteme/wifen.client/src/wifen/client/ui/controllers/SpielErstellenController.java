package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import wifen.client.application.ApplicationConstants;
import wifen.client.application.ClientApplication;
import wifen.commons.GridType;
import wifen.commons.SpielerRolle;

/**
 * Put description here
 * 
 * @author Justin Nussbaum
 * @author Konstantin Schaper (Logik/Fehlerbehebungen)
 *
 */
public class SpielErstellenController extends Pane {

	// Constants
	
	public static final String CSS_PATH = "/wifen/client/ui/css/SpielErstellen.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/SpielErstellen.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes
	
	@FXML TextField tfPort;
	@FXML TextField tfMaxSpieler;
	@FXML TextField tfName;
	@FXML CheckBox cbBeobachterZulassen;
	@FXML CheckBox cbMedienSichtbarkeit;
	@FXML ComboBox<Integer> comboSeitenanzahl;
	@FXML ComboBox<SpielerRolle> comboStandardRolle;
	@FXML ComboBox<GridType> comboRaster;
	@FXML Button btnSpielErstellen;

	// Constructor

	/**
	 * Put description here
	 * 
	 * @throws IOException
	 */
	public SpielErstellenController() throws IOException {
		super();

		// Apply CSS
		// getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());

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
		btnSpielErstellen.setOnAction(this::btnSpielErstellenOnAction);
		comboStandardRolle.setItems(FXCollections.observableArrayList(SpielerRolle.values()));
		comboStandardRolle.getSelectionModel().select(SpielerRolle.PLAYER);
		comboRaster.setItems(FXCollections.observableArrayList(GridType.values()));
		comboRaster.getSelectionModel().select(GridType.NONE);
		comboSeitenanzahl.setItems(FXCollections.observableArrayList(2, 4, 6, 10, 12, 20));
		comboSeitenanzahl.getSelectionModel().select(new Integer(20));
		tfPort.setEditable(false);
		tfPort.setText(String.valueOf(ApplicationConstants.APPLICATION_PORT));
		tfName.setPromptText("...");
		tfMaxSpieler.setText("4");
		cbBeobachterZulassen.setSelected(true);
	}

	// Event Handlers
	
	/**
	 * Put description here
	 * 
	 * @param event
	 */
	private final void btnSpielErstellenOnAction(ActionEvent event) {
		try {
			ClientApplication.instance().hostGame(Integer.valueOf(tfMaxSpieler.getText()), cbBeobachterZulassen.isSelected(),
					cbMedienSichtbarkeit.isSelected(), comboSeitenanzahl.getSelectionModel().getSelectedItem(), tfName.getText(),
					comboStandardRolle.getSelectionModel().getSelectedItem(), comboRaster.getSelectionModel().getSelectedItem());
		} catch (Exception e) {
			new Alert(AlertType.ERROR, "Das Spiel konnte nicht erstellt werden (" + e.getLocalizedMessage() + ")").showAndWait();
		}
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
