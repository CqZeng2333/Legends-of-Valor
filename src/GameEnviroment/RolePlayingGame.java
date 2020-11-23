package GameEnviroment;
/*
 * An abstract role-playing game
 */
import java.util.List;

import Avatars.Hero;
import Map.LegendBoard;
import Map.Plain;

public abstract class RolePlayingGame extends Game {
	private List<Hero> heros;
	private LegendBoard board;
	
	public RolePlayingGame() {
		super("role_playing_game");
	}
	
	public void initialize(List<Hero> heros, int boardSize) {
		this.heros = heros;
		board = new LegendBoard(boardSize);
		board.initializeHeroNMonster(this.heros);
	}
	
//	public abstract void run();
//	public abstract int fight();
//	
//	public abstract int action();
	
	public String displayHero() {
		String str = "";
		for (int i = 0; i < heros.size(); i++) {
			str += heros.get(i).toString() + "\n";
		}
		return str;
	}
	public String displayWorld() {
		return board.toString();
	}

	public List<Hero> getHeros() {
		return heros;
	}
	public LegendBoard getBoard() {
		return board;
	}
}
