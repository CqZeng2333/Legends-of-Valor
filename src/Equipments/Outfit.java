package Equipments;
import java.util.ArrayList;
import java.util.List;

/*
 * All the buyable objects that a hero is equipped or carrying
 */
public class Outfit {
	private List<BuyableObject> weaponEquipped;
	private List<BuyableObject> weaponCarrying;
	private List<BuyableObject> armorEquipped;
	private List<BuyableObject> armorCarrying;
	private List<BuyableObject> potionCarrying;
	private List<BuyableObject> spellCarrying;
	
	public Outfit() {
		weaponEquipped = new ArrayList<>();
		weaponCarrying = new ArrayList<>();
		armorEquipped = new ArrayList<>();
		armorCarrying = new ArrayList<>();
		potionCarrying = new ArrayList<>();
		spellCarrying = new ArrayList<>();
	}
	
	public int addWeapon(Weapon newWeapon) {
		weaponCarrying.add(newWeapon);
		return 0;
	}
	
	public int takeOffWeapon() {
		if (weaponEquipped.size() == 0) return -1;
		else {
			for (int i = 0; i < weaponEquipped.size(); i++) {
				weaponCarrying.add(weaponEquipped.get(i));
				weaponEquipped.remove(i);
			}
			return 0;
		}
	}
	
	public int putOnWeapon(String newWeaponName) {
		Weapon w;
		for (int i = 0; i < weaponCarrying.size(); i++) {
			if (weaponCarrying.get(i).getName().equals(newWeaponName)) {
				w = (Weapon) weaponCarrying.get(i); //get the weapon from the carrying
				//Equipped no weapon now
				if (weaponEquipped.size() == 0) {
					weaponEquipped.add(w); //equip the weapon
				}
				//already equipped with a weapon
				else {
					//remove the weapon equipped at present
					this.takeOffWeapon();
					//equip the new weapon
					weaponEquipped.add(w);
				}
				weaponCarrying.remove(i);
				return 0;
			}
		}
		return -1;
	}
	
	public Weapon removeWeapon(String weaponName) {
		Weapon w;
		for (int i = 0; i < weaponCarrying.size(); i++) {
			if (weaponCarrying.get(i).getName().equals(weaponName)) {
				w = (Weapon) weaponCarrying.get(i);
				weaponCarrying.remove(i);
				return w;
			}
		}
		return null;
	}
	
	public int addArmor(Armor newArmor) {
		armorCarrying.add(newArmor);
		return 0;
	}
	
	public int takeOffArmor() {
		if (armorEquipped.size() == 0) return -1;
		else {
			for (int i = 0; i < armorEquipped.size(); i++) {
				armorCarrying.add(armorEquipped.get(i));
				armorEquipped.remove(i);
			}
			return 0;
		}
	}
	
	public int putOnArmor(String newArmorName) {
		Armor a;
		for (int i = 0; i < armorCarrying.size(); i++) {
			if (armorCarrying.get(i).getName().equals(newArmorName)) {
				a = (Armor) armorCarrying.get(i); //get the armor from the carrying
				//Equipped no armor now
				if (armorEquipped.size() == 0) {
					armorEquipped.add(a); //equip the armor
				}
				//already equipped with an armor
				else {
					//remove the armor equipped at present
					this.takeOffArmor();
					//equip the new armor
					armorEquipped.add(a);
				}
				armorCarrying.remove(i);
				return 0;
			}
		}
		return -1;
	}
	
	public Armor removeArmor(String armorName) {
		Armor a;
		for (int i = 0; i < armorCarrying.size(); i++) {
			if (armorCarrying.get(i).getName().equals(armorName)) {
				a = (Armor) armorCarrying.get(i);
				armorCarrying.remove(i);
				return a;
			}
		}
		return null;
	}
	
	public int addPotion(Potion newPotion) {
		potionCarrying.add(newPotion);
		return 0;
	}
	
	public Potion removePotion(String potionName) {
		Potion p;
		for (int i = 0; i < potionCarrying.size(); i++) {
			if (potionCarrying.get(i).getName().equals(potionName)) {
				p = (Potion) potionCarrying.get(i);
				potionCarrying.remove(i);
				return p;
			}
		}
		return null;
	}
	
	public int addSpell(Spell newSpell) {
		spellCarrying.add(newSpell);
		return 0;
	}
	
	public Spell removeSpell(String spellName) {
		Spell s;
		for (int i = 0; i < spellCarrying.size(); i++) {
			if (spellCarrying.get(i).getName().equals(spellName)) {
				s = (Spell) spellCarrying.get(i);
				spellCarrying.remove(i);
				return s;
			}
		}
		return null;
	}

	// Getters
	public List<BuyableObject> getWeaponEquipped() {
		return weaponEquipped;
	}
	public List<BuyableObject> getWeaponCarrying() {
		return weaponCarrying;
	}
	public List<BuyableObject> getArmorEquipped() {
		return armorEquipped;
	}
	public List<BuyableObject> getArmorCarrying() {
		return armorCarrying;
	}
	public List<BuyableObject> getPotionCarrying() {
		return potionCarrying;
	}
	public List<BuyableObject> getSpellCarrying() {
		return spellCarrying;
	}
	
	public Weapon getWeaponWithName(String name) {
		for (BuyableObject w : this.weaponCarrying) {
			if (w.getName().equals(name)) return (Weapon) w;
		}
		return null;
	}
	public Armor getArmorWithName(String name) {
		for (BuyableObject a : this.armorCarrying) {
			if (a.getName().equals(name)) return (Armor) a;
		}
		return null;
	}
	public Spell getSpellWithName(String name) {
		for (BuyableObject s : this.spellCarrying) {
			if (s.getName().equals(name)) return (Spell) s;
		}
		return null;
	}
	public Potion getPotionWithName(String name) {
		for (BuyableObject p : this.potionCarrying) {
			if (p.getName().equals(name)) return (Potion) p;
		}
		return null;
	}
	
	public List<BuyableObject> getCarryingWithType(String type) {
		if (type.equals("weapon")) {
			return this.weaponCarrying;
		}
		else if (type.equals("armor")) {
			return this.armorCarrying;
		}
		else if (type.equals("potion")) {
			return this.potionCarrying;
		}
		else if (type.equals("spell")) {
			return this.spellCarrying;
		}
		else return null;
	}
}
