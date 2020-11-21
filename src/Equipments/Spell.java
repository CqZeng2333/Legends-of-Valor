package Equipments;

/*
 * A concrete class of spell
 * Can cost a specific amount of mana to cause certain damage
 */
public class Spell extends BuyableObject {
	private int damage;
	private int manaCost;
	private String nature;
	
	public Spell(String name, int cost, int level, int damage, int manaCost, String nature) {
		super(name, cost, level, "spell");
		this.damage = damage;
		this.manaCost = manaCost;
		this.nature = new String(nature);
	}

	public int causeDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getManaCost() {
		return manaCost;
	}
	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}
	public String getNature() {
		return new String(nature);
	}
	public void setNature(String nature) {
		this.nature = new String(nature);
	}
}
