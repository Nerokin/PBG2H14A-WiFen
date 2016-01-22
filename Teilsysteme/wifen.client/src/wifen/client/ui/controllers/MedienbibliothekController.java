package wifen.client.ui.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import wifen.client.application.ClientApplication;
import wifen.client.services.ClientMediaService;
import wifen.commons.Medium;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class MedienbibliothekController extends TitledPane
{
	public static final DataFormat MEDIUM_FORMAT = new DataFormat("medium");
	
	ObservableList<Medium> liste = FXCollections.observableArrayList();
	
	//Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	private final ObjectProperty<ClientMediaService> mediaService = new SimpleObjectProperty<>();
	
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

	
	/**
	 * Put description here
	 * 
	 * @param mediaService
	 * @throws IOException
	 */
	public MedienbibliothekController(ClientMediaService mediaService) throws IOException
	{
		this();
		setMediaService(mediaService);
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
		
		lv_medien.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Dragboard db = lv_medien.startDragAndDrop(TransferMode.COPY);
				@SuppressWarnings("unchecked")
				Medium m = (Medium) ((ListView<Medium>) e.getSource()).getSelectionModel().getSelectedItem();
				Image dv = m.getType().getImg();
				db.setDragView(dv);
				
				ClipboardContent cc = new ClipboardContent();
				cc.put(MEDIUM_FORMAT, m);
				
				db.setContent(cc);
				e.consume();
			}
		});
	}
	
	@Override
	public String toString() {
		return "Medien";
	}
	
	public void addMedium(Medium medium)
	{
		liste.add(medium);
		
		// Save to temp folder
		FileOutputStream fos;
		try
		{
			fos = new FileOutputStream(System.getProperty("java.io.tmpdir") + medium.getName());
			fos.write(medium.getRawData());
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
			try
			{
				getMediaService().trySendFile(null, new Medium(file));
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
		
		// Show content
		System.out.println(System.getProperty("java.io.tmpdir") + selectedMedium.getName());
		ClientApplication.instance().getHostServices().showDocument(System.getProperty("java.io.tmpdir") + selectedMedium.getName());
	}
	
	//Getter & Setter
	public ObservableList<Medium> getList()
	{
		return FXCollections.unmodifiableObservableList(liste);
	}
	
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

	
	public final ObjectProperty<ClientMediaService> mediaServiceProperty()
	{
		return this.mediaService;
	}
	
	public final ClientMediaService getMediaService()
	{
		return this.mediaServiceProperty().get();
	}	

	public final void setMediaService(final ClientMediaService chatService)
	{
		this.mediaServiceProperty().set(chatService);
	}	
}
