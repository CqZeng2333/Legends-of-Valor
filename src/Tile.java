/*
 * A tile of the game board
 */
public abstract class Tile {
	private final boolean accessible;
	private char mark;
	private boolean hasHero;
	private final String type;
	
	public Tile(boolean accessible, char mark, boolean hasHero, String type) {
		super();
		this.accessible = accessible;
		this.mark = mark;
		this.hasHero = hasHero;
		this.type = new String(type);
	}
	
	public boolean isAccessible() {
		return accessible;
	}
	public char getMark() {
		return mark;
	}
	public void setMark(char mark) {
		this.mark = mark;
	}
	public boolean hasHero() {
		return hasHero;
	}
	public void setHasHero(boolean hasHero) {
		this.hasHero = hasHero;
	}
	public String getType() {
		return type;
	}
	
	
}
