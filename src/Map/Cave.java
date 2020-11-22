/*
 * Cave cells boost the agility of any hero who is inside them by 10%. 
 */
package Map;
import Avatars.Hero;

public class Cave extends AccessibleTile implements Buff {
	public Cave() {
		super('C', "cave");
	}

	@Override
	public void buffAbility(Hero hero) {
		hero.getSkills().replace("agility", hero.getSkills().get("agility") * 1.1);
	}

	@Override
	public void removeBuff(Hero hero) {
		hero.getSkills().replace("agility", hero.getSkills().get("agility") / 1.1);
	}
}
