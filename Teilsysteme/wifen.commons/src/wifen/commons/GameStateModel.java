package wifen.commons;

import java.io.Serializable;

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
	private GridType grid;

	public GameStateModel(int maxPlayerCount, boolean spectatingAllowed, boolean mediaVisible, int maxSideCount,
			SpielerRolle standard, GridType grid) {
		this.maxPlayerCount = maxPlayerCount;
		this.spectatingAllowed = spectatingAllowed;
		this.mediaVisible = mediaVisible;
		this.maxDiceSideCount = maxSideCount;
		this.standardRolle = standard;
		this.grid = grid;
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

	void setGridType(GridType newGridType) {
		this.grid = newGridType;
	}

	int getMaxPlayerCount() {
		return this.maxPlayerCount;
	}

	boolean isSpectatingAllowed() {
		return this.spectatingAllowed;
	}

	boolean isMediaVisibleInitially() {
		return this.mediaVisible;
	}

	int maxDiceFaceCount() {
		return this.maxDiceSideCount;
	}

	SpielerRolle standardPlayerRole() {
		return this.standardRolle;
	}

	GridType gridType() {
		return this.grid;
	}
}
