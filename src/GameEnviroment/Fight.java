package GameEnviroment;
/*
 * A fight between the hero and monster
 */
import java.util.List;

import Avatars.Hero;
import Avatars.Monster;

public class Fight {
	private List<Hero> heros;
	private List<Monster> monsters;
	private int currentPair; // the current fighting hero-monster pair
	
	private final String ANSI_RESET = "\033[0m";
	private final String ANSI_RED = "\033[0;31m";
	private final String ANSI_BLUE = "\033[0;34m";
	
	public Fight(List<Hero> heros, List<Monster> monsters) {
		this.heros = heros;
		this.monsters = monsters;
		this.currentPair = 0;
	}
	
	/*
	 * Judge which side wins
	 * Return 1 if heros win;
	 * return 0 if still no winner;
	 * return -1 if monsters win.
	 */
	public int whoWin() {
		boolean heroWin = true, monsterWin = true;
		for (Hero h : heros) {
			if (h.isAlive()) monsterWin = false;
		}
		for (Monster m : monsters) {
			if (m.isAlive()) heroWin = false;
		}
		
		if (heroWin) return 1;
		else if (monsterWin) return -1;
		else return 0;
	}
	
	/*
	 * Select an alive hero-monster pair [heroi, monsterj]
	 * according to the sequence
	 */
	public int[] selectFightPair() {
		while (!this.heros.get(this.currentPair).isAlive() && !this.monsters.get(this.currentPair).isAlive()) {
			if (this.currentPair == this.heros.size() - 1) {
				this.currentPair = 0;
			}
			else {
				this.currentPair += 1;
			}
		}
		 // find an alive hero-monster pair (heroi, monsterj)
		int heroi, monsterj;
		heroi = this.currentPair;
		monsterj = this.currentPair;
		if (!this.heros.get(heroi).isAlive()) {
			//find an alive hero
			heroi = 0;
			while (!this.heros.get(heroi).isAlive() && heroi < this.heros.size()) heroi += 1;
			if (heroi == this.heros.size()) return null; //no hero alive
		}
		if (!this.monsters.get(monsterj).isAlive()) {
			//find an alive monster
			monsterj = 0;
			while (!this.monsters.get(monsterj).isAlive() && monsterj < this.monsters.size()) monsterj += 1;
			if (monsterj == this.monsters.size()) return null; //no monster alive
		}
		
		// move the pointer to next pair
		if (this.currentPair == this.heros.size() - 1) {
			this.currentPair = 0;
		}
		else {
			this.currentPair += 1;
		}
		
		return new int[] {heroi, monsterj};
	}
	
	/*
	 * Implement a round of hero i to monster j
	 * according to hero's action
	 * Return 0 if monster alive
	 * return -1 if monster dies
	 */
	public int heroTurn(int heroi, int monsterj, String[] action) {
		Hero hero = heros.get(heroi);
		Monster monster = monsters.get(monsterj);
		if (hero == null || monster == null) return 0;
		
		int status = 0;
		if (action[0].equals("attack")) {
			double attackValue = hero.attack();
			if (!monster.canDodge()) {
				status = monster.beAttacked(attackValue);
				System.out.println(ANSI_RED+"Monster"+ANSI_RESET+" loses "+ANSI_RED+monster.loseHowMuchHP(attackValue)+ANSI_RESET+" HP!");
			}
			else {
				System.out.println(ANSI_RED+"Monster"+ANSI_RESET+" dodges!");
			}
			System.out.println(monster);
		}
		else if (action[0].equals("spell")) {
			double spellValue = hero.castSpell(action[1]);
			String spellType = hero.castSpellSideEffect(action[1]);
			if (!monster.canDodge()) {
				monster.beWeaken(spellType);
				System.out.println(ANSI_RED+"Monster"+ANSI_RESET+" is weaken by "+spellType+"!");
				status = monster.beAttacked(spellValue);
				System.out.println(ANSI_RED+"Monster"+ANSI_RESET+" loses "+ANSI_RED+monster.loseHowMuchHP(spellValue)+ANSI_RESET+" HP!");
			}
			else {
				System.out.println(ANSI_RED+"Monster"+ANSI_RESET+" dodges!");
			}
			System.out.println(monster);
		}
		else if (action[0].equals("potion")) {
			hero.drinkPotion(action[1]);
			System.out.println(ANSI_BLUE+"Hero"+ANSI_RESET+" drinks potion!");
			System.out.println(hero.toStringInFight());
		}
		else if (action[0].equals("change_weapon")) {
			hero.changeEquipment("weapon", action[1]);
			System.out.println(hero.toStringInFight());
		}
		else if (action[0].equals("change_armor")) {
			hero.changeEquipment("armor", action[1]);
			System.out.println(hero.toStringInFight());
		}
		
		if (status == -1) {
			System.out.println(ANSI_RED+"Monster"+ANSI_RESET+" dies!");
		}
		return status;
	}

	/*
	 * Implement a round of monster j to hero i
	 * Return 0 if hero alive
	 * return -1 if hero dies
	 */
	public int monsterTurn(int heroi, int monsterj) {
		Hero hero = heros.get(heroi);
		Monster monster = monsters.get(monsterj);
		
		int status = 0;
		double attackValue = monster.attack();
		if (!hero.canDodge()) {
			status = hero.beAttacked(attackValue);
			System.out.println(ANSI_BLUE+"Hero"+ANSI_RESET+" loses "+ANSI_RED+hero.loseHowMuchHP(attackValue)+ANSI_RESET+" HP!");
		}
		else {
			System.out.println(ANSI_BLUE+"Hero"+ANSI_RESET+" dodges!");
		}
		System.out.println(hero.toStringInFight());
		if (status == -1) {
			System.out.println(ANSI_BLUE+"Hero"+ANSI_RESET+" dies!");
		}
		return status;
	}
}
