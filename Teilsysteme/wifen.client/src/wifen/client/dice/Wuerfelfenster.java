package wifen.client.dice;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Wuerfelfenster extends VBox 
{
		
	//Properties
	
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	private final ObjectProperty<String> dice = new SimpleObjectProperty<>(); // String zu Wuerfelklasse gilt auch für die Getter und Setter
	private final ObjectProperty<Image> image = new SimpleObjectProperty<>();
	
	//Injected Nodes
	
	@FXML private ToolBar diceTools;
	@FXML private Label diceLabel;
	@FXML private ImageView diceView;
	@FXML private TextField diceTex;
	
	
	
	//TODO
	
	//Constructor
	
	public Wuerfelfenster() throws IOException {
		//super();
		
		//Apply CSS
		getStylesheets().add(getClass().getResource("/css/Wuerfelfenser.css").toExternalForm());
		
		//Setup FXMLLoader
		setFxmlLoader(new FXMLLoader());
		getFxmlLoader().setRoot(this);
		getFxmlLoader().setLocation(getClass().getResource("/views/Wuerfelfenser.fxml"));
		getFxmlLoader().setController(this);
		
		//Load the View
		getFxmlLoader().load();
	}
	
	//Initialization
	
	@FXML
	private void initialize() {
		//TODO: Data Binding and Setup of Event Handling
		
		
	}

	
	//Event Handlers
	
	//TODO
	
	//Getter & Setter
	
	public final ObjectProperty<FXMLLoader> fxmlLoaderProperty() {
		return this.fxmlLoader;
	}
	

	public final javafx.fxml.FXMLLoader getFxmlLoader() {
		return this.fxmlLoaderProperty().get();
	}
	

	public final void setFxmlLoader(final javafx.fxml.FXMLLoader fxmlLoader) {
		this.fxmlLoaderProperty().set(fxmlLoader);
	}
	
	public final ObjectProperty<String> diceProperty() {
		return this.dice;
	}
	

	public final java.lang.String getDice() {
		return this.diceProperty().get();
	}
	

	public final void setDice(final java.lang.String dice) {
		this.diceProperty().set(dice);
	}
	
	public final ObjectProperty<Image> imageProperty() {
		return this.image;
	}
	

	public final javafx.scene.image.Image getImage() {
		return this.imageProperty().get();
	}
	

	public final void setImage(final javafx.scene.image.Image image) {
		this.imageProperty().set(image);
	}
}
