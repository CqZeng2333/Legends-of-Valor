/*
 * A factory that read the property of all the hero from txt files
 * and output hero according to the hero type(warrior, warrior or paladin) and name
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class HeroFactory {
	private Map<String, Map<String, Integer>> warrior;
	private Map<String, Map<String, Integer>> sorcerer;
	private Map<String, Map<String, Integer>> paladin;
	
	public HeroFactory() {
		warrior = new HashMap<>();
		sorcerer = new HashMap<>();
		paladin = new HashMap<>();
		
		this.readHero(this.warrior, this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Warriors.txt");
		this.readHero(this.sorcerer, this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Sorcerers.txt");
		this.readHero(this.paladin, this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Paladins.txt");
	}
	
	private void readHero(Map<String, Map<String, Integer>> hero, String address) {
		BufferedReader br = null;
		String str;
		String[] menu;
		String[] table;
		Map<String, Integer> attributes;
		try {
			br = new BufferedReader(new FileReader(address));
			//read menu
			str = br.readLine();
			str = new String(str.getBytes("GBK"),"UTF-8");
			menu = str.split("/");
			//read table
			while ((str = br.readLine()) != null) {
				table = str.split("\\s+");
				attributes = new HashMap<>(); // A map for attributes for one single weapon/armor
				for (int i = 1; i < table.length; i++) {
					attributes.put(menu[i], Integer.parseInt(table[i]));
				}
				hero.put(table[0], attributes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getTypeForHero(String name) {
		for (String s: warrior.keySet()) {
			if (s.equals(name)) return "warrior";
		}
		for (String s: sorcerer.keySet()) {
			if (s.equals(name)) return "sorcerer";
		}
		for (String s: paladin.keySet()) {
			if (s.equals(name)) return "paladin";
		}
		return null;
	}
	
	/*
	 * Produce an hero according to the hero type and name
	 */
	public Hero createHero(String type, String name) {
		Hero hero = null;
		Map<String, Integer> attr;
		if (type.equals("warrior")) {
			attr = this.warrior.get(name);
			hero = new Warrior(name, attr.get("mana"), attr.get("strength"), attr.get("agility"), attr.get("dexterity"), attr.get("starting money"), attr.get("starting experience"));
		}
		else if (type.equals("sorcerer")) {
			attr = this.sorcerer.get(name);
			hero = new Sorcerer(name, attr.get("mana"), attr.get("strength"), attr.get("agility"), attr.get("dexterity"), attr.get("starting money"), attr.get("starting experience"));
		}
		else if (type.equals("paladin")) {
			attr = this.paladin.get(name);
			hero = new Paladin(name, attr.get("mana"), attr.get("strength"), attr.get("agility"), attr.get("dexterity"), attr.get("starting money"), attr.get("starting experience"));
		}
		return hero;
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
		str += "Warrior: \n" ;
		str += this.readFile(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Warriors.txt");
		str += "Sorcerer: \n" ;
		str += this.readFile(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Sorcerers.txt");
		str += "Paladin: \n" ;
		str += this.readFile(this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Paladins.txt");
		return str;
	}
}
