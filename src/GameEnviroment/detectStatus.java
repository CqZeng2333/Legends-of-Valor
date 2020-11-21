package GameEnviroment;

import java.util.List;

import Avatars.Hero;
import Avatars.Monster;

public class detectStatus {

	public static List<Hero> detectHeros(int col, int row, LegendBoard board) {
		return null;
	}
	
	// input col and row
	// output a list of monster if there is any, null otherwise 
	
	public static List<Monster> detectMonsters(int col, int row, LegendBoard board) {
		return null;
	}

	public static boolean detectMovable(int heroCol, int heroRow, char direction, LegendBoard board) {
		return true;
	}

	public static boolean detectTeleportable(int heroCol, int heroRow, int col, int row, LegendBoard board) {
		return true;
	}
	
	public static boolean detectBuyable(int col, int row, LegendBoard board) {
		return true;
	}
}
