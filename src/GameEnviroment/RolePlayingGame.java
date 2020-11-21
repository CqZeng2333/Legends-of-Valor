package GameEnviroment;
/*
 * An abstract role-playing game
 */
import java.util.List;

import Avatars.Hero;
import Map.Plain;

public abstract class RolePlayingGame {
	private List<Hero> heros;
	private LegendBoard world;
	
	public void initialize(List<Hero> heros, int boardSize) {
		this.heros = heros;
		world = new LegendBoard(boardSize);
		world.initializeHeroNMonster(this.heros);
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
		return world.toString();
	}

	public List<Hero> getHeros() {
		return heros;
	}
	public LegendBoard getWorld() {
		return world;
	}
}
