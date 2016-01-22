package wifen.client.ui.controllers;

import java.io.File;
import java.io.IOException;

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
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * 
 * MarkerWindow shows and handles the main functions of the Marker
 * 
 * @author Hitziger Fabian (pbg2h14ahi)
 */
public class MarkerWindow extends TitledPane{
	private ImageView iv;
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	public ObservableList<Button> button_colors = FXCollections.observableArrayList();
	public ObservableList<ImageView> image_shapes = FXCollections.observableArrayList();
	public String[] colors = new String[]{"Rot","Grün","Blau","Gelb"};
	private String[] vorhanden = new String[]{"5-Eck","figur"};
	
	private static MarkerWindow instance;
	
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
		getStylesheets().add(getClass().getResource("../css/MarkerView.css").toExternalForm());
		//Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("../views/MarkerView.fxml"));
		getFXMLLoader().setController(this);

		//Load the View
		getFXMLLoader().load();
	}
	
	/**
	 * Initialize gibt das Verhalten des Marker Windows an
	 */
	@FXML
	private void initialize(){
		//Beispiel für das erstellen einer Farbe
		setText("Marker");
		
		instance=this;
		
		
		File file = new File(getClass().getResource("../../resources/marker").getFile());
		for(File f : file.listFiles()){
			System.out.println(f.getName());
			if(f.getName().indexOf("Rot") == -1 && f.getName().indexOf("Gelb") == -1 && f.getName().indexOf("Grün") == -1 && f.getName().indexOf("Blau") == -1)
			{

				ImageView tempView = new ImageView(new Image(f.toURI().toString()));
				tempView.setId(f.toURI().toString().substring(0, f.toURI().toString().lastIndexOf(".")));
				image_shapes.add(tempView);
				tempView.setFitWidth(70);
				tempView.setPreserveRatio(true);
				System.out.println(markerShape.getWidth());
			}
		}
		markerShape.setItems(image_shapes);
		for(Button b : button_colors){
			b.setOnAction(new EventHandler<ActionEvent>(){
				
				 @Override public void handle(ActionEvent e) {
					String colorName = b.getId();
					System.out.println(colorName);
					ImageView selected;
				
					if((selected = markerShape.getSelectionModel().getSelectedItem()) != null){
						for(String s : vorhanden){
							if(selected.getId().contains(s)){
								selected.setImage(new Image(selected.getId()+"_"+colorName+".png"));
								System.out.println(""+selected.getId()+"_"+colorName+".png");
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
				button_colors.clear();
				for(String s : vorhanden){
					if(newValue.getId().contains(s)){
						Button temp;
						for(int i = 0; i < colors.length;i++){
						temp = new Button(colors[i]);
						temp.setId(colors[i]);
						temp.setPrefWidth(markerColor.getPrefWidth());
						//temp.setBackground(new Background(new BackgroundFill(Color.web(colors[i]), CornerRadii.EMPTY, Insets.EMPTY)));
						button_colors.add(temp);
						}
						for(Button b : button_colors){
							b.setOnAction(new EventHandler<ActionEvent>(){
								
								 @Override public void handle(ActionEvent e) {
									String colorName = b.getId();
									System.out.println(colorName);
									ImageView selected;
								
									if((selected = markerShape.getSelectionModel().getSelectedItem()) != null){
										for(String s : vorhanden){
											if(selected.getId().contains(s)){
												selected.setImage(new Image(selected.getId()+"_"+colorName+".png"));
												System.out.println(""+selected.getId()+"_"+colorName+".png");
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
		
		
		markerShape.setOnDragDropped(new EventHandler<DragEvent>(){

			@Override
			public void handle(DragEvent event) {
				
				
			}
			
		});
		markerShape.getSelectionModel().select(0);
		iv = markerShape.getSelectionModel().getSelectedItem();
		markerShape.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				System.out.println(markerShape.getSelectionModel().getSelectedItem());
				setImageView(markerShape.getSelectionModel().getSelectedItem());
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
	 * Setzt den Farbeffekt eines Bildes zu der angegebenen Farbe
	 * 
	 * @param colorName colorName beschreibt den Namen der Farbe
	 * @return
	 */
	public static ColorAdjust getColor(String colorName){
		ColorAdjust adjust = new ColorAdjust();
		Color targetColor = Color.web(colorName); 
	          //calculate hue: map from [0,360] to [-1,1];
	    double hue = map( targetColor.getHue() + 180, 0, 360, -1, 1);

		adjust.setHue(hue);
		adjust.setSaturation(1);
		return adjust;
	}
	
	/**
	 * Gibt den aktuell ausgewählten Marker zurück
	 * 
	 * @return
	 */
	public ImageView getSelectedMarkerType(){
		System.out.println(iv);
		return markerShape.getSelectionModel().getSelectedItem();
	}	
	
	/**
	 * Berechnet den benötigten Wert für den Farbeffekt
	 * 
	 * @param value Der Farbwert der ausgewählten Farbe
	 * @param start Der Minimale Farbwert für die Farbe
	 * @param stop Der Maximale Farbwert für die Farbe
	 * @param targetStart Der Minimale Wert für den Farbeffekt
	 * @param targetStop Der Maximale Wert für den Farbeffekt
	 * @return
	 */
	public static double map(double value, double start, double stop, double targetStart, double targetStop) {
	     return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
	}
	public void setImageView(ImageView iv){
		this.iv=iv;
		System.out.println(iv);
		System.out.println(this.iv);
	}
}