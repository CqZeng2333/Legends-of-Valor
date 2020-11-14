import java.util.ArrayList;
import java.util.List;

public class Sorcerer extends Hero {
	private List<String> favoredAbilitly;
	
	public Sorcerer(String name, int mana, double strength, double agility, double dexterity, int money, int exp) {
		super(name, mana, strength, agility, dexterity, money, exp, "sorcerer");
		this.favoredAbilitly = new ArrayList<>();
		this.favoredAbilitly.add("dexterity");
		this.favoredAbilitly.add("agility");
	}

	/*
	 * Get 1.1 times increase of his favoring ability
	 */
	@Override
	public int levelUp() {
		int status = super.levelUp();
		if (status < 0) return status;
		else {
			double value;
			for (String s : this.favoredAbilitly) {
				value = this.getSkills().get(s);
				this.getSkills().replace(s, value / 1.05 * 1.1);
			}
			return status;
		}
	}
}
