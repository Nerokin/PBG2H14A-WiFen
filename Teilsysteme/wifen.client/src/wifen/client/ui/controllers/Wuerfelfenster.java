package wifen.client.ui.controllers;


import java.io.FileNotFoundException;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Random;


import java.util.logging.Logger;

import javafx.animation.KeyFrame;

import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
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
import wifen.client.application.ClientApplication;
import wifen.client.services.ClientGameeventService;
import wifen.client.services.GameService;
import wifen.client.services.impl.dice;




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
		private ArrayList<Timeline> AnimationLines = new ArrayList<Timeline>();		
		
		boolean temp = false;
		
		Image imagesD2[];
		Image animationD2;
		Image animationD2rot =  new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/d02_animation_rot.gif"));
		Image animationD2blau =  new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/d02_animation_blau.gif"));
		Image imagesD4[];
		Image animationD4 = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/d04_animation.gif"));
		Image imagesD6[];
		Image animationD6 = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/d06_animation.gif"));
		Image imagesD8[];
		Image animationD8 = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/d08_animation.gif"));
		Image imagesD10[];
		Image animationD10 = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/d10_animation.gif"));
		Image animations[];
		
		
	
		

		private static final Logger logger = Logger.getLogger(Wuerfelfenster.class.getName());


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
			
				diceText.setPromptText("Ziehe die Würfel in das mittlere Feld!");
				imagesD2 = new Image[2];
				imagesD4 = new Image[4];
				imagesD6 = new Image[6];
				imagesD8 = new Image[8];
				imagesD10 = new Image[11];
				
				animationGrid.setHgap(20);
				animationGrid.setVgap(75);
				animationGrid.setPadding(new Insets(20,5,5,5));
				
				
				try {
					// Bilder für 2er "Würfel" Animation laden... :
					imagesD2[0] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d02/d2_1.png"));
					imagesD2[1] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d02/d2_2.png"));
					// Bilder für 4er "Würfel" Animation laden... :
					imagesD4[0] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d04/d4_1.png"));
					imagesD4[1] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d04/d4_2.png"));
					imagesD4[2] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d04/d4_3.png"));
					imagesD4[3] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d04/d4_4.png"));
					// Bilder für 6er "Würfel" Animation laden... :
					imagesD6[0] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d06/d6_1.png"));
					imagesD6[1] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d06/d6_2.png"));
					imagesD6[2] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d06/d6_3.png"));
					imagesD6[3] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d06/d6_4.png"));
					imagesD6[4] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d06/d6_5.png"));
					imagesD6[5] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d06/d6_6.png"));
					// Bilder für 8er "Würfel" Animation laden... :
					imagesD8[0] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d08/d8_1.png"));
					imagesD8[1] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d08/d8_2.png"));
					imagesD8[2] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d08/d8_3.png"));
					imagesD8[3] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d08/d8_4.png"));
					imagesD8[4] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d08/d8_5.png"));
					imagesD8[5] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d08/d8_6.png"));
					imagesD8[6] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d08/d8_7.png"));
					imagesD8[7] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d08/d8_8.png"));
					// Bilder für 10er "Würfel" Animation laden... :
					imagesD10[10] = new Image(getClass().getResourceAsStream("../../resources/wuerfels/wuerfelAnimation/Einzelbilder_d10/d10_0.png"));
					imagesD10[1] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d10/d10_1.png"));
					imagesD10[2] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d10/d10_2.png"));
					imagesD10[3] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d10/d10_3.png"));
					imagesD10[4] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d10/d10_4.png"));
					imagesD10[5] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d10/d10_5.png"));
					imagesD10[6] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d10/d10_6.png"));
					imagesD10[7] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d10/d10_7.png"));
					imagesD10[8] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d10/d10_8.png"));
					imagesD10[9] = new Image(getClass().getResourceAsStream("../../resources/wuerfel/wuerfelAnimation/Einzelbilder_d10/d10_9.png"));
			
					
				} catch (Exception e) {

					System.err.println("Bilder konnten nicht geladen werden!\n"+e.getMessage());
				}
				
			
				
				
		//testMe.setOnMousePressed(wuerfelOnMousePressedEventHandler);
		//	testMe.setOnMouseDragged(wuerfelOnMouseDraggedEventHandler);


			//TODO: Data Binding and Setup of Event Handling
			setText("Würfel");
		}
		
		@Override
		public String toString() {
			return "Wuerfel";
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
				db.setDragView(imagesD2[0]);
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
			db.setDragView(imagesD4[3]);
		        ClipboardContent content = new ClipboardContent();
		    /* db.setDragViewOffsetX(300);
		     db.setDragViewOffsetY(75);*/

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
			db.setDragView(imagesD6[5]);
			ClipboardContent content = new ClipboardContent();
			/* db.setDragViewOffsetX(300);
			 db.setDragViewOffsetY(125);
			 */

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
			db.setDragView(imagesD8[7]);
			ClipboardContent content = new ClipboardContent();
			/* db.setDragViewOffsetX(300);
			 db.setDragViewOffsetY(125);*/

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
		db.setDragView(imagesD10[10]);
		ClipboardContent content = new ClipboardContent();
		/* db.setDragViewOffsetX(300);
		 db.setDragViewOffsetY(125);*/

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
			//System.out.println("onMousePressed");
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

			//System.out.println("DragDone");
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
		
		private int colInd = 0;
		private int rowInd = 0;
		
		@FXML
		public void wuerfelDrop(DragEvent ev){
			
			try{
				
			final ImageView tempImageView = new ImageView();
				
			tempImageView.setFitWidth(75);
			tempImageView.setFitHeight(75);
			int columnSize = 4;
			
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
			temp = false;
	
			if(w2!=0){
				ausgabe=w2+"w2";
				temp = true;
				tempImageView.setImage(imagesD2[0]);
				animationGrid.add(tempImageView, colInd, rowInd);
			}
			if(w4!=0){
				if(temp){					
					ausgabe+=";";	
				}
				temp = true;
				ausgabe+=w4+"w4";
				tempImageView.setImage(imagesD4[3]);
				animationGrid.add(tempImageView, colInd, rowInd);
			}
			if(w6!=0){
				if(temp){
					ausgabe+=";";
				}
				temp = true;
				ausgabe+=w6+"w6";
				tempImageView.setImage(imagesD6[5]);
				animationGrid.add(tempImageView, colInd, rowInd);
			}
	
			if(w8!=0){
				if(temp){
					ausgabe+=";";
				}
				temp = true;
				ausgabe+=w8+"w8";
				tempImageView.setImage(imagesD8[7]);
				animationGrid.add(tempImageView, colInd, rowInd);
			}
			if(w10!=0){
				if(temp){
					ausgabe+=";";
				}
				temp = true;
				ausgabe+=w10+"w10";
				tempImageView.setImage(imagesD10[0]);
				animationGrid.add(tempImageView, colInd, rowInd);
			}
			
			colInd++;
			if(colInd>columnSize-1){
				rowInd++;
				colInd = 0;
			}
			
			diceText.setText(ausgabe);
				ev.consume();

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
		}
		
		
		
		private static WürfelanimationListe w = new WürfelanimationListe();
		@FXML
		public void würfeln() throws IOException{
			rowInd = 0;
			colInd = 0;
//			logger.info("THIS IS WUERFELN!");
			AnimationLines.clear();
			w.resetWuerfelList();
			animationGrid.getChildren().clear();
			String[] tmp = diceText.getText().split(";");
			String output = "";
			
			ArrayList<Integer> arrayList = null;

			
			if(tmp.length >= 1){
				for(int i = 0; i < tmp.length; i++){
					String tmpInput = dice.checkInput(tmp[i]);
					if(tmpInput != null){
						output += tmpInput;
						String[] splitter = tmp[i].split("w");
					
						int anzahl = Integer.parseInt(splitter[0]);
						String[] tmpSeiten = splitter[1].split("\\+");
						int seiten = Integer.parseInt(tmpSeiten[0]);
						
						
						if(seiten==1)
							seiten =10;
						String thisWurfel = ""+seiten;
						for(int j = 0;j<=anzahl-1;j++)
							w.addWelcherWuerfel(thisWurfel);
					
					}
					else{
						//System.out.println("Kein gueltiger Wuerfelausdruck.");
						reset();
						return;
					}
				}
				arrayList = dice.returnAllSingleResults();
				int resultAsZahl =0;
				for(int tmpResult : arrayList)
					resultAsZahl+=tmpResult;
				
				resultAsZahl += dice.returnModifier().get(0);
				String p = ClientApplication.instance().getServiceRegistry().getServiceProviders(GameService.class, true)
						.next().getActivePlayerName();
			    ClientApplication.instance().getServiceRegistry().getServiceProviders(ClientGameeventService.class, true)
			    		.next().sendGameevent("", p +  " : Ergebnis: "+resultAsZahl+ " " + output);
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
			Timeline tempTimeLine = null;
			ArrayList<String> tempArrayList= w.getAlleWurfel();
			 // Beinhaltet insgesamtes Ergebnis aller Würfel!
			

			for(String wfl : tempArrayList){
				ImageView tempImageView = new ImageView();
				
				tempImageView.setFitWidth(75);
				tempImageView.setFitHeight(75);
				
				int result = arrayList.get(wuerfelCounter);
					if(Integer.parseInt(wfl)==2){
						tempTimeLine = d02animation(tempImageView, result);
						AnimationLines.add(tempTimeLine);
					}else if(Integer.parseInt(wfl)==4){
						tempTimeLine = d04animation(tempImageView, result);
						AnimationLines.add(tempTimeLine);
					}
					else if(Integer.parseInt(wfl)==6){
						tempTimeLine = d06animation(tempImageView, result);
						AnimationLines.add(tempTimeLine);
					}
					else if(Integer.parseInt(wfl)==8){
						tempTimeLine = d08animation(tempImageView, result);
						AnimationLines.add(tempTimeLine);
					}
					else if(Integer.parseInt(wfl)==10){
						tempTimeLine = d10animation(tempImageView, result);
						AnimationLines.add(tempTimeLine);
					}
				
				animationGrid.add(tempImageView, counterColumn, rowCounter);
				counterColumn++;
				wuerfelCounter++;
				if(counterColumn>columnSize-1){
					rowCounter++;
					counterColumn= 0;
				}
			}
			
			for(Timeline t1 : AnimationLines){
				//System.out.println("AnimationPlay");
				t1.setCycleCount(1);
				t1.setRate(0.5);
				t1.play();
			
			}
			dice.playWuerfeln();
			
		}
		@FXML
		public void onEnter() throws IOException{
			würfeln();
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
			diceText.requestFocus();
			AnimationLines.clear();
			animationGrid.getChildren().clear();	
			rowInd = 0;
			colInd = 0;

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
	
//			animationD10 = new Image(new FileInputStream("./ressources/wuerfelAnimation/d10_animation.gif"));

			//Animation und Ergebnis den KeyFrames zuweisen
			KeyFrame[] keys = new KeyFrame[10];
			Random r = new Random();
			for (int i = 0; i < keys.length; i++){
				int tmp = r.nextInt(10); 
				keys[i] = new KeyFrame(Duration.millis(i * 100), new KeyValue(iv.imageProperty(),imagesD10[tmp]));
			}
			
			KeyFrame resultFrame = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), imagesD10[result-1]));

			Timeline timeline = new Timeline(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], keys[6], keys[7], keys[8], keys[9], resultFrame);
			return timeline;
		}

		public Timeline d04animation(ImageView iv, int result) throws FileNotFoundException{
			/*genauso wie d10animation(), selbstverständlich weniger Einzelbilder
			* einziger Unterschied:
			* holt man sich das Ergebnis-image aus dem Array, wird von result 1 abgezogen,
			* da das Ergebnis 1 and Stelle 0 liegt usw.
			*/


			
//			animationD4 = new Image(new FileInputStream("./ressources/wuerfelAnimation/d04_animation.gif"));

			if(result < 1)
				result=1;

			KeyFrame[] keys = new KeyFrame[10];
			Random r = new Random();
			for (int i = 0; i < keys.length; i++){
				int tmp = r.nextInt(4); 
				keys[i] = new KeyFrame(Duration.millis(i * 100), new KeyValue(iv.imageProperty(),imagesD4[tmp]));
			}
			
			KeyFrame resultFrame = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), imagesD4[result-1]));

			Timeline timeline = new Timeline(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], keys[6], keys[7], keys[8], keys[9], resultFrame);
			return timeline;
		}

		public Timeline d06animation(ImageView iv, int result) throws FileNotFoundException{
			/*genauso wie d10animation(), selbstverständlich weniger Einzelbilder
			* einziger Unterschied:
			* holt man sich das Ergebnis-image aus dem Array, wird von result 1 abgezogen,
			* da das Ergebnis 1 and Stelle 0 liegt usw.
			*/
			if(result < 1)
				result=1;

			KeyFrame[] keys = new KeyFrame[10];
			Random r = new Random();
			for (int i = 0; i < keys.length; i++){
				int tmp = r.nextInt(6); 
				keys[i] = new KeyFrame(Duration.millis(i * 100), new KeyValue(iv.imageProperty(),imagesD6[tmp]));
			}
						
			KeyFrame resultFrame = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), imagesD6[result-1]));
			Timeline timeline = new Timeline(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], keys[6], keys[7], keys[8], keys[9], resultFrame);
			return timeline;
		}

		public Timeline d08animation(ImageView iv, int result) throws FileNotFoundException{
			/*genauso wie d10animation(), selbstverständlich weniger Einzelbilder
			* einziger Unterschied:
			* holt man sich das Ergebnis-image aus dem Array, wird von result 1 abgezogen,
			* da das Ergebnis 1 and Stelle 0 liegt usw.
			*/


//			animationD8 = new Image(new FileInputStream("./ressources/wuerfelAnimation/d08_animation.gif"));
			
			
			if(result < 1)
				result=1;
			KeyFrame[] keys = new KeyFrame[10];
			Random r = new Random();
			for (int i = 0; i < keys.length; i++){
				int tmp = r.nextInt(8); 
				keys[i] = new KeyFrame(Duration.millis(i * 100), new KeyValue(iv.imageProperty(),imagesD8[tmp]));
			}
			
			KeyFrame resultFrame = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), imagesD8[result-1]));

			Timeline timeline = new Timeline(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], keys[6], keys[7], keys[8], keys[9], resultFrame);
			return timeline;
		}

		public Timeline d02animation(ImageView iv, int result) throws FileNotFoundException{
			/*genauso wie d10animation(), selbstverständlich weniger Einzelbilder
			* einziger Unterschied:
			* holt man sich das Ergebnis-image aus dem Array, wird von result 1 abgezogen,
			* da das Ergebnis 1 and Stelle 0 liegt usw.
			*/
		
			if(result < 1)
				result=1;
				

			KeyFrame[] keys = new KeyFrame[10];
			for (int i = 0; i < keys.length; i++){
				int tmp;
				if (i % 2 == 0){
					tmp = 0;
				}
				else {
					tmp = 1;
				}
				keys[i] = new KeyFrame(Duration.millis(i * 100), new KeyValue(iv.imageProperty(),imagesD2[tmp]));
			}
			
			KeyFrame resultFrame = new KeyFrame(Duration.millis(1100), new KeyValue(iv.imageProperty(), imagesD2[result-1]));

			Timeline timeline = new Timeline(keys[0], keys[1], keys[2], keys[3], keys[4], keys[5], keys[6], keys[7], keys[8], keys[9], resultFrame);
			return timeline;
		}


}
