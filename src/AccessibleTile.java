/*
 * An accessible tile for common tile or market
 */
public abstract class AccessibleTile extends Tile {
	public AccessibleTile(char mark, boolean hasHero, String type) {
		super(true, mark, hasHero, type);
	}
	
	public abstract int stepOn(int numOfHero, int level);
	public abstract void moveOut();
}
