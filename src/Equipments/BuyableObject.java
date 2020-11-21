package Equipments;
/*
 * Buyable object in Legends
 * Including weapon, armor, potion and spell. 
 */
public abstract class BuyableObject {
	private String name;
	private int cost;
	private int requiredlevel;
	private final String type;
	
	public BuyableObject() {this.type = "";};
	public BuyableObject(String name, int cost, int level, String type) {
		this.name = new String(name);
		this.cost = cost;
		this.requiredlevel = level;
		this.type = new String(type);
	}

	/*
	 * To judge whether a hero with specific level and money can buy this object. 
	 * Return -1 if no enough money; 
	 * return -2 if no enough level; 
	 * return -3 if both not enough. 
	 */
	public int buyable(int level, int money) {
		if (level < this.requiredlevel && money < this.cost) return -3;
		else if (level < this.requiredlevel) return -2;
		else if (money < this.cost) return -1;
		else return 1;
	}
	public String getName() {
		return new String(name);
	}
	public void setName(String name) {
		this.name = new String(name);
	}
	public int getLevel() {
		return requiredlevel;
	}
	public void setLevel(int level) {
		this.requiredlevel = level;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getType() {
		return new String(type);
	}
}
