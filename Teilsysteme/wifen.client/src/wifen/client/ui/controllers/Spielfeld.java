package wifen.client.ui.controllers;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class Spielfeld {
	private Stage stage;
	private StackPane stack;
	private double sizeFieldX;
	private double sizeFieldY;
	private double sizeSceneX;
	private double sizeSceneY;
	private int tilesPerRow;
	private int tilesPerCol;
	private int typ;
	private double lastX=0;
	private double lastY=0;
	
	/**initialize a field with a rectangle grid
	 * sizeFieldX => Stack width
	 * sizeFieldY => Stack height
	 * sizeSceneX => Scene width
	 * sizeSceneY => Scene height
	 * tilesPerRow => rectangle per row
	 * tilesPerCol => rectangle per column*/
	Spielfeld(double sizeFieldX, double sizeFieldY, double sizeSceneX, double sizeSceneY, int tilesPerRow, int tilesPerCol){
		this.stage = new Stage();
		this.stack = new StackPane();
		this.sizeFieldX = sizeFieldX;
		this.sizeFieldY = sizeFieldY;
		this.sizeSceneX = sizeSceneX;
		this.sizeSceneY = sizeSceneY;
		this.tilesPerRow = tilesPerRow;
		this.tilesPerCol = tilesPerCol;
		this.typ = 1;		
	}
	/**initialize a field with a hexagon grid
	 * sizeFieldX => Stack width
	 * sizeFieldY => Stack height
	 * sizeSceneX => Scene width
	 * sizeSceneY => Scene height
	 * tilesPerCol => hexagons per column*/
	Spielfeld(double sizeFieldX, double sizeFieldY, double sizeSceneX, double sizeSceneY, int tilesPerCol){
		this.stage = new Stage();
		this.stack = new StackPane();
		this.sizeFieldX = sizeFieldX;
		this.sizeFieldY = sizeFieldY;
		this.sizeSceneX = sizeSceneX;
		this.sizeSceneY = sizeSceneY;
		this.tilesPerCol = tilesPerCol;
		this.typ = 2;
	}
	/**initialize a field without a grid
	 * sizeFieldX => Stack width
	 * sizeFieldY => Stack height
	 * sizeSceneX => Scene width
	 * sizeSceneY => Scene height*/
	Spielfeld(double sizeFieldX, double sizeFieldY, double sizeSceneX, double sizeSceneY){
		this.stage = new Stage();
		this.stack = new StackPane();
		this.sizeFieldX = sizeFieldX;
		this.sizeFieldY = sizeFieldY;
		this.sizeSceneX = sizeSceneX;
		this.sizeSceneY = sizeSceneY;
		this.typ = 0;
	}
	/**draws the field in a new scene*/
	public void draw(){
		Scene scene = new Scene(stack,sizeFieldX,sizeFieldY);
		stage.setScene(scene);
		stage.setWidth(sizeSceneX);
		stage.setHeight(sizeSceneY);
		stack.setStyle("-fx-background-color: #FFFFFF;");
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(event.getText().toLowerCase().equals("w")&&stack.getTranslateY()<(sizeFieldY/2-((stack.getHeight())/2))){
					stack.setTranslateY(stack.getTranslateY()+5);
				}
				else if(event.getText().toLowerCase().equals("a")&&stack.getTranslateX()<(sizeFieldX/2-(sizeSceneX/2))){
					stack.setTranslateX(stack.getTranslateX()+5);
				}
				else if(event.getText().toLowerCase().equals("s")&&stack.getTranslateY()>(-1*(sizeFieldY/2-((stack.getHeight())/2)))){
					stack.setTranslateY(stack.getTranslateY()-5);
				}
				else if(event.getText().toLowerCase().equals("d")&&stack.getTranslateX()>(-1*(sizeFieldX/2-(sizeSceneX/2)))){
					stack.setTranslateX(stack.getTranslateX()-5);
				}
			}
		});
		if(this.typ == 1){
			Polyline line = new Polyline();
			double a = stack.getWidth()/tilesPerRow;
			double b = stack.getHeight()/tilesPerCol;
			line.autosize();
			line.getPoints().addAll(new Double[]{
			        0.00, 0.00,
			        0.00, stack.getHeight()});
			for(int i=0; i<tilesPerCol;i++){
				for(int j=0; j<tilesPerRow;j++){
					line.getPoints().addAll(new Double[]{
					        j*a, i*b,
					        (j+1)*a, i*b,
					        (j+1)*a, (i+1)*b,
					        j*a, (i+1)*b,
					        j*a, i*b});				
				}
				line.getPoints().addAll(new Double[]{
				        (tilesPerRow-1)*a, (i+1)*b});	
			}
			stack.getChildren().add(line);
		}else if(this.typ == 2){
			boolean toggle = true;
			double height = stack.getHeight()/tilesPerCol;
			double b = height/2;
			double c = b/Math.sin(Math.toRadians(60));
			double a = 0.5*c;
			int tilesPerRow = (int) (stack.getWidth()/(2*b+c));
			Polyline line = new Polyline();
			for(int i=0; i<tilesPerCol;i++){
				if(toggle){
					for(int j=0; j<tilesPerRow;j++){
						line.getPoints().addAll(new Double[]{
								(((2*(c+a))*j)), (i*(2*b))+b,
						        (a+((2*(c+a))*j)), i*(2*b),
						        (a+c+((2*(c+a))*j)), i*(2*b),
						        ((2*a+2*c)*j)+(2*a+c), (i*(2*b))+b,
						        (a+c+((2*(c+a))*j)), (i*(2*b))+(2*b),
						        (a+((2*(c+a))*j)), (i*(2*b))+(2*b),
						        (((2*(c+a))*j)), (i*(2*b))+b,
						        (a+((2*(c+a))*j)), i*(2*b),
						        (a+c+((2*(c+a))*j)), i*(2*b),
						        ((2*a+2*c)*j)+(2*a+c), (i*(2*b))+b});				
					}
					if(i<tilesPerCol-1){
						line.getPoints().addAll(new Double[]{
								(a+c+((2*(c+a))*(tilesPerRow-1))), (i*(2*b))+(2*b),
								((2*a+2*c)*(tilesPerRow-1))+(2*a+c), ((i+1)*(2*b))+b,});
						toggle=false;
					}
				}else{
					for(int j=tilesPerRow-1; j>=0;j--){
						line.getPoints().addAll(new Double[]{
								((2*a+2*c)*j)+(2*a+c), (i*(2*b))+b,
								(a+c+((2*(c+a))*j)), (i*(2*b))+(2*b),
								(a+((2*(c+a))*j)), (i*(2*b))+(2*b),
								(((2*(c+a))*j)), (i*(2*b))+b,
						        (a+((2*(c+a))*j)), i*(2*b),
						        (a+c+((2*(c+a))*j)), i*(2*b),
						        ((2*a+2*c)*j)+(2*a+c), (i*(2*b))+b,
						        (a+c+((2*(c+a))*j)), i*(2*b),
						        (a+((2*(c+a))*j)), i*(2*b),
						        (((2*(c+a))*j)), (i*(2*b))+b});			
					}
					if(i<tilesPerCol-1){
						line.getPoints().addAll(new Double[]{
								(a+((2*(c+a))*0)), ((i)*(2*b))+(2*b),
								(a+((2*(c+a))*0)), (i+1)*(2*b)});
						toggle=true;
					}
				}
			}
			stack.getChildren().add(line);
		}
		stack.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				lastX=event.getSceneX()-scene.getWidth()/2;
				lastY=event.getSceneY()-scene.getHeight()/2;	
			}
			
		});
		stack.setOnMouseDragged(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				double x = event.getSceneX()-scene.getWidth()/2;
				double y = event.getSceneY()-scene.getHeight()/2;
				stack.setTranslateX(stack.getTranslateX()+((x-lastX)));
				stack.setTranslateY(stack.getTranslateY()+((y-lastY)));
				lastX = event.getSceneX()-scene.getWidth()/2;
				lastY = event.getSceneY()-scene.getHeight()/2;
			}
			
		});
		stage.setResizable(false);
		stage.show();
	}
	/**resize the grid. 
	 * sizeFieldX for the new X Value.
	 * sizeFieldY for the new Y Value.*/
	public void setGrid(double sizeFieldX,double sizeFieldY){
		this.sizeFieldX = sizeFieldX;
		this.sizeFieldY = sizeFieldY;
		draw();
	}
	/**resize the window.
	 * sizeSceneX for the new X Value.
	 * sizeSceneY for the new Y Value.*/
	public void setStage(double sizeSceneX, double sizeSceneY){
		this.sizeSceneX = sizeSceneX;
		this.sizeSceneY = sizeSceneY;
		draw();
	}
	/**Get all Children of the Pane*/
	public ObservableList<Node> getChildren(){
		return stack.getChildren();
	}
	/**Add Node to stack*/
	public void add(Node value){
		stack.getChildren().add(value);
	}
	/**Delete Node from stack*/
	public void remove(Node value){
		stack.getChildren().remove(value);
	}
}
