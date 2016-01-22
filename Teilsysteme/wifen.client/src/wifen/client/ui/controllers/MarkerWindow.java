package wifen.client.ui.controllers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.sun.istack.internal.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import wifen.commons.MarkerType;

/**
 *
 * MarkerWindow shows and handles the main functions of the Marker
 *
 * @author Hitziger Fabian (pbg2h14ahi)
 */
public class MarkerWindow extends TitledPane{

	private static final Logger logger = Logger.getLogger(MarkerWindow.class);

	public static final DataFormat MARKER_FORMAT = new DataFormat("marker");

	private ImageView iv = new ImageView();
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	public ObservableList<Button> button_colors = FXCollections.observableArrayList();
	public ObservableList<ImageView> image_shapes = FXCollections.observableArrayList();
	public String[] colors = new String[]{"Rot","Grün","Blau","Gelb"};
	private String[] vorhanden = new String[]{"5-Eck","figur","6-Eck","Baum","Ausrufezeichen","Blitz","Dreieck","Fahne","Feuer","Fragezeichen","Haus","Gras","Haken","Hï¿½uslein","Kreis","Krone","Nadel","PuppeM","PuppeW","Radioaktiv","Schlï¿½ssel","Sprechblase","Verbotsschild","Stern","Viereck","ViereckAbgerundet"};
	private static MarkerWindow instance;

	public boolean markerDragged = false;

	public static MarkerWindow getInstance(){
		if(instance == null)
			try {
				instance = new MarkerWindow();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return instance;
	}

	@FXML ListView<Button> markerColor;
	@FXML ListView<ImageView> markerShape;

	/**
	 * Erstellt die Standard Anzeige des Marker Windows
	 *
	 * @throws IOException
	 */
	public MarkerWindow() throws IOException{
		super();
		//Apply CSS
		getStylesheets().add(getClass().getResource("/wifen/client/ui/css/MarkerView.css").toExternalForm());
		//Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("/wifen/client/ui/views/MarkerView.fxml"));
		getFXMLLoader().setController(this);

		//Load the View
		getFXMLLoader().load();
	}

	/**
	 * Initialize gibt das Verhalten des Marker Windows an
	 */
	@FXML
	private void initialize(){
		//Beispiel fï¿½r das erstellen einer Farbe
		setText("Marker");

		instance=this;

        final File directory = new File("./resources/marker/");
        for (File f : directory.listFiles()) {
            System.out.println(f);
			if(f.getName().indexOf("Rot") == -1 && f.getName().indexOf("Gelb") == -1 && f.getName().indexOf("Grün") == -1 && f.getName().indexOf("Blau") == -1)
			{
				ImageView tempView = new ImageView(new Image(f.toURI().toString()));
				tempView.setId(f.toURI().toString().substring(0, f.toURI().toString().lastIndexOf(".")));
				image_shapes.add(tempView);
				tempView.setFitWidth(70);
				tempView.setPreserveRatio(true);
			}
        }

		//}

		markerShape.setItems(image_shapes);
		for(Button b : button_colors){
			b.setPrefWidth(Double.MAX_VALUE);
			b.setOnAction(new EventHandler<ActionEvent>(){

				 @Override public void handle(ActionEvent e) {
					String colorName = b.getId();
					ImageView selected;

					if((selected = markerShape.getSelectionModel().getSelectedItem()) != null){
						for(String s : vorhanden){
							if(selected.getId().contains(s)){
								selected.setImage(new Image(selected.getId()+"_"+colorName+".png"));
								setImageView(selected.getImage(),selected.getId());
							}
						}

					}
				 }
			});
		}

		markerShape.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImageView>() {
			@Override
			public void changed(ObservableValue<? extends ImageView> observable, ImageView oldValue, ImageView newValue) {
				if(oldValue != null){
					oldValue.setImage(new Image(oldValue.getId()+".png"));
				}
				setImageView(newValue.getImage(), newValue.getId());
				button_colors.clear();
				for(String s : vorhanden){
					if(newValue.getId().contains(s)){
						Button temp;
						for(int i = 0; i < colors.length;i++){
						temp = new Button(colors[i]);
						temp.setId(colors[i]);
						temp.setPrefWidth(markerColor.getPrefWidth());

						button_colors.add(temp);
						}
						for(Button b : button_colors){
							b.setOnAction(new EventHandler<ActionEvent>(){

								 @Override public void handle(ActionEvent e) {
									String colorName = b.getId();
									ImageView selected;

									if((selected = markerShape.getSelectionModel().getSelectedItem()) != null){
										for(String s : vorhanden){
											if(selected.getId().contains(s)){
												selected.setImage(new Image(selected.getId()+"_"+colorName+".png"));
												setImageView(selected.getImage(),selected.getId());
											}
										}

									}
								 }
							});
						}
					}

				}
				markerColor.setItems(button_colors);
				markerColor.refresh();

			}
		});
		/*markerShape.setOnDragDetected(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				System.out.println("Test ");
				markerDragged = true;
			}

		});

		markerShape.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				System.out.println("Test2");
				if(markerDragged){
					MarkerModel m = null;
					try {
						m = new MarkerModel(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer(), event.getSceneX(), event.getSceneY() - (iv.getImage().getHeight() / 2), new MarkerType(iv.getImage(),iv.getId()), "");
						ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMarkerPlaced(m);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					markerDragged = false;

				}

			}

		});*/
		markerShape.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Dragboard db = markerShape.startDragAndDrop(TransferMode.COPY);
				Image dv = getSelectedMarkerType().getImg();
				db.setDragViewOffsetX(dv.getWidth() / 2);
				db.setDragViewOffsetY(dv.getHeight() / 2);
				db.setDragView(dv);

				ClipboardContent cc = new ClipboardContent();
				cc.put(MARKER_FORMAT, getSelectedMarkerType());

				db.setContent(cc);
				e.consume();
			}
		});

	}

	@Override
	public String toString() {
		return "Marker";
	}

	public FXMLLoader getFXMLLoader() {
		return fxmlLoader.get();
	}

	public void setFXMLLoader(FXMLLoader value) {
		fxmlLoader.set(value);
	}


	/**
	 * Gibt den aktuell ausgewï¿½hlten Marker zurï¿½ck
	 *
	 * @return
	 */
	public MarkerType getSelectedMarkerType(){
		try {
			return new MarkerType(this.iv.getImage(), this.iv.getId());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setImageView(Image image, String id){
		this.iv.setId(id);
		this.iv.setImage(image);
	}
}