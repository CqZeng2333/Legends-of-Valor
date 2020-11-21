package GameEnviroment;

import java.util.List;

import Avatars.Hero;
import Avatars.Monster;
import Map.Tile;

public class detectStatus {

	public static List<Hero> detectHeros(int col, int row, LegendBoard board) {
		
		return null;
	}
	
	// input col and row
	// output a list of monster if there is any, null otherwise 
	
	public static List<Monster> detectMonsters(int col, int row, LegendBoard board) {
		return null;
	}
	
	// return:
	// 1 only movable for heroes
	// 2 movable both
	// -1 only movable for monsters
	// -2 not movable for both
	public static int detectMovable(int heroCol, int heroRow, char direction, LegendBoard board) {
		int targetCol = 0;
		int targetRow = 0;
		// get target position
		switch(direction) {
		// up
		case 'W':
		case 'w':{
			targetCol = heroCol;
			targetRow = heroRow - 1;
			break;
		}
		// left
		case 'A':
		case 'a':{
			targetCol = heroCol - 1;
			targetRow = heroRow;
			break;
		}
		// down
		case 'S':
		case 's':{
			targetCol = heroCol;
			targetRow = heroRow + 1;
			break;
		}
		// right
		case 'D':
		case 'd':{
			targetCol = heroCol + 1;
			targetRow = heroRow;
			break;
		}
		}

		Tile tile = board.getAGrid(targetRow, targetCol);
		// if accessible
		int h = tile.getContainer(0);
		int m = tile.getContainer(1);
		if(h == -2 || m == -2) {
			return -2;
		}else if(h == -1 && m == -1) {
			return 2;
		}else if(h == -1 && m == -2) {
			return 1;
		}else if(h == -2 && m == -1) {
			return -1;
		}else {
			return -2;
		}
	}

	public static boolean detectTeleportable(int heroCol, int heroRow, int col, int row, LegendBoard board) {
		return true;
	}
	
	public static boolean detectBuyable(int col, int row, LegendBoard board) {
		Tile tile = board.getAGrid(row, col);
		if(tile.getType().equals("market")) {
			return true;
		}
		else {
			return false;
		}
	}
}
