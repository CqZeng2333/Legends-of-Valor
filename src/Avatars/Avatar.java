package Avatars;

import Equipments.Weapon;

public abstract class Avatar {
	protected String name;
	protected int level;
	protected int HP;
	protected boolean isAlive;
	protected String type;
	
	
	public Avatar(String name, String type, int level, int HP) {
		this.name = new String(name);
		this.type = new String(type);
		this.level = level;
		this.HP = HP;
		this.isAlive = true;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public int getLevel() {
		return level;
	}
	public int getHP() {
		return HP;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public abstract double attack();
	
	public abstract int beAttacked(double num);
}