package Map;

public class InaccessibleTile extends Tile {

	public InaccessibleTile() {
		super(false, 'I', "inaccessible_tile");
		this.setContainer(0, -2);
		this.setContainer(1, -2);
	}
	
}
