/*
 * An abstract game
 */
package GameEnviroment;

public abstract class Game {
	private String gameName;

	public Game() {
		gameName = "game";
	}
	public Game(String gameName) {
		this.gameName = new String(gameName);
	}
	public String getGameName() {
		return new String(gameName);
	}
	public void setGameName(String gameName) {
		this.gameName = new String(gameName);
	}
}
