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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import wifen.client.services.impl.DocNode;
import wifen.client.services.impl.PdfNode;
import wifen.client.services.impl.XlsNode;
import wifen.commons.Medium;

public class MedienbibliothekController extends AnchorPane {
	ObservableList<Medium> liste = FXCollections.observableArrayList();

	// Properties
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

	// Injected Nodes
	@FXML private ListView<Medium> listViewMedien;
	@FXML private TextField tbxMedienBrowser;

	// Constructor
	public MedienbibliothekController() throws IOException {
		super();

		// Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("/fxml/Medienbibliothek.fxml"));
		getFXMLLoader().setController(this);

		// Load the View
		getFXMLLoader().load();
	}

	// Initialization
	@FXML
	private void initialize() {
		listViewMedien.setItems(liste);
	}

	// Event Handlers
	public void MedienUpload(ActionEvent event) {
		File file = (File) tbxMedienBrowser.getUserData();
		if (file != null) {
			// TODO: upload file to server
			liste.add(new Medium(file));
		}
	}

	public void MedienBrowser(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Media File");
		File file = fileChooser.showOpenDialog(this.getScene().getWindow());

		if (file != null) {
			tbxMedienBrowser.setUserData(file);
			tbxMedienBrowser.setText(file.getAbsolutePath());
		}
	}

	public void Medien÷ffnen(ActionEvent event) {
		Medium selectedMedium = listViewMedien.getSelectionModel().getSelectedItem();
		// For some types just call the function which handles the viewing of
		// that file alone
		if (selectedMedium.getFile() instanceof PdfNode || selectedMedium.getFile() instanceof DocNode
				|| selectedMedium.getFile() instanceof XlsNode)
			selectedMedium.getFile().getFileContent();
		else // Show content in seperate window. TODO: create window to show
				// content
			selectedMedium.getFile().getFileContent();
	}

	// Getter & Setter
	public ListView<Medium> getListViewMedien() {
		return listViewMedien;
	}

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
