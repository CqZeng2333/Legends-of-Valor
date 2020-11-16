
public class Cave extends AccessibleTile implements Buff {
	public Cave() {
		super('C', "cave");
	}

	@Override
	public void buffAbility(Hero hero) {
		hero.getSkills().replace("agility", hero.getSkills().get("agility") * 1.1);
	}
}
