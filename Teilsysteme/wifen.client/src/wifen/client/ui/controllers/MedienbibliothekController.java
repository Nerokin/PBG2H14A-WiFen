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
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import wifen.commons.Medium;
import wifen.commons.services.impl.CsvNode;
import wifen.commons.services.impl.ImageNode;
import wifen.commons.services.impl.TxtNode;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class MedienbibliothekController extends TitledPane
{
	ObservableList<Medium> liste = FXCollections.observableArrayList();
	
	//Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	
	//Injected Nodes	
	@FXML
	private ListView<Medium> lv_medien;
	@FXML
	private TextField tf_browse;
	
	//Constructor
	/**
	 * Put description here
	 * 
	 * @throws IOException
	 */
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
		setText("Medien");
		lv_medien.setItems(liste);	
	}
	
	@Override
	public String toString() {
		return "Medien";
	}
	
	//Event Handlers
	/**
	 * Put description here
	 * 
	 * @param event
	 */
	public void medienUpload(ActionEvent event)
	{
		File file = (File)tf_browse.getUserData();
		if(file != null)
		{
			// TODO: upload file to server
			liste.add(new Medium(file));
			
			// Clear text field
			tf_browse.setText(null);
			tf_browse.setUserData(null);
		}
	}
	
	/**
	 * Put description here
	 * 
	 * @param event
	 */
	public void medienBrowser(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Media File");
		File file = fileChooser.showOpenDialog(this.getScene().getWindow());
		
		if(file != null)
		{
			tf_browse.setUserData(file);
			tf_browse.setText(file.getAbsolutePath());
		}
	}
	
	/**
	 * Put description here
	 * 
	 * @param event
	 */
	public void medien÷ffnen(ActionEvent event)
	{
		Medium selectedMedium = lv_medien.getSelectionModel().getSelectedItem();
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
		else if(selectedMedium.getFile() instanceof CsvNode)
		{
			try
			{
				createSubWindow("Medienbibliothek", new Scene(new TableViewController(((CsvNode)selectedMedium.getFile()).getFileContent())));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else // All other filetypes open on their own
			selectedMedium.getFile().getFileContent();
	}
	
	/**
	 * Put description here
	 * 
	 * @param title
	 * @param scene
	 */
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
		return lv_medien;
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
