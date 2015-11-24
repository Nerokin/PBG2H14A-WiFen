package wifen.client.ui.controllers;

	import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;



public class SpielBeitretenController extends AnchorPane {

	
		//constants
		public static final String CSS_PATH = "/wifen/client/ui/css/SpielBeitreten.css";
		public static final String FXML_PATH ="/wifen/client/ui/views/SpielBeitreten.fxml";
		
		
		//Properties
		
		private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
		
		//Injected Nodes
		@FXML TextField tfPort;
		@FXML TextField tfname;
		@FXML TextField tfIp;
		@FXML Label ipAdress;
		@FXML Label versionNumber;
		 
		//@FXML private FormationDisplay formatDisplay;
		//TODO
		
		//Constructor
		
		public SpielBeitretenController() throws IOException {
			super();
			
			//Apply CSS
			//getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
			
			//Setup FXMLLoader
			setFXMLLoader(new FXMLLoader());
			getFXMLLoader().setRoot(this);
			getFXMLLoader().setLocation(getClass().getResource(FXML_PATH));
			getFXMLLoader().setController(this);
			
			//Load the View
			getFXMLLoader().load();
		}
		
		//Initialization
		
		@FXML
		private void initialize() {
		//	formatDisplay.setOnMouseClicked(this::click);
			//TODO: Data Binding and Setup of Event Handling
		}
		
		//Event Handlers
		/*private void click(MouseEvent event){
			
		}
		//TODO
		*/
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

