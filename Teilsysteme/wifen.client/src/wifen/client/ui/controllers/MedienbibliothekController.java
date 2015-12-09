package wifen.client.ui.controllers;

import java.io.File;
import java.io.IOException;

import Medien.Medium;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class MedienbibliothekController extends AnchorPane
{
	ObservableList<Medium> liste = FXCollections.observableArrayList();
	
	//Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	
	//Injected Nodes	
	@FXML
	private ListView<Medium> listViewMedien;
	@FXML
	private TextField tbxMedienBrowser;
	
	//Constructor
	public MedienbibliothekController() throws IOException
	{
		super();
		
		//Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("/fxml/Medienbibliothek.fxml"));
		getFXMLLoader().setController(this);
		
		//Load the View
		getFXMLLoader().load();
	}
	
	//Initialization
	@FXML
	private void initialize()
	{		
		listViewMedien.setItems(liste);	
	}
	
	//Event Handlers
	public void MedienUpload(ActionEvent event)
	{
		File file = (File)tbxMedienBrowser.getUserData();
		if(file != null)
		{
			// TODO: upload file to server
			liste.add(new Medium(file));
		}
	}
	
	public void MedienBrowser(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Media File");
		File file = fileChooser.showOpenDialog(this.getScene().getWindow());
		
		tbxMedienBrowser.setUserData(file);
		tbxMedienBrowser.setText(file.getAbsolutePath());
	}
	
	public void Medien÷ffnen(ActionEvent event)
	{
		// TODO: find ANY way to open file irrespective of actual FileNode implementation
	}
		
	//Getter & Setter
	public ListView<Medium> getListViewMedien()
	{
		return listViewMedien;
	}
	
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
