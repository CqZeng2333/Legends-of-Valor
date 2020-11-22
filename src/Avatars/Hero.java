package Avatars;
/*
 * An abstract hero class
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import Equipments.*;
import GameEnviroment.LegendBoard;


public class Hero extends Avatar implements HeroSkillsInterface {
	private String name;
	private int mana;
	private Map<String, Double> skills;
	private int money;
	private int exp;
	
	private int level;
	private int HP;
	private boolean isAlive;
	private Outfit outfit;
	private String type;
	
	public Hero() {}
	public Hero(String name, int mana, double strength, double agility, double dexterity, int money, int exp, String type) {
		super();
		this.name = new String(name);
		this.mana = mana;
		this.skills = new HashMap<>();
		skills.put("strength", strength);
		skills.put("agility", agility);
		skills.put("dexterity", dexterity);
		skills.put("defense", (double) 0);
		this.money = money;
		this.exp = exp;
		
		this.level = 1;
		this.HP = 100 * this.level;
		this.isAlive = true;
		outfit = new Outfit();
		this.type = new String(type);
	}
	
	public int levelUp() {
		if (this.exp < 10 * this.level) return -1;
		while (this.exp >= 10 * this.level) {
			this.exp -= 10 * this.level;
			this.level += 1;
		}
		this.HP = 100 * this.level;
		double value;
		for (String s : skills.keySet()) {
			if (s.equals("strength")||s.equals("dexterity")||s.equals("agility")) {
				value = skills.get(s);
				skills.replace(s, value * 1.05);
			}
		}
		return 0;
	}
	
	public int addHP(int hp) {
		this.HP += hp;
		return 0;
	}
	public int loseHP(int hp) {
		this.HP -= hp;
		if (this.HP <= 0) {
			this.isAlive = false;
			this.HP = 0;
			return -1;
		}
		else return 0;
	}
	
	public int addMana(int mana) {
		this.mana += mana;
		return 0;
	}
	public int loseMana(int mana) {
		if (this.mana >= mana) {
			this.mana -= mana;
			return 0;
		}
		else return -1;
	}
	
	// Recover during each round
	public void recover() {
		this.HP = (int) (1.1 * this.HP);
		this.mana = (int) (1.1 * this.mana);
	}
	
	public int revive() {
		if (this.isAlive == false) {
			this.isAlive = true;
			this.HP = 100 * level;
			return 0;
		}
		else return -1;
	}
	
	public double attack() {
		if (this.outfit.getWeaponEquipped().size() == 0) {
			return skills.get("strength") * 0.05;
		}
		else {
			Weapon myWeapon = (Weapon) this.outfit.getWeaponEquipped().get(0);
			return (skills.get("strength") + myWeapon.causeDamage()) * 0.05;
		}
	}
	
	public double defense() {
		if (this.outfit.getArmorEquipped().size() == 0) {
			return this.skills.get("defense");
		}
		else {
			Armor myArmor = (Armor) this.outfit.getArmorEquipped().get(0);
			return (this.skills.get("defense") + myArmor.reduceDamage()) * 0.05;
		}
	}
	
	public int loseHowMuchHP(double num) {
		if (num < this.defense()) return 0; // cannot hurt the hero
		else return (int) (num - this.defense());
	}
	
	public int beAttacked(double num) {
		double loseBlood = this.loseHowMuchHP(num);
		this.HP -= loseBlood;
		if (this.HP <= 0) {
			this.isAlive = false;
			this.HP = 0;
			return -1;
		}
		else return 0;
	}
	
	public boolean canDodge() {
		double chance = 0.0002 * this.skills.get("agility");
		double num = new Random().nextDouble();
		if (Double.compare(num, chance) < 0) return true;
		else return false;
	}
	
	public double castSpell(String spellName) {
		Spell spell = this.outfit.getSpellWithName(spellName);
		if (spell == null) return -1;
		else {
			if (this.mana < spell.getManaCost()) return -2;
			this.loseMana(spell.getManaCost());
			return spell.causeDamage() * (1 + this.skills.get("dexterity") / 10000);
		}
	}
	public String castSpellSideEffect(String spellName) {
		Spell spell = this.outfit.getSpellWithName(spellName);
		if (spell == null) return null;
		else {
			return spell.getNature();
		}
	}
	
	public int drinkPotion(String potionName) {
		Potion potion = this.outfit.removePotion(potionName);
		if (potion == null) return -1;
		else {
			int n = potion.getNumericIncreased();
			List<String> sks = potion.getSkillsAffected();
			for (String s : sks) {
				if (s.equals("health")) this.addHP(n);
				else if (s.equals("mana")) this.addMana(n);
				else if (s.equals("strength")||s.equals("dexterity")||s.equals("agility")||s.equals("defense"))
					this.skills.replace(s, this.skills.get(s) + n);
			}
			return 0;
		}
	}
	
	/*
	 * The hero buy an object with specific level and money. 
	 * Return -1 if no enough money; 
	 * return -2 if no enough level; 
	 * return -3 if both not enough;
	 * return 0 if successfully buy the object.
	 */
	public int buyObject(BuyableObject bo) {
		int status = bo.buyable(this.level, this.money);
		if (status < 0) return status;
		else {
			this.money -= bo.getCost();
			return this.carryObject(bo);
		}
	}
	
	/*
	 * Add a buyable object to the hero
	 * return 0 if success
	 * return -1 if not correct type
	 */
	private int carryObject(BuyableObject bo) {
		String type = bo.getType();
		if (type.equals("weapon")) {
			return this.outfit.addWeapon((Weapon) bo);
		}
		else if (type.equals("armor")) {
			return this.outfit.addArmor((Armor) bo);
		}
		else if (type.equals("potion")) {
			return this.outfit.addPotion((Potion) bo);
		}
		else if (type.equals("spell")) {
			return this.outfit.addSpell((Spell) bo);
		}
		else return -1;
	}
	
	/*
	 * Sell the object from the hero's outfit
	 * Return 0 if success
	 * return -1 if not correct type
	 * return -2 if no such object
	 */
	public int sellObject(String type, String name) {
		BuyableObject ob;
		if (type.equals("weapon")) {
			ob = this.outfit.removeWeapon(name);
		}
		else if (type.equals("armor")) {
			ob = this.outfit.removeArmor(name);
		}
		else if (type.equals("potion")) {
			ob = this.outfit.removePotion(name);
		}
		else if (type.equals("spell")) {
			ob = this.outfit.removeSpell(name);
		}
		else return -1;
		
		if (ob == null) return -2;
		else {
			money += ob.getCost() / 2;
			return 0;
		}
	}
	
	/*
	 * According to "type"(weapon/armor) to do exchange
	 * Return 0 if success;
	 * return -1 if no such equipment;
	 * return -2 if "type" not weapon or armor
	 */
	public int changeEquipment(String type, String name) {
		if (type.equals("weapon")) {
			return this.outfit.putOnWeapon(name);
		}
		else if (type.equals("armor")) {
			return this.outfit.putOnArmor(name);
		}
		else return -2;
	}
	
	public void teleport(int col, int row, LegendBoard lb, int index) {
		lb.setRowOfHero(index, row);
		lb.setColOfHero(index, col);
		return;
	}
	
	public void back(int col, int row, LegendBoard lb, int index) {
		lb.setRowOfHero(index, row);
		lb.setColOfHero(index, col);
		return;
	}
	
	@Override
	public String toString() {
		return "Name: "+name+" Type: "+type+" Level: "+level+" HP: "+HP
				+" Mana: "+mana+" EXP: "+exp+" Money: "+money
				+" Strength: "+skills.get("strength").intValue()
				+" Agility: "+skills.get("agility").intValue()
				+" Dexterity: "+skills.get("dexterity").intValue();
	}
	
	public String toStringInFight() {
		return "Name: "+name+" Type: "+type+" Level: "+level+" HP: "+HP
				+" Mana: "+mana+" EXP: "+exp+" Money: "+money
				+" Weapon: "+ (outfit.getWeaponEquipped().size() > 0 ? outfit.getWeaponEquipped().get(0).getName() : "None")
				+" Armor: "+ (outfit.getArmorEquipped().size() > 0 ? outfit.getArmorEquipped().get(0).getName() : "None");
	}
	
	// Getter and setter
	public String getName() {
		return new String(name);
	}
	public void setName(String name) {
		this.name = new String(name);
	}
	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	public Map<String, Double> getSkills() {
		return skills;
	}
	public void setSkills(Map<String, Double> skills) {
		this.skills = skills;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public Outfit getOutfit() {
		return outfit;
	}
	public String getType() {
		return type;
	}
	
}
