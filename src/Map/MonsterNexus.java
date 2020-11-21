package Map;
import Avatars.Avatar;

/*
 * The Nexus for monster with the function of monster born
 */
public class MonsterNexus extends Plain implements Nexus {
	private MonsterFactory mf = new MonsterFactory();

	public Avatar produceCharacter(int level) {
		return mf.createMonsterWithLevel(level);
	}

	@Override
	public Avatar produceCharacter(String name) {
		return mf.createMonsterWithName(mf.getTypeForMonster(name), name);
	}
}
