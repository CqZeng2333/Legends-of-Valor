package Equipments;

/*
 * A concrete class of weapon
 * Can reduce a a specific amount of damage
 */
public class Armor extends BuyableObject {
	private int damageReduction;
	
	public Armor(String name, int cost, int level, int damageReduction) {
		super(name, cost, level, "armor");
		this.damageReduction = damageReduction;
	}
	
	public int reduceDamage() {
		return damageReduction;
	}
	public void setDamage(int damage) {
		this.damageReduction = damage;
	}
}
