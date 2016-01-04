package wifen.commons;

public interface GameStateModel {

	int getMaxPlayerCount();
	boolean isSpectatingAllowed();
	boolean isMediaVisibleInitially();
	int maxDiceFaceCount();
	SpielerRolle standardPlayerRole();
	GridType gridType();
	
}
