package wifen.client.services.impl;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DiceObject {

	private int sideCount;
	private int diceCount;
	private int mod;
	private int threshold;
	private ImageView wuerfelImg;
	private int[] result;
	
	public int[] getResult() {
		return result;
	}

	public void setResult(int[] result) {
		this.result = result;
	}
	
	public int getEndResult(){
		return result[result.length-1];
	}

	public DiceObject(int sc, int dc, int m)
	{
		this.sideCount = sc;
		this.diceCount = dc;
		this.mod = m;
		this.result = new int[diceCount + 1];
	}
	
	public DiceObject(int sc, int dc, int m, int t)
	{
		this.sideCount = sc;
		this.diceCount = dc;
		this.mod = m;
		this.threshold = t;
		this.result = new int[diceCount + 1];
	}
	
	public DiceObject(Image i){
	
		ImageView wuerfelImageView = new ImageView();
		wuerfelImageView.setImage(i);
		
		this.wuerfelImg = wuerfelImageView;
	}
	
	public DiceObject(){}

	public int getSideCount() {
		return sideCount;
	}

	public void setSideCount(int sideCount) {
		this.sideCount = sideCount;
	}

	public int getDiceCount() {
		return diceCount;
	}

	public void setDiceCount(int diceCount) {
		this.diceCount = diceCount;
	}

	public int getMod() {
		return mod;
	}

	public void setMod(int mod) {
		this.mod = mod;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public ImageView getWuerfelImg() {
		return wuerfelImg;
	}

	public void setWuerfelImg(ImageView wuerfelImg) {
		this.wuerfelImg = wuerfelImg;
	}
	
}
