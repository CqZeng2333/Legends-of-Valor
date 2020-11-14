import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommonTile extends AccessibleTile {
	private boolean hasMonster;
	private List<Monster> monsters;

	public CommonTile() {
		super(' ', false, "common_tile");
		monsters = null;
	}
	
	/*
	 * Roll a dice for 75% chance to meet monsters
	 */
	private boolean rollDice() {
		double chance = 0.75; // 75% chance to meet monsters in this tile
		double num = new Random().nextDouble();
		if (Double.compare(num, chance) < 0) {
			return true;
		}
		else return false;
	}
	
	/*
	 * The tile is stepped on by several heros with a highest level
	 * Return 1 if they encounter monsters
	 * return 0 if this is a no-monster tile
	 */
	@Override
	public int stepOn(int numOfHero, int level) {
		this.setHasHero(true);
		if (this.rollDice()) {
			this.hasMonster = true;
			monsters = new ArrayList<>();
			MonsterFactory mf = new MonsterFactory();
			for (int i = 0; i < numOfHero; i++) {
				monsters.add(mf.createMonsterWithLevel(level));
			}
			return 1;
		}
		else return 0;
	}
	@Override
	public void moveOut() {
		this.setHasHero(false);
		this.removeMonster();
	}
	
	private int removeMonster() {
		this.monsters = null;
		this.hasMonster = false;
		return 0;
	}
	
	
	public List<Monster> getMonsters() {
		return monsters;
	}
	public boolean hasMonster() {
		return hasMonster;
	}
}
