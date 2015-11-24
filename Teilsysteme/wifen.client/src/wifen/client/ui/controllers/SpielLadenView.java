package wifen.client.ui.controllers;
import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;


public class SpielLadenView extends GridPane {
	
	//Properties	
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	
	
	//Injected Nodes
	
	//TODO
	
	//Constructor
	
	public SpielLadenView() throws IOException {
		super();
		
		//Apply CSS
		//getStylesheets().add(getClass().getResource("/css/MainView.css").toExternalForm());
		
		//Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("../views/spielLaden.fxml"));
		getFXMLLoader().setController(this);
		
		//Load the View
		getFXMLLoader().load();
		
	}
	
	//Initialization
	
	@FXML
	private void initialize() {
		//TODO: Data Binding and Setup of Event Handling
	}
	
	//Event Handlers
	
	
	//TODO
	
	//Getter & Setter
	
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