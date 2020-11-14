import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterFactory {
	private Map<String, Map<String, Integer>> dragon;
	private Map<String, Map<String, Integer>> exoskeleton;
	private Map<String, Map<String, Integer>> spirit;
	private Map<Integer, List<String>> monsterWithLevel; // Map level and according monsters 
	
	public MonsterFactory() {
		dragon = new HashMap<>();
		exoskeleton = new HashMap<>();
		spirit = new HashMap<>();
		monsterWithLevel = new HashMap<>();
		
		this.readHero(this.dragon, this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Dragons.txt");
		this.readHero(this.exoskeleton, this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Exoskeletons.txt");
		this.readHero(this.spirit, this.getClass().getClassLoader().getResource("").getPath()+"/Legends_Monsters_and_Heroes/Spirits.txt");
		this.createMonsterList();
	}
	
	private void readHero(Map<String, Map<String, Integer>> monster, String address) {
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
				attributes = new HashMap<>(); // A map for attributes for one single weapon/armor
				for (int i = 1; i < table.length; i++) {
					attributes.put(menu[i], Integer.parseInt(table[i]));
				}
				monster.put(table[0], attributes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createMonsterList() {
		for (int i = 1; i <= 10; i++) monsterWithLevel.put(i, new ArrayList<>());
		
		int level;
		for (String s: dragon.keySet()) {
			level = dragon.get(s).get("level");
			this.monsterWithLevel.get(level).add(s);
		}
		for (String s: exoskeleton.keySet()) {
			level = exoskeleton.get(s).get("level");
			this.monsterWithLevel.get(level).add(s);
		}
		for (String s: spirit.keySet()) {
			level = spirit.get(s).get("level");
			this.monsterWithLevel.get(level).add(s);
		}
	}
	
	/*
	 * Produce a monster according to the monster type and name
	 */
	public Monster createMonsterWithName(String type, String name) {
		Monster monster = null;
		Map<String, Integer> attr;
		if (type.equals("dragon")) {
			attr = this.dragon.get(name);
			monster = new Dragon(name, attr.get("level"), attr.get("damage"), attr.get("defense"), attr.get("dodge chance"));
		}
		else if (type.equals("exoskeleton")) {
			attr = this.exoskeleton.get(name);
			monster = new Exoskeleton(name, attr.get("level"), attr.get("damage"), attr.get("defense"), attr.get("dodge chance"));
		}
		else if (type.equals("spirit")) {
			attr = this.spirit.get(name);
			monster = new Spirit(name, attr.get("level"), attr.get("damage"), attr.get("defense"), attr.get("dodge chance"));
		}
		return monster;
	}
	
	/*
	 * Produce a monster according to monster's level randomly
	 */
	public Monster createMonsterWithLevel(int level) {
		Monster monster = null;
		List<String> mList = this.monsterWithLevel.get(level);
		int i = (int) (Math.random() * mList.size());
		String name = mList.get(i);
		
		Map<String, Integer> attr;
		if (this.dragon.get(name) != null) {
			attr = this.dragon.get(name);
			monster = new Dragon(name, attr.get("level"), attr.get("damage"), attr.get("defense"), attr.get("dodge chance"));
		}
		else if (this.exoskeleton.get(name) != null) {
			attr = this.exoskeleton.get(name);
			monster = new Exoskeleton(name, attr.get("level"), attr.get("damage"), attr.get("defense"), attr.get("dodge chance"));
		}
		else if (this.spirit.get(name) != null) {
			attr = this.spirit.get(name);
			monster = new Spirit(name, attr.get("level"), attr.get("damage"), attr.get("defense"), attr.get("dodge chance"));
		}
		return monster;
	}
}
