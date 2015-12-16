package wifen.client.ui.controllers;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import wifen.client.services.impl.ImageNode;
import wifen.client.services.impl.TxtNode;
import wifen.commons.Medium;

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
	public void medienUpload(ActionEvent event)
	{
		File file = (File)tbxMedienBrowser.getUserData();
		if(file != null)
		{
			// TODO: upload file to server
			liste.add(new Medium(file));
			
			// Clear text field
			tbxMedienBrowser.setText(null);
			tbxMedienBrowser.setUserData(null);
		}
	}
	
	public void medienBrowser(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Media File");
		File file = fileChooser.showOpenDialog(this.getScene().getWindow());
		
		if(file != null)
		{
			tbxMedienBrowser.setUserData(file);
			tbxMedienBrowser.setText(file.getAbsolutePath());
		}
	}
	
	public void medien�ffnen(ActionEvent event)
	{
		Medium selectedMedium = listViewMedien.getSelectionModel().getSelectedItem();
		if(selectedMedium == null)
			return;
		
		// Show content in seperate window. TODO: create window to show content
		if(selectedMedium.getFile() instanceof ImageNode)
		{
			try
			{
				createSubWindow("Medienbibliothek", new Scene(new ImageViewController(((ImageNode)selectedMedium.getFile()).getFileContent())));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(selectedMedium.getFile() instanceof TxtNode)
		{
			try
			{
				createSubWindow("Medienbibliothek", new Scene(new TextViewController(((TxtNode)selectedMedium.getFile()).getFileContent())));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else // All other filetypes open on their own
			selectedMedium.getFile().getFileContent();
	}
	
	private void createSubWindow(String title, Scene scene)
	{
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle(title);
		stage.show();
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
