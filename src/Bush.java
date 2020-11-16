
public class Bush extends AccessibleTile implements Buff {

	public Bush() {
		super('B', "bush");
	}

	@Override
	public void buffAbility(Hero hero) {
		hero.getSkills().replace("dexterity", hero.getSkills().get("dexterity") * 1.1);
	}
	
}
