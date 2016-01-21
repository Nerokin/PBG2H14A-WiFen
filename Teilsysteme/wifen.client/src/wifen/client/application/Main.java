package wifen.client.application;

import javafx.application.Application;

/**
 * Entry point of the program.<br>
 * Launches a new JavaFX {@linkplain ClientApplication}.
 *
 * @author Konstantin Schaper
 */
public class Main {

	public static void main(String[] args) {
		System.setProperty("glass.accessible.force", "false");
		Application.launch(ClientApplication.class, args);
	}

}
