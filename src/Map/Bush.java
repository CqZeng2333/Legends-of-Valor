/*
 * Bush cells increase the dexterity of any hero who is inside them by 10%.
 */

package Map;
import Avatars.Hero;

public class Bush extends AccessibleTile implements Buff {

	public Bush() {
		super('B', "bush");
	}

	@Override
	public void buffAbility(Hero hero) {
		hero.getSkills().replace("dexterity", hero.getSkills().get("dexterity") * 1.1);
	}

	@Override
	public void removeBuff(Hero hero) {
		hero.getSkills().replace("dexterity", hero.getSkills().get("dexterity") / 1.1);
	}
	
}
