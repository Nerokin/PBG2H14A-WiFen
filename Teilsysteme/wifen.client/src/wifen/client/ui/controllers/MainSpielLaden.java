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
public class MainSpielLaden extends Application{
		
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setScene(new Scene(new SpielLadenView()));
			primaryStage.centerOnScreen();
			primaryStage.setTitle("Spiel Laden");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

