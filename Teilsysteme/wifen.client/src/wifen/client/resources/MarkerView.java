package wifen.client.resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import wifen.client.application.ClientApplication;
import wifen.client.services.GameService;
import wifen.client.ui.controllers.SpielfeldView;
import wifen.commons.MarkerModel;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class MarkerView extends Parent {
	
	private double lastX=0;
	private double lastY=0;
	private SpielfeldView parent;
	private MarkerModel marker;
	

	/**
	 * Put description here
	 * 
	 * @param m
	 * @param f
	 */
	public MarkerView(MarkerModel m, SpielfeldView f) {
		parent = f;
		marker = m;
		this.setTranslateX(marker.getPosx());
		this.setTranslateY(marker.getPosy());
		this.adjustPosition();
		this.getChildren().add(new ImageView(marker.getType().getImg()));
		
		marker.getType().getImg().progressProperty().addListener(new ChangeListener<Number>() {

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
				if(event.getButton() == MouseButton.SECONDARY  && !event.isControlDown() && ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().equals(((MarkerView)event.getSource()).marker.getOwner())) {
					MarkerView mv = (MarkerView) event.getSource();
					ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMarkerRemoved(mv.marker.hashCode());
					//parent.removeFromView(mv);
					//parent.getModel().removeMarker(mv.marker); 
				}
			}
		});
	}
	
	/**
	 * Put description here
	 */
	protected void adjustPosition() {
		this.setTranslateX(getTranslateX() - (marker.getType().getImg().getWidth()/2));
		this.setTranslateY(getTranslateY() - (marker.getType().getImg().getHeight()/2));
	}
	
	public MarkerModel getMarkerModel() {
		return this.marker;
	}
}
