/*
 * Koulou cells buff the strength of any hero who is inside them by 10%. 
 */
package Map;
import Avatars.Hero;

public class Koulou extends AccessibleTile implements Buff {
	public Koulou() {
		super('K', "koulou");
	}

	@Override
	public void buffAbility(Hero hero) {
		hero.getSkills().replace("strength", hero.getSkills().get("strength") * 1.1);
	}

	@Override
	public void removeBuff(Hero hero) {
		hero.getSkills().replace("strength", hero.getSkills().get("strength") / 1.1);
	}
}
