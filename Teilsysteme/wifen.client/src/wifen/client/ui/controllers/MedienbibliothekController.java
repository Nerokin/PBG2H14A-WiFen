package wifen.client.ui.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import wifen.commons.Medium;

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
	@FXML
	private Button btn_browse;
	@FXML
	private Button btn_open;
	@FXML
	private Button btn_upload;
	
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
		getFXMLLoader().setLocation(getClass().getResource("../views/Medienbibliothek.fxml"));
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
		
		btn_browse.setOnAction(this::browse);
		btn_upload.setOnAction(this::upload);
		btn_open.setOnAction(this::openMedia);
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
	public void upload(ActionEvent event)
	{
		File file = (File)tf_browse.getUserData();
		if(file != null)
		{
			// TODO: upload file to server
			try
			{
				liste.add(new Medium(file));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
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
	public void browse(ActionEvent event)
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
	public void openMedia(ActionEvent event)
	{
		Medium selectedMedium = lv_medien.getSelectionModel().getSelectedItem();
		if(selectedMedium == null)
			return;
		
		// Show content in seperate window.
		String type = selectedMedium.getType();
		if(type.equalsIgnoreCase("png") || type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("bmp") || type.equalsIgnoreCase("gif"))
		{
			// Convert raw data to image
			InputStream stream = new ByteArrayInputStream(selectedMedium.getRawData());
			Image image = new Image(stream);
			
			try
			{
				// Create view window and show data
				createSubWindow("Medienbibliothek", new Scene(new ImageViewController(image)));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(type.equalsIgnoreCase("txt"))
		{
			try
			{
				// Convert raw data to text array
				String[] text = new String(selectedMedium.getRawData(), "ISO-8859-1").split("\n");

				// Create view window and show data
				createSubWindow("Medienbibliothek", new Scene(new TextViewController(text)));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(type.equalsIgnoreCase("csv"))
		{			
			try
			{
				// Convert raw data to csv array
				String[] text = new String(selectedMedium.getRawData(), "ISO-8859-1").split("\n");
				String[][] table = new String[text.length][];
				for(int i = 0; i < text.length; i++)
				{
					table[i] = text[i].split(";");
				}
				
				// Create view window and show data
				createSubWindow("Medienbibliothek", new Scene(new TableViewController(table)));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else // All other filetypes open on their own
		{
			// TODO: implement this
		}
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
