package wifen.client.resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import wifen.client.ui.controllers.Spielfeld;

public class Marker extends Parent {

	double lastX = 0;
	double lastY = 0;
	MarkerType type;
	Spielfeld parent;
	String desc;

	public Marker(double x, double y, MarkerType mt, Spielfeld f) {
		type = mt;
		parent = f;
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.getChildren().add(new ImageView(type.img));
		type.img.progressProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() >= 1)
					adjustPosition();
			}
		});
		/*
		 * this.setOnMousePressed(new EventHandler<MouseEvent>(){
		 * 
		 * @Override public void handle(MouseEvent event) {
		 * lastX=event.getSceneX(); lastY=event.getSceneY(); } });
		 * this.setOnMouseDragged(new EventHandler<MouseEvent>() {
		 * 
		 * @Override public void handle(MouseEvent event) { posx = event.getX();
		 * posy = event.getY(); Marker m = (Marker)event.getSource();
		 * m.setTranslateX(m.getTranslateX()+(posx-lastX));
		 * m.setTranslateY(m.getTranslateY()+(posy-lastY)); lastX =
		 * event.getX(); lastY = event.getSceneY();
		 * 
		 * } });
		 */
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				parent.getScrollPane().setPannable(false);
				if (lastX != 0 || lastY != 0) {
					Marker m = (Marker) event.getSource();
					double xoffs = event.getSceneX() - lastX;
					double yoffs = event.getSceneY() - lastY;
					m.setTranslateX(m.getTranslateX() + xoffs);
					m.setTranslateY(m.getTranslateY() + yoffs);
				}
				lastX = event.getSceneX();
				lastY = event.getSceneY();
			}

		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				parent.getScrollPane().setPannable(true);
				lastX = 0;
				lastY = 0;
			}

		});
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.SECONDARY)
					parent.removeMarker((Marker) event.getSource());
			}
		});
	}

	protected void adjustPosition() {
		this.setTranslateX(getTranslateX() - (type.img.getWidth() / 2));
		this.setTranslateY(getTranslateY() - (type.img.getHeight() / 2));
	}
	
	public MarkerType getType() {
		return type;
	}
}
