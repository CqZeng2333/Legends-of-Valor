package GameEnviroment;

import Avatars.Hero;
import Avatars.Monster;
import Avatars.Paladin;
import Avatars.Sorcerer;
import Avatars.Warrior;
import Equipments.Armor;
import Equipments.BuyableObject;
import Equipments.Potion;
import Equipments.Spell;
import Equipments.Weapon;

import java.util.List;
import java.util.Scanner;

public class HeroMonsterFight {

	final String ANSI_RESET = "\033[0m";
	final String ANSI_BLUE = "\033[0;34m";
	final String ANSI_RED = "\033[0;31m";

	private Hero hero;
	private Monster monster;
	private Scanner sc;
	public String str;
	public int num;

	public HeroMonsterFight(Hero hero, Monster monster) {
		this.hero = hero;
		this.monster = monster;
		this.sc = new Scanner(System.in);
	}

	// return 0 for hero wins, 1 for monster wins
	public int runFight() {
		while (hero.isAlive() && monster.isAlive()) {
			System.out.println("======================================================");
			System.out.println("Hero " + ANSI_BLUE + hero.getName() + ANSI_RESET + " VS Monster " + ANSI_RED
					+ monster.getName() + ANSI_RESET);

			// Hero turn
			System.out.println("1. Regular Attack");
			System.out.println("2. Cast a spell");
			System.out.println("3. Use a potion");
			System.out.println("4. Change weapon");
			System.out.println("5. Change armor");
			System.out.println("6. Display information");

			System.out.print("Please select from the above function: ");
			str = sc.nextLine();

			if ((num = this.isDigit(str)) > 0 && num >= 1 && num <= 6) {
				if (num == 6) {
					System.out.println("======================================================");
					System.out.println(ANSI_BLUE + "Hero" + ANSI_RESET + ": ");
					System.out.println(hero.toStringInFight());
					System.out.println(ANSI_RED + "Monster" + ANSI_RESET + ": ");
					System.out.println(monster.toString());
					System.out.println("======================================================");
				} else {
					String[] action = LegendsOfValor.inputActionData(hero, num);
					if (action != null) {
						heroTurn(action);
					} else {
						System.out.println("You cannot do this!");
					}
				}
			}
			if (monster.isAlive()) {
				monsterTurn();
			}
			if (!monster.isAlive()) {
				settle(true, 100 * monster.getLevel(), 2);
				return 0;
			}
			if (!hero.isAlive()) {
				hero.revive();
				return 1;
			}
		}
		return -1;
	}

	public int heroTurn(String[] action) {
		if (hero == null || monster == null)
			return 0;

		int status = 0;
		if (action[0].equals("attack")) {
			double attackValue = hero.attack();
			if (!monster.canDodge()) {
				status = monster.beAttacked(attackValue);
				System.out.println(ANSI_RED + "Monster" + ANSI_RESET + " loses " + ANSI_RED
						+ monster.loseHowMuchHP(attackValue) + ANSI_RESET + " HP!");
			} else {
				System.out.println(ANSI_RED + "Monster" + ANSI_RESET + " dodges!");
			}
			System.out.println(monster);
		} else if (action[0].equals("spell")) {
			double spellValue = hero.castSpell(action[1]);
			String spellType = hero.castSpellSideEffect(action[1]);
			if (!monster.canDodge()) {
				monster.beWeaken(spellType);
				System.out.println(ANSI_RED + "Monster" + ANSI_RESET + " is weaken by " + spellType + "!");
				status = monster.beAttacked(spellValue);
				System.out.println(ANSI_RED + "Monster" + ANSI_RESET + " loses " + ANSI_RED
						+ monster.loseHowMuchHP(spellValue) + ANSI_RESET + " HP!");
			} else {
				System.out.println(ANSI_RED + "Monster" + ANSI_RESET + " dodges!");
			}
			System.out.println(monster);
		} else if (action[0].equals("potion")) {
			hero.drinkPotion(action[1]);
			System.out.println(ANSI_BLUE + "Hero" + ANSI_RESET + " drinks potion!");
			System.out.println(hero.toStringInFight());
		} else if (action[0].equals("change_weapon")) {
			hero.changeEquipment("weapon", action[1]);
			System.out.println(hero.toStringInFight());
		} else if (action[0].equals("change_armor")) {
			hero.changeEquipment("armor", action[1]);
			System.out.println(hero.toStringInFight());
		}

		if (status == -1) {
			System.out.println(ANSI_RED + "Monster" + ANSI_RESET + " dies!");
		}
		return status;
	}

	/*
	 * Implement a round of monster j to hero i Return 0 if hero alive return -1 if
	 * hero dies
	 */
	public int monsterTurn() {
		int status = 0;
		double attackValue = monster.attack();
		if (!hero.canDodge()) {
			status = hero.beAttacked(attackValue);
			System.out.println(ANSI_BLUE + "Hero" + ANSI_RESET + " loses " + ANSI_RED + hero.loseHowMuchHP(attackValue)
					+ ANSI_RESET + " HP!");
		} else {
			System.out.println(ANSI_BLUE + "Hero" + ANSI_RESET + " dodges!");
		}
		System.out.println(hero.toStringInFight());
		if (status == -1) {
			System.out.println(ANSI_BLUE + "Hero" + ANSI_RESET + " dies!");
		}
		return status;
	}

	public void settle(boolean win, int money, int exp) {
		final String ANSI_RESET = "\033[0m";
		final String ANSI_BLUE = "\033[0;34m";
		final String ANSI_RED = "\033[0;31m";

		String type = hero.getType();
		int success = -1;
		if (type.equals("warrior")) {
			success = ((Warrior) hero).levelUp();
		} else if (type.equals("sorcerer")) {
			success = ((Sorcerer) hero).levelUp();
		} else if (type.equals("paladin")) {
			success = ((Paladin) hero).levelUp();
		}
		if (success == 0)
			System.out.println("Hero " + ANSI_BLUE + hero.getName() + ANSI_RESET + " levels up!");
	}

	private int isDigit(String string) {
		if (string.matches("\\d+")) {
			int digits = Integer.parseInt(string);
			return digits;
		}
		return -1;
	}
}
