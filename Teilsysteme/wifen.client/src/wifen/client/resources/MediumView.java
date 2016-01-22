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
import wifen.commons.MediumModel;
import wifen.commons.SpielerRolle;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class MediumView extends Parent {
	
	private double lastX=0;
	private double lastY=0;
	private SpielfeldView parent;
	private MediumModel medium;
	ContextMenu contextMenu;
	

	/**
	 * Put description here
	 * 
	 * @param m
	 * @param f
	 */
	public MediumView(MediumModel m, SpielfeldView f) {
		parent = f;
		setMedium(m);
		this.setTranslateX(medium.getPosx());
		this.setTranslateY(medium.getPosy());
		this.adjustPosition();
		this.getChildren().add(new ImageView(medium.getMedium().getType().getImg()));
		
		contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("entfernen");
		item1.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
				ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMediumRemoved(getMedium().getId());
		    }
		});
		
		CheckMenuItem item2 = new CheckMenuItem("statisch");
		item2.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	getMedium().setIsStatic(!(getMedium().getIsStatic()));
		    	ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMediumPlaced(getMedium());
		    }
		});

		contextMenu.getItems().addAll(item1, item2);		
		
		medium.getMedium().getType().getImg().progressProperty().addListener(new ChangeListener<Number>() {

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
				if(!(medium.getIsStatic())&&(lastX != 0 || lastY != 0)&&(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().equals(medium.getOwner()) || ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().getRolle().equals(SpielerRolle.ADMIN))&&event.getButton()==MouseButton.PRIMARY) {
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
				if((lastX != 0 || lastY != 0)&&(ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().equals(medium.getOwner()) || ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().getRolle().equals(SpielerRolle.ADMIN))) {
					MarkerView m = (MarkerView)event.getSource();
					medium.setPosx(m.getTranslateX());
					medium.setPosy(m.getTranslateY());
					ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().sendMediumPlaced(medium);
				}
				lastX=0;
				lastY=0;
			}
			
		});
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.SECONDARY  && !event.isControlDown() && (ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().equals(medium.getOwner()) || ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, false).next().getActivePlayer().getRolle().equals(SpielerRolle.ADMIN))) {
					contextMenu.show((MarkerView) event.getSource(), event.getScreenX(), event.getScreenY());
				}
			}
		});
	}
	
	/**
	 * Put description here
	 */
	protected void adjustPosition() {
		this.setTranslateX(getTranslateX() - (medium.getMedium().getType().getImg().getWidth()/2));
		this.setTranslateY(getTranslateY() - (medium.getMedium().getType().getImg().getHeight()/2));
	}
	
	public MediumModel getMedium() {
		return medium;
	}

	public void setMedium (MediumModel medium) {
		this.medium = medium;
	}

	public ContextMenu getContextMenu() {
		return contextMenu;
	}

	public void setContextMenu(ContextMenu contextMenu) {
		this.contextMenu = contextMenu;
	}
}
