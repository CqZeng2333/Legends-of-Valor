package GameEnviroment;
/*
 * A concrete gameboard for Legends
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Avatars.Hero;
import Avatars.Monster;
import Map.AccessibleTile;
import Map.Board;
import Map.Bush;
import Map.Cave;
import Map.HeroNexus;
import Map.InaccessibleTile;
import Map.Koulou;
import Map.MonsterNexus;
import Map.Plain;

public class LegendBoard extends Board {
	private List<Hero> heros;
	private List<Monster> monsters;
	//inside the list are int array of length 2
	//in each array is the row index and the column index
	private List<int[]> posOfHero, posOfMonster; 
	public LegendBoard(int size) {
		super(size);
		//first row
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				this.board[i][j] = new MonsterNexus();
			}
		}
		//middle row
		Random rd = new Random();
		double value;
		for (int i = 1; i < this.board.length - 1; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				if (j == 2 || j == 5) {
					this.board[i][j] = new InaccessibleTile();
				}
				else{
					value = rd.nextDouble();
					if (Double.compare(value, 0.4) < 0) {
						this.board[i][j] = new Plain();
					}
					else if (Double.compare(value, 0.6) < 0) {
						this.board[i][j] = new Bush();
					}
					else if (Double.compare(value, 0.8) < 0) {
						this.board[i][j] = new Cave();
					}
					else {
						this.board[i][j] = new Koulou();
					}
				}
			}
		}
		//last row
		for (int i = this.board.length - 1; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				this.board[i][j] = new HeroNexus();
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
	
	public void initializeHeroNMonster(List<Hero> hero) {
		//initialize heros
		this.heros = new ArrayList<>();
		Random rd = new Random();
		for (int i = 0; i < hero.size(); i++) {
			//add a hero
			heros.add(hero.get(i));
			
			//get the position of hero
			int[] pos = new int[2];
			pos[0] = this.board.length - 1;
			pos[1] = rd.nextInt(this.board.length) < 0.5 ? 3*i : 3*i+1; //randomly put in the left or right of a lane
			this.posOfHero.add(pos);
			((AccessibleTile) this.board[pos[0]][pos[1]]).stepOn(0, i);
		}
		
		//initialize monsters
		this.monsters = new ArrayList<>();
		int level = this.getHighestLevel();
		for (int i = 0; i < hero.size(); i++) {
			//get the position of monster
			int[] pos = new int[2];
			pos[0] = 0;
			pos[1] = rd.nextInt(this.board.length) < 0.5 ? 3*i : 3*i+1; //randomly put in the left or right of a lane
			this.posOfMonster.add(pos);
			//Monster Nexus create a monster
			Monster monster = (Monster) ((MonsterNexus)this.board[pos[0]][pos[1]]).produceCharacter(level);
			monsters.add(monster);
			((AccessibleTile) this.board[pos[0]][pos[1]]).stepOn(1, i);
		}
	}
	
	/*
	 * Hero i move a pace according to the direction
	 * Return -1 if cannot access
	 * Return -2 if there is a monster on the way
	 * return 0 if successfully move
	 */
	public int moveOfHero(int i, char direction) {
		int status = 0;
		int rowOfHero = this.posOfHero.get(i)[0];
		int colOfHero = this.posOfHero.get(i)[1];
		if (direction == 'w') {
			//on the edge or cannot access
			if (rowOfHero == 0 || !this.board[rowOfHero-1][colOfHero].isAccessible()) return -1;
			//have monster on the way
			if ((colOfHero != 0 && this.board[rowOfHero][colOfHero-1].isAccessible()) && ((AccessibleTile)this.board[rowOfHero][colOfHero-1]).hasMonster()
				|| (colOfHero != this.board.length-1 && this.board[rowOfHero][colOfHero+1].isAccessible()) && ((AccessibleTile)this.board[rowOfHero][colOfHero+1]).hasMonster()
				|| ((AccessibleTile)this.board[rowOfHero][colOfHero]).hasMonster()) return -2;
			//another hero in front
			if (((AccessibleTile)this.board[rowOfHero-1][colOfHero]).hasHero()) return -1;
			
			((AccessibleTile)this.board[rowOfHero][colOfHero]).moveOut(0);
			rowOfHero -= 1;
			this.setRowOfHero(i, rowOfHero);
			status = ((AccessibleTile)this.board[rowOfHero][colOfHero]).stepOn(0, i);
		}
		else if (direction == 'a') {
			//on the edge or cannot access
			if (colOfHero == 0 || !this.board[rowOfHero][colOfHero-1].isAccessible()) return -1;
			//another hero in the left
			if (((AccessibleTile)this.board[rowOfHero][colOfHero-1]).hasHero()) return -1;
			
			((AccessibleTile)this.board[rowOfHero][colOfHero]).moveOut(0);
			colOfHero -= 1;
			status = ((AccessibleTile)this.board[rowOfHero][colOfHero]).stepOn(0, i);
		}
		else if (direction == 's') {
			//on the edge or cannot access
			if (rowOfHero == this.board.length - 1 || !this.board[rowOfHero+1][colOfHero].isAccessible()) return -1;
			//another hero behind
			if (((AccessibleTile)this.board[rowOfHero+1][colOfHero]).hasHero()) return -1;
			
			((AccessibleTile)this.board[rowOfHero][colOfHero]).moveOut(0);
			rowOfHero += 1;
			status = ((AccessibleTile)this.board[rowOfHero][colOfHero]).stepOn(0, i);
		}
		else if (direction == 'd') {
			if (colOfHero == this.board[0].length - 1 || !this.board[rowOfHero][colOfHero+1].isAccessible()) return -1;
			//another hero in the right
			if (((AccessibleTile)this.board[rowOfHero][colOfHero+1]).hasHero()) return -1;
			
			((AccessibleTile)this.board[rowOfHero][colOfHero]).moveOut(0);
			colOfHero += 1;
			status = ((AccessibleTile)this.board[rowOfHero][colOfHero]).stepOn(0, i);
		}
		return status;
	}
	
	/*
	 * Monster i move a pace towards bottom
	 * Return -1 if cannot access
	 * Return -2 if there is a hero to attack
	 * return 0 if successfully move
	 */
	public int moveOfMonster(int i) {
		int status = 0;
		int rowOfMonster = this.posOfMonster.get(i)[0];
		int colOfMonster = this.posOfMonster.get(i)[1];
		
		//on the edge or cannot access
		if (rowOfMonster == this.board.length-1 || !this.board[rowOfMonster-1][colOfMonster].isAccessible()) return -1;
		//have hero in the side cell or in the same cell
		if ((colOfMonster != 0 && this.board[rowOfMonster][colOfMonster-1].isAccessible()) && ((AccessibleTile)this.board[rowOfMonster][colOfMonster-1]).hasHero()
			|| (colOfMonster != this.board.length-1 && this.board[rowOfMonster][colOfMonster+1].isAccessible()) && ((AccessibleTile)this.board[rowOfMonster][colOfMonster+1]).hasHero()
			|| ((AccessibleTile)this.board[rowOfMonster][colOfMonster]).hasHero()) return -2;
		//have hero in the cells behind
		if ((colOfMonster != 0 && this.board[rowOfMonster+1][colOfMonster-1].isAccessible()) && ((AccessibleTile)this.board[rowOfMonster+1][colOfMonster-1]).hasHero()
			|| (colOfMonster != this.board.length-1 && this.board[rowOfMonster-1][colOfMonster+1].isAccessible()) && ((AccessibleTile)this.board[rowOfMonster-1][colOfMonster+1]).hasHero()
			|| (((AccessibleTile)this.board[rowOfMonster+1][colOfMonster]).hasHero())) return -2;
		//another monster behind
		if (((AccessibleTile)this.board[rowOfMonster-1][colOfMonster]).hasMonster()) return -1;
		
		((AccessibleTile)this.board[rowOfMonster][colOfMonster]).moveOut(1);
		rowOfMonster += 1;
		this.setRowOfHero(i, rowOfMonster);
		status = ((AccessibleTile)this.board[rowOfMonster][colOfMonster]).stepOn(1, i);
		return status;
	}
	
	/*
	 * Return a string of the board
	 */
	@Override
	public String toString() {
		final String ANSI_RESET = "\033[0m";
		final String ANSI_BLUE = "\033[0;34m";
		final String ANSI_RED = "\033[0;31m";
		char mk;
		String board = "";
		for (int i = 0; i < this.getRow(); i++) {
			for (int j = 0; j < this.getCol(); j++) {
				mk = this.board[i][j].getMark();
				if (mk == 'N' && i == 0) board += ANSI_RED;
				if (mk == 'N' && i == this.getRow()-1) board += ANSI_BLUE;
				board += mk+"-"+mk+"-"+mk+" ";
				if (mk == 'N') board += ANSI_RESET;
			}
			board += "\n";
			
			for (int j = 0; j < this.getCol(); j++) {
				board += "|";
				if (this.board[i][j].getType().equals("inaccessible_tile")) {
					board += "XXX";
				}
				else {
					if (((AccessibleTile)this.board[i][j]).hasHero()) {
						int hi = ((AccessibleTile)this.board[i][j]).getContainer(0);
						board += ANSI_BLUE + "H" + (hi+1) + ANSI_RESET;
					}
					else {
						board += " ";
					}
					board += " ";
					if (((AccessibleTile)this.board[i][j]).hasMonster()) {
						int mi = ((AccessibleTile)this.board[i][j]).getContainer(1);
						board += ANSI_RED + "M" + (mi+1) + ANSI_RESET;
					}
					else {
						board += " ";
					}
					board += "| ";
				}
			}
			board += "\n";
			
			for (int j = 0; j < this.getCol(); j++) {
				mk = this.board[i][j].getMark();
				if (mk == 'N' && i == 0) board += ANSI_RED;
				if (mk == 'N' && i == this.getRow()-1) board += ANSI_BLUE;
				board += mk+"-"+mk+"-"+mk+" ";
				if (mk == 'N') board += ANSI_RESET;
			}
			board += "\n\n";
		}
		return board;
	}
	
	public int getRowOfHero(int i) {
		return posOfHero.get(i)[0];
	}
	public void setRowOfHero(int i, int rowOfHero) {
		this.posOfHero.get(i)[0] = rowOfHero;
	}
	public int getColOfHero(int i) {
		return posOfHero.get(i)[1];
	}
	public void setColOfHero(int i, int colOfHero) {
		this.posOfHero.get(i)[1] = colOfHero;
	}
	public Hero getHero(int heroIndex) {
		return heros.get(heroIndex);
	}
	public Monster getMonster(int monsterIndex) {
		return monsters.get(monsterIndex);
	}
	public List<int[]> getPosOfMonsters(){
		return this.posOfMonster;
	}
}
