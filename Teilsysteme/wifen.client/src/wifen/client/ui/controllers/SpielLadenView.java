package wifen.client.ui.controllers;

import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import wifen.client.application.ClientApplication;

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
	@FXML TextField txt_filePath;
	@FXML Button btn_showFilePicker;
	@FXML TextField txt_playername;
	@FXML Button btn_loadGame;

	// TODO

	// Constructor

	/**
	 * Constructor der spielLaden.fxml liest und läd
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
		getFXMLLoader().setLocation(getClass().getResource("../views/SpielLaden.fxml"));
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
				new Alert(AlertType.ERROR, "Hauptmenü konnte nicht geladen werden").showAndWait();
			}

		}
		});
	}

	// Event Handlers

	private final void btnSpielErstellenOnAction(ActionEvent event) {
		try {
			File saveFile = new File(txt_filePath.getText());
			if(saveFile.exists())
			{
				new Alert(AlertType.ERROR, "Die Datei " + saveFile.getAbsolutePath() + " existiert nicht!").showAndWait();
				return;

			}
			if(!saveFile.isFile())
			{
				new Alert(AlertType.ERROR, "Der Pfad verweist nicht auf eine Datei!").showAndWait();
				return;
			}
			ClientApplication.instance().hostLoadedGame(saveFile, txt_playername.getText());
		} catch (Exception e) {
			new Alert(AlertType.ERROR, "Das Spiel konnte nicht geladen werden (" + e.getLocalizedMessage() + ")").showAndWait();
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