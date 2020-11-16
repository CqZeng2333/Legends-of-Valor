/*
 * A tile of the game board
 */
public abstract class Tile {
	private final boolean accessible;
	private char mark;
	private final String type;
	// a 2-width container, container[0] carrying the index of hero, 
	// and container[1] carrying the index of monster.
	//The value is -1 if no one in it; -2 if the tile is inaccessible
	private int[] container;
	
	public Tile(boolean accessible, char mark, String type) {
		super();
		this.accessible = accessible;
		this.mark = mark;
		this.type = new String(type);
		this.container = new int[2];
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
	public String getType() {
		return type;
	}

	public int getContainer(int who) {
		if (who < container.length)
			return container[who];
		else return -100;
	}
	public int setContainer(int who, int index) {
		if (who < container.length) {
			this.container[who] = index;
			return 0;
		}
		else return -1;
	}
	
	
	
}
