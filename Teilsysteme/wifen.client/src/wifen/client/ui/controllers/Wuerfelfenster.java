package wifen.client.ui.controllers;

import wifen.client.services.impl.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;




//
// Justin Nussbaum
// Anfang:
// Drag & Drop in Zusammenarbeit mit Kevin Curtis
/**
 * Würfelfenster Klasse
 *
 * @author Justin Nussbaum
 * @author Johnny Gunko
 * @author Die Autoren der Animation (Animation)
 *
 */

/**TODO :
 * immernoch Ladeverzögerung bei mehreren!
 * Abstände passen noch nicht ganz!
 * und ab 10w wird falsch bzw. garnicht gesplittet!
 */
public class Wuerfelfenster extends TitledPane {

		//constants
		//public static final String CSS_PATH = "/css/MainView.css";
		public static final String FXML_PATH ="/wifen/client/ui/views/Wuerfelfenster.fxml";
		@FXML public ImageView d2;
		@FXML public ImageView d4;
		@FXML public ImageView d6;
		@FXML public ImageView d8;
		@FXML public ImageView d10;
		@FXML public TextField diceText;
		@FXML public Button würfeln;
		@FXML public Button reset;
		@FXML public GridPane animationGrid;
		//@FXML public Label dropped;
		double orgSceneX,orgSceneY;
		double orgTranslateX,orgTranslateY;
		String wuerfelAusdruck ="";
		double mousex,mousey;
		Image wuerfelImage = new Image("/wifen/client/ui/views/wuerfel.png");
		@FXML public Pane dropped;




		//Properties

		private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();

		//Injected Nodes

		//@FXML private FormationDisplay formatDisplay;
		//TODO

		//Constructor
		/**
		 * Put description here
		 * Würfelfenster
		 * @throws IOException
		 */
		public Wuerfelfenster() throws IOException {
			super();

			//Apply CSS
			//getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());

			//Setup FXMLLoader
			setFXMLLoader(new FXMLLoader());
			getFXMLLoader().setRoot(this);
			getFXMLLoader().setLocation(getClass().getResource(FXML_PATH));
			getFXMLLoader().setController(this);

			//Load the View
			getFXMLLoader().load();
			d2.setCursor(Cursor.HAND);
			d4.setCursor(Cursor.HAND);
			d6.setCursor(Cursor.HAND);
			d8.setCursor(Cursor.HAND);
			d10.setCursor(Cursor.HAND);


		}



		//Initialization

		@FXML
		private void initialize() {

		//testMe.setOnMousePressed(wuerfelOnMousePressedEventHandler);
		//	testMe.setOnMouseDragged(wuerfelOnMouseDraggedEventHandler);


			//TODO: Data Binding and Setup of Event Handling
		}

		//Event Handlers

		/**
		 * 2erWürfel DragEvent
		 *
		 * @param ev MouseClick auf 2er Würfel
		 * @throws FileNotFoundException
		 */
		@FXML
		public void wuerfelDrag2(MouseEvent ev) throws FileNotFoundException{	// Erst beim Klicken und gleichzeitig ziehen!

				Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
				db.setDragView(new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d02/d2_1.png")));
			        ClipboardContent content = new ClipboardContent();


			        content.putString("d2");

			        db.setContent(content);
			        ev.consume();
		}

		/**
		 * 4erWürfel DragEvent
		 *
		 * @param ev MouseClick auf 4er Würfel
		 * @throws FileNotFoundException
		 */
		public void wuerfelDrag4(MouseEvent ev) throws FileNotFoundException{	// Erst beim Klicken und gleichzeitig ziehen!

			Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
			db.setDragView(new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d04/d4_4.png")));
		        ClipboardContent content = new ClipboardContent();
		     db.setDragViewOffsetX(300);
		     db.setDragViewOffsetY(75);

		        content.putString("d4");

		        db.setContent(content);
		        ev.consume();
	}
		/**
		 * 6erWürfel DragEvent
		 *
		 * @param ev MouseClick auf 6er Würfel
		 * @throws FileNotFoundException
		 */
		public void wuerfelDrag6(MouseEvent ev) throws FileNotFoundException{	// Erst beim Klicken und gleichzeitig ziehen!

			Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
			db.setDragView(new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d06/d6_6.png")));
			ClipboardContent content = new ClipboardContent();
			 db.setDragViewOffsetX(300);
			 db.setDragViewOffsetY(125);

		        content.putString("d6");

		        db.setContent(content);
		        ev.consume();
	}
		/**
		 * 8erWürfel DragEvent
		 *
		 * @param ev MouseClick auf 8er Würfel
		 * @throws FileNotFoundException
		 */
	public void wuerfelDrag8(MouseEvent ev) throws FileNotFoundException{	// Erst beim Klicken und gleichzeitig ziehen!

			Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
			db.setDragView(new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d08/d8_8.png")));
			ClipboardContent content = new ClipboardContent();
			 db.setDragViewOffsetX(300);
			 db.setDragViewOffsetY(125);

		        content.putString("d8");

		        db.setContent(content);
		        ev.consume();
	}
	/**
	 * 10erWürfel DragEvent
	 *
	 * @param ev MouseClick auf 10er Würfel
	 * @throws FileNotFoundException
	 */
	public void wuerfelDrag10(MouseEvent ev) throws FileNotFoundException{	// Erst beim Klicken und gleichzeitig ziehen!

		Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
		db.setDragView(new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_0.png")));
		ClipboardContent content = new ClipboardContent();
		 db.setDragViewOffsetX(300);
		 db.setDragViewOffsetY(125);

	        content.putString("d10");

	        db.setContent(content);
	        ev.consume();
	}
	/**
	 * click auf einen Würfel
	 *
	 * @param ev MouseClick click auf einen Würfel
	 */

		public void onMousePressed(MouseEvent ev){
			System.out.println("onMousePressed");
		/*	mousex = ev.getSceneX();
		    mousey = ev.getSceneY();*/
			ev.consume();
		}
		/**
		 * Würfel gedroppt
		 *
		 * @param  void Würfel loslassen
		 */
		@FXML
		public void wuerfelDone(){

			System.out.println("DragDone");
		}
		@FXML
		public void wuerfelDragOver(){


		}

		int w2 = 0;
		int w4 = 0;
		int w6 = 0;
		int w8 = 0;
		int w10 = 0;
		/**
		 * Drop Würfel
		 *
		 * @param ev DragEvent beim Droppen
		 */
		@FXML

		public void wuerfelDrop(DragEvent ev){

			Dragboard db = ev.getDragboard();
			ev.setDropCompleted(true);
		switch(db.getString()){
			case("d2"):w2++;break;
			case("d4"):w4++;break;
			case("d6"):w6++;break;
			case("d8"):w8++;break;
			case("d10"):w10++;
		}

		String ausgabe = "";
		boolean temp = false;

		if(w2!=0){
			ausgabe=w2+"w2";
			temp = true;
		}
		if(w4!=0){
			if(temp){
				ausgabe+=";";
			}
			temp = true;
			ausgabe+=w4+"w4";
		}
		if(w6!=0){
			if(temp){
				ausgabe+=";";
			}
			temp = true;
			ausgabe+=w6+"w6";
		}

		if(w8!=0){
			if(temp){
				ausgabe+=";";
			}
			temp = true;
			ausgabe+=w8+"w8";

		}
		if(w10!=0){
			if(temp){
				ausgabe+=";";
			}
			ausgabe+=w10+"w10";
		}

		diceText.setText(ausgabe);
			ev.consume();

		}

		//EreignisFensterAusgabe?(Ersezen durch System.out)

		private static WürfelanimationListe w = new WürfelanimationListe();
		@FXML
		public void würfeln() throws IOException{
			w.resetWuerfelList();
			animationGrid.getChildren().clear();
			String[] tmp = diceText.getText().split(";");
			String[] output = new String[tmp.length];

			if(tmp.length >= 1){
				for(int i = 0; i < tmp.length; i++){
					if(dice.checkInput(tmp[i]) != null){
						output[i] = dice.checkInput(tmp[i]);
						//System.out.println(dice.checkInput(tmp[i]));
						//System.out.println(tmp[i]);

						String[] splitter = tmp[i].split("");
						int anzahl = Integer.parseInt(splitter[0]);
						int seiten = Integer.parseInt(splitter[2]);
						if(seiten==1)
							seiten =10;
						String thisWurfel = ""+seiten;
						for(int j = 0;j<=anzahl-1;j++)
							w.addWelcherWuerfel(thisWurfel);

						 // Testausgabe

					}
					else{
						//System.out.println("Kein gueltiger Wuerfelausdruck.");
						reset();
						return;
					}

				}
				for(String s : output){
					//System.out.println(dice.checkInput(s));
				}
			}
			else{
				if(dice.checkInput(tmp[0]) != null){
					//System.out.println(dice.checkInput(tmp[0]));
				}
				else{
					//System.out.println("Kein gueltiger Wuerfelausdruck.");
					reset();
					return;
				}

			}
			int counterColumn= 0;
			int rowCounter = 0;
			int columnSize = 4;
			int wuerfelCounter = 0;
			ArrayList<String> tempArrayList= w.getAlleWurfel();
			ArrayList<Integer> arrayList = dice.returnAllSingleResults();
			for(String wfl : tempArrayList){
				ImageView tempImageView = new ImageView();
				Timeline tempTimeLine = null;
				tempImageView.setFitWidth(80);
				tempImageView.setFitHeight(80);
				int result = arrayList.get(wuerfelCounter);
					if(Integer.parseInt(wfl)==2){
						tempTimeLine = d02animation(tempImageView, result);
					}else if(Integer.parseInt(wfl)==4){
						tempTimeLine = d04animation(tempImageView, result);
					}
					else if(Integer.parseInt(wfl)==6){
						tempTimeLine = d06animation(tempImageView, result);
					}
					else if(Integer.parseInt(wfl)==8){
						tempTimeLine = d08animation(tempImageView, result);
					}
					else if(Integer.parseInt(wfl)==10){
						tempTimeLine = d10animation(tempImageView, result);
					}

				tempTimeLine.setCycleCount(1);
				tempTimeLine.play();
				animationGrid.add(tempImageView, counterColumn, rowCounter);
				counterColumn++;
				wuerfelCounter++;
				if(counterColumn>columnSize){
					rowCounter++;
					counterColumn= 0;
				}
			}


			dice.playWuerfeln();

			reset();
		}



				/**
				 * testfunktion
				 *
				 * @param ev DragEvent Überm Label (Test)
				 */
				@FXML
				public void overLabel(DragEvent ev){
					Dragboard db = ev.getDragboard();
					ev.acceptTransferModes(TransferMode.ANY);
					ev.consume();
					//System.out.println("overLabel");
				}



		//Getter & Setter

		public FXMLLoader getFXMLLoader() {
			return fxmlLoader.get();
		}

		/**
		 * Reset
		 *
		 * @param void resettet die Variablen und leert das diceText-Feld
		 */
		public void reset(){
			w2 = 0;
			w4 = 0;
			w6 = 0;
			w8 = 0;
			w10 = 0;
			diceText.setText("");

		}

		public void setFXMLLoader(FXMLLoader value) {
			fxmlLoader.set(value);
		}

		public ReadOnlyObjectProperty<FXMLLoader> fxmlLoaderProperty() {
			return fxmlLoader;
		}

		/** @author Autoren der Animation etc..
		 * */

		public Timeline d10animation(ImageView iv, int result) throws FileNotFoundException{
			/*iv ist der ImageView in dem die Animation gezeigt werden soll
			* result ist das Ergebnis aus der Würfel-Methode
			* alle Einzelbilder und Animation laden...
			*/
			Image[] images = new Image[11];
			images[10] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_0.png"));
			images[1] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_1.png"));
			images[2] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_2.png"));
			images[3] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_3.png"));
			images[4] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_4.png"));
			images[5] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_5.png"));
			images[6] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_6.png"));
			images[7] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_7.png"));
			images[8] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_8.png"));
			images[9] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d10/d10_9.png"));
			Image ani = new Image(new FileInputStream("./ressources/wuerfelAnimation/d10_animation.gif"));

			//Animation und Ergebnis den KeyFrames zuweisen
			KeyFrame aniStart = new KeyFrame(Duration.millis(0), new KeyValue(iv.imageProperty(), ani));
			KeyFrame aniFinish = new KeyFrame(Duration.millis(1000), new KeyValue(iv.imageProperty(), ani));
			KeyFrame resultStart = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), images[result]));	//Ergebnis-image aus dem Array holen (-1 nicht notwendig, da d10 Zahlen von 0-9 zeigt)
			KeyFrame resultFinish = new KeyFrame(Duration.millis(2000), new KeyValue(iv.imageProperty(), images[result]));

			//Timeline aus den einzelnen KeyFrames erstellen und zurückgeben
			Timeline timeline = new Timeline(aniStart, aniFinish, resultStart, resultFinish);
			return timeline;
		}

		public Timeline d04animation(ImageView iv, int result) throws FileNotFoundException{
			/*genauso wie d10animation(), selbstverständlich weniger Einzelbilder
			* einziger Unterschied:
			* holt man sich das Ergebnis-image aus dem Array, wird von result 1 abgezogen,
			* da das Ergebnis 1 and Stelle 0 liegt usw.
			*/


			Image[] images = new Image[4];
			images[0] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d04/d4_1.png"));
			images[1] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d04/d4_2.png"));
			images[2] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d04/d4_3.png"));
			images[3] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d04/d4_4.png"));
			Image ani = new Image(new FileInputStream("./ressources/wuerfelAnimation/d04_animation.gif"));

			if(result < 1)
				result=1;

			KeyFrame aniStart = new KeyFrame(Duration.millis(0), new KeyValue(iv.imageProperty(), ani));
			KeyFrame aniFinish = new KeyFrame(Duration.millis(1000), new KeyValue(iv.imageProperty(), ani));
			KeyFrame resultStart = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), images[result-1]));
			KeyFrame resultFinish = new KeyFrame(Duration.millis(2000), new KeyValue(iv.imageProperty(), images[result-1]));

			Timeline timeline = new Timeline(aniStart, aniFinish, resultStart, resultFinish);
			return timeline;
		}

		public Timeline d06animation(ImageView iv, int result) throws FileNotFoundException{
			/*genauso wie d10animation(), selbstverständlich weniger Einzelbilder
			* einziger Unterschied:
			* holt man sich das Ergebnis-image aus dem Array, wird von result 1 abgezogen,
			* da das Ergebnis 1 and Stelle 0 liegt usw.
			*/


			Image[] images = new Image[6];
			images[0] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d06/d6_1.png"));
			images[1] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d06/d6_2.png"));
			images[2] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d06/d6_3.png"));
			images[3] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d06/d6_4.png"));
			images[4] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d06/d6_5.png"));
			images[5] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d06/d6_6.png"));
			Image ani = new Image(new FileInputStream("./ressources/wuerfelAnimation/d06_animation.gif"));

			if(result < 1)
				result=1;

			KeyFrame aniStart = new KeyFrame(Duration.millis(0), new KeyValue(iv.imageProperty(), ani));
			KeyFrame aniFinish = new KeyFrame(Duration.millis(1000), new KeyValue(iv.imageProperty(), ani));
			KeyFrame resultStart = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), images[result-1]));
			KeyFrame resultFinish = new KeyFrame(Duration.millis(2000), new KeyValue(iv.imageProperty(), images[result-1]));

			Timeline timeline = new Timeline(aniStart, aniFinish, resultStart, resultFinish);
			return timeline;
		}

		public Timeline d08animation(ImageView iv, int result) throws FileNotFoundException{
			/*genauso wie d10animation(), selbstverständlich weniger Einzelbilder
			* einziger Unterschied:
			* holt man sich das Ergebnis-image aus dem Array, wird von result 1 abgezogen,
			* da das Ergebnis 1 and Stelle 0 liegt usw.
			*/


			Image[] images = new Image[8];
			images[0] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d08/d8_1.png"));
			images[1] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d08/d8_2.png"));
			images[2] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d08/d8_3.png"));
			images[3] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d08/d8_4.png"));
			images[4] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d08/d8_5.png"));
			images[5] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d08/d8_6.png"));
			images[6] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d08/d8_7.png"));
			images[7] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d08/d8_8.png"));
			Image ani = new Image(new FileInputStream("./ressources/wuerfelAnimation/d08_animation.gif"));

			if(result < 1)
				result=1;

			KeyFrame aniStart = new KeyFrame(Duration.millis(0), new KeyValue(iv.imageProperty(), ani));
			KeyFrame aniFinish = new KeyFrame(Duration.millis(1000), new KeyValue(iv.imageProperty(), ani));
			KeyFrame resultStart = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), images[result-1]));
			KeyFrame resultFinish = new KeyFrame(Duration.millis(2000), new KeyValue(iv.imageProperty(), images[result-1]));

			Timeline timeline = new Timeline(aniStart, aniFinish, resultStart, resultFinish);
			return timeline;
		}

		public Timeline d02animation(ImageView iv, int result) throws FileNotFoundException{
			/*genauso wie d10animation(), selbstverständlich weniger Einzelbilder
			* einziger Unterschied:
			* holt man sich das Ergebnis-image aus dem Array, wird von result 1 abgezogen,
			* da das Ergebnis 1 and Stelle 0 liegt usw.
			*/


			Image[] images = new Image[2];
			images[0] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d02/d2_1.png"));
			images[1] = new Image(new FileInputStream("./ressources/wuerfelAnimation/Einzelbilder_d02/d2_2.png"));
			Image ani;
			if(result == 2)
				 ani = new Image(new FileInputStream("./ressources/wuerfelAnimation/d02_animation_rot.gif"));
			else
			{
				if(result < 1)
					result=1;
				ani = new Image(new FileInputStream("./ressources/wuerfelAnimation/d02_animation_blau.gif"));
			}




			KeyFrame aniStart = new KeyFrame(Duration.millis(0), new KeyValue(iv.imageProperty(), ani));
			KeyFrame aniFinish = new KeyFrame(Duration.millis(1000), new KeyValue(iv.imageProperty(), ani));
			KeyFrame resultStart = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), images[result-1]));
			KeyFrame resultFinish = new KeyFrame(Duration.millis(2000), new KeyValue(iv.imageProperty(), images[result-1]));

			Timeline timeline = new Timeline(aniStart, aniFinish, resultStart, resultFinish);
			return timeline;
		}


}
