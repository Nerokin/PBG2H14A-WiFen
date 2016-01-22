package wifen.commons;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStateModel implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int maxPlayerCount;
	private boolean spectatingAllowed;
	private boolean mediaVisible;
	private int maxDiceSideCount;
	private SpielerRolle standardRolle;
	private SpielfeldModel viewModel;
	private ArrayList<String> chatLog;
	private ArrayList<String> ereignisLog;
	private ArrayList<Medium> media;

	/*
	 * TODO: Image übergeben zur Berechnung der Bildgröße bzw. die Bildgröße übergeben
	 */
	public GameStateModel(int maxPlayerCount, boolean spectatingAllowed, boolean mediaVisible, int maxSideCount,
			SpielerRolle standard, GridType grid, BackgroundImage backgroundImage, int width, int height, ArrayList<String> chat, ArrayList<String> ereignis, ArrayList<Medium> media) {
		this.maxPlayerCount = maxPlayerCount;
		this.spectatingAllowed = spectatingAllowed;
		this.mediaVisible = mediaVisible;
		this.maxDiceSideCount = maxSideCount;
		this.standardRolle = standard;
		this.viewModel = new SpielfeldModel(grid, width, height, backgroundImage);
		this.chatLog = chat;
		this.ereignisLog = ereignis;
		this.media = media;
	}

	void setMaxPlayerCount(int newPlayerCount) {
		this.maxPlayerCount = newPlayerCount;
	}

	void setSpectating(boolean allowed) {
		this.spectatingAllowed = allowed;
	}

	void setMediaVisible(boolean visible) {
		this.mediaVisible = visible;
	}

	void setMaxDiceSide(int newMaxSideCount) {
		this.maxDiceSideCount = newMaxSideCount;
	}

	void setStandardRolle(SpielerRolle newStandard) {
		this.standardRolle = newStandard;
	}

	void setChatLog(ArrayList<String> chat){
		this.chatLog = chat;
	}

	void setEreignisLog(ArrayList<String> ereignis){
		this.chatLog = ereignis;
	}

	public int getMaxPlayerCount() {
		return this.maxPlayerCount;
	}

	public boolean isSpectatingAllowed() {
		return this.spectatingAllowed;
	}

	public boolean isMediaVisibleInitially() {
		return this.mediaVisible;
	}

	public int maxDiceFaceCount() {
		return this.maxDiceSideCount;
	}

	public SpielerRolle getStandardPlayerRole() {
		return this.standardRolle;
	}

	public ArrayList<String> getChatLog(){
		return this.chatLog;
	}

	public ArrayList<String> getEreignisLog(){
		return this.ereignisLog;
	}

	public SpielfeldModel getViewModel() {
		return viewModel;
	}

	public void setViewModel(SpielfeldModel viewModel) {
		this.viewModel = viewModel;
	}

	public ArrayList<Medium> getMedia() {
		return media;
	}

	public void setMedia(ArrayList<Medium> media) {
		this.media = media;
	}

}
