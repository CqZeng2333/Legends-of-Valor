package GameEnviroment;
import java.util.List;

import Avatars.*;

import Map.Plain;

public class LegdensOfValor extends RolePlayingGame {

	public LegendBoard board;
	public HeroMonsterFight fight;

	public void run() {
		// logics
		
	}

	public void fight(Hero hero, Monster monster) {
		// pass hero and monster
		fight = new HeroMonsterFight(hero, monster);
		
		return;
	}
	
	
	public int action() {
		/* 
		 * 1. move	
		 * 2. buy
		 * 3. sell
		 * 4. teleport
		 * 5. back
		 * 6. attack
		 * 7. potion
		 * 8. weapon	
		*/
		return 0;
	}

	
	// input col and row
	// output a list of monster if there is any, null otherwise 
	public List<Monster> detectMonsters(int col, int row, LegendBoard board) {
		return detectStatus.detectMonsters(col, row, board);
	}

	// return true if it is movable, else false
	public boolean detectMovable(char direction, LegendBoard board) {
		return detectStatus.detectMovable(direction, board);
	}
	
	// return true if it is teleportable else false
	public boolean detectTeleportable(int heroCol, int heroRow, int col, int row, LegendBoard board) {
		return detectStatus.detectTeleportable(heroCol, heroRow, col, row, board);
	}
	
	public boolean detectBuyable(int col, int row, LegendBoard board) {
		return detectStatus.detectBuyable(col, row, board);
	}
}
