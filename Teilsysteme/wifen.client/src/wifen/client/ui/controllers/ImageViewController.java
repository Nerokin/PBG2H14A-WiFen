package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class ImageViewController extends StackPane
{
	//Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	
	private final Image image;
	
	// Injected node
	@FXML private ImageView imgView;
	
	//Constructor
	/**
	 * Put description here
	 * 
	 * @param image
	 * @throws IOException
	 */
	public ImageViewController(Image image) throws IOException
	{
		super();
		
		this.image = image;
		
		//Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("/fxml/ImageView.fxml"));
		getFXMLLoader().setController(this);
		
		//Load the View
		getFXMLLoader().load();
	}
	
	//Initialization
	@FXML
	private void initialize()
	{
		imgView.setImage(image);
		
		this.widthProperty().addListener(this::OnWidthChanged);
		this.heightProperty().addListener(this::OnHeightChanged);
	}
	
	// Events
	/**
	 * Put description here
	 * 
	 * @param observableValue
	 * @param oldSceneWidth
	 * @param newSceneWidth
	 */
	private void OnWidthChanged(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth)
	{
		imgView.setFitWidth((double)newSceneWidth);
	}
	
	/**
	 * Put description here
	 * 
	 * @param observableValue
	 * @param oldSceneHeight
	 * @param newSceneHeight
	 */
	private void OnHeightChanged(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight)
	{
		imgView.setFitHeight((double)newSceneHeight);
	}
		
	//Getter & Setter	
	public FXMLLoader getFXMLLoader()
	{
		return fxmlLoader.get();
	}
	
	public void setFXMLLoader(FXMLLoader value)
	{
		fxmlLoader.set(value);
	}
	
	public ReadOnlyObjectProperty<FXMLLoader> fxmlLoaderProperty()
	{
		return fxmlLoader;
	}
}
