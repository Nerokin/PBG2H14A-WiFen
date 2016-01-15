package wifen.client.ui.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import wifen.client.services.ClientChatService;

/**
 * Responsible for managing a window to use for any cross player communcation.
 * This class also performs other informing tasks as displaying player roles.
 * 
 * @author Konstantin Schaper
 * @author Jannik Metzger (Vorlage)
 */
	
public class ChatController extends TitledPane {
	
	// Class Constants
	
	private final Logger logger = Logger.getLogger(ChatController.class.getName());

	// Constants

	public static final String CSS_PATH = "/wifen/client/ui/css/Chatfenster.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/Chatfenster.fxml";

	// Properties

	private final ObjectProperty<ClientChatService> chatService = new SimpleObjectProperty<>();
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	private final StringProperty playerName = new SimpleStringProperty();
	private final StringProperty playerRole = new SimpleStringProperty();
	
	// Attributes
	
	private final EventHandler<ActionEvent> onChatMessageAction = this::onChatMessageAction;

	// Customer

	// Injected Nodes
	
	@FXML
	private TextField txt_Eingabe;
	
	@FXML
	private ListView<String> Lv_Chat;

	// Constructor(s)

	/**
	 * Put description here
	 * 
	 * @throws IOException
	 */
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
	
	/**
	 * Put description here
	 * 
	 * @param chatService
	 * @throws IOException
	 */
	public ChatController(ClientChatService chatService) throws IOException {
		this();
		setChatService(chatService);
	}
	
	/**
	 * Put description here
	 * 
	 * @param chatService
	 * @param playerName
	 * @throws IOException
	 */
	public ChatController(ClientChatService chatService, String playerName, String playerRole) throws IOException {
		this(chatService);
		setPlayerName(playerName);
		setPlayerRole(playerRole);
	}

	// Initialization
	/**
	 * Put description here
	 */
	@FXML
	private void initialize() {
		
		// Make the list wrap text within cells
		
		
		// Send a chat packet when the user presses ENTER on the message input control
		txt_Eingabe.setOnAction(getOnChatMessageAction());
		
		// Initially disable the chat as no connection has been established
		setDisable(true);
		setText("Chat (Nicht Verbunden)");
		
		// Listen on Service change
		chatServiceProperty().addListener(this::onChatServiceChanged);
	}
	
	// Methods
	
	@Override
	public String toString() {
		return "Chat";
	}

	// Event Handlers
	
	/**
	 * Put description here
	 * 
	 * @param observable
	 * @param oldValue
	 * @param newValue
	 */
	public void onChatServiceChanged(ObservableValue<? extends ClientChatService> observable, ClientChatService oldValue, ClientChatService newValue) {
		if (newValue != null) {
			// Make the chat display the chat history
			Lv_Chat.setItems(newValue.getChatHistory());
			setText("Chat (Verbunden)");
			setDisable(false);
		} else {
			setDisable(true);
			Lv_Chat.setItems(null);
			setText("Chat (Nicht Verbunden)");
		}
	}

	// TODO
	
	/**
	 * Put description here
	 * 
	 * @param event
	 */
	public void onChatMessageAction(ActionEvent event) {
		if (getChatService() != null) {
			// Call the chat service to send the message
			getChatService().sendMessage(getPlayerName(), txt_Eingabe.getText());
			/*
			 * Chat an gezielten Spieler
			 * //<-- hier wird noch eine Liste benötigt in der alle Spieler stehen, damit der andere Spieler identifizierbar ist!!!
			 */
			String eingabe = txt_Eingabe.getText();
			String eingabeSplit [] = eingabe.split(" ");
			String Name = (eingabeSplit[1]);
			
			if(Name.equals(playerName))
			{
			if(txt_Eingabe.getText() == "/msg"+Name)
			{
				getChatService().sendMessage(getPlayerName(), txt_Eingabe.getText());
			}
			else{
				logger.warning("Der eingegebene Spieler Name ist falsch!");
			}
			}
			/*
			 * Chatbefehl für SpielerRolle
			 */
			if(txt_Eingabe.getText() == "/rolle")
			{
				Lv_Chat.setItems(getChatService().showRole(playerRole));
			}
			// Reset the text input
			txt_Eingabe.setText(null);
		} else {
			logger.warning("Es ist kein ChatService registriert. Die Nachricht konnte nicht gesendet werden.");
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

	public EventHandler<ActionEvent> getOnChatMessageAction() {
		return onChatMessageAction;
	}
	public final StringProperty playerNameProperty() {
		return this.playerName;
	}
	
	public final StringProperty playerRoleProperty(){
		return this.playerRole;	
	}
	

	public final java.lang.String getPlayerName() {
		return this.playerNameProperty().get();
	}
	
	public final java.lang.String getShowRole(){
		return this.playerRoleProperty().get();
	}

	public final void setPlayerName(final java.lang.String playerName) {
		this.playerNameProperty().set(playerName);
	}

	public final void setPlayerRole(final String playerRole2){
		this.playerRoleProperty().set(playerRole2);
	}
	public final ObjectProperty<ClientChatService> chatServiceProperty() {
		return this.chatService;
	}
	

	public final wifen.client.services.ClientChatService getChatService() {
		return this.chatServiceProperty().get();
	}
	

	public final void setChatService(final wifen.client.services.ClientChatService chatService) {
		this.chatServiceProperty().set(chatService);
	}	
}
