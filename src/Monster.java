/*
 * An abstract monster class
 */
import java.util.Random;

public abstract class Monster {
	private String name;
	private int level;
	private double damage;
	private double defense;
	private double dodge_chance;
	private String type;
	private boolean isAlive;
	private int HP;
	
	
	public Monster(String name, int level, int damage, int defense, int dodge_chance, String type) {
		super();
		this.name = new String(name);
		this.level = level;
		this.damage = damage;
		this.defense = defense;
		this.dodge_chance = dodge_chance;
		this.type = new String(type);
		this.isAlive = true;
		this.HP = level * 100;
	}

	public double attack() {
		return 0.05 * damage;
	}
	
	public double defense() {
		return 0.05 * defense;
	}
	
	public int loseHowMuchHP(double num) {
		if (num < this.defense()) return 0; // cannot hurt the monster
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
	
	public int beWeaken(String spellType) {
		if (spellType.equals("ice")) {
			this.damage *= 0.9; 
		}
		else if (spellType.equals("fire")) {
			this.defense *= 0.9; 
		}
		else if (spellType.equals("lightning")) {
			this.dodge_chance *= 0.9; 
		}
		else return -1;
		return 0;
	}
	
	public boolean canDodge() {
		double chance = 0.001 * this.dodge_chance;
		double num = new Random().nextDouble();
		if (Double.compare(num, chance) < 0) return true;
		else return false;
	}
	
	@Override
	public String toString() {
		return "Name: "+this.name+" Type: "+this.type+" Level: "+this.level+" HP: "+this.HP+" Alive: "+this.isAlive+" Damage: "+this.damage+" Defense: "+this.defense;
	}
	
	public String getName() {
		return new String(name);
	}
	public void setName(String name) {
		this.name = new String(name);
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int minLevel) {
		this.level = minLevel;
	}
	public double getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public double getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public double getDodge_chance() {
		return dodge_chance;
	}
	public void setDodge_chance(int dodge_chance) {
		this.dodge_chance = dodge_chance;
	}

	public boolean isAlive() {
		return isAlive;
	}
	public String getType() {
		return new String(type);
	}
}
