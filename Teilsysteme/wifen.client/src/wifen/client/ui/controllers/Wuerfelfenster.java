package wifen.client.ui.controllers;

import wifen.client.services.impl.*;
import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;





// Justin Nussbaum
// Anfang:
// Drag & Drop in Zusammenarbeit mit Kevin Curtis
/**
 * Würfelfenster Klasse
 * 
 * @author Justin Nussbaum
 *
 */
public class Wuerfelfenster extends VBox {
	
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
		 */
		@FXML
		public void wuerfelDrag2(MouseEvent ev){	// Erst beim Klicken und gleichzeitig ziehen!
			
				Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
				db.setDragView(wuerfelImage);
			        ClipboardContent content = new ClipboardContent();
			        
			        
			        content.putString("d2");
			        
			        db.setContent(content);
			        ev.consume();
		}

		/**
		 * 4erWürfel DragEvent
		 * 
		 * @param ev MouseClick auf 4er Würfel
		 */
		public void wuerfelDrag4(MouseEvent ev){	// Erst beim Klicken und gleichzeitig ziehen!
			
			Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
			db.setDragView(wuerfelImage);
		        ClipboardContent content = new ClipboardContent();
		        
		        
		        content.putString("d4");
		        
		        db.setContent(content);
		        ev.consume();
	}
		/**
		 * 6erWürfel DragEvent
		 * 
		 * @param ev MouseClick auf 6er Würfel
		 */
		public void wuerfelDrag6(MouseEvent ev){	// Erst beim Klicken und gleichzeitig ziehen!
			
			Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
			db.setDragView(wuerfelImage);
			ClipboardContent content = new ClipboardContent();
		        
		        
		        content.putString("d6");
		        
		        db.setContent(content);
		        ev.consume();
	}
		/**
		 * 8erWürfel DragEvent
		 * 
		 * @param ev MouseClick auf 8er Würfel
		 */
	public void wuerfelDrag8(MouseEvent ev){	// Erst beim Klicken und gleichzeitig ziehen!
			
			Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
			db.setDragView(wuerfelImage);
			ClipboardContent content = new ClipboardContent();
		        
		        
		        content.putString("d8");
		        
		        db.setContent(content);
		        ev.consume();
	}
	/**
	 * 10erWürfel DragEvent
	 * 
	 * @param ev MouseClick auf 10er Würfel
	 */
	public void wuerfelDrag10(MouseEvent ev){	// Erst beim Klicken und gleichzeitig ziehen!
		
		Dragboard db = this.startDragAndDrop(TransferMode.ANY); // this hinzugefügt.. alternativ : wuerfel1.startDragAndDrop..
		db.setDragView(wuerfelImage);
		ClipboardContent content = new ClipboardContent();
	        
	        
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
		// Johnny Gunko Anfang
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
		/**
		 * Drop Würfel
		 * 
		 * @param ev DragEvent resettet das Eingabeenster bzw. übrgibt den Würfel und gibt dies im Ereignisfenster aus
		 */
		@FXML
		public void würfeln() throws IOException{
			String[] tmp = diceText.getText().split(";");
			String[] output = new String[tmp.length];
			if(tmp.length >= 2){
				for(int i = 0; i < tmp.length; i++){
					if(dice.checkInput(tmp[i]) != null){
						output[i] = dice.checkInput(tmp[i]);
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
			dice.playWuerfeln();
			reset();
		}
		// Johnny Gunko Ende
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

	}

	//Justin Ende