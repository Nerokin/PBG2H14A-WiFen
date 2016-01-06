package wifen.client.resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import models.MarkerModel;
import ui.controllers.SpielfeldView;

public class MarkerView extends Parent {
	
	double lastX=0;
	double lastY=0;
	SpielfeldView parent;
	MarkerModel marker;
	

	public MarkerView(MarkerModel m, SpielfeldView f) {
		parent = f;
		marker = m;
		this.setTranslateX(marker.getPosx());
		this.setTranslateY(marker.getPosy());
		this.getChildren().add(new ImageView(marker.getType().img));
		
		marker.getType().img.progressProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue.intValue() >= 1) {
					adjustPosition();
				}
			}	
		});
		
		this.setOnMouseDragged(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				parent.setPannable(false);
				if(lastX != 0 || lastY != 0) {
					MarkerView m = (MarkerView)event.getSource();
					double xoffs = event.getSceneX() - lastX;
					double yoffs = event.getSceneY() - lastY;
					m.setTranslateX(m.getTranslateX() + xoffs);
					m.setTranslateY(m.getTranslateY() + yoffs);
				}
				lastX=event.getSceneX();
				lastY=event.getSceneY();
			}
			
		});
		
		this.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				parent.setPannable(true);
				lastX=0;
				lastY=0;
			}
			
		});
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY) {
					MarkerView mv = (MarkerView) event.getSource();
					parent.removeFromView(mv);
					parent.getModel().removeMarker(mv.marker); 
				}
			}
		});
	}
	
	protected void adjustPosition() {
		this.setTranslateX(getTranslateX() - (marker.getType().img.getWidth()/2));
		this.setTranslateY(getTranslateY() - (marker.getType().img.getHeight()/2));
	}
}