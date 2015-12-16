package wifen.client.ui.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatController extends TitledPane {

	// Constants

	public static final String CSS_PATH = "/wifen/client/ui/css/Chatfenster.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/Chatfenster.fxml";

	// Properties

	PrintWriter _out;
	BufferedReader _in;

	ChatController(PrintWriter out, BufferedReader in) {
		_out = out;
		_in = in;
	}

	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Customer

	// Injected Nodes
	@FXML
	private TextField txt_Eingabe;
	@FXML
	private ListView<String> Lv_Chat;

	private ObservableList<String> chatter = FXCollections.observableArrayList();
	private ObservableList<String> chatHistory = FXCollections.observableArrayList();

	// Constructor

	public ChatController() throws IOException {
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
		// playerView.setPlayer(player);
		// playerView.componentInitialization();

	}

	// Event Handlers

	// TODO

	public void addAusgabe(ActionEvent event) {

		String chat1 = txt_Eingabe.getText();

		// Ein & Ausgabe:
		chatter.add(chat1);
		Lv_Chat.setItems(chatter);
		// Chat_History:
		chatHistory.add(chat1);
		System.out.println(chatHistory);

		// Was noch fehlt :
		// SpielerRollen Unterscheidung und Erstellung von 2 Chatfenstern und
		// deren Kommunikation

		txt_Eingabe.setText("");
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
