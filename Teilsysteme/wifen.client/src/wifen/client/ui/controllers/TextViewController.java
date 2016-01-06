package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class TextViewController extends ScrollPane
{
	//Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	
	private String text = "";
	
	// Injected node
	@FXML private TextArea txtArea;
	
	//Constructor
	/**
	 * Put description here
	 * 
	 * @param text
	 * @throws IOException
	 */
	public TextViewController(String[] text) throws IOException
	{
		super();
		
		for(String part : text)
			this.text += part + '\n';
		
		//Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("/fxml/TextView.fxml"));
		getFXMLLoader().setController(this);
		
		//Load the View
		getFXMLLoader().load();
	}
	
	//Initialization
	@FXML
	private void initialize()
	{
		txtArea.setText(text);

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
		txtArea.setPrefWidth((double)newSceneWidth - 2);
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
		txtArea.setPrefHeight((double)newSceneHeight - 2);
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
