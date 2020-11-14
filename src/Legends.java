/*
 * A concrete role-playing game of Legends
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Legends extends RolePlayingGame {
	private HeroFactory hf = new HeroFactory();

	@Override
	public void initialize(List<Hero> heros, int boardSize) {
		super.initialize(heros, boardSize);
	}
	
	private List<Hero> selectHero() {
		Scanner sc = new Scanner(System.in);
		String str;
		int status;
		System.out.print("First please select your hero(s). \n");
		System.out.print("Please input how many heros you want(1~3): ");
		int numOfHero = 0;
		do {
			str = sc.nextLine();
			status = str.equals("1") ? 1 : (str.equals("2") ? 1 : (str.equals("3") ? 1 : 0));
			if (status == 0) System.out.print("Please input correct number: ");
			else numOfHero = Integer.parseInt(str);
		} while (status == 0);
		
		System.out.print(hf.display());
		System.out.print("Please input hero name: \n");
		List<Hero> heros = new ArrayList<>();
		for (int i = 0; i < numOfHero; i++) {
			do {
				str = sc.nextLine();
				if (hf.getTypeForHero(str) == null) {
					System.out.print("Please input correct name: \n");
					status = 0;
				}
				else {
					heros.add(hf.createBuyableObject(hf.getTypeForHero(str), str));
					status = 1;
				}
			} while (status == 0);
		}
		return heros;
	}
	
	/*
	 * Figure out whether a string is a digit.
	 * PostCondition: return the corresponding number if the string is number, -1 if not number
	 */
	private int isDigit(String string) {
		if (string.matches("\\d+")) {
			int digits = Integer.parseInt(string);
			return digits;
		}
		return -1;
	}
	
	private String[] inputActionData (Hero hero, int actionNum) {
		Scanner sc = new Scanner(System.in);
		String str;
		int status;
		
		if (actionNum == 1) { // attack
			return new String[] {"attack"};
		}
		else if (actionNum == 2) {// cast a spell, maybe no spell
			List<BuyableObject> spells = hero.getOutfit().getSpellCarrying();
			if (spells.size() == 0) return null;
			
			System.out.print("Your spells: ");
			for (BuyableObject s : spells) System.out.print(s.getName() + " ");
			System.out.print("\n");
			do {
				System.out.print("Please select from the above spell(s): ");
				str = sc.nextLine();
				Spell s = hero.getOutfit().getSpellWithName(str);
				if (s == null) status = 0;
				else {
					status = 1;
					return new String[] {"spell", str};
				}
			} while (status == 0);
		}
		else if (actionNum == 3) { // use a potion
			List<BuyableObject> potions = hero.getOutfit().getPotionCarrying();
			if (potions.size() == 0) return null;
			
			System.out.print("Your potions: ");
			for (BuyableObject p : potions) System.out.print(p.getName() + " ");
			System.out.print("\n");
			do {
				System.out.print("Please select from the above potion(s): ");
				str = sc.nextLine();
				Potion p = hero.getOutfit().getPotionWithName(str);
				if (p == null) status = 0;
				else {
					status = 1;
					return new String[] {"potion", str};
				}
			} while (status == 0);
		}
		else if (actionNum == 4) { // change weapon
			List<BuyableObject> weapons = hero.getOutfit().getWeaponCarrying();
			if (weapons.size() == 0) return null;
			
			System.out.print("Your weapons: ");
			for (BuyableObject w : weapons) System.out.print(w.getName() + " ");
			System.out.print("\n");
			do {
				System.out.print("Please select from the above weapon(s): ");
				str = sc.nextLine();
				Weapon w = hero.getOutfit().getWeaponWithName(str);
				if (w == null) status = 0;
				else {
					status = 1;
					return new String[] {"change_weapon", str};
				}
			} while (status == 0);
		}
		else if (actionNum == 5) { // change armor
			List<BuyableObject> armors = hero.getOutfit().getArmorCarrying();
			if (armors.size() == 0) return null;
			
			System.out.print("Your armors: ");
			for (BuyableObject a : armors) System.out.print(a.getName() + " ");
			System.out.print("\n");
			do {
				System.out.print("Please select from the above armor(s): ");
				str = sc.nextLine();
				Armor a = hero.getOutfit().getArmorWithName(str);
				if (a == null) status = 0;
				else {
					status = 1;
					return new String[] {"change_armor", str};
				}
			} while (status == 0);
		}
		return null;
	}

	@Override
	public int fight(CommonTile tile) {
		Scanner sc = new Scanner(System.in);
		String str;
		int num;
		int status;
		final String ANSI_RESET = "\033[0m";
		final String ANSI_BLUE = "\033[0;34m";
		final String ANSI_RED = "\033[0;31m";
		
		Fight fight = new Fight(this.getHeros(), ((CommonTile)tile).getMonsters());
		while (fight.whoWin() == 0) {
			int[] hmpair = fight.selectFightPair();
			Hero hero = this.getHeros().get(hmpair[0]);
			Monster monster = tile.getMonsters().get(hmpair[1]);
			System.out.println("======================================================");
			System.out.println("Hero "+ANSI_BLUE+hero.getName()+ANSI_RESET+" VS Monster "+ANSI_RED+monster.getName()+ANSI_RESET);
			
			// Hero turn
			System.out.println("1. Regular Attack");
			System.out.println("2. Cast a spell");
			System.out.println("3. Use a potion");
			System.out.println("4. Change weapon");
			System.out.println("5. Change armor");
			System.out.println("6. Display information");
			status = 0;
			do {
				System.out.print("Please select from the above function: ");
				str = sc.nextLine();
				if ((num = this.isDigit(str)) > 0 && num >= 1 && num <= 6) {
					if (num == 6) {
						System.out.println("======================================================");
						System.out.println(ANSI_BLUE+"Hero"+ANSI_RESET+": ");
						System.out.println(hero.toStringInFight());
						System.out.println(ANSI_RED+"Monster"+ANSI_RESET+": ");
						System.out.println(monster.toString());
						System.out.println("======================================================");
					}
					else {
						String[] action = this.inputActionData(hero, num);
						if (action != null) {
							fight.heroTurn(hmpair[0], hmpair[1], action);
							status = 1;
						}
						else {
							System.out.println("You cannot do this!");
						}
					}
				}
			} while (status == 0);
			if (fight.whoWin() != 0) break;
			
			// Monster turn
			fight.monsterTurn(hmpair[0], hmpair[1]);
			// hero gains back some HP and mana after each round;
			if (hero.isAlive()) hero.recover();
		}
		int result = fight.whoWin();
		if (result == 1) System.out.println(ANSI_BLUE+"Hero(s)"+ANSI_RESET+" win!");
		else if (result == -1) System.out.println(ANSI_RED+"Monster(s)"+ANSI_RESET+" win!");
		return result;
	}
	
	public void settle(boolean win, int money, int exp) {
		final String ANSI_RESET = "\033[0m";
		final String ANSI_BLUE = "\033[0;34m";
		final String ANSI_RED = "\033[0;31m";
		// Heros win
		if (win) {
			for (Hero h : this.getHeros()) {
				// winning and alive
				if (h.isAlive()) {
					h.setMoney(h.getMoney()+money);
					h.setExp(h.getExp()+exp);
					
					String type = h.getType();
					int success = -1;
					if (type.equals("warrior")) {
						success = ((Warrior)h).levelUp();
					}
					else if (type.equals("sorcerer")) {
						success = ((Sorcerer)h).levelUp();
					}
					else if (type.equals("paladin")) {
						success = ((Paladin)h).levelUp();
					}
					if (success == 0) System.out.println("Hero "+ANSI_BLUE+h.getName()+ANSI_RESET+" levels up!");
				}
				else {
					h.revive();
				}
			} //for
		} // if
		// Heros lose
		else {
			for (Hero h : this.getHeros()) {
				if (!h.isAlive()) {
					h.revive();
				}
				h.setMoney(h.getMoney() / 2);
			} //for
		}
	}
	
	private char isDirection(String str) {
		if (str.length() != 1) return ' ';
		char c = str.toLowerCase().charAt(0);
		if (c == 'w' || c == 'a' || c == 's' || c == 'd' || c == 'q' || c == 'i') return c;
		else return ' ';
	}
	
	public void displayHeros() {
		System.out.println("======================================================");
		System.out.println("Hero(s): ");
		System.out.println(this.displayHero());
		System.out.println("======================================================");
	}
	
	private void heroActionInPeace(int heroi, String[] action) {
		Hero hero = this.getHeros().get(heroi);
		if (action[0].equals("potion")) {
			hero.drinkPotion(action[1]);
			System.out.println("Hero drinks potion!");
			System.out.println(hero.toString());
		}
		else if (action[0].equals("change_weapon")) {
			hero.changeEquipment("weapon", action[1]);
			System.out.println(hero.toStringInFight());
		}
		else if (action[0].equals("change_armor")) {
			hero.changeEquipment("armor", action[1]);
			System.out.println(hero.toStringInFight());
		}
	}
	
	private int move() {
		Scanner sc = new Scanner(System.in);
		String str;
		int num, num2;
		int status, status2;
		System.out.println("======================================================");
		System.out.println("W/w: move up");
		System.out.println("A/a: move left");
		System.out.println("S/s: move down");
		System.out.println("D/d: move right");
		System.out.println("Q/q: quit game");
		System.out.println("I/i: display information");
		System.out.println("3. Use a potion");
		System.out.println("4. Change weapon");
		System.out.println("5. Change armor");
		status = 0;
		do {
			System.out.print("Please input the above letter to move: ");
			str = sc.nextLine();
			
			// 3||4||5
			if ((num = this.isDigit(str)) > 0 && num >= 3 && num <= 5) {
				//select hero
				System.out.println("======================================================");
				for (int i = 0; i < this.getHeros().size(); i++) {
					System.out.println((i+1)+". "+this.getHeros().get(i).getName());
				}
				System.out.println("======================================================");
				status2 = 0;
				do {
					System.out.print("Please select from the above hero(s): ");
					str = sc.nextLine();
					if ((num2 = this.isDigit(str)) > 0 && num2 >= 1 && num2 <= this.getHeros().size()) {
						num2 -= 1; //index of hero
						status2 = 1;
					}
				} while (status2 == 0);
			
				String[] action = this.inputActionData(this.getHeros().get(num2), num);
				if (action != null) {
					this.heroActionInPeace(num2, action);
				}
				else {
					System.out.println("You cannot do this!");
				}
			}
			
			// w||a||s||d||q||i
			char c = this.isDirection(str);
			if (c == ' ') ;
			else if (c == 'q') return 1;
			else if (c == 'i') {
				this.displayHeros();
			}
			else {
				if (super.move(c) < 0) System.out.println("Cannot access this place!");
				else {
					System.out.println(this.getWorld());
					status = 1;
				}
			}
		} while (status == 0);
		return 0;
	}
	
	public int purchase(Market mk) {
		Scanner sc = new Scanner(System.in);
		String str;
		boolean status1 = true;
		int num1 = 0; //buy or sell
		int num2 = 0; //the index of hero
		int num3 = 0; //object type
		
		System.out.println("You are at the market. ");
		System.out.println("======================================================");
		System.out.println(mk.displayObject());
		System.out.println("======================================================");
		System.out.println("Buy object with full price and sell object with half price. ");
		this.displayHeros();
		
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
			if (!status1) break;
			
			//select hero
			System.out.println("======================================================");
			for (int i = 0; i < this.getHeros().size(); i++) {
				System.out.println((i+1)+". "+this.getHeros().get(i).getName());
			}
			System.out.println("(Type Q/q to quit)");
			status2 = 0;
			do {
				System.out.print("Please select from the above hero(s): ");
				str = sc.nextLine();
				if (str.toLowerCase().equals("q")) {
					status1 = false;
					break;
				}
				if ((num2 = this.isDigit(str)) > 0 && num2 >= 1 && num2 <= this.getHeros().size()) {
					num2 -= 1; //index of hero
					status2 = 1;
				}
			} while (status2 == 0);
			// quit?
			if (!status1) break;
			
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
			if (!status1) break;
			
			// Object to sell
			status2 = 0;
			do {
				Hero hero = this.getHeros().get(num2);
				String type = (num3 == 1 ? "weapon" : (num3 == 2 ? "armor" : (num3 == 3 ? "spell" : "potion")));
				if (num1 == 2) {
					List<BuyableObject> bos = hero.getOutfit().getCarryingWithType(type);
					if (bos.size() == 0) {
						System.out.println("No "+type+"!");
						break;
					}
					else {
						System.out.println("======================================================");
						for (BuyableObject bo : bos) System.out.print(bo.getName()+" ");
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
					continue; //no such object
				}
				else status2 = 1;
				if (num1 == 1) { //buy
					bo = mk.getBF().createBuyableObject(type, str);
					if (hero.buyObject(bo) >= 0) {
						System.out.println("Hero "+hero.getName()+" buys "+bo.getName()+". ");
						this.displayHeros();
					}
					else {
						System.out.println("Cannot buy this!");
					}
					System.out.println("======================================================");
				}
				else { //sell
					if (hero.sellObject(type, str) == 0) {
						System.out.println("Hero "+hero.getName()+" sells "+str+". ");
						this.displayHeros();
					}
					else {
						System.out.println("Cannot sell this!");
					}
					System.out.println("======================================================");
				}
			} while (status2 == 0);
			if (!status1) break;
		} // while
		return 0;
	}
	
	@Override
	public void run() {
		// print welcome message
		this.welcome();
		// select heros
		List<Hero> heros = this.selectHero();
		// initialize the game
		this.initialize(heros, 8);
		System.out.println(this.getWorld());
		
		do {
			Tile heroOn = this.getWorld().getAGrid(this.getWorld().getRowOfHero(), this.getWorld().getColOfHero());
			String type = heroOn.getType();
			if (type.equals("common_tile")) {
				if (((CommonTile)heroOn).hasMonster()) { // encounter monster
					System.out.println("You encounter a fight!");
					int result = this.fight((CommonTile)heroOn);
					if (result == 1) { // win
						this.settle(true, 100 * ((CommonTile)heroOn).getMonsters().get(0).getLevel(), 2);
					}
					else { // lose
						this.settle(false, 0, 0);
					}
				}
				else {
					System.out.println("This is a safe place!");
				}
				
			}
			else if (type.equals("market")) {
				this.purchase((Market)heroOn);
			}
			// move on
			
			System.out.println("======================================================");
			System.out.println(this.getWorld());
			int status = this.move();
			if (status == 1) {
				System.out.println("You quit the game!");
				System.exit(0); // quit
			}
		} while (true);
		
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
		System.out.println("When they accumulate enough experience they level up, which means that their skills become stronger.");
		System.out.println("The goal of the game is for the heroes to gain experience and level up indefinitely.\n");
		System.out.println("You can just simply follow the instructions to play the game. ");
		System.out.println("Let's start out journey!\n");
	}
}
