package wifen.client.ui.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
public class SpielErstellenController extends BorderPane {

	// Constants
	
	public static final String CSS_PATH = "/wifen/client/ui/css/SpielErstellen.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/SpielErstellen.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes
	
	@FXML TextField tf_port;
	@FXML TextField tf_maxSpieler;
	@FXML TextField tf_eigenerName;
	@FXML CheckBox chb_beobachterZulassen;
	@FXML CheckBox chb_medienSichtbar;
	@FXML ComboBox<Integer> cbx_würfelSeitenzahl;
	@FXML ComboBox<SpielerRolle> cbx_standardRolle;
	@FXML ComboBox<GridType> cbx_raster;
	@FXML Button btn_spielErstellen;
	@FXML Button btn_backToMenu;
	@FXML Label lb_ip;
	@FXML Label lb_version;

	// Constructor

	/**
	 * Put description here
	 * 
	 * @throws IOException
	 */
	public SpielErstellenController() throws IOException {
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
		tf_port.setText(ApplicationConstants.APPLICATION_PORT+"");
		btn_backToMenu.setOnAction(this::backToMenuBtnOnAction);
		btn_spielErstellen.setOnAction(this::btnSpielErstellenOnAction);
		cbx_standardRolle.setItems(FXCollections.observableArrayList(SpielerRolle.values()));
		cbx_standardRolle.getSelectionModel().select(SpielerRolle.PLAYER);
		cbx_raster.setItems(FXCollections.observableArrayList(GridType.values()));
		cbx_raster.getSelectionModel().select(GridType.NONE);
		cbx_würfelSeitenzahl.setItems(FXCollections.observableArrayList(2, 4, 6, 10, 12, 20));
		cbx_würfelSeitenzahl.getSelectionModel().select(new Integer(20));
		tf_port.setEditable(false);
		tf_port.setText(String.valueOf(ApplicationConstants.APPLICATION_PORT));
		tf_eigenerName.setPromptText("...");
		tf_maxSpieler.setText("99");
		chb_medienSichtbar.setSelected(true);
		
		
		try {
			lb_ip.setText(InetAddress.getLocalHost().getHostAddress() + " (Lokal)");
		} catch (UnknownHostException e) {
			lb_ip.setText("Konnte nicht gefunden werden");
		}
	}

	// Event Handlers
	
	/**
	 * Put description here
	 * 
	 * @param event
	 */
	
	
	private final void backToMenuBtnOnAction(ActionEvent event) {
		Parent p = null;
		try {
			p = new Hauptmenu();
			getScene().setRoot(p);
		} catch (IOException e2) {
			new Alert(AlertType.ERROR, "Hauptmenü konnte nicht geladen werden").showAndWait();
		}
		
	}
	
	
	private final void btnSpielErstellenOnAction(ActionEvent event) {
		try {
			ClientApplication.instance().hostGame(Integer.valueOf(tf_maxSpieler.getText()), chb_beobachterZulassen.isSelected(),
					chb_medienSichtbar.isSelected(), cbx_würfelSeitenzahl.getSelectionModel().getSelectedItem(), tf_eigenerName.getText(),
					cbx_standardRolle.getSelectionModel().getSelectedItem(), cbx_raster.getSelectionModel().getSelectedItem());
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
