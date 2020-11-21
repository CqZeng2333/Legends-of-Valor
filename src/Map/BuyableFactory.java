package Map;
/*
 * A factory that read the property of all the buyable object from txt files
 * and output buyable objects according to the object type(weapon, armor, potion or spell) and name
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import Equipments.*;

public class BuyableFactory {
	private Map<String, Map<String, Integer>> weapon;
	private Map<String, Map<String, Integer>> armor;
	private Map<String, Map<String, Integer>> potion;
	private Map<String, String[]> potionAfftectedSkills; //the skills that affected by the potion
	private Map<String, Map<String, Integer>> spell;
	private Map<String, String> spellNature; // the nature property of spell(ice, fire or lightning)
	
	public BuyableFactory() {
		weapon = new HashMap<>();
		armor = new HashMap<>();
		potion = new HashMap<>();
		potionAfftectedSkills = new HashMap<>();
		spell = new HashMap<>();
		spellNature = new HashMap<>();
		
		this.readWeapon(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Weaponry.txt");
		this.readArmor(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Armory.txt");
		this.readPotion(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Potions.txt");
		this.readSpell(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/IceSpells.txt", "ice");
		this.readSpell(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/FireSpells.txt", "fire");
		this.readSpell(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/LightningSpells.txt", "lightning");
	}
	
	private void readWeapon(String address) {
		BufferedReader br = null;
		String str;
		String[] menu;
		String[] table;
		Map<String, Integer> attributes;
		try {
			br = new BufferedReader(new FileReader(address));
			//read menu
			str = br.readLine();
			menu = str.split("/");
			//read table
			while ((str = br.readLine()) != null) {
				table = str.split("\\s+");
				attributes = new HashMap<>(); // A map for attributes for one single weapon
				for (int i = 1; i < table.length; i++) {
					attributes.put(menu[i], Integer.parseInt(table[i]));
				}
				weapon.put(table[0], attributes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void readArmor(String address) {
		BufferedReader br = null;
		String str;
		String[] menu;
		String[] table;
		Map<String, Integer> attributes;
		try {
			br = new BufferedReader(new FileReader(address));
			//read menu
			str = br.readLine();
			menu = str.split("/");
			//read table
			while ((str = br.readLine()) != null) {
				table = str.split("\\s+");
				attributes = new HashMap<>(); // A map for attributes for one single armor
				for (int i = 1; i < table.length; i++) {
					attributes.put(menu[i], Integer.parseInt(table[i]));
				}
				armor.put(table[0], attributes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void readPotion(String address) {
		BufferedReader br = null;
		String str;
		String[] menu;
		String[] table;
		String[] skills;
		Map<String, Integer> attributes;
		try {
			br = new BufferedReader(new FileReader(address));
			//read menu
			str = br.readLine();
			menu = str.split("/");
			//read table
			while ((str = br.readLine()) != null) {
				table = str.split("\\s+");
				attributes = new HashMap<>(); // A map for attributes for one single potion
				for (int i = 1; i < table.length - 1; i++) {
					attributes.put(menu[i], Integer.parseInt(table[i]));
				}
				potion.put(table[0], attributes);
				//put in the skills affected by the potion
				skills = table[table.length - 1].split("/");
				for (int i = 0; i < skills.length; i++) skills[i] = skills[i].toLowerCase();
				this.potionAfftectedSkills.put(table[0], skills);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void readSpell(String address, String nature) {
		BufferedReader br = null;
		String str;
		String[] menu;
		String[] table;
		Map<String, Integer> attributes;
		try {
			br = new BufferedReader(new FileReader(address));
			//read menu
			str = br.readLine();
			menu = str.split("/");
			//read table
			while ((str = br.readLine()) != null) {
				table = str.split("\\s+");
				attributes = new HashMap<>(); // A map for attributes for one single spell
				for (int i = 1; i < table.length; i++) {
					attributes.put(menu[i], Integer.parseInt(table[i]));
				}
				this.spell.put(table[0], attributes);
				//put in the nature of the spell
				this.spellNature.put(table[0], nature);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasObject(String type, String name) {
		Map<String, Integer> attr = null;
		if (type.equals("weapon")) {
			attr = this.weapon.get(name);
		}
		else if (type.equals("armor")) {
			attr = this.armor.get(name);
		}
		else if (type.equals("potion")) {
			attr = this.potion.get(name);
		}
		else if (type.equals("spell")) {
			attr = this.spell.get(name);
		}
		return attr != null ? true : false;
	}
	
	/*
	 * Produce a buyable object according to the object type and name
	 */
	public BuyableObject createBuyableObject(String type, String name) {
		BuyableObject bo = null;
		Map<String, Integer> attr;
		if (type.equals("weapon")) {
			attr = this.weapon.get(name);
			bo = new Weapon(name, attr.get("cost"), attr.get("level"), attr.get("damage"), attr.get("required hands"));
		}
		else if (type.equals("armor")) {
			attr = this.armor.get(name);
			bo = new Armor(name, attr.get("cost"), attr.get("required level"), attr.get("damage reduction"));
		}
		else if (type.equals("potion")) {
			attr = this.potion.get(name);
			bo = new Potion(name, attr.get("cost"), attr.get("required level"), attr.get("attribute increase"), this.potionAfftectedSkills.get(name));
		}
		else if (type.equals("spell")) {
			attr = this.spell.get(name);
			bo = new Spell(name, attr.get("cost"), attr.get("required level"), attr.get("damage"), attr.get("mana cost"), this.spellNature.get(name));
		}
		return bo;
	}
	
	private String readFile(String address) {
		BufferedReader br = null;
		String str;
		String strs = "";
		try {
			br = new BufferedReader(new FileReader(address));
			while ((str = br.readLine()) != null) {
				strs += str + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}
	
	public String display() {
		String str = "";
		str += "Weapon: \n" ;
		str += this.readFile(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Weaponry.txt");
		str += "\nArmor: \n" ;
		str += this.readFile(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Armory.txt");
		str += "\nPotion: \n" ;
		str += this.readFile(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Potions.txt");
		str += "\nIce Spell: \n" ;
		str += this.readFile(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/IceSpells.txt");
		str += "\nFire Spell: \n" ;
		str += this.readFile(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/FireSpells.txt");
		str += "\nLightning Spell: \n" ;
		str += this.readFile(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/LightningSpells.txt");
		return str;
	}

	public Map<String, Map<String, Integer>> getWeapon() {
		return weapon;
	}
	public Map<String, Map<String, Integer>> getArmor() {
		return armor;
	}
	public Map<String, Map<String, Integer>> getPotion() {
		return potion;
	}
	public Map<String, Map<String, Integer>> getSpell() {
		return spell;
	}
}
