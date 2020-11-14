/*
 * A concrete gameboard for Legends
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LegendBoard extends Board {
	private List<Hero> heros;
	private int rowOfHero, colOfHero;
	public LegendBoard(int size) {
		super(size);
		
		Random rd = new Random();
		double value;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				value = rd.nextDouble();
				if (Double.compare(value, 0.5) < 0) {
					this.board[i][j] = new CommonTile();
				}
				else if (Double.compare(value, 0.8) < 0) {
					this.board[i][j] = new Market();
				}
				else {
					this.board[i][j] = new InaccessibleTile();
				}
			}
		}
	}
	
	public int getHighestLevel() {
		int level = 0;
		for (Hero h : heros) {
			if (h.getLevel() > level) level = h.getLevel();
		}
		return level;
	}
	
	public void initializeHero(List<Hero> hero) {
		this.heros = new ArrayList<>();
		for (int i = 0; i < hero.size(); i++) {
			heros.add(hero.get(i));
		}
		
		Random rd = new Random();
		do {
			rowOfHero = rd.nextInt(this.board.length);
			colOfHero = rd.nextInt(this.board.length);
			if (this.board[rowOfHero][colOfHero].getType().equals("common_tile")) {
				((CommonTile)this.board[rowOfHero][colOfHero]).stepOn(this.heros.size(), this.getHighestLevel());
			}
		} while (!this.board[rowOfHero][colOfHero].getType().equals("common_tile"));
	}
	
	/*
	 * Move a pace according to the direction
	 * Return -1 if cannot access
	 * return 0 if no-monster common tile or market
	 * return 1 if encounter monster
	 */
	public int move(char direction) {
		int status = 0;
		if (direction == 'w') {
			//on the edge or cannot access
			if (this.rowOfHero == 0 || !this.board[rowOfHero-1][colOfHero].isAccessible()) return -1;
			((AccessibleTile)this.board[rowOfHero][colOfHero]).moveOut();
			this.rowOfHero -= 1;
			status = ((AccessibleTile)this.board[rowOfHero][colOfHero]).stepOn(heros.size(), this.getHighestLevel());
		}
		else if (direction == 'a') {
			if (this.colOfHero == 0 || !this.board[rowOfHero][colOfHero-1].isAccessible()) return -1;
			((AccessibleTile)this.board[rowOfHero][colOfHero]).moveOut();
			this.colOfHero -= 1;
			status = ((AccessibleTile)this.board[rowOfHero][colOfHero]).stepOn(heros.size(), this.getHighestLevel());
		}
		else if (direction == 's') {
			if (this.rowOfHero == this.board.length - 1 || !this.board[rowOfHero+1][colOfHero].isAccessible()) return -1;
			((AccessibleTile)this.board[rowOfHero][colOfHero]).moveOut();
			this.rowOfHero += 1;
			status = ((AccessibleTile)this.board[rowOfHero][colOfHero]).stepOn(heros.size(), this.getHighestLevel());
		}
		else if (direction == 'd') {
			if (this.colOfHero == this.board.length - 1 || !this.board[rowOfHero][colOfHero+1].isAccessible()) return -1;
			((AccessibleTile)this.board[rowOfHero][colOfHero]).moveOut();
			this.colOfHero += 1;
			status = ((AccessibleTile)this.board[rowOfHero][colOfHero]).stepOn(heros.size(), this.getHighestLevel());
		}
		return status;
	}
	
	public int getRowOfHero() {
		return rowOfHero;
	}
	public void setRowOfHero(int rowOfHero) {
		this.rowOfHero = rowOfHero;
	}
	public int getColOfHero() {
		return colOfHero;
	}
	public void setColOfHero(int colOfHero) {
		this.colOfHero = colOfHero;
	}
	
}
