package GameEnviroment;

import java.util.ArrayList;
import java.util.List;

import Avatars.Hero;
import Avatars.Monster;
import Map.Tile;

public class detectStatus {
	// input col and row
	// output a list of monsters or heroes if there is any, null otherwise 
	public static List<Hero> detectHeros(int col, int row, LegendBoard board) {
		List<Hero> list = new ArrayList<>();
		// eight cell around it
		for(int i = row - 1; i <= row + 1; i++) {
			// out of board
			if(i < 0 || i >= board.getRow()) {
				continue;
			}
			for(int j = col - 1; j <= col + 1; j++) {
				//out of board
				if(j < 0 || j >= board.getCol()) {
					continue;
				}
				Tile tile = board.getAGrid(i, j);
				if(tile.isAccessible()) {
					int index = tile.getContainer(0);
					// the container of hero is not empty
					if(tile.getContainer(1) >= 0) {
						list.add(board.getHero(index));
					}
				}
			}
		}
		return list;
	}
	
	public static List<Monster> detectMonsters(int col, int row, LegendBoard board) {
		List<Monster> list = new ArrayList<>();
		// eight cell around it
		for(int i = row - 1; i <= row + 1; i++) {
			// out of board
			if(i < 0 || i >= board.getRow()) {
				continue;
			}
			for(int j = col - 1; j <= col + 1; j++) {
				//out of board
				if(j < 0 || j >= board.getCol()) {
					continue;
				}
				Tile tile = board.getAGrid(i, j);
				if(tile.isAccessible()) {
					int index = tile.getContainer(1);
					// the container of monster is not empty
					if(tile.getContainer(1) >= 0) {
						list.add(board.getMonster(index));
					}
				}
			}
		}
		return list;
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
		int col = board.getCol();
		int row = board.getRow();
		//out of the board
		if(targetCol < 0 || targetCol >= col || targetRow < 0 || targetRow >= row) {
			return -2;
		}
		Tile tile = board.getAGrid(targetRow, targetCol);
		// get container status
		int h = tile.getContainer(0);
		int m = tile.getContainer(1);
		
		//empty for both
		if(h == -1 && m == -1) {
			return 2;
		//empty for hero
		}else if(h == -1) {
			return 1;
		//empty for monster
		}else if(m == -1) {
			return -1;
		//inaccessible or not empty
		}else {
			return -2;
		}
	}

	public static boolean detectTeleportable(int heroIndex,int heroCol, int heroRow, int col, int row, LegendBoard board) {
		// not allow to teleport forward
		if(heroRow > row) {
			return false;
		}
		// inaccessible tile
		Tile tile = board.getAGrid(row, col);
		if(!tile.isAccessible()) {
			return false;
		}
		// if not empty
		if(tile.getContainer(0) != -1) {
			return false;
		}
		// not allow teleport to the hero's home lane
		if(heroIndex == 0 && (col == 0 || col == 1)) {
			return false;
		}
		if(heroIndex == 1 && (col == 3 || col == 4)) {
			return false;
		}
		if(heroIndex == 2 && (col == 6 || col == 7)) {
			return false;
		}
		// not allow to teleport behind monsters
		for(int[] pos : board.getPosOfMonsters()) {
			// if same lane
			if(pos[1] - col < 2 || col - pos[1] < 2) {
				// the position is behind a monster
				if(pos[0] > row) {
					return false;
				}
			}
		}
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
	
	//1: heroes win
	//-1: heroes lose
	//0: still fight
	public static int detectWinLose(LegendBoard lb) {
		int col = lb.getCol();
		int row = lb.getRow();
		for(int i = 0 ; i < col; i++) {
			Tile tile = lb.getAGrid(0, i);
			if(tile.getContainer(0)>=0) {
				return 1;
			}
		}
		for(int i = 0 ; i < col; i++) { 
			Tile tile = lb.getAGrid(row - 1, i);
			if(tile.getContainer(1)>=0) {
				return -1;
			}
		}
		return 0;
	}
}
