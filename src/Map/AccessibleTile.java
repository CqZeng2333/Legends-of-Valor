package Map;
/*
 * An accessible tile for common tile or market
 */
public abstract class AccessibleTile extends Tile {
	private boolean hasHero;
	private boolean hasMonster;
	
	public AccessibleTile(char mark, String type) {
		super(true, mark, type);
		this.hasHero = false;
		this.hasMonster = false;
		this.setContainer(0, -1);
		this.setContainer(1, -1);
	}
	public boolean hasHero() {
		return hasHero;
	}
	public void setHasHero(boolean hasHero) {
		this.hasHero = hasHero;
	}
	public boolean hasMonster() {
		return hasMonster;
	}
	public void setHasMonster(boolean hasMonster) {
		this.hasMonster = hasMonster;
	}
	
	/*
	 * The tile is stepped on by a hero or a monster with its index
	 * 0 for hero, and 1 for monster
	 * Return 0 if they successfully step on
	 * return -1 if not success
	 */
	public int stepOn(int heroOrMonster, int index) {
		if (heroOrMonster == 0) {
			if (this.getContainer(0) != -1) return -1;
			
			this.setHasHero(true);
			this.setContainer(0, index);
			return 0;
		}
		else if (heroOrMonster == 1) {
			if (this.getContainer(1) != -1) return -1;
			
			this.setHasMonster(true);
			this.setContainer(1, index);
			return 0;
		}
		return -1;
	}
	
	/*
	 * The tile is moved out by a hero or a monster with its index
	 * 0 for hero, and 1 for monster
	 * Return 0 if they successfully move out
	 * return -1 if not success
	 */
	public int moveOut(int heroOrMonster) {
		if (heroOrMonster == 0) {
			if (this.getContainer(0) == -1) return -1;
			
			this.setHasHero(false);
			this.setContainer(0, -1);
			return 0;
		}
		else if (heroOrMonster == 1) {
			if (this.getContainer(1) == -1) return -1;
			
			this.setHasMonster(true);
			this.setContainer(1, -1);
			return 0;
		}
		return -1;
	}
}
