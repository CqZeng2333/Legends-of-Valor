/*
 * A concrete class of weapon
 * Can cause a a specific amount of damage
 */
public class Weapon extends BuyableObject {
	private int damage;
	private int requiredHand;
	
	public Weapon(String name, int cost, int level, int damage, int requiredHand) {
		super(name, cost, level, "weapon");
		this.damage = damage;
		this.requiredHand = requiredHand;
	}
	
	public int causeDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getRequiredHand() {
		return requiredHand;
	}
	public void setRequiredHand(int requiredHand) {
		this.requiredHand = requiredHand;
	}
}
