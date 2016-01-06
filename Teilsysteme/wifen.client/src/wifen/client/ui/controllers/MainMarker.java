package wifen.client.ui.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class MainMarker extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	public void start(Stage stage) throws Exception {
		try {
			stage.setScene(new Scene(new MarkerView()));
			stage.centerOnScreen();
			stage.setTitle("MarkerView");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
