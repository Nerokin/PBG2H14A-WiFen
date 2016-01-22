package wifen.client.ui.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Put description here
 *
 * @author Kevin Curtis
 * @author Konstantin Schaper (Logik)
 * @author Justin Nussbaum (Exit Button)
 */
public class Hauptmenu extends BorderPane {

	Logger logger = Logger.getLogger(Hauptmenu.class.getSimpleName());

	// Constants

	public static final String CSS_PATH = "/wifen/client/ui/css/Hauptmenu.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/hauptmenu.fxml";

	// Properties

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes

	//@FXML private AnchorPane AnchorPaneHM;
	@FXML private Label lb_version;
	@FXML private Label lb_ip;
	@FXML private Button btn_spielBeitreten;
	@FXML private Button btn_spielErstellen;
	@FXML private Button btn_spielLaden;
	@FXML private Button btn_optionen;
	@FXML private Button btn_spielVerlassen;

	// Constructor

	/**
	 * Put description here
	 *
	 * @throws IOException
	 */
	public Hauptmenu() throws IOException {
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
	@FXML public void close(){
		Platform.exit();
	}

	// Initialization
	/**
	 * Put description here
	 */
	@FXML
	private void initialize() {
		// TODO: Data Binding and Setup of Event Handling
		//AnchorPaneHM.setMinSize(800, 600);

		// Look up IP-address and display it
		try {
			lb_ip.setText(InetAddress.getLocalHost().getHostAddress() + " (Lokal)");
		} catch (UnknownHostException e) {
			lb_ip.setText("Konnte nicht gefunden werden");
		}

		// Events
		btn_spielErstellen.setOnAction(this::erstellenHmBtnOnAction);
		btn_optionen.setOnAction(this::optionenHmBtnOnAction);
		btn_spielBeitreten.setOnAction(this::beitretenHmBtnOnAction);
		btn_spielLaden.setOnAction(this::ladenHmBtnOnAction);
	}

	// Event Handlers

	/**
	 * Put description here
	 *
	 * @param event
	 */
	private final void erstellenHmBtnOnAction(ActionEvent event) {
		Parent p = null;
		try {
			p = new SpielErstellenController();
			getScene().setRoot(p);
		} catch (IOException e) {
			new Alert(AlertType.ERROR, "Spielerstellung konnte nicht geladen werden").showAndWait();
		}

	}

	private final void ladenHmBtnOnAction(ActionEvent event) {
		Parent p = null;
		try {
			p = new SpielLadenView();
			getScene().setRoot(p);
		} catch (IOException e) {
			new Alert(AlertType.ERROR, "Spiel laden konnte nicht geladen werden").showAndWait();
		}

	}

	private final void beitretenHmBtnOnAction(ActionEvent event) {
		Parent p = null;
		try {
			p = new SpielBeitretenController();
			getScene().setRoot(p);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Spielbeitrittsformular konnte nicht geladen werden", e);
			new Alert(AlertType.ERROR, "Spielbeitrittsformular konnte nicht geladen werden").showAndWait();
		}
	}

	private final void optionenHmBtnOnAction(ActionEvent event) {
		try {
			 Parent root1 = new OptionenController();
             Stage stage = new Stage();
             stage.setScene(new Scene(root1));
             stage.show();
		} catch (IOException e) {
			new Alert(AlertType.ERROR, "Optionsmenü konnte nicht geladen werden").showAndWait();
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
