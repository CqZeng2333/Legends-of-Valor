package GameEnviroment;

import java.util.List;

import Avatars.Monster;

public class detectStatus {
	// input col and row
	// output a list of monster if there is any, null otherwise 
	public static List<Monster> detectMonsters(int col, int row, LegendBoard board) {
		return null;
	}

	public static boolean detectMovable(char direction, LegendBoard board) {
		return true;
	}

	public static boolean detectTeleportable(int heroCol, int heroRow, int col, int row, LegendBoard board) {
		return true;
	}
	
	public static boolean detectBuyable(int col, int row, LegendBoard board) {
		return true;
	}
}
