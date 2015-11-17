package wifen.client.view.impl;

import java.io.IOException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MarkerView extends Pane{
	private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
	public ObservableList<Button> button_colors = FXCollections.observableArrayList();
	public ObservableList<ImageView> image_shapes = FXCollections.observableArrayList();
	@FXML ListView<Button> markerColor;
	@FXML ListView<ImageView> markerShape;
	
	public MarkerView() throws IOException{
		super();
		//Apply CSS
		getStylesheets().add(getClass().getResource("MarkerView.css").toExternalForm());
		//Setup FXMLLoader
		setFXMLLoader(new FXMLLoader());
		getFXMLLoader().setRoot(this);
		getFXMLLoader().setLocation(getClass().getResource("Markierung.fxml"));
		getFXMLLoader().setController(this);
		
		//Load the View
		getFXMLLoader().load();
	}
	
	@FXML
	private void initialize(){
		//Beispiel für das erstellen einer Farbe
		//Temporär (zum testen) erstellter Button
		//Später mit foreach durch die vorhandenen Farben/Shapes gehen
		Button temp = new Button("Black");
		temp.autosize();
		temp.setStyle("-fx-base: #000;");
		temp.setId("black");
		button_colors.add(temp);
		markerColor.setItems(button_colors);
		
		//Beispiel für das erstellen eines Shapes
		Image tempImage = new Image(getClass().getResourceAsStream("Kreis.png"));
		image_shapes.add(new ImageView(tempImage));
		markerShape.setItems(image_shapes);
		tempImage = new Image(getClass().getResourceAsStream("Kreis.png"));
		image_shapes.add(new ImageView(tempImage));
		markerShape.setItems(image_shapes);
		
		for(Button b : button_colors){
			b.setOnAction(new EventHandler<ActionEvent>(){
				
				 @Override public void handle(ActionEvent e) {
					 System.out.println("Pressed "+b.getId());
					 String colorName = b.getId();
					 for(ImageView i : image_shapes){
						 ColorAdjust adjust = new ColorAdjust();
						 Color targetColor = Color.web(colorName); 
				          //calculate hue: map from [0,360] to [-1,1];
				         double hue = map( targetColor.getHue() + 180, 0, 360, -1, 1);

						 adjust.setHue(hue);
						 adjust.setSaturation(1);
						 i.setEffect(adjust);
						// adjust.setBrightness(-1);
						// ImageView view = new ImageView();
						// view.setEffect(adjust);
						// view.setImage(image_shapes.get(0));
					 }
				 }
			});
		}
		
		for(ImageView i : image_shapes){
			i.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					System.out.println("Pressed "+i.getId());
					
				}
			});
		}
	}
	public FXMLLoader getFXMLLoader() {
		return fxmlLoader.get();
	}
	
	public void setFXMLLoader(FXMLLoader value) {
		fxmlLoader.set(value);
	}
	
	public static double map(double value, double start, double stop, double targetStart, double targetStop) {
	     return targetStart + (targetStop - targetStart) * ((value - start) / (stop - start));
	}
}
