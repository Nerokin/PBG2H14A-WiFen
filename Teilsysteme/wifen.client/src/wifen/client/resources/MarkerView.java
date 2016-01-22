package wifen.client.resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import wifen.client.application.ClientApplication;
import wifen.client.services.GameService;
import wifen.client.ui.controllers.SpielfeldView;
import wifen.commons.MarkerModel;
import wifen.commons.SpielerRolle;

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
	ContextMenu contextMenu;


	/**
	 * Put description here
	 *
	 * @param m
	 * @param f
	 */
	public MarkerView(MarkerModel m, SpielfeldView f) {
		parent = f;
		setMarker(m);
		this.setTranslateX(marker.getPosx());
		this.setTranslateY(marker.getPosy());
		this.adjustPosition();
		this.getChildren().add(new ImageView(marker.getType().getImg()));

		contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("entfernen");
		item1.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
				ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMarkerRemoved(getMarkerModel().getId());
		    }
		});

		CheckMenuItem item2 = new CheckMenuItem("statisch");
		item2.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	getMarkerModel().setIsStatic(!(getMarkerModel().getIsStatic()));
		    	ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMarkerPlaced(getMarkerModel());
		    }
		});

		contextMenu.getItems().addAll(item1, item2);

//		marker.getType().getImg().progressProperty().addListener(new ChangeListener<Number>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				if(newValue.intValue() >= 1) {
//					adjustPosition();
//				}
//			}
//		});

		this.setOnMouseDragged(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				parent.setPannable(false);
				if((!marker.getIsStatic())&&(lastX != 0 || lastY != 0)&&(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().equals(((MarkerView)event.getSource()).marker.getOwner()) || ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().getRolle().equals(SpielerRolle.ADMIN))&&event.getButton()==MouseButton.PRIMARY) {
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
				if((lastX != 0 || lastY != 0)&&(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().equals(((MarkerView)event.getSource()).marker.getOwner()) || ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().getRolle().equals(SpielerRolle.ADMIN))) {
					MarkerView m = (MarkerView)event.getSource();
					marker.setPosx(m.getTranslateX());
					marker.setPosy(m.getTranslateY());
					ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMarkerPlaced(marker);
				}
				lastX=0;
				lastY=0;
			}

		});

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY  && !event.isControlDown() && (ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().equals(((MarkerView)event.getSource()).marker.getOwner()) || ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().getRolle().equals(SpielerRolle.ADMIN))) {
					contextMenu.show((MarkerView) event.getSource(), event.getScreenX(), event.getScreenY());
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
		return marker;
	}

	public void setMarker(MarkerModel marker) {
		this.marker = marker;
	}

	public ContextMenu getContextMenu() {
		return contextMenu;
	}

	public void setContextMenu(ContextMenu contextMenu) {
		this.contextMenu = contextMenu;
	}
}
