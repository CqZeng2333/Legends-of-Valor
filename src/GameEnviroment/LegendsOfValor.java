package GameEnviroment;

import GameEnviroment.detectStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Avatars.*;
import Equipments.Armor;
import Equipments.BuyableObject;
import Equipments.Potion;
import Equipments.Spell;
import Equipments.Weapon;
import Map.LegendBoard;
import Map.Market;
import Map.Plain;
import Map.Tile;

public class LegendsOfValor extends RolePlayingGame {

	private HeroFactory hf = new HeroFactory();
	private int roundGenerateMonster = 8; // the number of rounds to generate new monsters

	public void run() {
		// print welcome message
		this.welcome();
		// select heros
		List<Hero> heros = this.selectHero();
		// initialize the game
		this.initialize(heros, 8);
		List<Monster> monsters = this.getBoard().getMonsters();
		System.out.println(this.getBoard());

		int round = 0;
		game: while (true) {
			System.out.println("Round number " + round);
			// generate new monsters every 8 rounds
			if (round > 0 && round % roundGenerateMonster == 0) {
				System.out.print("New monsters are coming!");
				this.getBoard().createNewMonsters();
			}

			// iterate through all heros
			for (int i = 0; i < heros.size(); i++) {
				// check win/lose status after every move of a hero/monster
				int winStatus = this.detectWinLose(this.getBoard());
				if (winStatus == 1) {
					System.out.println("Heros win! Monsters lose!");
					System.out.println("Thank you for playing!");
					break game;
				} else if (winStatus == -1) {
					System.out.println("Heros lose! Monsters win!");
					System.out.println("Thank you for playing!");
					break game;
				}

				// if still on fight, then move on
				Tile heroOn = this.getBoard().getAGrid(this.getBoard().getRowOfHero(i),
						this.getBoard().getColOfHero(i));
				String type = heroOn.getType();
				String attackStatus = ""; // whether the hero choose to attack
				if (!type.equals("hero_nexus") && !type.equals("inaccessible_tile")) {
					// hero in tile where a fight can happen
					List<Monster> monstersList = this.detectMonsters(this.getBoard().getColOfHero(i),
							this.getBoard().getRowOfHero(i), this.getBoard());
					if (monstersList.size() > 0) {
						// the list of monsters is not empty
						System.out.println("You encounter a fight! Do you want to attack?");
						System.out.println("Enter Y|y to attack. Enter anything else to move on: ");

						Scanner sc = new Scanner(System.in);
						attackStatus = sc.nextLine();
						if (attackStatus.equals("y") || attackStatus.equals("Y")) {
							// start fight between the hero and the 1st monster in list
							this.fight(heros.get(i), monstersList.get(0));
						}
					} else {
						// no monster nearby
						System.out.println("This is a safe place!");
					}
				} else if (type.equals("hero_nexus")) {
					System.out.println("Hero "+heros.get(i).getName()+", you are in the Heors Nexus. Do you want to enter the market?");
					System.out.println("Enter Y|y to enter the market. Enter anything else to move on: ");

					Scanner sc = new Scanner(System.in);
					String purchaseStatus = sc.nextLine();
					if (purchaseStatus.equals("y") || purchaseStatus.equals("Y")) {
						// enter the market
						this.purchase((Market) heroOn, i);
					}
				}
				// move on

				System.out.println("======================================================");
				if (!attackStatus.equals("y") && !attackStatus.equals("Y")) {
					// attack counts as an action, if the hero attacks then no need for extra action
					int status = this.move(i);
					if (status == 1) {
						System.out.println("You quit the game!");
						System.exit(0); // quit
					}
				}
				// print board after the hero moves
				System.out.println(this.getBoard());
			}
			// iterate through all monster
			for (int i = 0; i < monsters.size(); i++) {
				// monster item can be null because once monster is killed it is removed from
				// list
				if (monsters.get(i) != null) {
					Tile monsterOn = this.getBoard().getAGrid(this.getBoard().getRowOfMonster(i),
							this.getBoard().getColOfMonster(i));
					String type = monsterOn.getType();

					// check if there are heros nearby
					List<Hero> herosList = this.detectHeros(this.getBoard().getColOfMonster(i),
							this.getBoard().getRowOfMonster(i), this.getBoard());
					if (herosList.size() > 0) {
						// the list of heros is not empty
						System.out.println("Monster starts the fight!");
						// start fight between the monster and the 1st hero in list
						this.fight(herosList.get(0), monsters.get(i));
					} else {
						this.getBoard().moveOfMonster(i); // monster move forward
					}

					// print board after the monster moves
					System.out.println(this.getBoard());
				}
			}
			round += 1;
		}
	}

	// operate actions by the input number
	public static String[] inputActionData(Hero hero, int actionNum) {
		Scanner sc = new Scanner(System.in);
		String str;
		int status;

		if (actionNum == 1) { // attack
			return new String[] { "attack" };
		} else if (actionNum == 2) {// cast a spell, maybe no spell
			List<BuyableObject> spells = hero.getOutfit().getSpellCarrying();
			if (spells.size() == 0)
				return null;

			System.out.print("Your spells: ");
			for (BuyableObject s : spells)
				System.out.print(s.getName() + " ");
			System.out.print("\n");
			do {
				System.out.print("Please select from the above spell(s): ");
				str = sc.nextLine();
				Spell s = hero.getOutfit().getSpellWithName(str);
				if (s == null)
					status = 0;
				else {
					status = 1;
					return new String[] { "spell", str };
				}
			} while (status == 0);
		} else if (actionNum == 3) { // use a potion
			List<BuyableObject> potions = hero.getOutfit().getPotionCarrying();
			if (potions.size() == 0)
				return null;

			System.out.print("Your potions: ");
			for (BuyableObject p : potions)
				System.out.print(p.getName() + " ");
			System.out.print("\n");
			do {
				System.out.print("Please select from the above potion(s): ");
				str = sc.nextLine();
				Potion p = hero.getOutfit().getPotionWithName(str);
				if (p == null)
					status = 0;
				else {
					status = 1;
					return new String[] { "potion", str };
				}
			} while (status == 0);
		} else if (actionNum == 4) { // change weapon
			List<BuyableObject> weapons = hero.getOutfit().getWeaponCarrying();
			if (weapons.size() == 0)
				return null;

			System.out.print("Your weapons: ");
			for (BuyableObject w : weapons)
				System.out.print(w.getName() + " ");
			System.out.print("\n");
			do {
				System.out.print("Please select from the above weapon(s): ");
				str = sc.nextLine();
				Weapon w = hero.getOutfit().getWeaponWithName(str);
				if (w == null)
					status = 0;
				else {
					status = 1;
					return new String[] { "change_weapon", str };
				}
			} while (status == 0);
		} else if (actionNum == 5) { // change armor
			List<BuyableObject> armors = hero.getOutfit().getArmorCarrying();
			if (armors.size() == 0)
				return null;

			System.out.print("Your armors: ");
			for (BuyableObject a : armors)
				System.out.print(a.getName() + " ");
			System.out.print("\n");
			do {
				System.out.print("Please select from the above armor(s): ");
				str = sc.nextLine();
				Armor a = hero.getOutfit().getArmorWithName(str);
				if (a == null)
					status = 0;
				else {
					status = 1;
					return new String[] { "change_armor", str };
				}
			} while (status == 0);
		}
		return null;
	}

	private int move(int heroIndex) {
		Scanner sc = new Scanner(System.in);
		String str;
		int actionIndex; // index of action
		int status;
		System.out.println("======================================================");
		System.out.println("W/w: move up");
		System.out.println("A/a: move left");
		System.out.println("S/s: move down");
		System.out.println("D/d: move right");
		System.out.println("B/b: back to nexus");
		System.out.println("Q/q: quit game");
		System.out.println("I/i: display information");
		System.out.println("3. Use a potion");
		System.out.println("4. Change weapon");
		System.out.println("5. Change armor");
		System.out.println("6. Teleport");
		status = 0;
		do {
			System.out.print("Hero "+this.getHeros().get(heroIndex).getName()+", please input the above letter to move: ");
			str = sc.nextLine();

			// input action is 3||4||5
			if ((actionIndex = this.isDigit(str)) > 0 && actionIndex >= 3 && actionIndex <= 5) {
				String[] action = LegendsOfValor.inputActionData(this.getHeros().get(heroIndex), actionIndex);
				if (action != null) {
					this.heroActionInPeace(heroIndex, action);
					status = 1;
				} else {
					System.out.println("You cannot do this!");
				}
			}

			// input action is 6
			else if ((actionIndex = this.isDigit(str)) > 0 && actionIndex == 6) {
				int targetRow, targetCol;
				while (true) {
					System.out.println("Enter the target row: ");
					String strTargetRow = sc.nextLine();
					targetRow = this.isDigit(strTargetRow);
					System.out.println("Enter the target column: ");
					String strTargetCol = sc.nextLine();
					targetCol = this.isDigit(strTargetCol);

					if (0 <= targetRow && targetRow <= this.getBoard().getRow() && 0 <= targetCol
							&& targetCol <= this.getBoard().getCol()) {
						boolean teleportable = this.detectTeleportable(heroIndex,
								this.getBoard().getColOfHero(heroIndex), this.getBoard().getRowOfHero(heroIndex),
								targetCol, targetRow, this.getBoard());
						if (teleportable) {
							// teleport to the target location
							this.getBoard().getHero(heroIndex).teleport(targetCol, targetRow, this.getBoard(),
									heroIndex);
							System.out.println("Hero " + this.getBoard().getHero(heroIndex).getName()
									+ " successfully teleport to location (" + targetRow + "," + targetCol + ")!");
							break;
						} else {
							// cannot teleport
							System.out.println("Cannot teleport to the target position. Please reenter.");
						}
					} else {
						System.out.println("Invalid input. Please reenter.");
					}
				}
				this.getHeros().get(heroIndex).teleport(targetCol, targetRow, this.getBoard(), heroIndex);
				status = 1;

			} else {
				// input action is w||a||s||d||q||i||b
				char c = this.isDirection(str);
				if (c == ' ')
					;
				else if (c == 'q') {
					return 1;
				} else if (c == 'i') {
					this.displayHeros();
				} else if (c == 'b') {
					// return back to nexus
					this.getBoard().getHero(heroIndex).back(this.getBoard(), heroIndex);
					System.out.println(
							"Hero " + this.getBoard().getHero(heroIndex).getName() + " returns back to nexus.");
					status = 1;
				} else {
					int movableHero = this.getBoard().moveOfHero(heroIndex, c);
					if (movableHero != -1) { // 0 for successful move, -1 for cannot access, -2 for monster on the way
						status = 1;
					} else {
						System.out.println("Cannot access this place!");
					}
				}
			}
		} while (status == 0);
		return 0;
	}

	private void heroActionInPeace(int heroi, String[] action) {
		Hero hero = this.getHeros().get(heroi);
		if (action[0].equals("potion")) {
			hero.drinkPotion(action[1]);
			System.out.println("Hero drinks potion!");
			System.out.println(hero.toString());
		} else if (action[0].equals("change_weapon")) {
			hero.changeEquipment("weapon", action[1]);
			System.out.println(hero.toStringInFight());
		} else if (action[0].equals("change_armor")) {
			hero.changeEquipment("armor", action[1]);
			System.out.println(hero.toStringInFight());
		}
	}

	public int purchase(Market mk, int heroIndex) {
		Scanner sc = new Scanner(System.in);
		String str;
		boolean status1 = true;
		int num1 = 0; // buy or sell
		int num3 = 0; // object type

		System.out.println("Welcome, " + this.getHeros().get(heroIndex).getName() + "! You are at the market. ");
		System.out.println("======================================================");
		System.out.println(mk.displayObject());
		System.out.println("======================================================");
		System.out.println("Buy object with full price and sell object with half price. ");

		while (status1) {
			// sell or buy
			int status2 = 0;
			do {
				System.out.print("You want to? 1. buy object; 2. sell object(Type Q/q to quit): ");
				str = sc.nextLine();
				if (str.toLowerCase().equals("q")) {
					status1 = false;
					break;
				}
				if ((num1 = this.isDigit(str)) > 0 && (num1 == 1 || num1 == 2)) {
					status2 = 1;
				}
			} while (status2 == 0);
			// quit?
			if (!status1)
				break;

			// select object type
			System.out.println("======================================================");
			System.out.println("1. Weapon");
			System.out.println("2. Armor");
			System.out.println("3. Spell");
			System.out.println("4. Potion");
			System.out.println("(Type Q/q to quit)");
			status2 = 0;
			do {
				System.out.print("Please select from the above object: ");
				str = sc.nextLine();
				if (str.toLowerCase().equals("q")) {
					status1 = false;
					break;
				}
				if ((num3 = this.isDigit(str)) > 0 && num3 >= 1 && num3 <= 4) {
					status2 = 1;
				}
			} while (status2 == 0);
			// quit?
			if (!status1)
				break;

			// Object to sell
			status2 = 0;
			do {
				Hero hero = this.getHeros().get(heroIndex);
				String type = (num3 == 1 ? "weapon" : (num3 == 2 ? "armor" : (num3 == 3 ? "spell" : "potion")));
				if (num1 == 2) {
					List<BuyableObject> bos = hero.getOutfit().getCarryingWithType(type);
					if (bos.size() == 0) {
						System.out.println("No " + type + "!");
						break;
					} else {
						System.out.println("======================================================");
						for (BuyableObject bo : bos)
							System.out.print(bo.getName() + " ");
						System.out.println("\n======================================================");
					}
				}

				System.out.print("Please input the correct object name(type Q/q to quit): ");
				str = sc.nextLine();
				if (str.toLowerCase().equals("q")) {
					status1 = false;
					break;
				}
				BuyableObject bo;
				if (!mk.getBF().hasObject(type, str)) {
					System.out.println("No such object! ");
					continue; // no such object
				} else
					status2 = 1;
				if (num1 == 1) { // buy
					bo = mk.getBF().createBuyableObject(type, str);
					if (hero.buyObject(bo) >= 0) {
						System.out.println("Hero " + hero.getName() + " buys " + bo.getName() + ". ");
						System.out.println(this.getHeros().get(heroIndex).toString());
					} else {
						System.out.println("Cannot buy this!");
					}
					System.out.println("======================================================");
				} else { // sell
					if (hero.sellObject(type, str) == 0) {
						System.out.println("Hero " + hero.getName() + " sells " + str + ". ");
						System.out.println(this.getHeros().get(heroIndex).toString());
					} else {
						System.out.println("Cannot sell this!");
					}
					System.out.println("======================================================");
				}
			} while (status2 == 0);
			if (!status1)
				break;
		} // while
		return 0;
	}

	private char isDirection(String str) {
		if (str.length() != 1)
			return ' ';
		char c = str.toLowerCase().charAt(0);
		if (c == 'w' || c == 'a' || c == 's' || c == 'd' || c == 'q' || c == 'i' || c == 'b')
			return c;
		else
			return ' ';
	}

	private int isDigit(String string) {
		if (string.matches("\\d+")) {
			int digits = Integer.parseInt(string);
			return digits;
		}
		return -1;
	}

	public void displayHeros() {
		System.out.println("======================================================");
		System.out.println("Hero(s): ");
		System.out.println(this.displayHero());
		System.out.println("======================================================");
	}

	public void fight(Hero hero, Monster monster) {
		int heroIndex = this.getHeros().indexOf(hero);
		int monsterIndex = this.getBoard().getMonsters().indexOf(monster);
		// pass hero and monster
		HeroMonsterFight fight = new HeroMonsterFight(hero, monster);
		int fightStatus = fight.runFight();
		if (fightStatus == 0) {
			// hero wins and remove the monster from board
			this.getBoard().monsterDie(monsterIndex);
		} else if (fightStatus == 1) {
			// hero loses and return back to nexus
			this.getBoard().heroBack(heroIndex);
			// monster wins and go forward by 1
			this.getBoard().moveOfMonster(monsterIndex);
		}
	}

	// input col and row
	// output a list of hero if there is any, null otherwise
	public List<Hero> detectHeros(int col, int row, LegendBoard board) {
		return detectStatus.detectHeros(col, row, board);
	}

	// input col and row
	// output a list of monster if there is any, null otherwise
	public List<Monster> detectMonsters(int col, int row, LegendBoard board) {
		return detectStatus.detectMonsters(col, row, board);
	}

	// return true if it is teleportable else false
	public boolean detectTeleportable(int heroIndex, int heroCol, int heroRow, int col, int row, LegendBoard board) {
		return detectStatus.detectTeleportable(heroIndex, heroCol, heroRow, col, row, board);
	}

	// return true if it is buyable else false
	public boolean detectBuyable(int col, int row, LegendBoard board) {
		return detectStatus.detectBuyable(col, row, board);
	}

	// return 1: heroes win, -1: heroes lose, 0: still fight
	public int detectWinLose(LegendBoard lb) {
		return detectStatus.detectWinLose(lb);
	}

	private List<Hero> selectHero() {
		Scanner sc = new Scanner(System.in);
		String str;
		int status;
		System.out.print("First please select your 3 heros. \n");

		System.out.print(hf.display());
		System.out.print("Please input hero name: \n");
		List<Hero> heros = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			do {
				str = sc.nextLine();
				if (hf.getTypeForHero(str) == null) {
					System.out.print("Please input correct name: \n");
					status = 0;
				} else {
					heros.add(hf.createHero(hf.getTypeForHero(str), str));
					status = 1;
				}
			} while (status == 0);
		}
		return heros;
	}

	private void welcome() {
		System.out.println("Welcome to Legends: Monsters and Heroes!\n");
		System.out.println("This is a magical game full of spells, heroes and monsters.");
		System.out.println("The heroes and monsters live in a world represented by a square grid of fixed dimensions.");
		System.out.println("This world has three types of places to be;");
		System.out.println("Common space (either a safe zone or where heroes come across monsters and fight), ");
		System.out.println("Inaccessible (places the heroes can't go), ");
		System.out.println("and Markets (where items are bought and sold).\n");
		System.out.println("The heroes and monsters do not get along and therefore fight each other.");
		System.out.println("Heroes can use weapons, armors, potions, and spells against the monsters.");
		System.out.println("Every time the heroes win, they gain some experience and some money.");
		System.out.println(
				"When they accumulate enough experience they level up, which means that their skills become stronger.");
		System.out.println("The goal of the game is for the heroes to gain experience and level up indefinitely.\n");
		System.out.println("You can just simply follow the instructions to play the game. ");
		System.out.println("Let's start out journey!\n");
	}
}
