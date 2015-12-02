package wifen.client.ui.controllers;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setScene(new Scene(new Wuerfelfenster()));
			primaryStage.centerOnScreen();
			primaryStage.setTitle("Tiel");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
