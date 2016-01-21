package wifen.client.ui.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import wifen.client.application.ClientApplication;
import wifen.client.services.impl.OptionProvider;

/**
 * Interaction logic for the options view
 * 
 * @author unknown
 * @author Marc Brinkmann
 *
 */
public class OptionenController extends BorderPane {
	
	// Attributes
	OptionProvider op;

	// constants
	public static final String CSS_PATH = "/wifen/client/ui/css/Optionen.css";
	public static final String FXML_PATH = "/wifen/client/ui/views/Optionen.fxml";

	// Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes
	@FXML Slider sd_volumen;
	@FXML CheckBox cbx_muteMusic;
	@FXML CheckBox cbx_muteSound;
	@FXML Slider sd_filesize;
	@FXML Button btn_speichern;
	@FXML Button btn_abbrechen;
	@FXML Label lb_ip;
	@FXML Label lb_version;

	// @FXML private FormationDisplay formatDisplay;
	// TODO


	/**
	 * Put description here
	 * 
	 * @throws IOException
	 */
	public OptionenController() throws IOException {
		super();
		op = ClientApplication.instance().getServiceRegistry().getServiceProviderByClass(OptionProvider.class);

		// Apply CSS
		// getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());

		// Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource(FXML_PATH));
		getFXMLLoader().setController(this);

		// Load the View
		getFXMLLoader().load();
	}

	// Initialization
	@FXML	
	private void initialize() {		
		btn_speichern.setOnAction(this::speichernOnAction);
		btn_abbrechen.setOnAction(this::abbrechenOnAction);			
		
		sd_volumen.setValue(op.getVolume());
		cbx_muteMusic.setSelected(op.getMusicMuted());
		cbx_muteSound.setSelected(op.getSoundMuted());
		sd_filesize.setValue(op.getMaxFileSize());
		
		try {
			lb_ip.setText(InetAddress.getLocalHost().getHostAddress() + " (Lokal)");
		} catch (UnknownHostException e) {
			lb_ip.setText("Konnte nicht gefunden werden");
		}
	}

	// Event Handlers	
	/**
	 * Saves the options in the user preferences via {@linkplain OptionProvider}
	 */
	private void speichernOnAction(ActionEvent event){	
		op.setVolume(sd_volumen.getValue());		
		op.setMusicMuted(cbx_muteMusic.isSelected());
		op.setSoundMuted(cbx_muteSound.isSelected());
		op.setMaxFileSize(sd_filesize.getValue());
		closeWindow();
	}
	
	private void abbrechenOnAction(ActionEvent event){	
		closeWindow();
	}
	
	private void closeWindow() {
		Window window = getScene().getWindow();   

        if (window instanceof Stage){
            ((Stage) window).close();
        }
	}
	
	
	// Getter & Setter
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
