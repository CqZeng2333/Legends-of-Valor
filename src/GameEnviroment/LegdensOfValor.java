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
		 * 5. attack
		 * 6. potion
		 * 7. weapon	
		*/
		return 0;
	}

	
	// input col and row
	// output a list of monster if there is any, null otherwise 
	public List<Monster> detectMonsters(int col, int row) {
		return detectStatus.detectMonsters(col, row);
	}

	// return true if it is movable, else false
	public boolean detectMovable(char direction) {
		return detectStatus.detectMovable(direction);
	}
}
