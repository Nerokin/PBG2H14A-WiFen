package wifen.client.ui.controllers;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;



public class Wuerfelfenster extends VBox {

	
		//constants
		//public static final String CSS_PATH = "/css/MainView.css";
		public static final String FXML_PATH ="/view/Wuerfelfenster.fxml";
		@FXML public Label wuerfel1;
		//@FXML public Label dropped;
		double orgSceneX,orgSceneY;
		double orgTranslateX,orgTranslateY;
	
		double mousex,mousey;
		Image wuerfelImage = new Image("/view/123.png");
		@FXML public Pane dropped;
		
		
		
		//Properties
		
		private final ObjectProperty<FXMLLoader> fxmlLoader = new SimpleObjectProperty<>();
		
		//Injected Nodes
		
		//@FXML private FormationDisplay formatDisplay;
		//TODO
		
		//Constructor
		
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
			wuerfel1.setCursor(Cursor.HAND);
			
			
		}
		
		
		
		//Initialization
		
		@FXML
		private void initialize() {

			
		//testMe.setOnMousePressed(wuerfelOnMousePressedEventHandler);
		//	testMe.setOnMouseDragged(wuerfelOnMouseDraggedEventHandler);
			
			
			//TODO: Data Binding and Setup of Event Handling
		}
		
		//Event Handlers

		@FXML
		public void wuerfelDrag(MouseEvent ev){	// Erst beim Klicken und gleichzeitig ziehen!
			
				Dragboard db = wuerfel1.startDragAndDrop(TransferMode.ANY);
				db.setDragView(wuerfelImage);
			        ClipboardContent content = new ClipboardContent();
			       
			        content.putString(wuerfel1.getText());
			        db.setContent(content);
			     
			        ev.consume();
		}
		public void onMouseDragged(MouseEvent ev){
			
			System.out.println("OnMouseDragged");
			/*double x = ev.getSceneX();
	        double y = ev.getSceneY();
	        testMe.relocate(testMe.getLayoutX()+(x-mousex),testMe.getLayoutY() + (y - mousey));
	*/
			ev.consume();
		}
		public void onMousePressed(MouseEvent ev){
			System.out.println("onMousePressed");
		/*	mousex = ev.getSceneX();
		    mousey = ev.getSceneY();*/
			ev.consume();
		}
		
		@FXML
		public void wuerfelDone(){
			
			System.out.println("DragDone");
		}/*
		@FXML
		public void DragDropped(){
			System.out.println("DragDropped");
			
		}
		@FXML
		public void DragEntered(){
			System.out.println("DragEntered");
		}
		@FXML
		public void DragExited(){
			System.out.println("DragExited");
		}
		
		
		@FXML
		public void MouseDragEntered(){
			System.out.println("MouseDragentered");
			
		}
		@FXML
		public void MouseDragExited(){
			System.out.println("MouseDragExited");
		}
		@FXML
		public void MouseDragOver(){
			System.out.println("MouseDragOver");
		}
		@FXML
		public void MouseDragReleased(){
			System.out.println("MouseDragReleased");
		}*/
		@FXML
		public void wuerfelDragOver(){
			
			   
		        
		}
		@FXML
		public void wuerfelDrop(DragEvent ev){
			Dragboard db = ev.getDragboard();
			ev.setDropCompleted(true);
			System.out.println("targetDropped");
			ev.consume();
			
		}

		@FXML
		public void overLabel(DragEvent ev){
			Dragboard db = ev.getDragboard();
			ev.acceptTransferModes(TransferMode.ANY);
			ev.consume();
			//System.out.println("overLabel");
		}
		/*EventHandler<MouseEvent> wuerfelOnMousePressedEventHandler = 
				new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent t){
				System.out.println("Hallo");
				orgSceneX = t.getSceneX();
				orgSceneY = t.getSceneY();
				orgTranslateX = ((Label)(t.getSource())).getTranslateX();
				orgTranslateY = ((Label)(t.getSource())).getTranslateY();
			}
		};
		EventHandler<MouseEvent> wuerfelOnMouseDraggedEventHandler = 
				new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent t){
				System.out.println("Hallo2");
				double offsetX = t.getSceneX() - orgSceneX;
				double offsetY = t.getSceneY()  -orgSceneY;
				double newTranslateX = orgTranslateX+offsetX;
				double newTranslateY= orgTranslateY+offsetY;
				
				 ((Label)(t.getSource())).setTranslateX(newTranslateX);
				 ((Label)(t.getSource())).setTranslateY(newTranslateY);
			}
			
		};*/

	
		//TODO
		
		//Getter & Setter
		
		public FXMLLoader getFXMLLoader() {
			return fxmlLoader.get();
		}
		
		public void setFXMLLoader(FXMLLoader value) {
			fxmlLoader.set(value);
		}
		
		public ReadOnlyObjectProperty<FXMLLoader> fxmlLoaderProperty() {
			return fxmlLoader;
		}
		
		
		

	}


